package rocket.launch.stubs;

import rocket.launch.domain.Scientist;
import rocket.launch.dto.LoginRequest;
import rocket.launch.dto.LoginResponse;
import rocket.launch.dto.ScientistDto;


import static java.time.LocalDateTime.now;

public class EntityStubs {

    public static final String EMAIL = "elonmusk@spacex.com";
    public static final String UNAME = "Elon";
    public static final String SURNAME = "Musk";
    public static final String PASSWORD = "OfCourseIStillLoveYouPlatform";


    public static ScientistDto getScientistDto() {
        return ScientistDto.builder()
                .email(EMAIL)
                .name(UNAME)
                .surname(SURNAME)
                .password(PASSWORD)
                .build();
    }

    public static LoginRequest getLoginRequest() {
        return LoginRequest.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .build();
    }

    public static LoginResponse getLoginResponse() {
        return LoginResponse.builder()
                .email(EMAIL)
                .status("SUCCESS")
                .loginAt(now())
                .build();
    }

    public static Scientist getScientist() {
        return Scientist.builder()
                .email(EMAIL)
                .name(UNAME)
                .surname(SURNAME)
                .password(PASSWORD)
                .build();
    }
}
