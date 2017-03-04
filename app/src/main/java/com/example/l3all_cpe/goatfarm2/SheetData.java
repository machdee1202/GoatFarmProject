package com.example.l3all_cpe.goatfarm2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class SheetData extends Activity {
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private Button mButton5;
    private ImageButton mButtonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_data);
        selectMenu();
        setWidgetEvenListener();
    }
    private void setWidgetEvenListener() {
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "เหตุผลและความเหมาะสม", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Sheet_1.class));
            }
        });
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "รูปแบบการเลี้ยง", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Sheet_2.class));
            }
        });
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "พันธุ์แพะที่น่าสนใจ", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getApplicationContext(), Sheet_3.class));
            }
        });
        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "ผลผลิตจากแพะ", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getApplicationContext(), Sheet_4.class));
            }
        });
        mButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "ตารางการให้อาหาร", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getApplicationContext(), Sheet_5.class));
            }
        });
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "ย้อนกลับ", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void selectMenu() {
        mButton1 = (Button) findViewById(R.id.button1);
        mButton2 = (Button) findViewById(R.id.button2);
        mButton3 = (Button) findViewById(R.id.button3);
        mButton4 = (Button) findViewById(R.id.button4);
        mButton5 = (Button) findViewById(R.id.button5);
        mButtonBack = (ImageButton) findViewById(R.id.button_back);

    }

}
