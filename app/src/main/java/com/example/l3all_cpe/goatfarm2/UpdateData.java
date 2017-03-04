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
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UpdateData extends Activity {
    private ImageButton mButtonBack;
    String strIP = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        final IPHelper ipHelper = new IPHelper(this);

        //*** Get Login Status
        if (!ipHelper.getLoginStatus()) {
            Intent newActivity = new Intent(UpdateData.this, MainActivity.class);
            startActivity(newActivity);
        }
        strIP = ipHelper.getIP();

        // Read var from Intent
        Intent intent = getIntent();
        //final String GID = intent.getStringExtra("GID");

        // Show Data
        ShowInfo();
        //ShowData(GID);

        mButtonBack = (ImageButton) findViewById(R.id.button_back);
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "ย้อนกลับ", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        // btnSave (Save)
        final Button save = (Button) findViewById(R.id.btnSave);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // If Save Complete
                if (UpdateGoatData()) {
                    // Open Form ListUpdate
                    Intent newActivity = new Intent(UpdateData.this, ShowData.class);
                    finish();
                    startActivity(newActivity);
                }
            }
        });

        // btnCancel (Cancel)
        final Button cancel = (Button) findViewById(R.id.btnCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Open Form Main
                Toast.makeText(getApplicationContext(), "ยกเลิก", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
    public void ShowInfo()
    {
        // txtMemberID,txtUsername,txtPassword,txtConPassword,txtName,txtEmail,txtTel
        final TextView tGoatID = (TextView)findViewById(R.id.txtGoatID);
        final TextView tStall = (TextView)findViewById(R.id.txtStall);
        final TextView tAge = (TextView)findViewById(R.id.txtAge);
        final TextView tGenetic = (TextView)findViewById(R.id.txtGenetic);
        final TextView tSex = (TextView)findViewById(R.id.txtSex);
        final TextView tDate_start = (TextView)findViewById(R.id.txtDate_start);

        Button btnSave = (Button) findViewById(R.id.btnSave);
        Button btnCancel = (Button) findViewById(R.id.btnCancel);

        //String url = "http://www.thaicreate.com/android/getByMemberID.php";
        String ww = "http://";
        String fphp ="/getByGoatID.php";
        String url = ww+strIP+fphp;

        Intent intent= getIntent();
        final String GoatID = intent.getStringExtra("GoatID");

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sGoatID", GoatID));

        /** Get result from Server (Return the JSON Code)
         *
         * {"MemberID":"2","Username":"adisorn","Password":"adisorn@2","Name":"Adisorn Bunsong","Tel":"021978032","Email":"adisorn@thaicreate.com"}
         */

        String resultServer = NetConnect.getHttpPost(url, params);

        String strGoatID = "";
        String strStall = "";
        String strAge = "";
        String strGenetic = "";
        String strSex = "";
        String strDate_start = "";

        JSONObject c;
        try {
            c = new JSONObject(resultServer);
            strGoatID = c.getString("GoatID");
            strStall = c.getString("Stall");
            strAge = c.getString("Age");
            strGenetic = c.getString("Genetic");
            strSex = c.getString("Sex");
            strDate_start = c.getString("Date_start");

            if(!strGoatID.equals(""))
            {
                tGoatID.setText(strGoatID);
                tStall.setText(strStall);
                tAge.setText(strAge);
                tGenetic.setText(strGenetic);
                tSex.setText(strSex);
                tDate_start.setText(strDate_start);

            }
            else
            {
                tGoatID.setText("-");
                tStall.setText("-");
                tAge.setText("-");
                tGenetic.setText("-");
                tSex.setText("-");
                tDate_start.setText("-");
                btnSave.setEnabled(false);
                btnCancel.requestFocus();
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    public boolean UpdateGoatData()
    {

        // txtMemberID,txtPassword,txtName,txtEmail,txtTel
        final TextView txtGoatID = (TextView)findViewById(R.id.txtGoatID);
        final EditText txtStall = (EditText)findViewById(R.id.txtStall);
        final EditText txtAge = (EditText)findViewById(R.id.txtAge);
        final EditText txtGenetic = (EditText)findViewById(R.id.txtGenetic);
        final EditText txtSex = (EditText)findViewById(R.id.txtSex);
        final EditText txtDate_start = (EditText)findViewById(R.id.txtDate_start);


        // Dialog
        final AlertDialog.Builder ad = new AlertDialog.Builder(this);

        ad.setTitle("ผิดพลาด! ");
        ad.setIcon(android.R.drawable.btn_star_big_on);
        ad.setPositiveButton("ปิด", null);

        // Check Password
        if(txtStall.getText().length() == 0 )
        {
            ad.setMessage("กรุณาระบุ [คอก] ");
            ad.show();
            txtStall.requestFocus();
            return false;
        }

        // Check Name
        if(txtAge.getText().length() == 0)
        {
            ad.setMessage("กรุณาระบุ [อายุ] ");
            ad.show();
            txtAge.requestFocus();
            return false;
        }
        // Check Email
        if(txtGenetic.getText().length() == 0)
        {
            ad.setMessage("Please input [Email] ");
            ad.show();
            txtGenetic.requestFocus();
            return false;
        }
        // Check Tel
        if(txtSex.getText().length() == 0)
        {
            ad.setMessage("Please input [Tel] ");
            ad.show();
            txtSex.requestFocus();
            return false;
        }

        // Check Tel
        if(txtDate_start.getText().length() == 0)
        {
            ad.setMessage("Please input [Tel] ");
            ad.show();
            txtDate_start.requestFocus();
            return false;
        }

        //String url = "http://www.thaicreate.com/android/updateData.php";
        String ww = "http://";
        String fphp ="/updateGoatData.php";
        String url = ww+strIP+fphp;

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sGoatID", txtGoatID.getText().toString()));
        params.add(new BasicNameValuePair("sStall", txtStall.getText().toString()));
        params.add(new BasicNameValuePair("sAge", txtAge.getText().toString()));
        params.add(new BasicNameValuePair("sGenetic", txtGenetic.getText().toString()));
        params.add(new BasicNameValuePair("sSex", txtSex.getText().toString()));
        params.add(new BasicNameValuePair("sDate_start", txtDate_start.getText().toString()));
        /** Get result from Server (Return the JSON Code)
         * StatusID = ? [0=Failed,1=Complete]
         * Error	= ?	[On case error return custom error message]
         *
         * Eg Save Failed = {"StatusID":"0","Error":"Email Exists!"}
         * Eg Save Complete = {"StatusID":"1","Error":""}
         */

        String resultServer = NetConnect.getHttpPost(url, params);

        /*** Default Value ***/
        String strStatusID = "0";
        String strError = "Unknow Status!";

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
            Toast.makeText(UpdateData.this, "แก้ไขข้อมูลสำเร็จ", Toast.LENGTH_SHORT).show();
        }


        return true;
    }




    /*public void ShowData(String GID) {
        // txtMemberID, txtName, txtTel
        final TextView tGoatID = (TextView) findViewById(R.id.txtGoatID);
        final EditText tStall = (EditText) findViewById(R.id.txtStall);
        final EditText tAge = (EditText) findViewById(R.id.txtAge);
        final EditText tGenetic = (EditText) findViewById(R.id.txtGenetic);
        final EditText tSex = (EditText) findViewById(R.id.txtSex);
        final EditText tDate_start = (EditText) findViewById(R.id.txtDate_start);

        // new Class DB
        final Goat_Database myDb = new Goat_Database(this);

        // Show Data
        String arrData[] = myDb.SelectData(GID);
        if (arrData != null) {
            tGoatID.setText(arrData[0]);
            tStall.setText(arrData[1]);
            tAge.setText(arrData[2]);
            tGenetic.setText(arrData[3]);
            tSex.setText(arrData[4]);
            tDate_start.setText(arrData[5]);
        }

    }

    public boolean UpdateData(String GID) {

        // txtName, txtTel
        final EditText tStall = (EditText) findViewById(R.id.txtStall);
        final EditText tAge = (EditText) findViewById(R.id.txtAge);
        final EditText tGenetic = (EditText) findViewById(R.id.txtGenetic);
        final EditText tSex = (EditText) findViewById(R.id.txtSex);
        final EditText tDate_start = (EditText) findViewById(R.id.txtDate_start);


        // Dialog
        final AlertDialog.Builder adb = new AlertDialog.Builder(this);
        AlertDialog ad = adb.create();

        // Check stall
        if (tStall.getText().length() == 0) {
            ad.setMessage("กรุณาระบุ [คอก] ");
            ad.show();
            tStall.requestFocus();
            return false;
        }

        // Check
        if (tAge.getText().length() == 0) {
            ad.setMessage("กรุณาระบุ [อายุ] ");
            ad.show();
            tAge.requestFocus();
            return false;
        }

        // Check
        if (tGenetic.getText().length() == 0) {
            ad.setMessage("กรุณาระบุ [พันธุ์] ");
            ad.show();
            tGenetic.requestFocus();
            return false;
        }

        // Check
        if (tSex.getText().length() == 0) {
            ad.setMessage("กรุณาระบุ [เพศ] ");
            ad.show();
            tSex.requestFocus();
            return false;
        }

        // Check Tel
        if (tDate_start.getText().length() == 0) {
            ad.setMessage("กรุณาระบุ [วันที่รับเลี้ยง] ");
            ad.show();
            tDate_start.requestFocus();
            return false;
        }

        // new Class DB
        final Goat_Database myDb = new Goat_Database(this);

        // Save Data
        long saveStatus = myDb.UpdateData(GID,
                tStall.getText().toString(),
                tAge.getText().toString(),
                tGenetic.getText().toString(),
                tSex.getText().toString(),
                tDate_start.getText().toString());
        if (saveStatus <= 0) {
            ad.setMessage("ผิดพลาด!! ");
            ad.show();
            return false;
        }

        Toast.makeText(UpdateData.this, "แก้ไขข้อมูลสำเร็จ. ",
                Toast.LENGTH_SHORT).show();

        return true;

    }*/

}
