package com.inf8405.tp1.flowfree;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToLevels(View view) {
        Intent intent = new Intent(getApplicationContext(), Levels.class);
        startActivity(intent);
    }

    public void gameExit(View view) {
     finish();
    }
}
