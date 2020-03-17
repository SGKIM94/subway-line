package atdd.station.domain;

import org.hibernate.annotations.Where;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Embeddable
public class Edges {
    @OneToMany(mappedBy = "subwayLine")
    @Where(clause = "deleted = false")
    @OrderBy("id ASC")
    private List<Edge> edges = new ArrayList<>();

    public Edges() {
    }

    public Edges(List<Edge> edges) {
        this.edges = edges;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public List<Station> getStations() {
        if (edges.isEmpty()) {
            return new ArrayList<>();
        }

        List<Station> sourceStations = getSourceStations();
        List<Station> targetStations = getTargetStations();

        if (sourceStations.isEmpty() && targetStations.isEmpty()) {
            return new ArrayList<>();
        }

        return Stream.of(sourceStations, targetStations)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<Station> getTargetStations() {
        return this.edges.stream()
                .map(Edge::getTargetStation)
                .collect(Collectors.toList());
    }

    private List<Station> getSourceStations() {
        return this.edges.stream()
                .map(Edge::getSourceStation)
                .collect(Collectors.toList());
    }

    public List<SubwayLine> getSubwayLines() {
        return this.edges.stream()
                .map(Edge::getSubwayLine)
                .collect(Collectors.toList());
    }

    public Station findStationByName(String stationName) {
        return this.edges.stream()
                .filter(edge -> stationName.equals(edge.getSourceStationName()))
                .findFirst()
                .orElse();
    }

}
