package subway.domain.fare;

import subway.exception.ErrorType;
import subway.exception.ServiceException;

import java.math.BigDecimal;
import java.util.Objects;

public class Fare {

    public static final Fare BASIC_FARE = Fare.of(1250);

    private BigDecimal value;

    private Fare(BigDecimal value) {
        validateMinus(value);
        this.value = value;
    }

    public static Fare of(long fare) {
        return new Fare(BigDecimal.valueOf(fare));
    }

    private void validateMinus(BigDecimal fare) {
        if (fare.compareTo(BigDecimal.ZERO) < 0) {
            throw new ServiceException(ErrorType.VALIDATE_FARE_MINUS);
        }
    }

    public Fare add(BigDecimal fare) {
        return new Fare(this.value.add(fare));
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fare fare = (Fare) o;
        return Objects.equals(value, fare.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
