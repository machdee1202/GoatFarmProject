package com.example.l3all_cpe.goatfarm2;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddAutoMilkFeeders extends Activity {

    public static final int DIALOG_DOWNLOAD_JSON_PROGRESS = 0;
    private ProgressDialog mProgressDialog;
    ArrayList<HashMap<String, String>> MyArrList;
    private Button mEdButtonName;
    private ImageButton mButtonBack;
    Button mStatus;
    private Button buttonSave;
    private Button buttonCancle;
    private Button buttonplnAge;
    ToggleButton tSun,tMon,tTues,tWed,tThurs,tFri,tSat;
    //ArrayList<HashMap<String, String>> TimeList;

    String strIP = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_auto_milk_feeders);

        //ShowListStallAtMilk();
        // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        final IPHelper ipHelper = new IPHelper(this);
        //*** Get Login Status
   /*     if (!ipHelper.getLoginStatus()) {
            Intent newActivity = new Intent(ShowData.this, MainActivity.class);
            startActivity(newActivity);
        }*/

        strIP = ipHelper.getIP();

        new  DownloadJSONAsync().execute();


        final TextView pName = (TextView) findViewById(R.id.TextNamePattern);


        mEdButtonName = (Button) findViewById(R.id.btnEdName);
        mEdButtonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(AddAutoMilkFeeders.this);
                dialog.setTitle("แก้ไขชื่อรูปแบบ");
                dialog.setContentView(R.layout.dialog_setname);
                final EditText Name = (EditText) dialog.findViewById(R.id.edSetName);
                Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
                Button btnConfirm = (Button) dialog.findViewById(R.id.btnConfirm);
                Name.setText("รูปแบบ");



                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(getApplicationContext(), "แก้ไขชื่อแล้ว", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(getApplicationContext(), "ยกเลิก", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });



                dialog.show();
            }
        });



        tSun = (ToggleButton)findViewById(R.id.txtSun);
        tMon = (ToggleButton)findViewById(R.id.txtMon);
        tTues = (ToggleButton)findViewById(R.id.txtTues);
        tWed = (ToggleButton)findViewById(R.id.txtWed);
        tThurs = (ToggleButton)findViewById(R.id.txtThurs);
        tFri = (ToggleButton)findViewById(R.id.txtFri);
        tSat = (ToggleButton)findViewById(R.id.txtSat);




        tSun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tSun.isChecked() == true) {
                    Toast.makeText(getApplicationContext(), "เลือก_วันอาทิตย์", Toast.LENGTH_SHORT).show();


                }
                else {
                    Toast.makeText(getApplicationContext(), "ยกเลิก_วันอาทิตย์", Toast.LENGTH_SHORT).show();

                }
            }
        });

        tMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tMon.isChecked() == true) {
                    Toast.makeText(getApplicationContext(), "เลือก_วันจันทร์", Toast.LENGTH_SHORT).show();
                    tMon.setTextColor(Color.GREEN);
                }
                else {
                    Toast.makeText(getApplicationContext(), "ยกเลิก_วันจันทร์", Toast.LENGTH_SHORT).show();
                    tMon.setTextColor(Color.BLACK);
                }
            }
        });

        tTues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tTues.isChecked() == true) {
                    Toast.makeText(getApplicationContext(), "เลือก_วันอังคาร", Toast.LENGTH_SHORT).show();
                    tTues.setTextColor(Color.GREEN);
                }
                else {
                    Toast.makeText(getApplicationContext(), "ยกเลิก_วันอังคาร", Toast.LENGTH_SHORT).show();
                    tTues.setTextColor(Color.BLACK);
                }
            }
        });
        tWed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tWed.isChecked() == true) {
                    Toast.makeText(getApplicationContext(), "เลือก_วันพุธ", Toast.LENGTH_SHORT).show();
                    tWed.setTextColor(Color.GREEN);
                }
                else {
                    Toast.makeText(getApplicationContext(), "ยกเลิก_วันพุธ", Toast.LENGTH_SHORT).show();
                    tWed.setTextColor(Color.BLACK);
                }
            }
        });
        tThurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tThurs.isChecked() == true) {
                    Toast.makeText(getApplicationContext(), "เลือก_วันพฤหัสบดี", Toast.LENGTH_SHORT).show();
                    tThurs.setTextColor(Color.GREEN);
                }
                else {
                    Toast.makeText(getApplicationContext(), "ยกเลิก_วันพฤหัสบดี", Toast.LENGTH_SHORT).show();
                    tThurs.setTextColor(Color.BLACK);
                }
            }
        });
        tFri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tFri.isChecked() == true) {
                    Toast.makeText(getApplicationContext(), "เลือก_วันศุกร์", Toast.LENGTH_SHORT).show();
                    tFri.setTextColor(Color.GREEN);
                }
                else {
                    Toast.makeText(getApplicationContext(), "ยกเลิก_วันศุกร์", Toast.LENGTH_SHORT).show();
                    tFri.setTextColor(Color.BLACK);
                }
            }
        });
        tSat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tSat.isChecked() == true) {
                    Toast.makeText(getApplicationContext(), "เลือก_วันเสาร์", Toast.LENGTH_SHORT).show();
                    tSat.setTextColor(Color.GREEN);
                }
                else {
                    Toast.makeText(getApplicationContext(), "ยกเลิก_วันเสาร์", Toast.LENGTH_SHORT).show();
                    tSat.setTextColor(Color.BLACK);
                }
            }
        });


        buttonplnAge = (Button) findViewById(R.id.btnPlanAge);
        buttonplnAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(AddAutoMilkFeeders.this);
                dialog.setTitle("เลือกช่วงอายุ");
                dialog.setContentView(R.layout.dialog_planage);
                final Button plan1 = (Button) dialog.findViewById(R.id.txtplan1);
                final Button plan2 = (Button) dialog.findViewById(R.id.txtPlan2);
                plan1.setText("4วัน ถึง 2สัปดาห์");
                plan2.setText("2สัปดาห์ ถึง 6สัปดาห์");


                plan1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        buttonplnAge.getText();
                        buttonplnAge.setText("4วัน ถึง 2สัปดาห์");
                        dialog.dismiss();

                        ListTimeAutoP1();

                    }
                });
                plan2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        buttonplnAge.getText();
                        buttonplnAge.setText("2สัปดาห์ ถึง 6สัปดาห์");
                        dialog.dismiss();

                        ListTimeAutoP2();

                    }
                });

                dialog.show();
            }
        });



        buttonSave = (Button)findViewById(R.id.btnSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_SHORT).show();

            }
        });

        buttonCancle = (Button)findViewById(R.id.btnCancel);
        buttonCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "ยกเลิก", Toast.LENGTH_SHORT).show();
                finish();
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


    public void ListTimeAutoP1(){

        //final EditText tListID = (EditText) findViewById(R.id.gid);
        final EditText thTimelist = (EditText) findViewById(R.id.edthTimelist);
        final EditText tmTimelist = (EditText) findViewById(R.id.edtmTimelist);
        final EditText tMilklist = (EditText) findViewById(R.id.edtMilklist);
        //DBAutoFeeders myDb = new DBAutoFeeders(this);
        //TimeList = myDb.SelectAllData();
        int i=0;
        i++;


        ListView lisView1 = (ListView)findViewById(R.id.listViewTime);
        ArrayList<HashMap<String, String>> TimeList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;


        //row1
        map = new HashMap<String, String>();
        map.put("listID", "" + i++);
        TimeList.add(map);

        //row2
        map = new HashMap<String, String>();
        map.put("listID", "" + i++);
        TimeList.add(map);

        //row3
        map = new HashMap<String, String>();
        map.put("listID", "" + i++);
        TimeList.add(map);

        SimpleAdapter sAdap;
        sAdap = new SimpleAdapter(AddAutoMilkFeeders.this, TimeList, R.layout.setlist_time,
                new String[] {"hTimelist","mTimelist","Milklist"},
                new int[] {R.id.edthTimelist,R.id.edtmTimelist, R.id.edtMilklist});
        lisView1.setAdapter(sAdap);

    }
    public void ListTimeAutoP2(){

        //final EditText tListID = (EditText) findViewById(R.id.gid);
        final EditText thTimelist = (EditText) findViewById(R.id.edthTimelist);
        final EditText tmTimelist = (EditText) findViewById(R.id.edtmTimelist);
        final EditText tMilklist = (EditText) findViewById(R.id.edtMilklist);
        //DBAutoFeeders myDb = new DBAutoFeeders(this);
        //TimeList = myDb.SelectAllData();
        int i=0;
        i++;


        ListView lisView1 = (ListView)findViewById(R.id.listViewTime);
        ArrayList<HashMap<String, String>> TimeList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;


        //row1
        map = new HashMap<String, String>();
        map.put("listID", "" + i++);
        TimeList.add(map);

        //row2
        map = new HashMap<String, String>();
        map.put("listID", "" + i++);
        TimeList.add(map);

        //row3
   /*     map = new HashMap<String, String>();
        map.put("listID", "" + i++);
        TimeList.add(map); */

        SimpleAdapter sAdap;
        sAdap = new SimpleAdapter(AddAutoMilkFeeders.this, TimeList, R.layout.setlist_time,
                new String[] {"hTimelist","mTimelist","Milklist"},
                new int[] {R.id.edthTimelist,R.id.edtmTimelist, R.id.edtMilklist});
        lisView1.setAdapter(sAdap);

    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DOWNLOAD_JSON_PROGRESS:
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("กำลังเรียกข้อมูล.....");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                return mProgressDialog;
            default:
                return null;
        }
    }
    public void ShowListStallAtMilk(){

        final ListView lisView1 = (ListView)findViewById(R.id.listView_slStallAtMilk);

        String ww = "http://";
        String fphp ="/showListStall.php";
        String url = ww+strIP+fphp;


        // Paste Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        String resultServer  = NetConnect.getHttpPost(url,params);

        try {
            JSONArray data = new JSONArray(resultServer);

            MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for(int i = 0; i < data.length(); i++){
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();

                map.put("Stall", c.getString("Stall"));
                MyArrList.add(map);

            }
            lisView1.setAdapter(new ImageAdapter(this));
            registerForContextMenu(lisView1);



        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    public class ImageAdapter extends BaseAdapter
    {
        private Context context;

        public ImageAdapter(Context c)
        {
            // TODO Auto-generated method stub
            context = c;
        }

        public int getCount() {
            // TODO Auto-generated method stub
            return MyArrList.size();
        }

        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.setlist_stall, null);
            }


            final CheckBox chk_slStall = (CheckBox) convertView.findViewById(R.id.chk_slStall);

            chk_slStall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (chk_slStall.isChecked()==true){
                        Toast.makeText(getApplicationContext(), "เลือกคอกที่ : "+ MyArrList.get(position).get("Stall"), Toast.LENGTH_SHORT).show();
                    }

                    // Request to Select Stall.

                }
            });

            // Collist_Stall
            TextView txtStall = (TextView) convertView.findViewById(R.id.Collist_stall);
            txtStall.setPadding(5, 0, 0, 0);
            txtStall.setText(MyArrList.get(position).get("Stall"));





            return convertView;

        }

    }

    // Download JSON in Background
    public class DownloadJSONAsync extends AsyncTask<String, Void, Void> {


        ArrayList<HashMap<String, String>> mArrList;

        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub


            /** JSON return
             *  [{"MemberID":"1","Name":"Weerachai","Tel":"0819876107"},
             * {"MemberID":"2","Name":"Win","Tel":"021978032"},
             * {"MemberID":"3","Name":"Eak","Tel":"0876543210"}]
             */
            String ww = "http://";
            String fphp = "/showListStall.php";
            String url = ww + strIP + fphp;

            //String url = "http://www.thaicreate.com/android/getJSON.php";
            String resultServer = NetConnect.getHttpGet(url);
            try {


                JSONArray data = new JSONArray(resultServer);

                MyArrList = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> map;

                for (int i = 0; i < data.length(); i++) {
                    JSONObject c = data.getJSONObject(i);

                    map = new HashMap<String, String>();
                    map.put("GoatID", c.getString("GoatID"));
                    map.put("Stall", c.getString("Stall"));
                    map.put("Age", c.getString("Age"));
                    map.put("Genetic", c.getString("Genetic"));
                    map.put("Sex", c.getString("Sex"));
                    map.put("Date_start", c.getString("Date_start"));

                    MyArrList.add(map);

                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            return null;
        }

        protected void onPostExecute(Void unused) {
            ShowListStallAtMilk(); // When Finish Show Content
            dismissDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
            removeDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
        }
    }
}



