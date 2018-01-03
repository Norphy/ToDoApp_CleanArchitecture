package com.example.orphy.domain;

public class Todo {
    private int id;
    private final String DESCRIPTION;
    private final boolean IS_CHECKED;
    private final boolean IS_PINNED;
    private final int PRIORITY;

    public Todo(int id, String description, boolean isChecked, boolean isPinned, int priority) {
        this.id = id;
        this.DESCRIPTION = description;
        this.IS_CHECKED = isChecked;
        this.IS_PINNED = isPinned;
        this.PRIORITY = priority;
    }

    public Todo(String description, boolean isChecked, boolean isPinned, int priority) {
        this.DESCRIPTION = description;
        this.IS_CHECKED = isChecked;
        this.IS_PINNED = isPinned;
        this.PRIORITY = priority;
    }

    //Getter Methods
    public int getId() {
        return id;
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
