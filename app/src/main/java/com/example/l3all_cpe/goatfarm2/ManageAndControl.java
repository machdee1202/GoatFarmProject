package com.example.l3all_cpe.goatfarm2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ManageAndControl extends Activity {
    private TextView textViewStatus;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private ImageButton mButtonBack;
    String strIP = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_and_control);
        selectMenu();


        //*** Get Session Login
        final IPHelper ipHelper = new IPHelper(this);

        //*** Get Login Status
       /* if(!ipHelper.getLoginStatus())
        {
            Intent newActivity = new Intent(ManageAndControl.this, MainActivity.class);
            startActivity(newActivity);
        }*/

        //*** Get Member ID from Session
        strIP = ipHelper.getIP();


        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "ข้อมูลแพะ", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), ShowData.class));
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "ตั้งค่าการให้นม", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), SetMilkFeeders.class));
            }
        });
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "เปิด/ปิดการให้น้ำ", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), SetWater.class));
            }
        });
        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "ตั้งค่าการล้างภาชนะ", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), SetWashing.class));
            }
        });

        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void selectMenu() {
        //textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        mButton1 = (Button) findViewById(R.id.button1);
        mButton2 = (Button) findViewById(R.id.button2);
        mButton3 = (Button) findViewById(R.id.button3);
        mButton4 = (Button) findViewById(R.id.button4);
        mButtonBack = (ImageButton) findViewById(R.id.button_back);
    }
    public void onBackPressed() {
        final IPHelper ipHelper = new IPHelper(this);
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("ออก");
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setCancelable(true);
        dialog.setMessage("คุณแน่ใจว่าจะออกจากการเชื่อมต่อหรือไม่?");
        dialog.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ipHelper.deleteSession();
                finish();
            }
        });
        dialog.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

}
