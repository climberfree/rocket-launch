package rocket.launch.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import rocket.launch.domain.Scientist;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static rocket.launch.stubs.EntityStubs.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ScientistRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ScientistRepository scientistRepository;

    @Test
    public void whenFindByEmail_thenReturnScientist() {
        Scientist expected = getScientist();
        entityManager.persist(expected);
        entityManager.flush();

        Optional<Scientist> actual = scientistRepository.findByEmail(EMAIL);

        assertThat(actual.isPresent()).isTrue();
        Scientist scientist = actual.get();
        assertThat(scientist.getEmail())
                .isEqualTo(expected.getEmail());
    }

    @Test
    public void whenFindByEmailAndPassword_thenReturnScientist() {
        Scientist expected = getScientist();
        entityManager.persist(expected);
        entityManager.flush();

        Optional<Scientist> actual = scientistRepository.findByEmailAndPassword(EMAIL, PASSWORD);

        assertThat(actual.isPresent()).isTrue();
        Scientist scientist = actual.get();
        assertThat(scientist.getEmail()).isEqualTo(expected.getEmail());
        assertThat(scientist.getPassword()).isEqualTo(expected.getPassword());
    }
}


