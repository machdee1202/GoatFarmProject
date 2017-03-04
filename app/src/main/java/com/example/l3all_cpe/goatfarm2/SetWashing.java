package com.example.l3all_cpe.goatfarm2;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SetWashing extends Activity {
    private ImageButton mButtonBack;
    private Button mStallWashing;
    private Button mButtonCancel;
    private Button mActiveWashing;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_washing);
        selectMenu();



        mActiveWashing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "ล้างภาชณะ", Toast.LENGTH_SHORT).show();
            }
        });

        mStallWashing.setOnClickListener(new View.OnClickListener()

                                       {
                                           @Override
                                           public void onClick(View v) {
                                               Toast.makeText(getApplicationContext(), "เลือกคอก", Toast.LENGTH_SHORT).show();
                                               startActivity(new Intent(getApplicationContext(), Select_StallWashing.class));
                                           }

                                       }

        );

        mButtonCancel.setOnClickListener(new View.OnClickListener()

                                         {
                                             @Override
                                             public void onClick(View view) {
                                                 Toast.makeText(getApplicationContext(), "ยกเลิก", Toast.LENGTH_SHORT).show();
                                                 finish();
                                             }
                                         }

        );

        mButtonBack = (ImageButton) findViewById(R.id.button_back);
        mButtonBack.setOnClickListener(new View.OnClickListener()

                                       {
                                           @Override
                                           public void onClick(View view) {
                                               Toast.makeText(getApplicationContext(), "ย้อนกลับ", Toast.LENGTH_SHORT).show();
                                               finish();
                                           }
                                       }

        );
    }

    private void selectMenu() {
       // btAdd = (TextView) findViewById(R.id.btAdd);
        mButtonCancel = (Button) findViewById(R.id.btnCancel);
        mButtonBack = (ImageButton) findViewById(R.id.button_back);
        mStallWashing = (Button) findViewById(R.id.btStallWashing);
        mActiveWashing = (Button) findViewById(R.id.btActiveWashing);
    }
}
