package rocket.launch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rocket.launch.domain.Scientist;

import java.util.Optional;

public interface ScientistRepository extends JpaRepository<Scientist, Long> {
    Optional<Scientist> findByEmailAndPassword(String email, String password);

    Optional<Scientist> findByEmail(String email);
}
