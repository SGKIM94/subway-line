package atdd.path.domain;

import atdd.station.domain.Station;

public class Edge extends atdd.path.domain.Item {
    private Long id;
    private Station sourceStation;
    private Station targetStation;
    private int distance;

    public Edge() {
    }

    public Edge(Long id, Station sourceStation, Station targetStation, int distance) {
        this.id = id;
        this.sourceStation = sourceStation;
        this.targetStation = targetStation;
        this.distance = distance;
    }

    public static Edge of(Station sourceStation, Station targetStation, int distance) {
        return new Edge(null, sourceStation, targetStation, distance);
    }

    public boolean isSameNameWithSourceAndTarget() {
        return getSourceStationName().equals(getTargetStationName());
    }

    private String getTargetStationName() {
        return targetStation.getName();
    }

    private String getSourceStationName() {
        return sourceStation.getName();
    }

    public Long getId() {
        return id;
    }

    public int getDistance() {
        return distance;
    }

    public Station getSourceStation() {
        return sourceStation;
    }

    public Station getTargetStation() {
        return targetStation;
    }

    public boolean hasStation(Station station) {
        return sourceStation.equals(station) || targetStation.equals(station);
    }
}
