package com.example.orphy.todoapp;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.orphy.todoapp.adapter.TodoAdapter;
import com.example.orphy.todoapp.viewmodel.AddTodoViewModel;

/**
 * Created on 12/12/2017.
 */

public class AddTodoActivity extends AppCompatActivity {

    private EditText mDescriptionEditText;
    private Spinner mPrioritySpinner;
    private CheckBox mCheckBox;
    private String mPriorityString;

    private AddTodoViewModel mAddTodoViewModel;

    @Override
    public void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);
        setContentView(R.layout.add_todo_layout);

        mDescriptionEditText = findViewById(R.id.add_description_edit_text);
        mPrioritySpinner = findViewById(R.id.add_priority_spinner);
        mCheckBox = findViewById(R.id.add_todo_check_box);

        setupSpinner();

        mAddTodoViewModel = ViewModelProviders.of(this).get(AddTodoViewModel.class);

        Button addTodoButton = findViewById(R.id.add_todo_button);
        addTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(mDescriptionEditText.getText().toString().trim())) {
                    addTodo();
                } else {
                    Toast.makeText(AddTodoActivity.this, "Please enter a description and priority before adding", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addTodo() {
        String descriptionString = mDescriptionEditText.getText().toString().trim();
        boolean isChecked = mCheckBox.isChecked();
        mAddTodoViewModel.getNewTodo(descriptionString, mPriorityString, isChecked);
        mAddTodoViewModel.addTodoDatabase();
        Toast.makeText(this, "Added a todo successfully", Toast.LENGTH_SHORT).show();
    }

    private void setupSpinner() {
        ArrayAdapter prioritySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.priority_spinner_options, android.R.layout.simple_spinner_item);

        prioritySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mPrioritySpinner.setAdapter(prioritySpinnerAdapter);

        mPrioritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String selection = (String) adapterView.getItemAtPosition(position);
                if(!TextUtils.isEmpty(selection)) {
                    if(selection.equals(getString(R.string.high_priority))) {
                        mPriorityString = String.valueOf(TodoAdapter.HIGH_PRIORITY);
                    } else if(selection.equals(getString(R.string.medium_priority))) {
                        mPriorityString = String.valueOf(TodoAdapter.MED_PRIORITY);
                    } else if(selection.equals(getString(R.string.low_priority))) {
                        mPriorityString = String.valueOf(TodoAdapter.LOW_PRIORITY);
                    } else { throw new IllegalArgumentException("Illegal priority value entered through spinner");}
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}
