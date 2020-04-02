package atdd.station.domain;

import atdd.path.domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findStationByUser(User user);
    List<Favorite> findEdgeByUser(User user);
}
