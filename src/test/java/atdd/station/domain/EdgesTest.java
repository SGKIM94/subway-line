package atdd.station.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static atdd.station.fixture.StationFixture.*;
import static atdd.station.fixture.SubwayLineFixture.SECOND_SUBWAY_LINE;
import static org.assertj.core.api.Assertions.assertThat;

public class EdgesTest {

    @DisplayName("Edgs 에 저장되어 있는 모든 Station 을 가져오는지")
    @Test
    public void  getStationsByEdgesTest() {
        //given
        Edge firstEdge = new Edge(0L, KANGNAM_STATION, PANGYO_STATION, SECOND_SUBWAY_LINE, 10);
        Edge secondEdge = new Edge(1L, SUNLENG_STATON, EMAE_STATION, FIRST_SUBWAY_LINE, 10);
        Edges edges = new Edges(Arrays.asList(firstEdge, secondEdge));

        //when
        List<Station> stations = edges.getStations();

        //then
        assertThat(stations).hasSize(4);
    }

    package atdd.station.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static atdd.station.fixture.StationFixture.*;
import static atdd.station.fixture.SubwayLineFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

    public class EdgesTest {

        @DisplayName("Edgs 에 저장되어 있는 모든 Station 을 가져오는지")
        @Test
        public void  getStationsByEdgesTest() {
            //when
            List<Station> stations = FIRST_EDGES.getStations();

            //then
            assertThat(stations).hasSize(4);
        }

        @DisplayName("강남역 의 이름을 가진 역을 가져오는지")
        @Test
        public void  findStationByName() {
            Station station = FIRST_EDGES.findStationByName(KANGNAM_STATION_NAME);

            assertThat(station.getName()).isEqualTo(KANGNAM_STATION_NAME);
        }

        @DisplayName("노선의 구간들에 새로운 구간을 추가할 수 있는지")
        @Test
        public void addEdge() {
            //give
            Edges addedEdges = FIRST_EDGES.addEdge(THIRD_EDGE);

            assertThat(addedEdges.getSize()).isEqualTo(3);
        }
    }

}
