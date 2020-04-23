package com.example.cubaud.cubaud;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ParamActivity extends Activity {

    TheApplication app;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    public int  SIZE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_param);
        app = (TheApplication) this.getApplication();

        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.item_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " ", Toast.LENGTH_LONG).show();
                if(position==1){
                    SIZE=25;
                    saveSize(SIZE);
                    TheApplication.size=SIZE;
                    app.reNew();
                }
                if(position==2){
                    SIZE=36;
                    saveSize(SIZE);
                    TheApplication.size=SIZE;
                    app.reNew();
                }
                if(position==3){
                    SIZE=49;
                    saveSize(SIZE);
                    TheApplication.size=SIZE;
                    app.reNew();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void onClickBack(View v){
        finish();
    }

    public void saveSize(int s){
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput("sizeFic.txt", Context.MODE_PRIVATE);
            fileOutputStream.write((("" + s).getBytes()));

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
