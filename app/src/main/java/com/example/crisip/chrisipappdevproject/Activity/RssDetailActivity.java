package com.example.crisip.chrisipappdevproject.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.crisip.chrisipappdevproject.R;

public class RssDetailActivity extends AppCompatActivity {

    private TextView tvDesc, tvLink;
    String sDescription, sLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_detail);
        Intent intent = getIntent();
        sDescription = intent.getStringExtra("passDescription");
        sLink = intent.getStringExtra("passLink");
        tvDesc = (TextView)findViewById(R.id.tvD);
        tvLink=  (TextView)findViewById(R.id.tvLink);
        tvDesc.setText(sDescription);
        tvLink.setText(sLink);
    }
}
