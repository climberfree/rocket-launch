package rocket.launch.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse implements Serializable {
    private String email;
    private String status;
    private LocalDateTime loginAt;
}
