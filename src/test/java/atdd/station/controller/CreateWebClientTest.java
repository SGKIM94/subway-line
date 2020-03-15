package atdd.station.controller;

import atdd.station.domain.Edge;
import atdd.station.domain.Edges;
import atdd.station.dto.station.StationCreateRequestDto;
import atdd.station.dto.station.StationCreateResponseDto;
import atdd.station.dto.subwayLine.SubwayLineCreateRequestDto;
import atdd.station.dto.subwayLine.SubwayLineCreateResponseDto;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.Objects;

import static atdd.station.fixture.SubwayLineFixture.*;

public class CreateWebClientTest extends atdd.path.web.RestWebClientTest {
    public static final String LINE_BASE_URL = "/subway-lines/";
    public static final String STATION_BASE_URL = "/stations/";

    public CreateWebClientTest(WebTestClient webTestClient) {
        super(webTestClient);
    }

    Long createStation(Long id, String stationName) {
        StationCreateRequestDto station = new StationCreateRequestDto(id, stationName);
        return Objects.requireNonNull(postMethodAcceptance(STATION_BASE_URL, station, StationCreateResponseDto.class)
                .getResponseBody()).getId();
    }

    Long createLine(String lineName) {
        SubwayLineCreateRequestDto line = new SubwayLineCreateRequestDto
                (lineName, DEFAULT_START_TIME, DEFAULT_END_TIME, DEFAULT_INTERVAL, new Edges());

        return Objects.requireNonNull(postMethodAcceptance(LINE_BASE_URL, line, SubwayLineCreateResponseDto.class)
                .getResponseBody()).getId();
    }

    void createEdge(Edge edge, Long lineId) {
        Objects.requireNonNull(
                postMethodAcceptance(LINE_BASE_URL + "/" + lineId + "/edges", edge, Void.class))
                .getResponseBody();
    }
}
