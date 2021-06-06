package org.zerobs.polla.repositories;

public interface EntityRepository<T> {
    void save(T object);

    T get(String id);

    void delete(T object);
}