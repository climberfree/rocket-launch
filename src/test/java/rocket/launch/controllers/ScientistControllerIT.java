package rocket.launch.controllers;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import rocket.launch.App;
import rocket.launch.domain.Scientist;
import rocket.launch.dto.LoginResponse;
import rocket.launch.repositories.LoginHistoryRepository;
import rocket.launch.repositories.ScientistRepository;
import rocket.launch.stubs.EntityStubs;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static rocket.launch.stubs.EntityStubs.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class ScientistControllerIT {

    @LocalServerPort
    private int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ScientistRepository repo;

    @Autowired
    private LoginHistoryRepository historyRepository;

    private String URL;
    private static final String REGISTRATION_REQUEST = "{\"email\":\"elonmusk@spacex.com\",\"password\":\"OfCourseIStillLoveYouPlatform\",\"name\": \"Elon\",\"surname\":\"Musk\"}";
    private static final String LOGIN_REQUEST = "{\"email\":\"elonmusk@spacex.com\",\"password\":\"OfCourseIStillLoveYouPlatform\"}";

    @Before
    public void setUp() {
        repo.deleteAll();
        historyRepository.deleteAll();
        URL = "http://localhost:" + randomServerPort;
    }

    @After
    public void cleanup() {
        repo.deleteAll();
        historyRepository.deleteAll();
    }

    @Test
    public void registrateNewScientistSuccessfully() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(REGISTRATION_REQUEST, headers);

        restTemplate.postForObject(URL + "/scientists/registrate", request, String.class);

        Optional<Scientist> scientistActual = repo.findByEmail(EMAIL);

        assertThat(scientistActual.isPresent()).isTrue();
        Scientist sc = scientistActual.get();
        assertThat(EMAIL).isEqualTo(sc.getEmail());
        assertThat(PASSWORD).isEqualTo(sc.getPassword());
        assertThat(UNAME).isEqualTo(sc.getName());
        assertThat(SURNAME).isEqualTo(sc.getSurname());
    }

    @Test
    public void loginActivitySuccessfully() {

        Scientist scientistExpected = EntityStubs.getScientist();
        repo.save(scientistExpected);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(LOGIN_REQUEST, headers);

        LoginResponse loginResponse = restTemplate.postForObject(URL + "/scientists/login", request, LoginResponse.class);

        assertThat(loginResponse).isNotNull();
        assertThat(scientistExpected.getEmail()).isEqualTo(loginResponse.getEmail());
        assertThat("SUCCESS").isEqualTo(loginResponse.getStatus());

        ResponseEntity<List<LoginResponse>> historyResponse =
                restTemplate.exchange(URL + "/scientists/loginHistory?email=elonmusk@spacex.com",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<LoginResponse>>() {
                        });


        assertThat(HttpStatus.OK).isEqualTo(historyResponse.getStatusCode());
        List<LoginResponse> responses = historyResponse.getBody();
        assertThat(responses).isNotEmpty();
        LoginResponse actualHistory = responses.get(0);
        assertThat(scientistExpected.getEmail()).isEqualTo(actualHistory.getEmail());
        assertThat("SUCCESS").isEqualTo(actualHistory.getStatus());
    }
}