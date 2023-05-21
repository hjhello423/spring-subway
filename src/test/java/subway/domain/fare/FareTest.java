package subway.domain.fare;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import subway.exception.ErrorType;
import subway.exception.ServiceException;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FareTest {


    @DisplayName("요금을 생성할 수 있다.")
    @ParameterizedTest(name = "요금: {0}원 생성")
    @ValueSource(ints = {0, 1, 100, 200, 500, 1000, 10000})
    void of(int value) {
        // when
        Fare fare = Fare.of(value);

        // then
        assertThat(fare.getValue()).isEqualTo(BigDecimal.valueOf(value));
    }

    @DisplayName("0보다 적은 금액의 요금을 생성할 수 없다.")
    @ParameterizedTest(name = "요금: {0}원 생성 실패")
    @ValueSource(ints = {-10000, -5000, -1000, -100, -1})
    void validateMinus(int value) {
        // then
        assertThatThrownBy(() -> Fare.of(value))
                .isInstanceOf(ServiceException.class)
                .hasMessage(ErrorType.VALIDATE_FARE_MINUS.getMessage());
    }

    @DisplayName("요금 동등성 비교")
    @ParameterizedTest(name = "요금: {0}원 비교")
    @ValueSource(ints = {0, 100, 500, 1000, 10000})
    void equals(int value) {
        // given
        Fare fare1 = Fare.of(value);
        Fare fare2 = Fare.of(value);

        // when
        boolean compare = fare1.equals(fare2);

        // then
        assertThat(compare).isTrue();
    }
}
