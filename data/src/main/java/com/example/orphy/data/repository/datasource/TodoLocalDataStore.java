package com.example.orphy.data.repository.datasource;

import android.content.Context;
import android.util.Log;

import com.example.orphy.data.AppDatabase;
import com.example.orphy.data.TodoModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

/**
 * Created on 12/10/2017.
 */

public class TodoLocalDataStore implements TodoDataStore {

    private final static String TAG = TodoLocalDataStore.class.getSimpleName();
    private final AppDatabase appDatabase;

    public TodoLocalDataStore(Context context) {

        appDatabase = AppDatabase.getDatabase(context);
    }

    @Override
    public Observable<List<TodoModel>> todos() {

        Flowable<List<TodoModel>> list = appDatabase.todoModelDao().getAllTodoItems().subscribeOn(Schedulers.io()).observeOn(Schedulers.io());
        return list.toObservable();
    }

    @Override
    public Void addTodo(final TodoModel todoModel) {
        Log.v(TAG, "addTodo(TodoModel run");
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                long id = appDatabase.todoModelDao().addTodo(todoModel);
                if (id > 0) {
                    Log.v(TAG, "Todo successfully added with id: " + id);
                } else {
                    Log.v(TAG, "Todo may not have been added id: " + id);
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe();
        return null;
    }

    @Override
    public void updateTodo(final TodoModel todoModel) {
        Log.v(TAG, "updateTodo: " + todoModel.getId());
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                int numRows = appDatabase.todoModelDao().updateTodo(todoModel);
                if (numRows > 0) {
                    Log.v(TAG, "Successfully updated " + numRows + " with ID: " + todoModel.getId());
                } else {
                    Log.v(TAG, "Failed to update with ID: " + todoModel.getId());
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe();
    }

    @Override
    public void deleteTodo(final TodoModel todoModel) {
        Log.v(TAG, "deleteTodo: " + todoModel.getId());
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                int  numRows = appDatabase.todoModelDao().deleteTodo(todoModel);
                if(numRows > 0) {
                    Log.v(TAG, "Successfully deteled " + numRows + " row with ID: " + todoModel.getId());
                } else {
                    Log.v(TAG, "Failed to delete row with ID: " + todoModel.getId());
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe();
    }
}
