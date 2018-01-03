package com.example.orphy.domain.interactor;

import com.example.orphy.domain.Todo;
import com.example.orphy.domain.TodoRepository;
import com.example.orphy.domain.UIThread;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created on 12/10/2017.
 */

public class GetTodos extends UseCase<List<Todo>, Void> {

    private final TodoRepository todoRepository;

    public GetTodos(TodoRepository newTodoRepository, UIThread uiThread) {
        super(uiThread);
        this.todoRepository = newTodoRepository;
    }

    @Override
    Observable<List<Todo>> buildUseCaseObservable(Void nothing) {
        return todoRepository.todos();
    }


}
