package atdd.station.fixture;

import java.util.Arrays;
import java.util.List;

import static atdd.station.fixture.StationFixture.*;
import static atdd.station.fixture.SubwayLineFixture.NEW_BUNDANG_SUBWAY_LINE;
import static atdd.station.fixture.SubwayLineFixture.SECOND_SUBWAY_LINE;

public class SubwaysFixture {
    public static final Subway SECOND_SUBWAY = new Subway(KANGNAM_STATION, SECOND_SUBWAY_LINE);
    public static final Subway YUCKSAM_SUBWAY = new Subway(YUCKSAM_STATON, SECOND_SUBWAY_LINE);
    public static final Subway SUNLENG_SUBWAY = new Subway(SUNLENG_STATON, SECOND_SUBWAY_LINE);

    public static final Subway NEW_BUNDANG_SUBWAY = new Subway(PANGYO, NEW_BUNDANG_SUBWAY_LINE);

    public static final List<Subway> SUBWAYS = Arrays.asList(SECOND_SUBWAY, NEW_BUNDANG_SUBWAY);
    public static final List<Subway> SECOND_SUBWAYS = Arrays.asList(SECOND_SUBWAY, YUCKSAM_SUBWAY, SUNLENG_SUBWAY);
}
