package subway.domain.fare;

public enum DistanceFarePolicy {

    SECTION1(10, 5, Fare.of(100)),
    SECTION2(50, 8, Fare.of(100));

    private final int start;
    private final int extraFareBasedDistance;
    private final Fare extraFare;

    DistanceFarePolicy(int start, int extraFareBasedDistance, Fare extraFare) {
        this.start = start;
        this.extraFareBasedDistance = extraFareBasedDistance;
        this.extraFare = extraFare;
    }

    public int getStart() {
        return start;
    }

    public int getExtraFareBasedDistance() {
        return extraFareBasedDistance;
    }

    public Fare getExtraFare() {
        return extraFare;
    }
}
