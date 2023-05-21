package subway.integration;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import subway.dto.PathResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static subway.integration.StationStep.강남역;
import static subway.integration.StationStep.교대역;
import static subway.integration.StationStep.양재역;
import static subway.integration.StationStep.판교역;

public final class PathStep {

    public static ExtractableResponse<Response> 경로_조회_api(long sourceId, long destinationId) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("sourceId", sourceId)
                .queryParam("destinationId", destinationId)
                .when().get("/paths")
                .then().log().all().
                extract();
    }

    public static void 경로_응답_검증(ExtractableResponse<Response> extractableResponse, int distance, BigDecimal fare) {
        PathResponse response = extractableResponse.as(PathResponse.class);
        List<String> names = response.getStations()
                .stream().map(station -> station.getName())
                .collect(Collectors.toList());

        assertThat(extractableResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getDistance()).isEqualTo(distance);
        assertThat(response.getFare()).isEqualTo(fare);
        assertThat(names).containsExactly(교대역, 강남역, 양재역, 판교역);
    }

}
