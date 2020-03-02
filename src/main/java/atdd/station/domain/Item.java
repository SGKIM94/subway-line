package atdd.path.domain;

import lombok.NoArgsConstructor;
import org.springframework.dao.DuplicateKeyException;

import static atdd.path.dao.FavoriteDao.EDGE_TYPE;

@NoArgsConstructor
public class Item {
    private Long id;

    public Item(Long id) {
        this.id = id;
    }

    public void checkSourceAndTargetStationIsSameWhenEdge(String type) {
        if (!EDGE_TYPE.equals(type)) {
            return;
        }

        if (((Edge) this).isSameNameWithSourceAndTarget()) {
            throw new DuplicateKeyException("시작역과 종착역이 같을 수 없습니다.");
        }
    }

    public Long getId() {
        return id;
    }
}
