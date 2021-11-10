package net.mapoint.compare;

import com.google.common.collect.ImmutableMap;
import java.util.Comparator;
import java.util.Map;
import net.mapoint.compare.comparator.OfferApprovedComparator;
import net.mapoint.compare.comparator.OfferCategoryComparator;
import net.mapoint.model.OfferDto;

public class OfferComparatorFactory {

    private static final Map<OfferComparatorType, Comparator<OfferDto>> comparatorMap = ImmutableMap.of(
        OfferComparatorType.APPROVED, new OfferApprovedComparator(),
        OfferComparatorType.CATEGORY, new OfferCategoryComparator()
    );

    public static Comparator<OfferDto> comparator(String sortBy) {
        return comparatorMap.get(OfferComparatorType.fromString(sortBy));
    }

}
