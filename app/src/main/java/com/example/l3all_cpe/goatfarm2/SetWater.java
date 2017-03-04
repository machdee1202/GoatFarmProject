package com.example.l3all_cpe.goatfarm2;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SetWater extends Activity {


    public static final int DIALOG_DOWNLOAD_JSON_PROGRESS = 0;
    private ProgressDialog mProgressDialog;
    ArrayList<HashMap<String, String>> MyArrList;


    private ImageButton mButtonBack;
    String strIP = "";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_water);
        selectMenu();

       // ListWater();

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

    public void ListWater(){

        //final EditText tListID = (EditText) findViewById(R.id.gid);

                //DBAutoFeeders myDb = new DBAutoFeeders(this);
                //TimeList = myDb.SelectAllData();
                int i = 0;
                i++;


                ListView lisView1 = (ListView) findViewById(R.id.listViewWater);
                ArrayList<HashMap<String, String>> StallList = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> map;


                int j = 0;
                j++;

                map = new HashMap<String, String>();
                map.put("Stall", "1");
                StallList.add(map);

                map = new HashMap<String, String>();
                map.put("Stall", "2");
                StallList.add(map);

                map = new HashMap<String, String>();
                map.put("Stall", "3");
                StallList.add(map);

                SimpleAdapter sAdap;
                sAdap = new SimpleAdapter(SetWater.this, StallList, R.layout.setlist_stallwater,
                        new String[]{"Stall"},
                        new int[]{R.id.Collist_stallwater});
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
    public void ShowListWater(){

        final ListView lisView1 = (ListView)findViewById(R.id.listViewWater);

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
                convertView = inflater.inflate(R.layout.setlist_stallwater, null);
            }

            final Switch sw_slStall = (Switch) convertView.findViewById(R.id.swWater);

            sw_slStall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sw_slStall.isChecked()==true){
                        Toast.makeText(getApplicationContext(), "เปิดน้ำคอกที่ : "+ MyArrList.get(position).get("Stall"), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "ปิดน้ำคอกที่ : "+ MyArrList.get(position).get("Stall"), Toast.LENGTH_SHORT).show();
                    }

                    // Request to Select Stall.

                }
            });


            // Collist_Stall
            TextView txtStall = (TextView) convertView.findViewById(R.id.Collist_stallwater);
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
            ShowListWater(); // When Finish Show Content
            dismissDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
            removeDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
        }
    }


    private void selectMenu() {

        mButtonBack = (ImageButton) findViewById(R.id.button_back);

    }
}
