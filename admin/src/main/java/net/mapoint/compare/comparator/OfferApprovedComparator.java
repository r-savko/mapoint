package net.mapoint.compare.comparator;

import java.util.Comparator;
import net.mapoint.model.OfferDto;

public class OfferApprovedComparator implements Comparator<OfferDto> {

    @Override
    public int compare(OfferDto o1, OfferDto o2) {
        return (o1.isApproved() == o2.isApproved() ? 0 : (o1.isApproved() ? 1 : -1));
    }
}
