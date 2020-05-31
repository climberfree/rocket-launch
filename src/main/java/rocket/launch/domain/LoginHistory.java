package rocket.launch.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "login_history")
public class LoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "login_at")
    private LocalDateTime loginAt;
    @ManyToOne(fetch = FetchType.LAZY)
    private Scientist scientist;

    private LoginStatus status;

}
