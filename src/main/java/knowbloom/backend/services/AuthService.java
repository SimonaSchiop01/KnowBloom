package knowbloom.backend.services;

import knowbloom.backend.constants.MemberConstants;
import knowbloom.backend.constants.PendingTeacherConstants;
import knowbloom.backend.constants.UserConstants;
import knowbloom.backend.dtos.requests.*;
import knowbloom.backend.enums.Role;
import knowbloom.backend.exceptions.ConflictException;
import knowbloom.backend.mappers.*;
import knowbloom.backend.models.*;
import knowbloom.backend.security.JwtService;
import knowbloom.backend.security.SecurityService;
import knowbloom.backend.security.TokenService;
import knowbloom.backend.services.data.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
   // 1. Nu folosim userRepository aici, folosim userDataService
   // 2. Nu folosim pendingTeacherRepository aici, folosim pendingTeacherDataService
   // 3. Redenumim ApplicationMapper in PendingTeacherMapper
   // 4. Trebuie sa hash-ui parola
   // 5. Trebuie sa trimitem un email profului, sa-l anuntam ca contul sau va fi activat de catre un administrator

   private final UserDataService userDataService;
   private final PendingTeacherMapper pendingTeacherMapper;
   private final PendingMemberDataService pendingMemberDataService;
   private final PendingTeacherDataService pendingTeacherDataService;

   private final MemberDataService memberDataService;

   private final TeacherDataService teacherDataService;

   private final PendingMemberMapper pendingMemberMapper;

   private final PasswordEncoder passwordEncoder; // injectat automat de Spring

   private final EmailService emailService;

   private final TokenService tokenService;

   private final RoleDataService roleDataService;

   private final MemberMapper memberMapper;

   private final TeacherMapper teacherMapper;

   private final AuthenticationManager authenticationManager;

   private final SecurityService securityService;

   private final JwtService jwtService;

