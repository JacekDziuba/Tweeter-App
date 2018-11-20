package pl.coderslab.tweeter.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.tweeter.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmail(String email);

}
