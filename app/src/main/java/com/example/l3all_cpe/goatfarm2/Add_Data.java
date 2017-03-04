package com.example.l3all_cpe.goatfarm2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Add_Data extends Activity {

    private Button btnSave;
    private Button btnCancel;
    private ImageButton mButtonBack;
    //private int gNum;
    String strIP = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        selectMenu();
        setWidgetEvenListener();

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

    }


    private void setWidgetEvenListener() {
        final IPHelper ipHelper = new IPHelper(this);

        //*** Get Login Status
  /*      if (!ipHelper.getLoginStatus()) {
            Intent newActivity = new Intent(Add_Data.this, MainActivity.class);
            startActivity(newActivity);
        }*/

        strIP = ipHelper.getIP();

        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Toast.makeText(getApplicationContext(), "ย้อนกลับ", Toast.LENGTH_SHORT).show();
                    finish();
            }
        });
        // btnSave (Save)
        //final Button save = (Button) findViewById(R.id.button_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // If Save Complete
                if(SaveDataOnServer())
                {
                        finish();
                        // Open Form Main
                        Intent newActivity = new Intent(Add_Data.this, ShowData.class);
                        //finish();
                        startActivity(newActivity);

                }
            }
        });
        // btnCancel (Cancel)
        //final Button cancel = (Button) findViewById(R.id.button_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Back
                Toast.makeText(getApplicationContext(), "ยกเลิก", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    /*public boolean SaveData()
    {
        // txtStall, txtAge, txtGenetic, txtSex, txtDate_start
        final EditText tGoatID = (EditText) findViewById(R.id.gid);
        final EditText tStall = (EditText) findViewById(R.id.stall);
        final EditText tAge = (EditText) findViewById(R.id.age);
        final EditText tGenetic = (EditText) findViewById(R.id.genetic);
        final EditText tSex = (EditText) findViewById(R.id.sex);
        final EditText tDate_start = (EditText) findViewById(R.id.startG);

        // Dialog
        //final AlertDialog.Builder adb = new AlertDialog.Builder(this);
        //AlertDialog ad = adb.create();

        // Check Id
        if(tGoatID.getText().length() == 0)
        {
            ad.setMessage("กรุณาระบุ [ลำดับ] ");
            ad.show();
            tGoatID.requestFocus();
            return false;
        }
        // Check Stall
        if(tStall.getText().length() == 0)
        {
            ad.setMessage("กรุณาระบุ [คอก] ");
            ad.show();
            tStall.requestFocus();
            return false;
        }
        // Check Age
        if(tAge.getText().length() == 0)
        {
            ad.setMessage("กรุณาระบุ [อายุ] ");
            ad.show();
            tAge.requestFocus();
            return false;
        }
        // Check Genetic
        if(tGenetic.getText().length() == 0)
        {
            ad.setMessage("กรุณาระบุ [พันธุ์] ");
            ad.show();
            tGenetic.requestFocus();
            return false;
        }
        if(tSex.getText().length() == 0)
        {
            ad.setMessage("กรุณาระบุ [เพศ] ");
            ad.show();
            tSex.requestFocus();
            return false;
        }
        if(tDate_start.getText().length() == 0)
        {
            ad.setMessage("กรุณาระบุ [วันที่รับเลี้ยง] ");
            ad.show();
            tDate_start.requestFocus();
            return false;
        }


        // new Class DB
        final Goat_Database myDb = new Goat_Database(this);

        // Check Data (GoatID exists)
        String arrData[] = myDb.SelectData(tGoatID.getText().toString());
        if(arrData != null)
        {
            ad.setMessage("ไอดีนี้มีอยู่แล้ว");
            ad.show();
            tGoatID.requestFocus();
            return false;
        }

        // Save Data
        long saveStatus = myDb.InsertData(tGoatID.getText().toString(),
                tStall.getText().toString(),
                tAge.getText().toString(),
                tGenetic.getText().toString(),
                tSex.getText().toString(),
                tDate_start.getText().toString());

        if(saveStatus <=  0)
        {
            ad.setMessage("ผิดพลาด!! ");
            ad.show();
            return false;
        }

        Toast.makeText(Add_Data.this,"เพิ่มข้อมูลแพะสำเร็จ ", Toast.LENGTH_SHORT).show();
        return true;
    }*/


    public boolean SaveDataOnServer(){

        final EditText tGoatID = (EditText) findViewById(R.id.gid);
        final EditText tStall = (EditText) findViewById(R.id.stall);
        final EditText tAge = (EditText) findViewById(R.id.age);
        final EditText tGenetic = (EditText) findViewById(R.id.genetic);
        final EditText tSex = (EditText) findViewById(R.id.sex);
        final EditText tDate_start = (EditText) findViewById(R.id.startG);

        final AlertDialog.Builder adb = new AlertDialog.Builder(this);
        AlertDialog ad = adb.create();

        // Check Id
        if(tGoatID.getText().length() == 0)
        {
            ad.setMessage("กรุณาระบุ [ลำดับ] ");
            ad.show();
            tGoatID.requestFocus();
            return false;
        }
        // Check Stall
        if(tStall.getText().length() == 0)
        {
            ad.setMessage("กรุณาระบุ [คอก] ");
            ad.show();
            tStall.requestFocus();
            return false;
        }
        // Check Age
        if(tAge.getText().length() == 0)
        {
            ad.setMessage("กรุณาระบุ [อายุ] ");
            ad.show();
            tAge.requestFocus();
            return false;
        }
        // Check Genetic
        if(tGenetic.getText().length() == 0)
        {
            ad.setMessage("กรุณาระบุ [พันธุ์] ");
            ad.show();
            tGenetic.requestFocus();
            return false;
        }
        if(tSex.getText().length() == 0)
        {
            ad.setMessage("กรุณาระบุ [เพศ] ");
            ad.show();
            tSex.requestFocus();
            return false;
        }
        if(tDate_start.getText().length() == 0)
        {
            ad.setMessage("กรุณาระบุ [วันที่รับเลี้ยง] ");
            ad.show();
            tDate_start.requestFocus();
            return false;
        }

        //final AlertDialog.Builder adb = new AlertDialog.Builder(this);
       // AlertDialog ad = adb.create();

        String ww = "http://";
        String fphp ="/saveAddGoatData.php";
        String url = ww+strIP+fphp;

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sGoatID", tGoatID.getText().toString()));
        params.add(new BasicNameValuePair("sStall", tStall.getText().toString()));
        params.add(new BasicNameValuePair("sAge", tAge.getText().toString()));
        params.add(new BasicNameValuePair("sGenetic", tGenetic.getText().toString()));
        params.add(new BasicNameValuePair("sSex", tSex.getText().toString()));
        params.add(new BasicNameValuePair("sDate_start", tDate_start.getText().toString()));

        /** Get result from Server (Return the JSON Code)
         * StatusID = ? [0=Failed,1=Complete]
         * Error	= ?	[On case error return custom error message]
         *
         * Eg Save Failed = {"StatusID":"0","Error":"Email Exists!"}
         * Eg Save Complete = {"StatusID":"1","Error":""}
         */

        String resultServer  = NetConnect.getHttpPost(url,params);

        /*** Default Value ***/
        String strStatusID = "0";
        String strError = "ไม่สามารถเชื่อมต่อเซิฟเวอร์!";

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
            return false;
        }
        else
        {
            Toast.makeText(Add_Data.this, "เพิ่มข้อมูลแพะแล้ว", Toast.LENGTH_SHORT).show();
            tGoatID.setText("");
            tStall.setText("");
            tAge.setText("");
            tGenetic.setText("");
            tSex.setText("");
            tDate_start.setText("");
        }
        return true;
    }

    private void selectMenu() {
        btnSave = (Button) findViewById(R.id.button_save);
        btnCancel = (Button) findViewById(R.id.button_cancel);
        mButtonBack = (ImageButton) findViewById(R.id.button_back);
    }
}
