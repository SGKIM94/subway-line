package atdd.station.domain;

import atdd.path.SoftAssertionTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static atdd.path.TestConstant.TEST_STATION;
import static org.assertj.core.api.Assertions.assertThat;


public class EdgeTest extends SoftAssertionTest {

    @DisplayName("시작역광 종착역이 같은지 확인 가능한지")
    @Test
    public void isSameNameWithSourceAndTarget() {
        atdd.path.domain.Edge edge = new atdd.path.domain.Edge(0L, TEST_STATION, TEST_STATION, 10);

        assertThat(edge.isSameNameWithSourceAndTarget()).isTrue();
    }
}


