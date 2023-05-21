package subway.domain.fare;

import java.math.BigDecimal;

import static subway.domain.fare.DistanceFarePolicy.SECTION1;
import static subway.domain.fare.DistanceFarePolicy.SECTION2;

public class DistanceFareCalculator implements FareCalculator{

    @Override
    public Fare calculate(int distance) {
        Fare fare = Fare.BASIC_FARE;
        if (distance <= SECTION1.getStart()) {
            return fare;
        }

        fare = fare.add(calculateSection1(distance));
        fare = fare.add(calculateSection2(distance));

        return fare;
    }

    private BigDecimal calculateSection1(int distance) {
        int fareDistance = discardNumbersLessThan(distance, SECTION2.getStart());
        fareDistance -= SECTION1.getStart();

        int fare = calculateOverFare(fareDistance, SECTION1.getExtraFareBasedDistance());

        return BigDecimal.valueOf(fare);
    }

    private BigDecimal calculateSection2(int distance) {
        if (distance <= SECTION2.getStart()) {
            return BigDecimal.ZERO;
        }

        int fareDistance = distance - SECTION2.getStart();

        int fare = calculateOverFare(fareDistance, SECTION2.getExtraFareBasedDistance());

        return BigDecimal.valueOf(fare);
    }

    private int discardNumbersLessThan(int distance, int extraFareBasedDistance) {
        if (distance >= extraFareBasedDistance) {
            return extraFareBasedDistance;
        }
        return distance;
    }

    public int calculateOverFare(int overDistance, int extraFareBasedDistance) {
        if (overDistance == 0) {
            return 0;
        }
        return (int) ((Math.ceil((overDistance - 1) / extraFareBasedDistance) + 1) * 100);
    }
}
