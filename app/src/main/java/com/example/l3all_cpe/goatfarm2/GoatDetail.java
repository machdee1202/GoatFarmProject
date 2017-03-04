package com.example.l3all_cpe.goatfarm2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
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

public class GoatDetail extends Activity {
    private ImageButton mButtonBack;
    String strIP = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goat_detail);

        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        final IPHelper ipHelper = new IPHelper(this);
        //*** Get Login Status
        if (!ipHelper.getLoginStatus()) {
            Intent newActivity = new Intent(GoatDetail.this, MainActivity.class);
            finish();
            startActivity(newActivity);
        }

        strIP = ipHelper.getIP();

        // Read var from Intent
        //Intent intent= getIntent();
        //String GID = intent.getStringExtra("GID");

        // Show Data
        ShowInfo();

        // btnCancel (Cancel)
        final Button cancel = (Button) findViewById(R.id.btnCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Open Form Main
                Toast.makeText(getApplicationContext(), "ยกเลิก", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        mButtonBack = (ImageButton) findViewById(R.id.button_back);
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "ย้อนกลับ", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    /*public void ShowData(String GID) {
        // txtMemberID, txtName, txtTel.
        final TextView tID = (TextView) findViewById(R.id.txtID);
        final TextView tStall = (TextView) findViewById(R.id.txtStall);
        final TextView tAge = (TextView) findViewById(R.id.txtAge);
        final TextView tGenetic = (TextView) findViewById(R.id.txtGenetic);
        final TextView tSex = (TextView) findViewById(R.id.txtSex);
        final TextView tDate_start = (TextView) findViewById(R.id.txtDate_start);

        // new Class DB
        final Goat_Database myDb = new Goat_Database(this);

        // Show Data
        String arrData[] = myDb.SelectData(GID);
        if (arrData != null) {
            tID.setText(arrData[0]);
            tStall.setText(arrData[1]);
            tAge.setText(arrData[2]);
            tGenetic.setText(arrData[3]);
            tSex.setText(arrData[4]);
            tDate_start.setText(arrData[5]);
        }
    }*/
        public void ShowInfo() {
            // txtGoatID, txtStall.....
            final TextView tGoatID = (TextView) findViewById(R.id.txtGoatID);
            final TextView tStall = (TextView) findViewById(R.id.txtStall);
            final TextView tAge = (TextView) findViewById(R.id.txtAge);
            final TextView tGenetic = (TextView) findViewById(R.id.txtGenetic);
            final TextView tSex = (TextView) findViewById(R.id.txtSex);
            final TextView tDate_start = (TextView) findViewById(R.id.txtDate_start);

            String ww = "http://";
            String fphp = "/getByGoatID.php";
            String url = ww + strIP + fphp;

            Intent intent = getIntent();
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

                if (!strGoatID.equals("")) {
                    tGoatID.setText(strGoatID);
                    tStall.setText(strStall);
                    tAge.setText(strAge);
                    tGenetic.setText(strGenetic);
                    tSex.setText(strSex);
                    tDate_start.setText(strDate_start);
                } else {
                    tGoatID.setText("-");
                    tStall.setText("-");
                    tAge.setText("-");
                    tGenetic.setText("-");
                    tSex.setText("-");
                    tDate_start.setText("-");
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

    }
}
