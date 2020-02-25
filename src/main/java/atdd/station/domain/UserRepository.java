package atdd.station.domain;

import atdd.station.dto.user.FindByEmailResponseView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    FindByEmailResponseView findByEmail(String email);
}
