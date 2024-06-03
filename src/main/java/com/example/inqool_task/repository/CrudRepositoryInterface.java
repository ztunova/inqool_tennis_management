package com.example.inqool_task.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepositoryInterface<T> {

    Optional<T> get(Long id);

    List<T> getAll();

    T save(T t);

    T update(T t);

    void delete(T t);

}
