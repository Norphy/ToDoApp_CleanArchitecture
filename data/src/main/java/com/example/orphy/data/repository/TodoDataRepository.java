package com.example.orphy.data.repository;

import android.util.Log;

import com.example.orphy.data.TodoModel;
import com.example.orphy.data.TodoModelMapper;
import com.example.orphy.data.repository.datasource.TodoLocalDataStore;
import com.example.orphy.domain.Todo;
import com.example.orphy.domain.TodoRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created on 12/10/2017.
 */

public class TodoDataRepository implements TodoRepository {

    private final TodoModelMapper todoModelMapper;
    private final TodoLocalDataStore todoLocalDataStore;
    private static final String TAG = TodoDataRepository.class.getSimpleName();

    public TodoDataRepository(TodoModelMapper todoModelMapper, TodoLocalDataStore todoLocalDataStore){

    this.todoModelMapper = todoModelMapper;
    this.todoLocalDataStore = todoLocalDataStore;

    }

    @Override
    public Observable<List<Todo>> todos() {
        return todoLocalDataStore.todos().map(new Function<List<TodoModel>, List<Todo>>() {
            @Override
            public List<Todo> apply(List<TodoModel> todoModels) throws Exception {
                return todoModelMapper.transformListDataToDomain(todoModels);
            }
        });
    }

    @Override
    public void addTodo(Todo todo) {
        TodoModel todoModel = todoModelMapper.transformFromDomainToData(todo);
        todoLocalDataStore.addTodo(todoModel);
        Log.v("TodoDataRespository: ", "todoLocalDataStore.addTodo(Todomodel) was run");
    }

    @Override
    public void updateTodo(Todo todo) {
        TodoModel todoModel = todoModelMapper.transformFromDomainToData(todo);
        todoLocalDataStore.updateTodo(todoModel);
        Log.v("TodoDataResposity: ", "todoLocalDataStore.updateTodo(Todo todo)  was run");
    }

    @Override
    public void deleteTodo(Todo todo) {
        TodoModel todoModel = todoModelMapper.transformFromDomainToData(todo);
        todoLocalDataStore.deleteTodo(todoModel);
        Log.v(TAG, "todoLocalDataStore.deleteTodo(todo) was run");
    }
}
