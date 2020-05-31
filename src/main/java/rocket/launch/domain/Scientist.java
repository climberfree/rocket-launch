package rocket.launch.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "scientists")
public class Scientist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String name;
    private String surname;
    private String password;
    @OneToMany(mappedBy = "scientist",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<LoginHistory> loginHistory = new ArrayList<>();

}
