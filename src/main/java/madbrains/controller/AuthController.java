package madbrains.controller;

import lombok.extern.slf4j.Slf4j;
import madbrains.component.JwtProvider;
import madbrains.controller.authComponents.AuthRequest;
import madbrains.controller.authComponents.AuthResponce;
import madbrains.controller.authComponents.RegistrationRequest;
import madbrains.model.User;
import madbrains.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@Slf4j
@RestController
public class AuthController {
    @Autowired
    private SecurityService securityService;
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegistrationRequest registrationRequest) {
        try {
            User user = new User();
            user.setPassword(registrationRequest.getPassword());
            user.setLogin(registrationRequest.getLogin());
            securityService.addUser(user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.info("Error at creating user: this user have been already registered " + registrationRequest);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/auth")
    public AuthResponce auth(@RequestBody AuthRequest request) {
        try {
            User user = securityService.getByUsernamePassword(request.getLogin(), request.getPassword());
            String token = jwtProvider.genericToken(user.getLogin());
            return new AuthResponce(token);
        } catch (Exception e) {
            return new AuthResponce("BAD_REQUEST");
        }
    }
}
