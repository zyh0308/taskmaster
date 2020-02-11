package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Addtask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);

        Button addTaskButton =findViewById(R.id.button3);
        addTaskButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
               Toast text =Toast.makeText(getApplicationContext(),"Submitted", Toast.LENGTH_SHORT);
               text.show();


            }

        });
    }
}
