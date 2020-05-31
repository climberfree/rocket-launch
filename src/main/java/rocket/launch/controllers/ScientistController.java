package rocket.launch.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rocket.launch.dto.LoginRequest;
import rocket.launch.dto.LoginResponse;
import rocket.launch.dto.ScientistDto;
import rocket.launch.services.ScientistService;

import java.util.List;

@RestController
@RequestMapping("/scientists")
public class ScientistController {

    Logger log = LoggerFactory.getLogger(ScientistController.class);
    @Autowired
    private ScientistService scientistService;

    @PostMapping(value = "/registrate")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity registrate(@RequestBody ScientistDto dto) {
        log.info("Registrating of scientist " + dto.getEmail());
        scientistService.create(dto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        log.info("User login " + loginRequest.getEmail());
        return scientistService.login(loginRequest);
    }

    @GetMapping("/loginHistory")
    public List<LoginResponse> loginHistory(@RequestParam String email) {
        return scientistService.loginHistories(email);
    }
}
