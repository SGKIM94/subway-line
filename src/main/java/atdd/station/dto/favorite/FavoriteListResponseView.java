package atdd.station.dto.favorite;

import atdd.path.domain.Favorite;
import atdd.path.domain.Station;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
public class FavoriteListResponseView {
    private List<Favorite> favorites;

    @Builder
    public FavoriteListResponseView(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    public static FavoriteListResponseView toDtoEntity(List<Favorite> favorites) {
        return FavoriteListResponseView.builder()
                .favorites(favorites)
                .build();
    }

    public Station getFirstFavoriteStation() {
        return getFirstIndex().getStation();
    }

    public Favorite getFirstIndex() {
        return this.favorites.get(0);
    }

    public int getSize() {
        return this.favorites.size();
    }
}
