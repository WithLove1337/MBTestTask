package madbrains;

import com.fasterxml.jackson.databind.ObjectMapper;

import madbrains.component.JwtFilter;
import madbrains.component.JwtProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import madbrains.controller.ComparatorController;
import madbrains.model.MyComparator;
import madbrains.service.ComparatorService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ComparatorController.class)
@ActiveProfiles("comparatortest")
public class ComparatorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ComparatorService comparatorService;

    @MockBean
    private JwtFilter jwtFilter;

    @MockBean
    private JwtProvider jwtProvider;

    private List<MyComparator> myComparatorList;

    @Test
    void shouldAdd() throws Exception {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTYxNzQ4MDAwMH0.tyderbCROc_NmVX9JudWIHRYqhrVqbyap7ebXzK4JtIVa5Lng2gn0fOieaMzAG-PRcuanlXEk6xtqfTqJ-EZXg";
        String code = "testCode";

        this.mockMvc.perform(post("/user/comparator/add")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(code)))
                .andExpect(status().isOk());
    }

    @Test
    void addWithBadRequest() throws Exception {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTYxNzQ4MDAwMH0.tyderbCROc_NmVX9JudWIHRYqhrVqbyap7ebXzK4JtIVa5Lng2gn0fOieaMzAG-PRcuanlXEk6xtqfTqJ-EZXg";

        this.mockMvc.perform(post("/user/comparator/add")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isBadRequest());
    }
}
