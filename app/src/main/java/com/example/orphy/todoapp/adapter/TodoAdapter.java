package com.example.orphy.todoapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.orphy.data.TodoModelMapper;
import com.example.orphy.data.repository.TodoDataRepository;
import com.example.orphy.data.repository.datasource.TodoLocalDataStore;
import com.example.orphy.domain.Todo;
import com.example.orphy.todoapp.R;
import com.example.orphy.todoapp.TodoPresentationModel;
import com.example.orphy.todoapp.TodoPresentationModelMapper;

import java.util.List;

/**
 * Created on 12/12/2017.
 */

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private List<TodoPresentationModel> mTodoList;
    //private SortedList<TodoPresentationModel> mTodoSortedList;
    private boolean mOnBind;

    public static final int HIGH_PRIORITY = 1;
    public static final int MED_PRIORITY = 2;
    public static final int LOW_PRIORITY = 3;
    private static final String TAG = TodoAdapter.class.getSimpleName();

    private Context mContext;
    final private TodoModelMapper todoModelMapper = new TodoModelMapper();
    final private TodoDataRepository todoDataRepository;
    final private TodoPresentationModelMapper todoPresentationModelMapper = new TodoPresentationModelMapper();


    public TodoAdapter(List<TodoPresentationModel> newTodoList, Context context) {
        this.mContext = context;
        this.mTodoList = newTodoList;
        //mTodoSortedList = sortTodos(newTodoList);
        final TodoLocalDataStore todoLocalDataStore = new TodoLocalDataStore(mContext);
        todoDataRepository = new TodoDataRepository(todoModelMapper, todoLocalDataStore);
    }

    @Override
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.todo_item_layout, parent, false);
        TodoViewHolder todoViewHolder = new TodoViewHolder(view);
        return todoViewHolder;
    }

    @Override
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        TodoPresentationModel currentTodo = mTodoList.get(position);//mTodoSortedList.get(position);

        String description = currentTodo.getDescription();
        boolean isChecked = currentTodo.getIsChecked();
        boolean isPinned = currentTodo.getIsPinned();
        int priority = currentTodo.getPriority();

        mOnBind = true;
        holder.todoDescriptionTextView.setText(description);
        holder.todoCheckBox.setChecked(isChecked);
        holder.itemView.setTag(currentTodo);
        mOnBind = false;
        createTodoView(holder, currentTodo);

    }

    @Override
    public int getItemCount() {
        //return mTodoSortedList.size();
        return mTodoList.size();
    }

    public void swapList(List<TodoPresentationModel> newList) {
        mTodoList = newList;
        //mTodoSortedList = sortTodos(todoList);
        notifyDataSetChanged();
    }

    class TodoViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {

        TextView todoDescriptionTextView;
        CheckBox todoCheckBox;


        public TodoViewHolder(View itemView) {
            super(itemView);

            todoDescriptionTextView = itemView.findViewById(R.id.description_text_view);
            todoCheckBox = itemView.findViewById(R.id.todo_check_box);

            todoCheckBox.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (!mOnBind) {
                TodoPresentationModel currentTodoPresentationModel = mTodoList.get(getAdapterPosition());//mTodoSortedList.get(getAdapterPosition());
                TodoPresentationModel newTodoPresentationModel = new TodoPresentationModel(currentTodoPresentationModel.getId(), currentTodoPresentationModel.getDescription(),
                        b, currentTodoPresentationModel.getIsPinned(), currentTodoPresentationModel.getPriority());
                Todo updatedTodo = todoPresentationModelMapper.transformFromPresentToDomain(newTodoPresentationModel);
                todoDataRepository.updateTodo(updatedTodo);

                /*
                if (b) {
                    mTodoList.remove(getAdapterPosition());
                    mTodoList.add(currentTodoPresentationModel);
                } else {
                    mTodoList.remove(getAdapterPosition());
                    mTodoList.add(0, currentTodoPresentationModel);
                }*/

                notifyDataSetChanged();
            }
        }
    }


    SortedList<TodoPresentationModel> sortTodos(List<TodoPresentationModel> todoList) {
        SortedList<TodoPresentationModel> todoSortedList = new SortedList<TodoPresentationModel>(TodoPresentationModel.class, new SortedList.Callback<TodoPresentationModel>() {
            @Override
            public int compare(TodoPresentationModel o1, TodoPresentationModel o2) {
                if(o1.getIsChecked() && !o2.getIsChecked()) {
                    return 1;
                } else if(!o1.getIsChecked() && o2.getIsChecked()) {
                    return -1;
                } else {
                    return 0;
                }
            }

            @Override
            public void onChanged(int position, int count) {

            }

            @Override
            public boolean areContentsTheSame(TodoPresentationModel oldItem, TodoPresentationModel newItem) {
                return false;
            }

            @Override
            public boolean areItemsTheSame(TodoPresentationModel item1, TodoPresentationModel item2) {
                return false;
            }

            @Override
            public void onInserted(int position, int count) {

            }

            @Override
            public void onRemoved(int position, int count) {

            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {

            }
        });
        for(TodoPresentationModel todo:todoList) {
            todoSortedList.add(todo);
        }
        return todoSortedList;
    }

    private void createTodoView(TodoViewHolder holder, TodoPresentationModel todoPresentationModel) {
        switch(todoPresentationModel.getPriority()) {
            case HIGH_PRIORITY:
                holder.itemView.setBackgroundColor(Color.parseColor("#FFCDD2"));
                break;
            case MED_PRIORITY:
                holder.itemView.setBackgroundColor(Color.parseColor("#FFF9C4"));
                break;
            case LOW_PRIORITY:
                holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(android.R.color.white));
                break;
            default:
                throw new IllegalArgumentException("Invalid priority inserted.");
        }

        if(todoPresentationModel.getIsChecked()) {
            holder.todoDescriptionTextView.setTextColor(holder.itemView.getResources().getColor(android.R.color.darker_gray));
            holder.todoDescriptionTextView.setPaintFlags(holder.todoDescriptionTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.itemView.setBackgroundColor(Color.parseColor("#F5F5F5"));
        } else {
            holder.todoDescriptionTextView.setPaintFlags(holder.todoDescriptionTextView.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
            holder.todoDescriptionTextView.setTextColor(holder.itemView.getResources().getColor(android.R.color.primary_text_light));
        }
    }

}