//   contul profesorului trebuie activat de un admin, nu si cel al membrului care il activeaza singur de pe email
   @Transactional
   public void register(RegisterRequestDto registerRequestDto){
      String email = registerRequestDto.getEmail();
      String phoneNumber = registerRequestDto.getPhoneNumber();

      if(this.userDataService.existsByEmail(email)){
         throw new ConflictException(UserConstants.ALREADY_EXISTS_BY_EMAIL);
      }

      if(this.userDataService.existsByPhoneNumber(phoneNumber)){
         throw new ConflictException(UserConstants.ALREADY_EXISTS_BY_PHONE_NUMBER);
      }

      if(this.pendingMemberDataService.existsByEmail(email)){
         throw new ConflictException(MemberConstants.ALREADY_EXISTS_BY_EMAIL);
      }

      if(this.pendingMemberDataService.existsByPhoneNumber(phoneNumber)){
         throw new ConflictException(MemberConstants.ALREADY_EXISTS_BY_PHONE_NUMBER);
      }

      String hashedPassword = this.passwordEncoder.encode(registerRequestDto.getPassword());

      PendingMemberModel pendingMemberModel = this.pendingMemberMapper.toModel(registerRequestDto);

      pendingMemberModel.setPassword(hashedPassword);

      String token = this.tokenService.generateActivateToken(email);

      this.emailService.sendActivationEmail(email, token);

      System.out.println(token);

      this.pendingMemberDataService.save(pendingMemberModel);
   }

   @Transactional
   public void activate(ActivateRequestDto requestDto){
      log.info("Activate pending member with token {}", requestDto.getToken());

      String token = requestDto.getToken();

      String email = this.tokenService.validateActivateToken(token);

      PendingMemberModel pendingMemberModel = this.pendingMemberDataService.findByEmail(email);

      MemberModel memberModel = this.memberMapper.toModel(pendingMemberModel);

      RoleModel roleModel = this.roleDataService.findByName(Role.MEMBER);

      memberModel.addRole(roleModel);

      System.out.println(memberModel);

      // apelat memberDataService.create(memberModel)
      this.memberDataService.create(memberModel);

      UUID id = pendingMemberModel.getId();

      // apelat pendingMemberDataService.delete(pendingMemberModel)
      this.pendingMemberDataService.removeById(id);
   }

   public void apply(ApplyRequestDto applyRequestDto){
      // aici nu mai generam token, pur si simplu creezi un pending teacher, dar fara token

      String email = applyRequestDto.getEmail();

      if(this.pendingTeacherDataService.existsByEmail(email)){
         throw new ConflictException(PendingTeacherConstants.ALREADY_EXISTS_BY_EMAIL);
      }

      if(this.userDataService.existsByEmail(email)){
         throw new ConflictException(UserConstants.ALREADY_EXISTS_BY_EMAIL);
      }

      String hashedPassword = this.passwordEncoder.encode(applyRequestDto.getPassword());

      PendingTeacherModel pendingTeacherModel = this.pendingTeacherMapper.toModel(applyRequestDto);

      pendingTeacherModel.setPassword(hashedPassword);

      this.pendingTeacherDataService.create(pendingTeacherModel);
   }

   @Transactional
   public void accept(AcceptRequestDto requestDto) {
      // Sa faci activarea contului dupa id, adica pur si simplu, creezi un cont de teacher cu detaliile
      // luate din pending teacher model si dupa stergi pending teacher model

      log.info("Activate pending teacher with id {}", requestDto.getPendingTeacherId());

      UUID pendingTeacherId = requestDto.getPendingTeacherId();

      PendingTeacherModel pendingTeacherModel = this.pendingTeacherDataService.findById(pendingTeacherId);

      System.out.println(pendingTeacherModel.toString());

      TeacherModel teacherModel = this.teacherMapper.toModel(pendingTeacherModel);

      System.out.println(teacherModel);

      RoleModel roleModel = this.roleDataService.findByName(Role.TEACHER);

      teacherModel.addRole(roleModel);

      System.out.println(teacherModel);

      this.teacherDataService.create(teacherModel);

      this.pendingTeacherDataService.removeById(pendingTeacherId);
   }

   @Transactional
   public String login(AuthLoginRequestDto requestDto){
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
           requestDto.getEmail(),
           requestDto.getPassword()
      );

      Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

      SecurityContextHolder.getContext().setAuthentication(authentication);

      return this.tokenService.generateAccessToken(authentication);
   }

   public void forgotPassword(AuthForgotPasswordRequestDto requestDto){
      String email = requestDto.getEmail();

      log.info("Attempting to reset password for emsail: {}", email);

      UserModel userModel = this.userDataService.findByEmail(email);

      UUID userId = userModel.getId();

      String token = this.tokenService.generateResetPasswordToken(email, userId);

      System.out.println(token);

      this.emailService.sendForgotPasswordEmail(email, token);

      log.info("Successfully sent reset password email to: {}", email);

   }

   @Transactional
   public void resetPassword(AuthResetPasswordRequestDto requestDto){
      String token = requestDto.getToken();
      String newPassword = requestDto.getNewPassword();

      String email = this.tokenService.validateResetPasswordToken(token);

      UserModel userModel = this.userDataService.findByEmail(email);

      String hashedPassword = this.passwordEncoder.encode(newPassword);

      userModel.setPassword(hashedPassword);
   }

   @Transactional
   public void changeEmail(ChangeEmailRequestDto requestDto) {
      String newEmail = requestDto.getNewEmail();

      UserModel userModel = this.securityService.getModelCurrentUser();

      String userEmail = userModel.getEmail();

      String token = this.jwtService.generateChangeEmailToken(userEmail, newEmail);

      System.out.println(token);

      this.emailService.sendChangeEmail(userEmail, token);

      // eyJhbGciOiJIUzUxMiJ9.eyJuZXdFbWFpbCI6ImVsbGVuYUB5YWhvby5jb20iLCJ0eXBlIjoiQ0hBTkdFX0VNQUlMIiwiY3VycmVudEVtYWlsIjoic3RzX2VsbGVuYUB5YWhvby5jb20iLCJzdWIiOiI1OGZiNDAxOS1kMDc3LTQ0YWMtODc0ZS0xYmQ4MDlmYWY4NWUiLCJpYXQiOjE3NTUwMTM1NzUsImV4cCI6MTc1NTA5OTk3NX0.lYRkRLntXKxJS2RQVGZvxJgN69p14P2vOxf6nrL2UMM-J4h81PU3MxuAOyqVwCf28FOAXXCyr6dOffEN_5i3rw
   }

   @Transactional
   public void confirmChangeEmail(ConfirmChangeEmailRequestDto requestDto){ // in Dto asta tre' sa ai token
         log.info("Confirm change email"); // ramane

         String token = requestDto.getToken(); // ramane

         String email = this.tokenService.validateChangeEmail(token); // ramane

         UserModel userModel = this.userDataService.findByEmail(email);

         String newEmail = this.tokenService.getNewEmailFromToken(token);

         userModel.setEmail(newEmail);
   }

   @Transactional
   public void changePassword(ChangePasswordRequestDto requestDto) {
      String newPassword = requestDto.getNewPassword();

      UserModel userModel = this.securityService.getModelCurrentUser();

      String userEmail = userModel.getEmail();

      String hashedPassword = this.passwordEncoder.encode(newPassword);

      String token = this.jwtService.generateChangePasswordToken(userEmail, hashedPassword);

      System.out.println(token);

      this.emailService.sendChangePassword(userEmail, token);

   }

   @Transactional
   public void confirmChangePassword(ConfirmChangePasswordRequestDto requestDto){
      log.info("Confirm change password");

      String token = requestDto.getToken();

      String email = this.tokenService.validateChangePassword(token); // ramane

      UserModel userModel = this.userDataService.findByEmail(email);

      String newPassword = this.tokenService.getNewPasswordFromToken(token);

      userModel.setPassword(newPassword);
   }

}