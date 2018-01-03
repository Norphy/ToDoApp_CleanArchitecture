package com.example.orphy.data;


import com.example.orphy.domain.Todo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 12/6/2017.
 */

public class TodoModelMapper {

    public TodoModelMapper() {}

    public Todo transformDataToDomain(TodoModel todoModel) {
        Todo todo = new Todo(todoModel.getId(),
                todoModel.getDescription(),
                todoModel.getIsChecked(),
                todoModel.getIsPinned(),
                todoModel.getPriority());

        return todo;
    }

    public List<Todo> transformListDataToDomain(List<TodoModel> todoModels) {
        List<Todo> todos = new ArrayList<>();
        Todo todo;
        for(TodoModel todoModel:todoModels) {
            todo = new Todo(todoModel.getId(),
                    todoModel.getDescription(),
                    todoModel.getIsChecked(),
                    todoModel.getIsPinned(),
                    todoModel.getPriority());
            todos.add(todo);
        }

        return todos;
    }

    public TodoModel transformFromDomainToData(Todo todo) {
        TodoModel todoModel = new TodoModel(todo.getId(),
                todo.getDescription(),
                todo.getIsChecked(),
                todo.getIsPinned(),
                todo.getPriority());
        return todoModel;
    }

}
