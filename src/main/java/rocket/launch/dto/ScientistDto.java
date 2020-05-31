package rocket.launch.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScientistDto {
    private String name;
    private String surname;
    private String email;
    private String password;
}
