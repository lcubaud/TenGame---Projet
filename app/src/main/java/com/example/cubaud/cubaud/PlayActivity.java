package com.example.cubaud.cubaud;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


import tendroid.model.Position;
import tendroid.model.PositionList;
import tendroid.model.TenGame;

public class PlayActivity extends Activity {
    private static int score;
    TheApplication app;
    StringBuilder stringBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        if(MainActivity.n){
            score=TheApplication.readScore();
        }
        app = (TheApplication)this.getApplication();
        readBestScore("unfichier.txt");
        setBestScore();

    }


    public void savedGame(){
        TenGame currentGame = app.getGame();
        PositionList all = currentGame.allPositions();
        int size=currentGame.nbCol()*currentGame.nbLig();
        int gameVal[]=new int[size];
        int i=0;
        for(Position p : all){
           gameVal[i++]=currentGame.get(p);
        }
        writesavedGameFile("gameFic.txt",gameVal,size);
    }

    public void writesavedGameFile(String name,int ns[],int size){
        FileOutputStream fileOutputStream = null;
        try {
            //byte[] rc = ("\n").getBytes();
            fileOutputStream = openFileOutput(name, Context.MODE_PRIVATE);
            fileOutputStream.write((("" + size).getBytes()));
            //fileOutputStream.write(rc);
            for(int i=0; i<size; i++) {
                fileOutputStream.write(("" + ns[i]).getBytes() );
            }
            fileOutputStream.write((("" + score).getBytes()));
            //fileOutputStream.write(rc);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null)
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

    }


    public void onClickBack(View v){
        score=0;
        finish();
    }

    public void onClickReset(View view){
        app.reNew();
        ((GameView)findViewById(R.id.gameView)).reDraw();
        ((GameView)findViewById(R.id.gameView)).score0();
        ((TextView)findViewById(R.id.score)).setText("0");
        setBestScore();
    }

    public void onClickRule(View view){
        Intent ruleIntent = new Intent(this, RuleActivity.class);
        startActivity(ruleIntent);
    }

    public static int keepScore(){
       return score;
    }

    public void setScore(int score) {
        this.score=score;
        ((TextView)findViewById(R.id.score)).setText(score + "");
    }

    public void setBestScore(){
        if(stringBuilder != null){
        ((TextView)findViewById(R.id.bestscore)).setText("Best Score: "+stringBuilder);
        }
    }

    void readBestScore(String name){
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = openFileInput(name);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            stringBuilder = new StringBuilder();
            String line;

            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null)
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        if (stringBuilder != null)
            System.out.println(stringBuilder);
        else
            System.err.println("stringBuilder est null");

    }

    void writeBestScore(String name,int s){
        int bs;
        if(stringBuilder==null){
            bs=0;
        }
        else{
            bs=Integer.parseInt(String.valueOf(stringBuilder));
        }
        if((s>bs) || (stringBuilder==null)) {
            String score = s + "";
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = openFileOutput(name, Context.MODE_PRIVATE);
                fileOutputStream.write(score.getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fileOutputStream != null)
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        }
    }
}
