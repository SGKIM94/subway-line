package atdd.station.domain;

import lombok.Builder;

import javax.persistence.*;

@Entity
public class Edge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "station_id")
    private Station sourceStation;

    @OneToOne
    @JoinColumn(name = "station_id")
    private Station targetStation;

    private int distance;

    public Edge() {
    }

    @Builder
    public Edge(Station sourceStation, Station targetStation, int distance) {
        this.sourceStation = sourceStation;
        this.targetStation = targetStation;
        this.distance = distance;
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

    public Station getTargetStation() {
        return targetStation;
    }

    public int getDistance() {
        return distance;
    }
}
