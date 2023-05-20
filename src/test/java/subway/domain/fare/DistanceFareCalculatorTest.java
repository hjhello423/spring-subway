package subway.domain.fare;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class DistanceFareCalculatorTest {

    static Stream<Arguments> fareArguments() {
        return Stream.of(
                Arguments.of(9, 1250),
                Arguments.of(10, 1250),
                Arguments.of(11, 1350),
                Arguments.of(12, 1350),
                Arguments.of(13, 1350),
                Arguments.of(14, 1350),
                Arguments.of(15, 1350),
                Arguments.of(16, 1450),
                Arguments.of(20, 1450),
                Arguments.of(25, 1550),
                Arguments.of(30, 1650),
                Arguments.of(40, 1850),
                Arguments.of(50, 2050),
                Arguments.of(51, 2150),
                Arguments.of(58, 2150),
                Arguments.of(59, 2250),
                Arguments.of(66, 2250),
                Arguments.of(67, 2350),
                Arguments.of(74, 2350),
                Arguments.of(75, 2450)
        );
    }

    @DisplayName("요금을 계산할 수 있다.")
    @MethodSource("fareArguments")
    @ParameterizedTest(name = "거리: {0}km, 요금: {1}원")
    void under_50Km(int distance, int addFare) {
        // given
        DistanceFareCalculator strategy = new DistanceFareCalculator();

        // when
        Fare fare = strategy.calculate(distance);

        // then
        System.out.println("fare = " + fare.getValue());
        System.out.println("addFare = " + addFare);
        assertThat(fare).isEqualTo(Fare.of(addFare));
    }

}
