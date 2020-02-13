package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.prefs.PreferenceChangeEvent;

public class Setting extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        //hit save button to go back to home pages
        Button saveUserName=findViewById(R.id.saveusername);
        saveUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //store username using sharedpreferences
               EditText inputUsername = findViewById(R.id.username);
                String username=inputUsername.getText().toString();
                SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor=p.edit();
                editor.putString("username",username);
                editor.apply();
                Intent saveUserNameIntent=new Intent(Setting.this, MainActivity.class);
               Setting.this.startActivity(saveUserNameIntent);

            }




    });
}
}
