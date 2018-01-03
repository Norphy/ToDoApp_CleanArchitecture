package com.example.orphy.domain;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created on 12/7/2017.
 */

public interface TodoRepository {

    Observable<List<Todo>> todos();

    void addTodo(Todo todo);

    void updateTodo(Todo todo);

    void deleteTodo(Todo todo);
}
