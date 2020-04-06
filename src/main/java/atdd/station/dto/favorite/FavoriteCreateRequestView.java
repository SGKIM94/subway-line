package atdd.path.application.dto.favorite;

import atdd.station.domain.Edge;
import atdd.station.domain.Item;
import atdd.station.domain.Station;
import atdd.station.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static atdd.path.application.FavoriteService.EDGE_TYPE;

@Getter
@NoArgsConstructor
public class FavoriteCreateRequestView {
    private Item item;
    private Edge edge;
    private Station station;
    private String type;

    public FavoriteCreateRequestView(Item item, String type) {
        this.item = item;
        setItemByType(item, type);
        this.type = type;
    }

    private void setItemByType(Item item, String type) {
        if (EDGE_TYPE.equals(type)) {
            this.edge = (Edge) item;
            return;
        }

        this.station = (Station) item;
    }

    public atdd.path.domain.Favorite toEntity(User user) {
        return atdd.path.domain.Favorite.builder()
                .user(user)
                .item(item)
                .build();
    }
}
