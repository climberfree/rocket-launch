package rocket.launch.services;

import rocket.launch.dto.LoginRequest;
import rocket.launch.dto.LoginResponse;
import rocket.launch.dto.ScientistDto;

import java.util.List;

public interface ScientistService {
    void create(ScientistDto scientistDto);
    LoginResponse login(LoginRequest loginRequest);
    List<LoginResponse> loginHistories(String email);
}
