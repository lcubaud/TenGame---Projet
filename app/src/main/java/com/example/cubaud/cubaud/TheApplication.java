package com.example.cubaud.cubaud;

import android.app.Application;
import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import tendroid.model.*;


public class TheApplication extends Application {
    TenGame jeu;
    int ns[];
    static int saveScore;
    static int size;
    StringBuilder stringBuilder;
    StringBuilder stringBuilderSize;

    @Override
    public void onCreate() {
        super.onCreate();
        getSize();
        tabAleatoire(size);
        this.jeu = new TenGame(ns);
    }

    TenGame getGame() {
        return jeu;
    }

    void reNew(){
        tabAleatoire(size);
        jeu=new TenGame(ns);
    }

    void tabAleatoire(int s){
        ns=new int[s];
        for (int i=0; i<s;i++){
          this.ns[i]= (int)(Math.random()*4)+1;
            //this.ns[i]=9;
        }
    }

    static int readScore(){
        return saveScore;

    }


    void readTab(String ficName){
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = openFileInput(ficName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            stringBuilder = new StringBuilder();
            String line;

            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }
            size=Integer.parseInt(String.valueOf(stringBuilder.charAt(0)))*10+Integer.parseInt(String.valueOf(stringBuilder.charAt(1)));
            int j=0;
            for(int i=2; i<size+1;i++) {
                ns[j++] = Integer.parseInt(String.valueOf(stringBuilder.charAt(i)));
            }
            stringBuilder.delete(0,(size+2));
            saveScore=Integer.parseInt(String.valueOf(stringBuilder));
            jeu=new TenGame(ns);

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

    public void getSize(){
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = openFileInput("sizeFic.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            stringBuilderSize = new StringBuilder();
            String line;

            while((line = bufferedReader.readLine()) != null){
                stringBuilderSize.append(line);
            }
            size=Integer.parseInt(String.valueOf(stringBuilderSize));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            this.size=25;
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

        if (stringBuilderSize != null)
            System.out.println(stringBuilderSize);
        else
            System.err.println("stringBuilder est null");

    }

}




