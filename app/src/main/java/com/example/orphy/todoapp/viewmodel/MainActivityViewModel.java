package com.example.orphy.todoapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.orphy.data.TodoModelMapper;
import com.example.orphy.data.repository.TodoDataRepository;
import com.example.orphy.data.repository.datasource.TodoLocalDataStore;
import com.example.orphy.domain.Todo;
import com.example.orphy.domain.TodoRepository;
import com.example.orphy.domain.interactor.DefaultObserver;
import com.example.orphy.domain.interactor.GetTodos;
import com.example.orphy.todoapp.TodoPresentationModel;
import com.example.orphy.todoapp.TodoPresentationModelMapper;
import com.example.orphy.todoapp.UiThread;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created on 12/12/2017.
 */

public class MainActivityViewModel extends AndroidViewModel {

    Context mContext = getApplication().getApplicationContext();
    private MutableLiveData<List<TodoPresentationModel>> todos = new MutableLiveData<>();
    private UiThread uiThread = new UiThread();
    private TodoModelMapper todoModelMapper = new TodoModelMapper();
    private TodoLocalDataStore todoLocalDataStore = new TodoLocalDataStore(mContext);
    private TodoDataRepository todoDataRepository = new TodoDataRepository(todoModelMapper,todoLocalDataStore);
    private TodoPresentationModel mDeletedTodo;
    private static final String TAG = MainActivityViewModel.class.getSimpleName();

    private TodoPresentationModelMapper todoPresentationModelMapper = new TodoPresentationModelMapper();

    private GetTodos getTodos = new GetTodos(todoDataRepository, uiThread);

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    private void setTodoAsLive(List<Todo> newTodos) {
        todos.setValue(todoPresentationModelMapper.transformListFromDomainToPresent(newTodos));
    }

    public void loadList() {
        this.getTodos.execute(new TodoListObserver(), null);
    }

    public MutableLiveData<List<TodoPresentationModel>> getTheTodos() {

        return todos;
    }

    @Override
    public void onCleared() {
        super.onCleared();
        getTodos.dispose();
    }

    public void delete(TodoPresentationModel todoPresentationModel) {
        mDeletedTodo = todoPresentationModel;
        todoDataRepository.deleteTodo(todoPresentationModelMapper.transformFromPresentToDomain(todoPresentationModel));

    }

    public void undoDelete(TodoPresentationModel todoPresentationModel) {
        Log.v(TAG, "UndoDelete ID: " + todoPresentationModel.getId());
        todoDataRepository.addTodo(todoPresentationModelMapper.transformFromPresentToDomain(todoPresentationModel));
    }

    private class TodoListObserver extends DefaultObserver<List<Todo>> {

        @Override
        public void onNext(List<Todo> observTodos) {
            setTodoAsLive(observTodos);
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
        }
    }

}
