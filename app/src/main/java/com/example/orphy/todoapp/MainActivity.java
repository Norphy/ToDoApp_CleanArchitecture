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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;

import com.example.orphy.data.Constants;
import com.example.orphy.data.SharedPreferencesManager;
import com.example.orphy.todoapp.adapter.TodoAdapter;
import com.example.orphy.todoapp.viewmodel.MainActivityViewModel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;

public class MainActivity extends AppCompatActivity {

    private TodoAdapter mTodoAdapter;
    private RecyclerView mTodoRecyclerView;
    SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ignored) {
        }
        sharedPreferencesManager = SharedPreferencesManager.getInstance(this);
        if(sharedPreferencesManager.read(Constants.SharedPrefs.PREF_THEME_KEY, getResources().getBoolean(R.bool.pref_theme_def_value))) {
            setTheme(R.style.AppTheme_Dark);
        }
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

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                mTodoAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }



            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final TodoPresentationModel currentTodo = (TodoPresentationModel) viewHolder.itemView.getTag();
                mainActivityViewModel.delete(currentTodo);
                Snackbar.make(findViewById(R.id.main_coordinator_layout), "Todo Deleted", Snackbar.LENGTH_LONG).setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mainActivityViewModel.undoDelete(currentTodo);
                    }
                }).show();
            }
        }).attachToRecyclerView(mTodoRecyclerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sharedPreferencesManager.read(SharedPreferencesManager.RECREATE_ACTIVITY_KEY,
                getResources().getBoolean(R.bool.recreate_activity_def_value))) {
            sharedPreferencesManager.write(SharedPreferencesManager.RECREATE_ACTIVITY_KEY, false);
            recreate();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
