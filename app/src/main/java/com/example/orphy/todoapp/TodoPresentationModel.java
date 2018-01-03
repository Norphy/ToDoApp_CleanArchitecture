package com.example.orphy.todoapp;

/**
 * Created on 12/6/2017.
 */

public class TodoPresentationModel {
    private int ID;
    private final String DESCRIPTION;
    private final boolean IS_CHECKED;
    private final boolean IS_PINNED;
    private final int PRIORITY;

    public TodoPresentationModel(int id, String description, boolean isChecked, boolean isPinned, int priority) {
        this.ID = id;
        this.DESCRIPTION = description;
        this.IS_CHECKED = isChecked;
        this.IS_PINNED = isPinned;
        this.PRIORITY = priority;
    }

    public TodoPresentationModel(String description, boolean isChecked, boolean isPinned, int priority) {
        this.DESCRIPTION = description;
        this.IS_CHECKED = isChecked;
        this.IS_PINNED = isPinned;
        this.PRIORITY = priority;
    }

    //Getter Methods
    public int getId() {
        return ID;
    }

    public String getDescription() {
        return DESCRIPTION;
    }

    public boolean getIsChecked() {
        return IS_CHECKED;
    }

    public boolean getIsPinned() {
        return IS_PINNED;
    }

    public int getPriority() {
        return PRIORITY;
    }


}
