package atdd.station.domain;

import lombok.Builder;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class SubwayLine {
    private static final String DEFAULT_START_TIME = "05:00";
    private static final String DEFAULT_END_TIME = "23:50";
    private static final String DEFAULT_INTERVAL = "10";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 2, max = 20)
    private String name;

    @Size(min = 4, max = 10)
    private String startTime;

    @Size(min = 4, max = 10)
    private String endTime;

    @Size(min = 2, max = 20)
    private String intervalTime;

    @Embedded
    private Edges edges = new Edges();

    private boolean deleted = false;

    public SubwayLine() {
    }

    @Builder
    public SubwayLine(long id, String name, String startTime, String endTime, String intervalTime, Edges edges) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.intervalTime = intervalTime;
        this.startTime = startTime;
        this.edges = edges;
    }

    public SubwayLine(String name) {
        this.name = name;
        this.startTime = DEFAULT_START_TIME;
        this.endTime = DEFAULT_END_TIME;
        this.intervalTime = DEFAULT_INTERVAL;
        this.edges = new Edges();
    }

    public SubwayLine(String name, Edges edges) {
        this.name = name;
        this.startTime = DEFAULT_START_TIME;
        this.endTime = DEFAULT_END_TIME;
        this.intervalTime = DEFAULT_INTERVAL;
        this.edges = edges;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getIntervalTime() {
        return intervalTime;
    }

    public void deleteSubwayLine() {
        this.deleted = true;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public List<Station> getStations() {
        return this.edges.getStations();
    }

    public SubwayLine updateEdgesByStations(List<Station> stations) {
        return this;
    }

    List<Edge> makeEdgesByStations(List<Station> stations) {
        return stations.stream()
                .map(station -> new Edge(station, null,this))
                .collect(Collectors.toList());
    }

    public void deleteStationByName(String stationName) {
        Station station = getStationByName(stationName);
        station.deleteStation();
    }

    public Station getStationByName(String stationName) {
        return this.edges.findStationByName(stationName);
    }

    public void updateEdge(Edge edge) {
        edges.addEdge(edge);
    }

    @Override
    public String toString() {
        return "SubwayLine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", interval='" + intervalTime + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}


