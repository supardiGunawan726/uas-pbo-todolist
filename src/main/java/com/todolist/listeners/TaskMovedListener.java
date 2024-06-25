package com.todolist.listeners;

import com.todolist.frames.TaskPanel;

public interface TaskMovedListener {
    void onMoved(String moveDirection, TaskPanel taskPanel);
}
