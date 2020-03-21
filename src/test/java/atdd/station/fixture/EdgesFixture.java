package atdd.station.fixture;

import atdd.station.domain.Edge;
import atdd.station.domain.Edges;

import java.util.Arrays;

import static atdd.station.fixture.StationFixture.*;
import static atdd.station.fixture.SubwayLineFixture.FIRST_SUBWAY_LINE;
import static atdd.station.fixture.SubwayLineFixture.SECOND_SUBWAY_LINE;

public class EdgesFixture {
    public static final Edge FIRST_EDGE = new Edge(0L, KANGNAM_STATION, PANGYO_STATION, SECOND_SUBWAY_LINE, 10);
    public static final Edge SECOND_EDGE = new Edge(1L, SUNLENG_STATON, EMAE_STATION, FIRST_SUBWAY_LINE, 10);
    public static final Edge THIRD_EDGE = new Edge(3L, EMAE_STATION, SEHYEN_STATION, SECOND_SUBWAY_LINE, 10);

    public static final Edges FIRST_EDGES = new Edges(Arrays.asList(FIRST_EDGE, SECOND_EDGE));

}
