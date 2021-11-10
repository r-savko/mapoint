package net.mapoint.service;

import java.util.Set;
import org.springframework.transaction.annotation.Transactional;

@Transactional
interface CommonService<E> {

    E add(E e);

    E get(int id);

    Set<E> getAll();

    E update(E e);

    void delete(int id);
}