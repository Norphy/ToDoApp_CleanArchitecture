package com.example.orphy.todoapp;

import com.example.orphy.domain.Todo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 12/12/2017.
 */

public class TodoPresentationModelMapper {

    public TodoPresentationModelMapper() {}

    public List<TodoPresentationModel> transformListFromDomainToPresent(List<Todo> todoList) {
        List<TodoPresentationModel> todoPresentationModels= new ArrayList<>();
        for(Todo todo:todoList) {
            TodoPresentationModel todoPresentationModel = new TodoPresentationModel(todo.getId(),
                    todo.getDescription(),
                    todo.getIsChecked(),
                    todo.getIsPinned(),
                    todo.getPriority());
            todoPresentationModels.add(todoPresentationModel);
        }
     return todoPresentationModels;
    }

    public Todo transformFromPresentToDomain(TodoPresentationModel todoPresentationModel) {
        Todo todo = new Todo(todoPresentationModel.getId(),
                todoPresentationModel.getDescription(),
                todoPresentationModel.getIsChecked(),
                todoPresentationModel.getIsPinned(),
                todoPresentationModel.getPriority());
        return todo;
    }

}
