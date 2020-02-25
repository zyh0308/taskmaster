package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.amplify.generated.graphql.CreateTaskMutation;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import java.util.List;

import javax.annotation.Nonnull;

import type.CreateTaskInput;

public class Addtask extends AppCompatActivity {
    AppDatabase db;
    private AWSAppSyncClient mAWSAppSyncClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);

        db= Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"tasks")
                .allowMainThreadQueries().build();

        mAWSAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();

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

                runMutation(tasktitle,taskdetail,taskstate);


                Task newTask=new Task(tasktitle,taskdetail,taskstate);
                db.taskDao().addTask(newTask);







            }

        });
    }


    public void runMutation(String title,String body,String state){
        CreateTaskInput createTaskInput = CreateTaskInput.builder().
                title(title).
                body(body).
                state(state).
                build();

        mAWSAppSyncClient.mutate(CreateTaskMutation.builder().input(createTaskInput).build())
                .enqueue(mutationCallback);
    }

    private GraphQLCall.Callback<CreateTaskMutation.Data> mutationCallback = new GraphQLCall.Callback<CreateTaskMutation.Data>() {
        @Override
        public void onResponse(@Nonnull Response<CreateTaskMutation.Data> response) {
            Log.i("Results", "Added Todo");
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("Error", e.toString());
        }
    };
}
