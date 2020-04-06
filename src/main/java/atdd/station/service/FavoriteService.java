package atdd.path.application;

import atdd.path.application.dto.favorite.FavoriteCreateRequestView;
import atdd.path.domain.Favorite;
import atdd.station.domain.FavoriteRepository;
import atdd.station.domain.User;
import atdd.station.dto.favorite.FavoriteCreateResponseView;
import atdd.station.dto.favorite.FavoriteListResponseView;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FavoriteService {
    private static final String STATION_TYPE = "station";
    public static final String EDGE_TYPE = "edge";

    private FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    @Transactional
    public FavoriteCreateResponseView save(User loginUser, FavoriteCreateRequestView favorite) {
        if (EDGE_TYPE.equals(favorite.getType())) {
            favorite.getEdge().validateFavoriteEdge();
        }

        return FavoriteCreateResponseView.toDtoEntity(favoriteRepository.save(favorite.toEntity(loginUser), favorite.getType()));
    }

    @Transactional(readOnly = true)
    public FavoriteListResponseView findByUser(User loginUser, String type) {
        return FavoriteListResponseView.toDtoEntity(findStationByUserAndType(loginUser, type));
    }

    @Transactional
    public void deleteItem(User loginUser, Long itemId) {
        favoriteRepository.delete(loginUser, itemId);
    }

    private List<Favorite> findStationByUserAndType(User loginUser, String type) {
        if (STATION_TYPE.equals(type)) {
            return favoriteRepository.findStationByUser(loginUser);
        }

        return favoriteRepository.findEdgeByUser(loginUser);
    }
}
