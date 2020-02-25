package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class TaskDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        TextView title=findViewById(R.id.tasktitle);

    }

    @Override
    protected void onStart() {
        super.onStart();
        TextView tasktitle = findViewById(R.id.tasktitle);
        String titleTask = getIntent().getStringExtra("mTitleView");
        tasktitle.setText(titleTask);
    }


}
