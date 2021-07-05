package com.geek.todoapp.room;



import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.geek.todoapp.models.Task;

@Database(entities = {Task.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract TaskDao taskDao();
}
