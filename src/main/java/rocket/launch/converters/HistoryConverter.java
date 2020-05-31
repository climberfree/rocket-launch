package rocket.launch.converters;

import org.springframework.stereotype.Component;
import rocket.launch.domain.LoginHistory;
import rocket.launch.dto.LoginResponse;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HistoryConverter {

    public LoginResponse convert(LoginHistory history) {
        return LoginResponse.builder()
                .email(history.getScientist().getEmail())
                .loginAt(history.getLoginAt())
                .status(history.getStatus().name())
                .build();
    }

    public List<LoginResponse> convert(List<LoginHistory> histories) {
        return histories.stream().map(this::convert).collect(Collectors.toList());
    }
}
