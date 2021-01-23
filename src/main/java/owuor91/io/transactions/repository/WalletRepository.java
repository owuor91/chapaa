package owuor91.io.transactions.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import owuor91.io.transactions.model.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {
}
