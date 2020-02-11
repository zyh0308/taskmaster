package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hit button and go to add task page
        Button goToTheAddTaskPage=findViewById(R.id.button);
        goToTheAddTaskPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAddTaskPageIntent=new Intent(MainActivity.this, Addtask.class);
                MainActivity.this.startActivity(goToAddTaskPageIntent);
            }
        });

        //hit button and go to all tasks page
        Button goToAllTasksPage=findViewById(R.id.button2);
        goToAllTasksPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAllTasksPageIntent=new Intent(MainActivity.this, AllTasks.class);
                MainActivity.this.startActivity(goToAllTasksPageIntent);
            }
        });


    }
}
