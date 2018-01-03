package com.example.orphy.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
import java.util.concurrent.Callable;


import io.reactivex.Flowable;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created on 12/5/2017.
 */
@Dao
public interface TodoModelDao {

    @Query("select * from TodoModel")
    Flowable<List<TodoModel>> getAllTodoItems();

    //@Query("select * from TodoModel where id = :id")
    //Flowable<TodoModel> getTodoById();

    @Insert(onConflict = REPLACE)
    long addTodo(TodoModel todoModel);

    @Update(onConflict = REPLACE)
    int updateTodo(TodoModel todoModel);

    @Delete
    int deleteTodo(TodoModel todoModel);
}
