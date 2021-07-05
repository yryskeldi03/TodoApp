package com.geek.todoapp.room;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.geek.todoapp.models.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Query("SELECT * FROM task ORDER BY id DESC")
    LiveData<List<Task>> getAllLive();

    @Query("SELECT * FROM task ORDER BY title ASC")
    LiveData<List<Task>> getAllSorted();

    @Insert
    void insert(Task task);

    @Delete
    void delete(Task task);

}
