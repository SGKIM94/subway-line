package atdd.station.dto.favorite;

import atdd.path.domain.Favorite;
import atdd.path.domain.Station;
import atdd.path.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class FavoriteCreateRequestView {
    private Long userId;
    private Long subwayId;
    private String subwayName;
    private String type;

    @Builder
    public FavoriteCreateRequestView(Long userId, Long subwayId, String subwayName, String type) {
        this.userId = userId;
        this.subwayId = subwayId;
        this.subwayName = subwayName;
        this.type = type;
    }

    public Favorite toEntity(User user) {
        return Favorite.builder()
                .user(user)
                .subway(new Station(subwayId, subwayName))
                .build();
    }
}
