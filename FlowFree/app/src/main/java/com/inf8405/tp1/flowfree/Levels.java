package com.inf8405.tp1.flowfree;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Levels extends AppCompatActivity {
    private Button btnLevel_71 = null;
    private Button btnLevel_72 = null;
    private Button btnLevel_73 = null;
    private Button btnLevel_81 = null;
    private Button btnLevel_82 = null;
    private Button btnLevel_83 = null;
    private TextView TxtVwGrid_7 = null;
    private TextView TxtVwGrid_8 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void StartGame(View view) {
        Intent intent = new Intent(getApplicationContext(), Game.class);
        startActivity(intent);
    }
}
