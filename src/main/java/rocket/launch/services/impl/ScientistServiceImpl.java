package rocket.launch.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rocket.launch.converters.HistoryConverter;
import rocket.launch.converters.ScientistConverter;
import rocket.launch.domain.LoginHistory;
import rocket.launch.domain.LoginStatus;
import rocket.launch.domain.Scientist;
import rocket.launch.dto.LoginRequest;
import rocket.launch.dto.LoginResponse;
import rocket.launch.dto.ScientistDto;
import rocket.launch.exceptions.UserNotFoundException;
import rocket.launch.repositories.LoginHistoryRepository;
import rocket.launch.repositories.ScientistRepository;
import rocket.launch.services.ScientistService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScientistServiceImpl implements ScientistService {

    @Autowired
    private ScientistRepository scientistRepository;
    @Autowired
    private LoginHistoryRepository historyRepository;

    @Autowired
    private ScientistConverter converter;

    @Autowired
    private HistoryConverter historyConverter;

    @Override
    @Transactional
    public void create(ScientistDto dto) {
        scientistRepository.save(converter.convertToEntity(dto));
    }

    @Override
    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        Scientist scientist = scientistRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("\"User not found with email : \"+ email"));

        LoginHistory history;
        if (loginRequest.getPassword().equals(scientist.getPassword())) {
            history = successLogin(scientist);
        } else {
            history = failLogin(scientist);
        }

        return historyConverter.convert(historyRepository.save(history));
    }

    @Override
    @Transactional
    public List<LoginResponse> loginHistories(String email) {
        Scientist scientist = scientistRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("\"User not found with email : \"+ email"));

        List<LoginHistory> histories = historyRepository.findAllByScientist(scientist);

        return historyConverter.convert(histories);
    }

    private LoginHistory successLogin(Scientist scientist) {
        return historyRepository.save(LoginHistory
                .builder()
                .scientist(scientist)
                .status(LoginStatus.SUCCESS)
                .loginAt(LocalDateTime.now())
                .build());
    }

    private LoginHistory failLogin(Scientist scientist) {
        return historyRepository.save(LoginHistory
                .builder()
                .scientist(scientist)
                .status(LoginStatus.FAIL)
                .loginAt(LocalDateTime.now())
                .build());
    }
}
