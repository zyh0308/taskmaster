package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "zyihang.main";

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

        // hit button and go to setting page

        Button goToSettingPage=findViewById(R.id.setting);
        goToSettingPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSettingPageIntent=new Intent(MainActivity.this, Setting.class);
                MainActivity.this.startActivity(goToSettingPageIntent);
            }
        });

        //hit button and go to task detail page
//
//        final Button goToTaskOneDetailPage=findViewById(R.id.taskone);
//        goToTaskOneDetailPage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent goToTaskOneDetailPageIntent=new Intent(MainActivity.this, TaskDetail.class);
//                goToTaskOneDetailPageIntent.putExtra("task",goToTaskOneDetailPage.getText().toString());
//                MainActivity.this.startActivity(goToTaskOneDetailPageIntent);
//            }
//        });
//
//        final Button goToTaskTwoDetailPage=findViewById(R.id.tasktwo);
//        goToTaskTwoDetailPage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent goToTaskTwoDetailPageIntent=new Intent(MainActivity.this, TaskDetail.class);
//                goToTaskTwoDetailPageIntent.putExtra("task",goToTaskTwoDetailPage.getText().toString());
//                MainActivity.this.startActivity(goToTaskTwoDetailPageIntent);
//            }
//        });
//
//        final Button goToTaskThreeDetailPage=findViewById(R.id.taskthree);
//        goToTaskThreeDetailPage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent goToTaskThreeDetailPageIntent=new Intent(MainActivity.this, TaskDetail.class);
//                goToTaskThreeDetailPageIntent.putExtra("task",goToTaskThreeDetailPage.getText().toString());
//
//                MainActivity.this.startActivity(goToTaskThreeDetailPageIntent);
//            }
//        });
//
//



    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "started");
    }

    @Override
    protected void onResume(){
        super.onResume();
        SharedPreferences p= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String username=p.getString("username","")+" Task's";
        TextView showusername=findViewById(R.id.mainpageusername);
        showusername.setText(username);
    }



    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "paused");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "stopped");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "destroyed");
    }
}
