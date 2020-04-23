package com.example.cubaud.cubaud;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RuleActivity extends Activity {

    TheApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule);
        app = (TheApplication) this.getApplication();
    }

    public void onClickRuleBack(View v){
        finish();
    }
}
