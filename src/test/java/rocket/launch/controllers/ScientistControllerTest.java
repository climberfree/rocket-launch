package rocket.launch.controllers;

import org.assertj.core.api.Assertions;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import rocket.launch.domain.LoginStatus;
import rocket.launch.dto.LoginResponse;
import rocket.launch.exceptions.UserNotFoundException;
import rocket.launch.services.ScientistService;

import java.time.LocalDateTime;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
public class ScientistControllerTest {

    @MockBean
    private ScientistService scientistService;

    @Autowired
    ScientistController scientistController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenUserControllerInjected_thenNotNull() throws Exception {
        Assertions.assertThat(scientistController).isNotNull();
    }

    @Test
    public void whenPostRequestToCreate_thenCreateScientistResponse() throws Exception {
        String scientistRequest = "{\"name\": \"Elon\", \"email\" : \"elon@gmail.com\", \"surname\" : \"Musk\", \"password\" : \"password\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/scientists/registrate")
                .content(scientistRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void loginSuccessfully() throws Exception {
        when(scientistService.login(ArgumentMatchers.any())).thenReturn(LoginResponse.builder()
                .email("elon@gmail.com")
                .status(LoginStatus.SUCCESS.name())
                .loginAt(LocalDateTime.now()).build()
        );
        String loginRequest = "{\"email\" : \"elon@gmail.com\", \"password\" : \"password\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/scientists/login")
                .content(loginRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Is.is("elon@gmail.com")));
    }

    @Test
    public void whenLoginWithNotExistedUser_thenBadRequestReponse() throws Exception {
        when(scientistService.login(ArgumentMatchers.any())).thenThrow(new UserNotFoundException("User not found with email " + "elon@gmail.com"));

        String loginRequest = "{\"email\" : \"elon@gmail.com\", \"password\" : \"password\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/scientists/login")
                .content(loginRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    public void whenRequestLoginHistory_thenSuccessReponse() throws Exception {
        when(scientistService.loginHistories(ArgumentMatchers.any()))
                .thenReturn(singletonList(LoginResponse.builder().email("elon@gmail.com")
                        .loginAt(LocalDateTime.now()).status(LoginStatus.SUCCESS.name()).build()));

        mockMvc.perform(MockMvcRequestBuilders.get("/scientists/loginHistory?email=elon@gmail.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void whenLoginHistoryForNotExistedUser_thenBadRequestReponse() throws Exception {
        when(scientistService.loginHistories(ArgumentMatchers.any()))
                .thenThrow(new UserNotFoundException("User not found with email " + "elon@gmail.com"));

        mockMvc.perform(MockMvcRequestBuilders.get("/scientists/loginHistory?email=elon2@gmail.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()));
    }

}
