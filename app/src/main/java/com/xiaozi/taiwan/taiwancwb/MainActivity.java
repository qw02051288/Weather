package com.xiaozi.taiwan.taiwancwb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private Button btnConnect;
    private ProgressBar progressBar;
    private Handler mHandler = new Handler();

    private String KEY = "CWB-7AAC11ED-B4AE-4314-BB2D-12BC2774BE76";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new WeatherObject();
        getId();

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        startActivity(new Intent(MainActivity.this, DataResult.class));
                    }
                }, 500);
            }
        });
    }

    private void getId() {
        btnConnect = findViewById(R.id.btnConnect);
        progressBar = findViewById(R.id.progressBar);
    }
}