package com.example.l3all_cpe.goatfarm2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SetMilkFeeders extends Activity {
    private Button mActiveMilk;
    private Button mSelectStall;
    private ImageButton mButtonBack;
    private TextView btAdd;
    private TextView btDel;
    Button btnOnOff;
    RadioButton rdBt;
    ArrayList<HashMap<String, String>> BtnList;


    String strIP = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_milk_feeders);
        selectMenu();


        //setWidgetEvenListener();


        // Permission StrictMode
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


       // mSelectStall
        mSelectStall.setOnClickListener(new View.OnClickListener()

                                       {
                                           @Override
                                           public void onClick(View v) {
                                               Toast.makeText(getApplicationContext(), "เลือกคอก", Toast.LENGTH_SHORT).show();
                                               startActivity(new Intent(getApplicationContext(), Select_StallMilk.class));
                                               finish();
                                           }

                                       }

        );

        mActiveMilk.setOnClickListener(new View.OnClickListener()

                                       {
                                           @Override
                                           public void onClick(View v) {
                                               Toast.makeText(getApplicationContext(), "ให้อาหาร", Toast.LENGTH_SHORT).show();
                                               Activemilk_now();
                                           }

                                       }

        );
        mButtonBack.setOnClickListener(new View.OnClickListener()

                                       {
                                           @Override
                                           public void onClick(View view) {
                                               Toast.makeText(getApplicationContext(), "ย้อนกลับ", Toast.LENGTH_SHORT).show();
                                               finish();
                                           }
                                       }

        );
        btAdd.setOnClickListener(new View.OnClickListener()

                                 {
                                     @Override
                                     public void onClick(View v) {
                                         Toast.makeText(getApplicationContext(), "เพิ่ม", Toast.LENGTH_SHORT).show();
                                         startActivity(new Intent(getApplicationContext(), AddAutoMilkFeeders.class));
                                     }
                                 }

        );
        btDel.setOnClickListener(new View.OnClickListener()

                                 {
                                     @Override
                                     public void onClick(View v) {

                                         final AlertDialog.Builder adb1 = new AlertDialog.Builder(SetMilkFeeders.this);
                                         final AlertDialog.Builder adb2 = new AlertDialog.Builder(SetMilkFeeders.this);
                                         adb1.setTitle("ลบข้อมูล?");
                                         adb1.setMessage("test");
                                         //adb1.setMessage("คุณแน่ใจหรือว่าจะลบข้อมูล [" + MyArrList.get(info.position).get("Stall") + "]");
                                         adb1.setNegativeButton("ยกเลิก", null);
                                         adb1.setPositiveButton("ยืนยัน",null);




                                         adb1.show();

                                     }
                                 }

        );

        Show_SelectStall();
        ShowListButton();


    }


    public  void Activemilk_now(){

         final EditText strinputmilk = (EditText)findViewById(R.id.strInmilk) ;



        // Disbled Keyboard auto focus
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(strinputmilk.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        return;
    }

    public void Show_SelectStall(){

        // txtGoatID, txtStall.....

        final TextView tStall = (TextView) findViewById(R.id.txtStallSL);

        String ww = "http://";
        String fphp ="/getByStall_Select.php";
        String url = ww + strIP + fphp;

        Intent intent = getIntent();
        final String Stall = intent.getStringExtra("Stall");

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sStall", Stall));

        /** Get result from Server (Return the JSON Code)
         *
         * {"MemberID":"2","Username":"adisorn","Password":"adisorn@2","Name":"Adisorn Bunsong","Tel":"021978032","Email":"adisorn@thaicreate.com"}
         */

        String resultServer = NetConnect.getHttpPost(url, params);

        String strStall = "";


        JSONObject c;
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
        }
    }



    public void ShowListButton() {


        //final Button Feeders=(Button)findViewById(R.id.btStatusFeeders);
        //final Button Swiched =(Button)findViewById(R.id.btSwiched) ;
        int j=0;
        j++;


      //  final DBAutoFeeders myDb = new DBAutoFeeders(this);
       // BtnList = myDb.SelectAllData();

        // listView1
        ListView listView1 = (ListView) findViewById(R.id.listViewPattern);
        ArrayList<HashMap<String, String>> BtnList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;


        map = new HashMap<String, String>();
        map.put("BtnID", "" + j++);
        BtnList.add(map);

        map = new HashMap<String, String>();
        map.put("BtnID", "" + j++);
        BtnList.add(map);


        SimpleAdapter sAdap;

        sAdap = new SimpleAdapter(SetMilkFeeders.this, BtnList, R.layout.setbutton,
                new String[] {"Name","Status"}, new int[] {R.id.btPattern, R.id.btSwiched});
        listView1.setAdapter(sAdap);


        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int position, long mylng) {

                Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_SHORT).show();


                //Show on new activity
                //Intent newActivity = new Intent(SetMilkFeeders.this,GoatDetail.class);
                //newActivity.putExtra("GID", BtnList.get(position).get("ID").toString());
                //startActivity(newActivity);
            }
        });


    }





    public void StatusOn() {
        btnOnOff.setEnabled(true);
        btnOnOff.setText("ON");
    }
    public void StatusOff() {
        btnOnOff.setEnabled(true);
        btnOnOff.setText("OFF");
    }

    private void selectMenu() {

        btAdd = (TextView) findViewById(R.id.btAdd);
        btDel = (TextView) findViewById(R.id.btDel);
        mButtonBack = (ImageButton) findViewById(R.id.button_back);
        mActiveMilk = (Button) findViewById(R.id.btActiveNow);
        mSelectStall = (Button) findViewById(R.id.btStallActiveNow);
    }
}