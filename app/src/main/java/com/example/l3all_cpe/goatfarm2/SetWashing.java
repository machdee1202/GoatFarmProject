package com.example.l3all_cpe.goatfarm2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
    private Button mButtonTimeclean;

    String[] itemtime ={"30 นาที","1 ชั่วโมง","1 ชั่วโมง 30 นาที","2 ชั่วโมง"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_washing);
        selectMenu();



        mActiveWashing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder Washing = new AlertDialog.Builder(SetWashing.this);
                Washing.setTitle("ยืนยันการล้างภาชนะ");
                Washing.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface confirm, int which) {

                        Toast.makeText(getApplicationContext(), "ล้างภาชณะ", Toast.LENGTH_SHORT).show();
                    }
                });
                Washing.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface cancel, int which) {
                        Toast.makeText(getApplicationContext(), "ยกเลิก", Toast.LENGTH_SHORT).show();
                        cancel.cancel();
                    }
                });
                Washing.show();
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

        mButtonTimeclean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder selectMilk = new AlertDialog.Builder(SetWashing.this);
                selectMilk.setTitle("เลือกเวลา");
                selectMilk.setItems(itemtime, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String selected = itemtime[which];
                        Toast.makeText(getApplicationContext(), "เลือก " +
                                selected, Toast.LENGTH_SHORT).show();
                        mButtonTimeclean.setText(selected);


                    }
                });
                selectMilk.create();


                selectMilk.show();

            }
        });

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
        mButtonTimeclean = (Button) findViewById(R.id.btnTimeclean);
        mButtonCancel = (Button) findViewById(R.id.btnCancel);
        mButtonBack = (ImageButton) findViewById(R.id.button_back);
        mStallWashing = (Button) findViewById(R.id.btStallWashing);
        mActiveWashing = (Button) findViewById(R.id.btActiveWashing);
    }
}
