package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.Button;

import java.util.List;

public class AllTasks extends AppCompatActivity {
    AppDatabase db;
    List<Task> listOfTasks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_taks);

        db= Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"tasks")
                .allowMainThreadQueries().build();
        listOfTasks=db.taskDao().getTasks();


        RecyclerView recyclerView = findViewById(R.id.alltaskrecycleview);



        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AllTaskRecyclerViewAdapter(listOfTasks, null));


    }
}
