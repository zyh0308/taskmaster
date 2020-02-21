package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class Addtask extends AppCompatActivity {
    AppDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);

        db= Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"tasks")
                .allowMainThreadQueries().build();

        //hit submit to go to task detail page


        Button addTaskButton =findViewById(R.id.button3);
        addTaskButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast text =Toast.makeText(getApplicationContext(),"Submitted", Toast.LENGTH_SHORT);
                text.show();

                EditText addtasktitleinput=findViewById(R.id.addtasktitle);
                String tasktitle=addtasktitleinput.getText().toString();


                EditText addtaskdetailinput=findViewById(R.id.addtaskdetail);
                String taskdetail=addtaskdetailinput.getText().toString();

                EditText addtaskstateinput=findViewById(R.id.addtaskstate);
                String taskstate=addtaskstateinput.getText().toString();

                Task newTask=new Task(tasktitle,taskdetail,taskstate);
                db.taskDao().addTask(newTask);







            }

        });
    }
}
