package atdd.path.application.dto.favorite;

import atdd.path.domain.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static atdd.path.dao.FavoriteDao.EDGE_TYPE;

@Getter
@NoArgsConstructor
public class FavoriteCreateRequestView {
    private atdd.path.domain.Item item;
    private atdd.path.domain.Edge edge;
    private Station station;
    private String type;

    public FavoriteCreateRequestView(atdd.path.domain.Item item, String type) {
        this.item = item;
        if (EDGE_TYPE.equals(type)) {
            this.edge = (atdd.path.domain.Edge) item;
        } else {
            this.station = (Station) item;
        }

        this.type = type;
    }

    public Favorite toEntity(atdd.path.domain.User user) {
        return Favorite.builder()
                .user(user)
                .item(item)
                .build();
    }
}
