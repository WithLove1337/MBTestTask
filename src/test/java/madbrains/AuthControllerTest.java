package madbrains;

import com.fasterxml.jackson.databind.ObjectMapper;

import madbrains.component.JwtFilter;
import madbrains.component.JwtProvider;
import madbrains.controller.*;
import madbrains.controller.authComponents.AuthRequest;
import madbrains.controller.authComponents.RegistrationRequest;
import madbrains.model.Role;
import madbrains.model.User;
import madbrains.service.SecurityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
@ActiveProfiles("authtest")
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SecurityService securityService;

    @MockBean
    private JwtFilter jwtFilter;

    @MockBean
    private JwtProvider jwtProvider;

    @Test
    void shouldRegister() throws Exception {
        RegistrationRequest rr = new RegistrationRequest();
        rr.setLogin("login");
        rr.setPassword("password");
        this.mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(rr)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRegisterWithBadRequest() throws Exception {
        RegistrationRequest user = new RegistrationRequest();
        this.mockMvc.perform(post("/register"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldAuth() throws Exception {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsb2dpbiIsImV4cCI6MTYxNzQ4MDAwMH0.2R38sR1gKhe4XrdVI3_EDk4UoP_QgDI4kZqFsY7RQrqBww3Xu8eQyr8d0Dc6wnnfU7uWQ5IiJYYL3wgWRPIrGA";

        Role role = new Role();
        role.setId(2);
        role.setName("ROLE_USER");

        User user = new User();
        user.setId(1);
        user.setLogin("login");
        user.setPassword("password");
        user.setRole(role);

        AuthRequest ar = new AuthRequest();
        ar.setLogin("login");
        ar.setPassword("password");

        SecurityService securityService = mock(SecurityService.class);
        when(securityService.getByUsernamePassword("login", "password")).thenReturn(user);

        JwtProvider jwtProvider = mock(JwtProvider.class);
        when(jwtProvider.genericToken(user.getLogin())).thenReturn(token);


        this.mockMvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ar)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("token", is(token)));
    }
}
