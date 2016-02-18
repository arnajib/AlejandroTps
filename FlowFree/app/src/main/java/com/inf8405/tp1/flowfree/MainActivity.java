package com.inf8405.tp1.flowfree;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private Button btn7x7 = null;
    private Button btn8x8 = null;
    private Button btnQuit = null;
    String dataStr;
    int data_block = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            FileInputStream fis = openFileInput("dataLevelSize.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            char[] dataChar = new char[data_block];
            String final_data = "";
            int size1;
            try {
                while((size1=isr.read(dataChar))>0)
                {
                    String read_data = String.copyValueOf(dataChar, 0, size1);
                    final_data+=read_data;
                    dataChar = new char[data_block];

                }
                //Toast.makeText(getBaseContext(),"Contenu: " + final_data, Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        btn7x7 = (Button) findViewById(R.id.button_grille7x7);
        btn8x8 = (Button) findViewById(R.id.button_grille8x8);
        btnQuit = (Button) findViewById(R.id.button_exit);
        btn7x7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Levels_7x7.class);
                startActivity(intent);

            }
        });
        btn8x8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Levels_8x8.class);
                startActivity(intent);

            }
        });
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    finish();

            }
        });


    }

//    public void goToLevels(View view) {
//        Intent intent = new Intent(getApplicationContext(), Levels.class);
//        startActivity(intent);
//    }
//
//    public void gameExit(View view) {
//     finish();
//    }
}
