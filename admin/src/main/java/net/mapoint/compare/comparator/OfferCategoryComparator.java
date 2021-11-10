package net.mapoint.compare.comparator;

import java.util.Comparator;
import net.mapoint.model.OfferDto;

public class OfferCategoryComparator implements Comparator<OfferDto> {

    @Override
    public int compare(OfferDto o1, OfferDto o2) {
        return o1.getSubcategories().size() - o2.getSubcategories().size();
    }
}
