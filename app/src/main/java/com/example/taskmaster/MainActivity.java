package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amazonaws.amplify.generated.graphql.ListTasksQuery;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserState;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferService;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import java.util.List;

import javax.annotation.Nonnull;

public class MainActivity extends AppCompatActivity implements MyTaskRecyclerViewAdapter.OnListFragmentInteractionListener {

    static AppDatabase db;
    private AWSAppSyncClient mAWSAppSyncClient;


    private String TAG = "zyihang.main";
    List<Task> listOfTasks;
    MyTaskRecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hit button and go to add task page
        Button goToTheAddTaskPage = findViewById(R.id.button);
        goToTheAddTaskPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAddTaskPageIntent = new Intent(MainActivity.this, Addtask.class);
                MainActivity.this.startActivity(goToAddTaskPageIntent);
            }
        });

        //hit button and go to all tasks page
        Button goToAllTasksPage = findViewById(R.id.button2);
        goToAllTasksPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAllTasksPageIntent = new Intent(MainActivity.this, AllTasks.class);
                MainActivity.this.startActivity(goToAllTasksPageIntent);
            }
        });

        // hit button and go to setting page

        Button goToSettingPage = findViewById(R.id.setting);
        goToSettingPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSettingPageIntent = new Intent(MainActivity.this, Setting.class);
                MainActivity.this.startActivity(goToSettingPageIntent);
            }
        });


        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "tasks")
                .allowMainThreadQueries().build();
        listOfTasks = db.taskDao().getTasks();

//            db.taskDao().addTask(new Task("clean the house","cleaning","assigned"));
//            db.taskDao().addTask(new Task("do the laundry","landury","complete"));
//            db.taskDao().addTask(new Task("cook dinner","cooking","inprogress"));
//            db.taskDao().addTask(new Task("work on lab","working","new"));
//            db.taskDao().addTask(new Task("clean the house","cleaning","assigned"));
//            db.taskDao().addTask(new Task("do the laundry","landury","complete"));
//            db.taskDao().addTask(new Task("cook dinner","cooking","inprogress"));
//            db.taskDao().addTask(new Task("work on lab","working","new"));
        RecyclerView recyclerView = findViewById(R.id.tasksrecyclerView);
//
//
//
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyTaskRecyclerViewAdapter(listOfTasks, this);
        recyclerView.setAdapter(adapter);


        mAWSAppSyncClient = AWSAppSyncClient.builder()
                .context(getApplicationContext())
                .awsConfiguration(new AWSConfiguration(getApplicationContext()))
                .build();

        runQuery();

        //sign out

        Button signOutButton = findViewById(R.id.signout);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AWSMobileClient.getInstance().signOut();

                MainActivity.this.startActivity(new Intent(MainActivity.this, MainActivity.class));


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


        getApplicationContext().startService(new Intent(getApplicationContext(), TransferService.class));


        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {

                    @Override
                    public void onResult(final UserStateDetails userStateDetails) {
                        Log.i("INIT", "onResult: " + userStateDetails.getUserState());


                        AWSMobileClient.getInstance().showSignIn(MainActivity.this, new Callback<UserStateDetails>() {
                            @Override
                            public void onResult(UserStateDetails result) {
                                Log.d(TAG, "onResult: " + result.getUserState());
                                if (result.getUserState().equals(UserState.SIGNED_OUT)) {

                                    AWSMobileClient.getInstance().showSignIn(MainActivity.this, new Callback<UserStateDetails>() {
                                        @Override
                                        public void onResult(UserStateDetails result) {
                                            Log.d(TAG, "onResult: " + result.getUserState());
                                            if(result.getUserState().equals(UserState.SIGNED_IN)){
                                                uploadWithTransferUtility();

                                            }


                                        }

                                        @Override
                                        public void onError(Exception e) {
                                            Log.e(TAG, "onError: ", e);
                                        }
                                    });
                                }

                            }


                            @Override
                            public void onError(Exception e) {
                                Log.e(TAG, "onError: ", e);
                            }

                        });


                    }



            @Override
                    public void onError(Exception e) {
                        Log.e("INIT", "Initialization error.", e);
                    }
                }
        );


    }

    public void runQuery() {
        mAWSAppSyncClient.query(ListTasksQuery.builder().build())
                .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK)
                .enqueue(tasksCallback);
    }

    private GraphQLCall.Callback<ListTasksQuery.Data> tasksCallback = new GraphQLCall.Callback<ListTasksQuery.Data>() {
        @Override
        public void onResponse(@Nonnull final Response<ListTasksQuery.Data> response) {
            Log.i("zhang", response.data().listTasks().items().toString());
            Handler handler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);
                    List<ListTasksQuery.Item> tasks = response.data().listTasks().items();

                    listOfTasks.clear();

                    for (ListTasksQuery.Item taskFromDyno : tasks) {
                        listOfTasks.add(new Task(taskFromDyno));


                    }
                    adapter.notifyDataSetChanged();


                }

            };

            handler.obtainMessage().sendToTarget();

        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("ERROR", e.toString());
        }

    };

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "started");
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String username = p.getString("username", "") + " Task's";
        TextView showusername = findViewById(R.id.mainpageusername);
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

    public void uploadWithTransferUtility() {

        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance()))
                        .build();

        File file = new File(getApplicationContext().getFilesDir(), "sample.txt");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.append("Howdy World!");
            writer.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        TransferObserver uploadObserver =
                transferUtility.upload(
                        "public/sample.txt",
                        new File(getApplicationContext().getFilesDir(), "sample.txt"));

        // Attach a listener to the observer to get state update and progress notifications
        uploadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    // Handle a completed upload.
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                int percentDone = (int) percentDonef;

                Log.d(TAG, "ID:" + id + " bytesCurrent: " + bytesCurrent
                        + " bytesTotal: " + bytesTotal + " " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {
                // Handle errors
            }

        });

        // If you prefer to poll for the data, instead of attaching a
        // listener, check for the state and progress in the observer.
        if (TransferState.COMPLETED == uploadObserver.getState()) {
            // Handle a completed upload.
        }

        Log.d(TAG, "Bytes Transferred: " + uploadObserver.getBytesTransferred());
        Log.d(TAG, "Bytes Total: " + uploadObserver.getBytesTotal());
    }



    @Override
    public void onTaskInteraction(Task task) {
        Log.i(TAG,"CLICKED");
    }

}
