package com.example.orphy.data.repository.datasource;

import com.example.orphy.data.TodoModel;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created on 12/10/2017.
 */

public interface TodoDataStore {

    Observable<List<TodoModel>> todos();

    Void addTodo(TodoModel todoModel);

    void updateTodo(TodoModel todoModel);

    void deleteTodo(TodoModel todoModel);
}
