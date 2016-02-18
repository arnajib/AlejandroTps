package com.inf8405.tp1.flowfree;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by mahdi on 16-02-17.
 */
public class Levels_7x7 extends AppCompatActivity {
    private Button btnLevel_71 = null;
    private Button btnLevel_72 = null;
    private Button btnLevel_73 = null;
    private Button btnLevel_81 = null;
    private Button btnLevel_82 = null;
    private Button btnLevel_83 = null;
    private TextView TxtVwGrid_7 = null;
    private TextView TxtVwGrid_8 = null;
    private int sizel;
    private int levell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_7x7);
        boolean isFileExist = false;
        try {
            FileInputStream fis = openFileInput("dataLevelSize.txt");
            if(fis != null)
                isFileExist = true;
            InputStreamReader isr = new InputStreamReader(fis);
            char[] dataChar = new char[100];
            String final_data = "";
            int size1;
            try {
                while((size1=isr.read(dataChar))>0)
                {
                    String read_data = String.copyValueOf(dataChar, 0, size1);
                    final_data+=read_data;
                    dataChar = new char[100];

                }
                //Toast.makeText(getBaseContext(), "Contenu: " + final_data, Toast.LENGTH_LONG).show();
                String[] splitedStr = final_data.split(";");
                String sz = splitedStr[0];
                String lvl = splitedStr[1];
                sizel = Integer.valueOf(sz);
                levell = Integer.valueOf(lvl);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        btnLevel_71 = (Button) findViewById(R.id.level_71);
        btnLevel_72 = (Button) findViewById(R.id.level_72);
        btnLevel_73 = (Button) findViewById(R.id.level_73);
        btnLevel_81 = (Button) findViewById(R.id.level_81);
        btnLevel_82 = (Button) findViewById(R.id.level_82);
        btnLevel_83 = (Button) findViewById(R.id.level_83);
        if(isFileExist) {
            if (levell == 1 && sizel == 7) {
                btnLevel_72.setEnabled(false);
                btnLevel_72.setAlpha(.5f);
                btnLevel_72.setClickable(false);
                btnLevel_73.setEnabled(false);
                btnLevel_73.setAlpha(.5f);
                btnLevel_73.setClickable(false);
                btnLevel_81.setEnabled(false);
                btnLevel_81.setAlpha(.5f);
                btnLevel_81.setClickable(false);
                btnLevel_82.setEnabled(false);
                btnLevel_82.setAlpha(.5f);
                btnLevel_82.setClickable(false);
                btnLevel_83.setEnabled(false);
                btnLevel_83.setAlpha(.5f);
                btnLevel_83.setClickable(false);
            } else if (levell == 2 && sizel == 7) {
                btnLevel_73.setEnabled(false);
                btnLevel_81.setEnabled(false);
                btnLevel_82.setEnabled(false);
                btnLevel_83.setEnabled(false);
            } else if (levell == 3 && sizel == 7) {
                btnLevel_81.setEnabled(false);
                btnLevel_82.setEnabled(false);
                btnLevel_83.setEnabled(false);
            } else if (levell == 1 && sizel == 8) {
                btnLevel_82.setEnabled(false);
                btnLevel_83.setEnabled(false);
            } else if (levell == 2 && sizel == 8) {
                btnLevel_83.setEnabled(false);
            }
        }
        else{
            btnLevel_72.setEnabled(false);
            btnLevel_72.setAlpha(.5f);
            btnLevel_72.setClickable(false);
            btnLevel_73.setEnabled(false);
            btnLevel_73.setAlpha(.5f);
            btnLevel_73.setClickable(false);
            btnLevel_81.setEnabled(false);
            btnLevel_81.setAlpha(.5f);
            btnLevel_81.setClickable(false);
            btnLevel_82.setEnabled(false);
            btnLevel_82.setAlpha(.5f);
            btnLevel_82.setClickable(false);
            btnLevel_83.setEnabled(false);
            btnLevel_83.setAlpha(.5f);
            btnLevel_83.setClickable(false);
        }

        btnLevel_71.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Game.class);
                Bundle sizeRecu = new Bundle();
                Bundle levelRecu = new Bundle();
                sizeRecu.putInt("size", 7); // size
                levelRecu.putInt("level", 1); // levle
                intent.putExtras(sizeRecu);
                intent.putExtras(levelRecu);
                startActivity(intent);
                finish();

            }
        });
        btnLevel_72.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Game.class);
                Bundle sizeRecu = new Bundle();
                Bundle levelRecu = new Bundle();
                sizeRecu.putInt("size", 7); // size
                levelRecu.putInt("level", 2); // levle
                intent.putExtras(sizeRecu);
                intent.putExtras(levelRecu);
                startActivity(intent);
                finish();

            }
        });
        btnLevel_73.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Game.class);
                Bundle sizeRecu = new Bundle();
                Bundle levelRecu = new Bundle();
                sizeRecu.putInt("size", 7); // size
                levelRecu.putInt("level", 3); // levle
                intent.putExtras(sizeRecu);
                intent.putExtras(levelRecu);
                startActivity(intent);
                finish();

            }
        });
        btnLevel_81.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Game.class);
                Bundle sizeRecu = new Bundle();
                Bundle levelRecu = new Bundle();
                sizeRecu.putInt("size", 8); // size
                levelRecu.putInt("level", 1); // levle
                intent.putExtras(sizeRecu);
                intent.putExtras(levelRecu);
                startActivity(intent);
                finish();

            }
        });
        btnLevel_82.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Game.class);
                Bundle sizeRecu = new Bundle();
                Bundle levelRecu = new Bundle();
                sizeRecu.putInt("size", 8); // size
                levelRecu.putInt("level", 2); // levle
                intent.putExtras(sizeRecu);
                intent.putExtras(levelRecu);
                startActivity(intent);
                finish();

            }
        });
        btnLevel_83.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Game.class);
                Bundle sizeRecu = new Bundle();
                Bundle levelRecu = new Bundle();
                sizeRecu.putInt("size", 8); // size
                levelRecu.putInt("level", 3); // levle
                intent.putExtras(sizeRecu);
                intent.putExtras(levelRecu);
                startActivity(intent);
                finish();

            }
        });
    }

//    public void StartGame(View view) {
//        Intent intent = new Intent(getApplicationContext(), Game.class);
//        startActivity(intent);
//    }
}
