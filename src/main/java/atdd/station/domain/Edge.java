package atdd.station.domain;

import lombok.Builder;

import javax.persistence.*;
import javax.sound.sampled.Line;
import java.util.List;

@Entity
public class Edge extends Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "station_source_id")
    private Station sourceStation;

    @OneToOne
    @JoinColumn(name = "station_target_id")
    private Station targetStation;

    @ManyToOne
    @JoinColumn(name = "subway_line_id")
    private SubwayLine subwayLine;

    private int distance;

    private boolean deleted = false;

    public Edge() {
    }

    @Builder
    public Edge(Long id, Station sourceStation, Station targetStation, SubwayLine subwayLine, int distance) {
        this.id = id;
        this.sourceStation = sourceStation;
        this.targetStation = targetStation;
        this.subwayLine = subwayLine;
        this.distance = distance;
    }

    public Edge(Station sourceStation, Station targetStation, SubwayLine subwayLine) {
        this.id = id;
        this.sourceStation = sourceStation;
        this.targetStation = targetStation;
        this.subwayLine = subwayLine;
        this.distance = 10;
    }

    public SubwayLine getSubwayLine() {
        return subwayLine;
    }

    public long getId() {
        return id;
    }

    public long getSourceStationId() {
        return sourceStation.getId();
    }

    public long getTargetStationId() {
        return targetStation.getId();
    }

    public Station getSourceStation() {
        return sourceStation;
    }

    public String getSourceStationName() {
        return sourceStation.getName();
    }

    public Station getTargetStation() {
        return targetStation;
    }

    public String getTargetStationName() {
        return targetStation.getName();
    }

    public int getDistance() {
        return distance;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void validateFavoriteEdge() {
        checkBidirectionalSourceAndTarget();
        checkSourceAndTargetStationIsSameWhenEdge();
    }

    public void checkBidirectionalSourceAndTarget() {
        checkLineInStationHasOppositeStation(getSourceStation(), getTargetStation());
        checkLineInStationHasOppositeStation(getTargetStation(), getSourceStation());
    }

    private void checkLineInStationHasOppositeStation(Station station, Station oppositeStation) {
        List<Line> linesInStation = station.getLines();

        if (linesInStation.isEmpty()) {
            return;
        }

        linesInStation.stream()
                .filter(line -> line.getStations().contains(oppositeStation))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
