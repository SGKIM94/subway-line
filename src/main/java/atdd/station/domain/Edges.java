package atdd.station.domain;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Edges {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "subwayLine", fetch = FetchType.EAGER)
    @Where(clause = "deleted = false")
    @OrderBy("id ASC")
    private List<Edge> edges;

    public Edges() {
    }

    public Edges(List<Edge> edges) {
        this.edges = edges;
    }

    public long getId() {
        return id;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    private List<Station> getStations(List<atdd.path.domain.Edge> edges) {
        if (edges.size() == 0) {
            return new ArrayList<>();
        }

        List<Station> stations = new ArrayList();
        Station lastStation = findFirstStation(edges);
        stations.add(lastStation);

        while (true) {
            Station nextStation = findNextStationOf(edges, lastStation);
            if (nextStation == null) {
                break;
            }
            stations.add(nextStation);
            lastStation = nextStation;
        }

        return stations;
    }

}
