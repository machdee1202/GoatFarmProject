package com.example.l3all_cpe.goatfarm2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private Button mButton1;
    private Button mButton2;
    //EditText ip;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectMenu();
        setWidgetEvenListener();

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //final IPHelper ipHelper = new IPHelper(this);
        //EditText ip;
    }

    private void setWidgetEvenListener() {
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "การแนะนำและข้อมูลการเลี้ยง", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), SheetData.class));
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setTitle("ล็อกอินเพื่อเชื่อมต่อตัวควบคุม");
                dialog.setContentView(R.layout.dialog_login);
                //edittext ip && usename
                //final EditText editTextIP = (EditText)dialog.findViewById(R.id.ip);
                final IPHelper ipHelper = new IPHelper(MainActivity.this);
                final EditText ip = (EditText) dialog.findViewById(R.id.ip);
                final EditText user = (EditText) dialog.findViewById(R.id.user);
                //edittext Password
                final EditText password = (EditText) dialog.findViewById(R.id.password);
                Button buttonCancel = (Button) dialog.findViewById(R.id.button_cancel);
                Button buttonLogin = (Button) dialog.findViewById(R.id.button_login);
                TextView btnRegister = (TextView) dialog.findViewById(R.id.txtRegister);


                btnRegister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(getApplicationContext(), "สมัครสมาชิก", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), RegisterMember.class));
                        //dialog.dismiss();
                    }
                });
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                buttonLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String ww = "http://";
                        String strIP = ip.getText().toString();
                        String fphp ="/checkLogin.php";
                        String url = ww+strIP+fphp;

                        if (strIP.equals(""))
                        {
                            //Dialog
                            final AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
                            ad.setTitle("ผิดพลาด! ");
                            ad.setIcon(android.R.drawable.btn_star_big_on);
                            ad.setPositiveButton("ปิด", null);
                            ad.setMessage("กรุณาระบุไอพีด้วย");
                            ad.show();

                        } else {
                            //ip.getText().toString();
                            List<NameValuePair> params = new ArrayList<NameValuePair>();
                            params.add(new BasicNameValuePair("strUser", user.getText().toString()));
                            params.add(new BasicNameValuePair("strPass", password.getText().toString()));

                            /**
                             * Get result from Server (Return the JSON Code)
                             * StatusID = ? [0=Failed,1=Complete]
                             * MemberID = ? [Eg : 1]
                             * Error	= ?	[On case error return custom error message]
                             * <p/>
                             * Eg Login Failed = {"StatusID":"0","MemberID":"0","Error":"Incorrect Username and Password"}
                             * Eg Login Complete = {"StatusID":"1","MemberID":"2","Error":""}
                             */

                            String resultServer = NetConnect.getHttpPost(url, params);

                            /***
                             * Default Value
                             ***/
                            String strStatusID = "0";
                            String strMemberID = "0";
                            String strError = "ไม่สามารถเชื่อมต่อเซิฟเวอร์!";

                            JSONObject c;
                            try

                            {
                                c = new JSONObject(resultServer);
                                strStatusID = c.getString("StatusID");
                                strMemberID = c.getString("MemberID");
                                strError = c.getString("Error");

                            } catch (
                                    JSONException e
                                    )

                            {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            // if(ip.equals(0))
                            //{}

                            // Prepare Login
                            if (strStatusID.equals("0"))

                            {
                                //Dialog
                                final AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
                                ad.setTitle("ผิดพลาด! ");
                                ad.setIcon(android.R.drawable.btn_star_big_on);
                                ad.setPositiveButton("ปิด", null);
                                ad.setMessage(strError);
                                ad.show();

                                //Toast.makeText(getApplicationContext(), "ล็อกอินไม่สำเร็จ!", Toast.LENGTH_SHORT).show();
                                user.setText("");
                                password.setText("");
                            } else

                            {
                                Toast.makeText(getApplicationContext(), "ล็อกอินสำเร็จ!", Toast.LENGTH_SHORT).show();
                                ipHelper.createSession(strIP);
                                startActivity(new Intent(getApplicationContext(), ManageAndControl.class));
                                dialog.dismiss();
                            }
                        }
                        /*if (ip.getText().equals("pi") &&
                                username.getText().equals("ball") &&
                                password.getText().equals("bb")) {*/
              //          Toast.makeText(getApplicationContext(), "ล็อกอินสำเร็จ!", Toast.LENGTH_SHORT).show();
             //           startActivity(new Intent(getApplicationContext(), ManageAndControl.class));
                 //         dialog.dismiss();
                        /*} else {
                            Toast.makeText(getApplicationContext(), "ล้อกอินไม่สำเร็จ", Toast.LENGTH_SHORT).show();*/
                        // }
                        //dialog.show();
                        //dialog.show();
                    }
                    //dialog.show();
                });
                dialog.show();
            //}
            }
        });
}

            private void selectMenu() {
                mButton1 = (Button) findViewById(R.id.button1);
                mButton2 = (Button) findViewById(R.id.button2);
            }

            public void onBackPressed() {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("ออก");
                dialog.setIcon(R.mipmap.ic_launcher);
                dialog.setCancelable(true);
                dialog.setMessage("คุณแน่ใจว่าจะออกจากแอพพลิเคชันไหม?");
                dialog.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
