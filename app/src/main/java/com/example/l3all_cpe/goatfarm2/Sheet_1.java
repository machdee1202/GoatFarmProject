package com.example.l3all_cpe.goatfarm2;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Sheet_1 extends Activity {

    private ImageButton mButtonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_1);
        selectMenu();
        setWidgetEvenListener();
    }

    private void setWidgetEvenListener() {
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "ย้อนกลับ", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void selectMenu() {
        mButtonBack = (ImageButton) findViewById(R.id.button_back);
    }
}


