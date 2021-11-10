package net.mapoint.transformer;


public interface Transformer<T> {

    void transform(T t, int dayIndexFromToday);

}
