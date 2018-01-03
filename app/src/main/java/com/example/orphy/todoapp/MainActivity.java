package com.example.orphy.todoapp;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.orphy.todoapp.adapter.TodoAdapter;
import com.example.orphy.todoapp.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class MainActivity extends AppCompatActivity {

    private TodoAdapter mTodoAdapter;
    private RecyclerView mTodoRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTodoRecyclerView = findViewById(R.id.todo_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mTodoRecyclerView.setLayoutManager(linearLayoutManager);
        mTodoRecyclerView.setHasFixedSize(true);

        mTodoAdapter = new TodoAdapter(new ArrayList<TodoPresentationModel>(), this);
        mTodoRecyclerView.setAdapter(mTodoAdapter);

        final MainActivityViewModel mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mainActivityViewModel.loadList();
        mainActivityViewModel.getTheTodos().observe(this, new Observer<List<TodoPresentationModel>>() {
                @Override
                public void onChanged(@Nullable List<TodoPresentationModel> todoPresentationModels) {
                    mTodoAdapter.swapList(todoPresentationModels);
                }
        });

        FloatingActionButton fab = findViewById(R.id.add_todo_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addTodoIntent = new Intent(MainActivity.this, AddTodoActivity.class);
                startActivity(addTodoIntent);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final TodoPresentationModel currentTodo = (TodoPresentationModel) viewHolder.itemView.getTag();
                mainActivityViewModel.delete(currentTodo);
                Snackbar.make(findViewById(R.id.main_relative_layout), "Todo Deleted", Snackbar.LENGTH_LONG).setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mainActivityViewModel.undoDelete(currentTodo);
                    }
                }).show();
            }
        }).attachToRecyclerView(mTodoRecyclerView);
    }
}
