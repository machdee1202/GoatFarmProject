package com.example.l3all_cpe.goatfarm2;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import org.apache.http.message.BasicNameValuePair;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class RegisterMember extends Activity {

    private ImageButton mButtonBack;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_member);
        setWidgetEvenListener();

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
    }

    // btnSave
    final Button btnSave = (Button) findViewById(R.id.btnSave);
    // Perform action on click
    btnSave.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(SaveData())
            {
                // When Save Complete
            }
        }
    });

}

    public boolean SaveData()
    {

        // txtUsername,txtPassword,txtName,txtEmail,txtTel
        final EditText ip = (EditText)findViewById(R.id.ip);
        final EditText txtUsername = (EditText)findViewById(R.id.txtUsername);
        final EditText txtPassword = (EditText)findViewById(R.id.txtPassword);
        final EditText txtConPassword = (EditText)findViewById(R.id.txtConPassword);
        final EditText txtEmail = (EditText)findViewById(R.id.txtEmail);



        // Dialog
        final AlertDialog.Builder ad = new AlertDialog.Builder(this);

        ad.setTitle("ผิดพลาด! ");
        ad.setIcon(android.R.drawable.btn_star_big_on);
        ad.setPositiveButton("ปิด", null);

        // Check IP
        if(ip.getText().length() == 0)
        {
            ad.setMessage("กรุณาระบุ [ไอพีแอดเดรส] ");
            ad.show();
            ip.requestFocus();
            return false;
        }
        // Check Username
        if(txtUsername.getText().length() == 0)
        {
            ad.setMessage("กรุณาระบุ [ชื่อผู้ใช้] ");
            ad.show();
            txtUsername.requestFocus();
            return false;
        }
        // Check Password
        if(txtPassword.getText().length() == 0 || txtConPassword.getText().length() == 0 )
        {
            ad.setMessage("กรุณาระบุ [รหัสผ่าน/ยืนยันรหัสผ่าน] ");
            ad.show();
            txtPassword.requestFocus();
            return false;
        }
        // Check Password and Confirm Password (Match)
        if(!txtPassword.getText().toString().equals(txtConPassword.getText().toString()))
        {
            ad.setMessage("รหัสผ่านไม่ตรงกัน! ");
            ad.show();
            txtConPassword.requestFocus();
            return false;
        }

        // Check Email
        if(txtEmail.getText().length() == 0)
        {
            ad.setMessage("กรุณาระบุ [อีเมลแอดเดรส] ");
            ad.show();
            txtEmail.requestFocus();
            return false;
        }


        String url = "http://" + ip.getText()+"/saveAddData.php";

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sUsername", txtUsername.getText().toString()));
        params.add(new BasicNameValuePair("sPassword", txtPassword.getText().toString()));
        params.add(new BasicNameValuePair("sEmail", txtEmail.getText().toString()));

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
        }
        else
        {
            Toast.makeText(RegisterMember.this, "เพิ่มข้อมูลสำเร็จ", Toast.LENGTH_SHORT).show();
            txtUsername.setText("");
            txtPassword.setText("");
            txtConPassword.setText("");
            txtEmail.setText("");
        }


        return true;
    }

    private void setWidgetEvenListener() {
        mButtonBack = (ImageButton) findViewById(R.id.button_back);

        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "ย้อนกลับ", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
