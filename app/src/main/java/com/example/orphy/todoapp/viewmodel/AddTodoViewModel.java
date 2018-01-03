package com.example.orphy.todoapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.orphy.data.TodoModelMapper;
import com.example.orphy.data.repository.TodoDataRepository;
import com.example.orphy.data.repository.datasource.TodoLocalDataStore;
import com.example.orphy.todoapp.TodoPresentationModel;
import com.example.orphy.todoapp.TodoPresentationModelMapper;

/**
 * Created on 12/13/2017.
 */

public class AddTodoViewModel extends AndroidViewModel {

    private String mDescription;
    private String mPriority;
    private boolean mIsChecked;
    private int mPriorityInt;
    TodoPresentationModel newTodoPresenationModel;


    private Context mContext = getApplication().getApplicationContext();
    private TodoModelMapper todoModelMapper = new TodoModelMapper();
    private TodoLocalDataStore todoLocalDataStore = new TodoLocalDataStore(mContext);
    private TodoDataRepository todoDataRepository = new TodoDataRepository(todoModelMapper,todoLocalDataStore);
    private TodoPresentationModelMapper todoPresentationModelMapper = new TodoPresentationModelMapper();

    public AddTodoViewModel(@NonNull Application application) {
        super(application);
    }


    public void getNewTodo(String description, String priority, boolean isChecked) {
        this.mDescription = description;
        this.mPriority = priority;
        this.mIsChecked = isChecked;
        try {
            mPriorityInt = Integer.parseInt(mPriority);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void addTodoDatabase() {
        newTodoPresenationModel = new TodoPresentationModel(mDescription, mIsChecked, false, mPriorityInt);
        todoDataRepository.addTodo(todoPresentationModelMapper.transformFromPresentToDomain(newTodoPresenationModel));
    }

}
