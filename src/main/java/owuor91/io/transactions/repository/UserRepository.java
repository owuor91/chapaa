package owuor91.io.transactions.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import owuor91.io.transactions.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByPhoneNumber(String phoneNumber);

  Optional<User> findByName(String name);
}
