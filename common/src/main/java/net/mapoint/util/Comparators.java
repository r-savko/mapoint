package net.mapoint.util;


import java.util.Comparator;

public class Comparators {

    public static final Comparator<String> nullSafeStringComparator = Comparator.nullsFirst(String::compareTo);

}
