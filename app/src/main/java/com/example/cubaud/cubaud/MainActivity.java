package com.example.cubaud.cubaud;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
    static boolean n = false;
    TheApplication app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = (TheApplication)(this.getApplication());
    }
    public void onClickExit(View v){
        finish();
    }

    public void onClickPlay(View v){
        showAlertDialogButtonClicked(v);
    }

    public void onClickParam(View v){
        Intent paramIntent = new Intent(this, ParamActivity.class);
        startActivity(paramIntent);
    }


    public void showAlertDialogButtonClicked(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Continuer la partie précédente ?");
        builder.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                app.readTab("gameFic.txt");
                MainActivity.this.n=true;
                Intent playIntent = new Intent(MainActivity.this, PlayActivity.class);
                startActivity(playIntent);
            }
        });
        builder.setNegativeButton("NOUVELLE PARTIE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                app.reNew();
                MainActivity.this.n=false;
                Intent playIntent = new Intent(MainActivity.this, PlayActivity.class);
                startActivity(playIntent);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
