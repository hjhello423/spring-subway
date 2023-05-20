package subway.integration;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static subway.integration.LineStep.BG_RED_600;
import static subway.integration.LineStep.노선_생성_api_응답변환;
import static subway.integration.PathStep.경로_응답_검증;
import static subway.integration.PathStep.경로_조회_api;
import static subway.integration.SectionStep.구간_생성_api;
import static subway.integration.StationStep.역_생성_api_응답변환;

@DisplayName("경로 관련 기능")
public class PathIntegrationTest extends IntegrationTest {
    public long 강남역;
    public long 판교역;
    public long 정자역;
    public long 미금역;
    public long 교대역;
    public long 양재역;
    public long 남부터미널역;
    public long 신분당선;
    private long 이호선;
    private long 삼호선;

    @BeforeEach
    public void setUp() {
        super.setUp();
        강남역 = 역_생성_api_응답변환(StationStep.강남역).getId();
        판교역 = 역_생성_api_응답변환(StationStep.판교역).getId();
        정자역 = 역_생성_api_응답변환(StationStep.정자역).getId();
        미금역 = 역_생성_api_응답변환(StationStep.미금역).getId();
        교대역 = 역_생성_api_응답변환(StationStep.교대역).getId();
        양재역 = 역_생성_api_응답변환(StationStep.양재역).getId();
        남부터미널역 = 역_생성_api_응답변환(StationStep.남부터미널역).getId();

        신분당선 = 노선_생성_api_응답변환("신분당선", BG_RED_600).getId();
        이호선 = 노선_생성_api_응답변환("이호선", BG_RED_600).getId();
        삼호선 = 노선_생성_api_응답변환("삼호선", BG_RED_600).getId();

        구간_생성_api(신분당선, 양재역, 강남역, 10);
        구간_생성_api(신분당선, 판교역, 양재역, 20);
        구간_생성_api(이호선, 강남역, 교대역, 5);
        구간_생성_api(삼호선, 남부터미널역, 교대역, 10);
        구간_생성_api(삼호선, 양재역, 남부터미널역, 10);

    }

    @DisplayName("지하철 경로를 조회한다.")
    @Test
    void getLine() {
        // given
        int expectedDistance = 35;

        // when
        ExtractableResponse<Response> extractableResponse = 경로_조회_api(교대역, 판교역);

        // then
        경로_응답_검증(extractableResponse, expectedDistance);
    }
}
