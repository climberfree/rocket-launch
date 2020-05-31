package rocket.launch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rocket.launch.domain.LoginHistory;
import rocket.launch.domain.Scientist;

import java.util.List;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
    List<LoginHistory> findAllByScientist(Scientist scientist);
}
