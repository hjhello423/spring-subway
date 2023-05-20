package subway.domain.path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.domain.Distance;
import subway.domain.Section;
import subway.domain.Sections;
import subway.domain.Station;
import subway.exception.ErrorType;
import subway.exception.ServiceException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static subway.integration.StationStep.강남역;
import static subway.integration.StationStep.교대역;
import static subway.integration.StationStep.남부터미널역;
import static subway.integration.StationStep.양재역;
import static subway.integration.StationStep.판교역;

class ShortestPathFinderTest {

    private Station 강남;
    private Station 양재;
    private Station 판교;
    private Station 교대;
    private Station 남부터미널;
    private Section 강남_양재;
    private Section 양재_판교;
    private Section 교대_남부터미널;
    private Section 남부터미널_양재;
    private Section 교대_강남;
    private Sections 신분당선;
    private Sections 이호선;
    private Sections 삼호선;

    @BeforeEach
    void setUp() {
        강남 = new Station(1L, 강남역);
        양재 = new Station(2L, 양재역);
        판교 = new Station(3L, 판교역);

        교대 = new Station(4L, 교대역);
        남부터미널 = new Station(5L, 남부터미널역);


        강남_양재 = Section.builder()
                .upStation(강남)
                .downStation(양재)
                .distance(Distance.of(10))
                .build();
        양재_판교 = Section.builder()
                .upStation(양재)
                .downStation(판교)
                .distance(Distance.of(20))
                .build();
        교대_남부터미널 = Section.builder()
                .upStation(교대)
                .downStation(남부터미널)
                .distance(Distance.of(10))
                .build();
        남부터미널_양재 = Section.builder()
                .upStation(남부터미널)
                .downStation(양재)
                .distance(Distance.of(10))
                .build();

        교대_강남 = Section.builder()
                .upStation(교대)
                .downStation(강남)
                .distance(Distance.of(5))
                .build();

        신분당선 = new Sections(List.of(강남_양재, 양재_판교));
        이호선 = new Sections(List.of(교대_강남));
        삼호선 = new Sections(List.of(교대_남부터미널, 남부터미널_양재));
    }

    @DisplayName("환승을 통한 최단거리를 구할 수 있다.")
    @Test
    void getPath() {
        // given
        int expectedDistance = 35;
        ShortestPathFinder pathFinder = ShortestPathFinder.of(List.of(신분당선, 이호선, 삼호선));

        // when
        Path path = pathFinder.getPath(교대, 판교);

        // then
        assertThat(path.getStations()).containsExactly(교대, 강남, 양재, 판교);
        assertThat(path.getDistance().getValue()).isEqualTo(expectedDistance);
    }

    @DisplayName("출발역, 도착역이 동일할 경우 최단거리를 구할 수 없다.")
    @Test
    void validatePath() {
        // given


        ShortestPathFinder pathFinder = ShortestPathFinder.of(List.of(신분당선, 삼호선));

        // then
        assertThatThrownBy(() -> pathFinder.getPath(교대, 교대))
                .isInstanceOf(ServiceException.class)
                .hasMessage(ErrorType.SAME_SOURCE_DESTINATION_STATION.getMessage());
    }

    @DisplayName("최단거리를 찾을 수 없는 경우 예외를 던진다.")
    @Test
    void not_found_Path() {
        // given
        Station 서현 = new Station(100L, "서현");
        Station 미금 = new Station(200L, "미금");
        Section 서현_미금 = Section.builder()
                .upStation(서현)
                .downStation(미금)
                .distance(Distance.of(10))
                .build();
        Sections 분당선 = new Sections(List.of(서현_미금));

        ShortestPathFinder pathFinder = ShortestPathFinder.of(List.of(신분당선, 삼호선, 분당선));

        // then
        assertThatThrownBy(() -> pathFinder.getPath(교대, 미금))
                .isInstanceOf(ServiceException.class)
                .hasMessage(ErrorType.NOT_FOUND_PATH.getMessage());
    }
}
