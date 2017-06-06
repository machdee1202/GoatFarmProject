package com.example.l3all_cpe.goatfarm2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SetWashing extends Activity {
    private ImageButton mButtonBack;
    private Button mStallWashing;
    private Button mButtonCancel;
    private Button mActiveWashing;
    TextView tStall;
    private Button mButtonTimeclean;
    String Tjobs = "NOW()";

    String[] itemtime ={"30 นาที","1 ชั่วโมง","1 ชั่วโมง 30 นาที","2 ชั่วโมง"};

    String strIP = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_washing);
        selectMenu();

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        final IPHelper ipHelper = new IPHelper(this);

        //*** Get Login Status
   /*     if (!ipHelper.getLoginStatus()) {
            Intent newActivity = new Intent(SetMilkFeeders.this, MainActivity.class);
            startActivity(newActivity);
        }*/

        strIP = ipHelper.getIP();
        tStall = (TextView) findViewById(R.id.txtStallWashingSL);

        mActiveWashing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivWashing_now();
            }
        });

        mStallWashing.setOnClickListener(new View.OnClickListener()

                                       {
                                           @Override
                                           public void onClick(View v) {
                                               Toast.makeText(getApplicationContext(), "เลือกคอก", Toast.LENGTH_SHORT).show();
                                               startActivity(new Intent(getApplicationContext(), Select_StallWashing.class));
                                               finish();
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


        mButtonBack.setOnClickListener(new View.OnClickListener()

                                       {
                                           @Override
                                           public void onClick(View view) {
                                               Toast.makeText(getApplicationContext(), "ย้อนกลับ", Toast.LENGTH_SHORT).show();
                                               finish();
                                           }
                                       }

        );
        Show_SelectStall();
    }
    public  void ActivWashing_now(){

        Intent intent = getIntent();
        final String Stall = intent.getStringExtra("Stall");


        final AlertDialog.Builder giveClean = new AlertDialog.Builder(SetWashing.this);
        giveClean.setTitle("ยืนยันการล้างภาชนะ");
        giveClean.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface confirm, int which) {

                if(tStall.getText().toString().equals("")){
                    final AlertDialog.Builder giveMilk3 = new AlertDialog.Builder(SetWashing.this);
                    giveMilk3.setTitle("กรุณาระบุคอก");
                    giveMilk3.setPositiveButton("ปิด", null);
                    giveMilk3.show();


                }
                else {
                    //** รอแก้การส่งค่าไปยังเซิฟเวอร์**//
                    final AlertDialog.Builder adb = new AlertDialog.Builder(SetWashing.this);
                    AlertDialog ad = adb.create();


                    String ww = "http://";
                    String fphp ="/addJobsClean.php";
                    String url = ww + strIP + fphp;


                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("sStall_sl", tStall.getText().toString()));
                    params.add(new BasicNameValuePair("sTjob_sl", Tjobs.toString()));

                    String resultServer = NetConnect.getHttpPost(url, params);

                    //JSONArray data = new JSONArray(params);

                    /*** Default Value ***/
                    String strStatusID = "0";
                    String strError = "ไม่สามารถเชื่อมต่อเซิฟเวอร์!";

                    //for(int i = 0; i < data.length(); i++){}
                    JSONObject c;
                    try {
                        c = new JSONObject(resultServer);
                        strStatusID = c.getString("StatusID");
                        strError = c.getString("Error");
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    // Prepare Save Data
                    if(strStatusID.equals("0"))
                    {
                        ad.setMessage(strError);
                        ad.show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "เพิ่มการล้างภาชนะคอกที่ " + Stall +" แล้ว", Toast.LENGTH_SHORT).show();

                        tStall.setText("");
                    }

                    //Toast.makeText(getApplicationContext(), "ให้นมคอกที่ " + Stall, Toast.LENGTH_SHORT).show();
                    //finish();
                }


            }
        });
        giveClean.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface cancel, int which) {
                Toast.makeText(getApplicationContext(), "ยกเลิก", Toast.LENGTH_SHORT).show();
                cancel.cancel();
            }
        });
        giveClean.show();





        // Disbled Keyboard auto focus
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
       // inputManager.hideSoftInputFromWindow(btninputmilk.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        //return;

    }

    public void Show_SelectStall(){

        // txtGoatID, txtStall.....




        //String ww = "http://";
        //String fphp ="/getByStall_Select.php";
        //String url = ww + strIP + fphp;

        Intent intent = getIntent();
        final String Stall = intent.getStringExtra("Stall");

        //List<NameValuePair> params = new ArrayList<NameValuePair>();
        //params.add(new BasicNameValuePair("sStall", Stall));

        /** Get result from Server (Return the JSON Code)
         *
         *
         */

        //String resultServer = NetConnect.getHttpPost(url, params);

        //String strStall = "";


        /*JSONObject c;
        try {
            c = new JSONObject(resultServer);

            strStall = c.getString("Stall");


            if (!strStall.equals("")) {

                tStall.setText(strStall);

            } else {
                tStall.setText("-");
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/

        tStall.setText(Stall);
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
