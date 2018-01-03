package com.example.orphy.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created on 12/5/2017.
 */
@Entity(tableName = "TodoModel")
public class TodoModel {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "isChecked")
    private boolean isChecked;
    @ColumnInfo(name = "isPinned")
    private boolean isPinned;
    @ColumnInfo(name = "priority")
    private int priority;

    public TodoModel(int id, String description, boolean isChecked, boolean isPinned, int priority) {
        this.id = id;
        this.description = description;
        this.isChecked = isChecked;
        this.isPinned = isPinned;
        this.priority = priority;
    }

    /*
    Getter methods
    */
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean getIsChecked() {
        return isChecked;
    }

    public boolean getIsPinned() {
        return isPinned;
    }

    public int getPriority() {
        return priority;
    }

    /*
    Setter methods
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public void setIsPinned(boolean isPinned) {
        this.isPinned = isPinned;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
