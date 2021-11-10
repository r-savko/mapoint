package net.mapoint.compare;

public enum OfferComparatorType {

    APPROVED, CATEGORY;

    static OfferComparatorType fromString(String s) {
        for (OfferComparatorType comparatorType : OfferComparatorType.values()) {
            if (comparatorType.name().equalsIgnoreCase(s)) {
                return comparatorType;
            }
        }
        return CATEGORY;
    }
}
