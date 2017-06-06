package com.example.l3all_cpe.goatfarm2;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Select_StallWashing extends Activity {



    public static final int DIALOG_DOWNLOAD_JSON_PROGRESS = 0;
    private ProgressDialog mProgressDialog;
    ArrayList<HashMap<String, String>> MyArrList;
    private ImageButton mButtonBack;
    private Button mButtonConfirm;
    private Button mButtonCancel;

    String strIP = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__stall_washing);
        selectMenu();


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



        mButtonConfirm.setOnClickListener(new View.OnClickListener()

                                          {
                                              @Override
                                              public void onClick(View view) {
                                                  Toast.makeText(getApplicationContext(), "ยืนยัน", Toast.LENGTH_SHORT).show();

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
    public void ShowListStallWashing(){

        final ListView lisView1 = (ListView)findViewById(R.id.listView_slStallWashing);

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
                        Log.d("เลือกคอกที่  "+MyArrList.get(position).get("Stall").toString(), chk_slStall.getTag().toString());
                        Toast.makeText(getApplicationContext(), "เลือกคอกที่ : "+ MyArrList.get(position).get("Stall"), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Log.d("ยกเลิกคอกที่  "+MyArrList.get(position).get("Stall").toString(), chk_slStall.getTag().toString());
                        Toast.makeText(getApplicationContext(), "ยกเลิกคอกที่ : "+ MyArrList.get(position).get("Stall"), Toast.LENGTH_SHORT).show();
                    }
                    // Request to Select Stall.

                }
            });
            mButtonConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final ListView lisView1 = (ListView)findViewById(R.id.listView_slStallMilk);
                    //lisView1.setAdapter(new ImageAdapter(Select_StallMilk.this));
                    Intent newActivity = new Intent(Select_StallWashing.this,SetWashing.class);

                    int count = lisView1.getAdapter().getCount();
                    for (int i = 0; i < count; i++) {
                        LinearLayout itemLayout = (LinearLayout)lisView1.getChildAt(i); // Find by under LinearLayout
                        CheckBox chk_slStall = (CheckBox)itemLayout.findViewById(R.id.chk_slStall);

                        if(chk_slStall.isChecked()==true)
                        {
                            //Log.d("Item  "+String.valueOf(i), chk_slStall.getTag().toString());
                            Log.d("Item  ", chk_slStall.getTag().toString());
                            Toast.makeText(getApplicationContext(), "เลือกคอกที่ " + chk_slStall.getTag().toString() ,Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getApplicationContext(), "เลือกคอกที่ : "+ MyArrList.get(position).get("Stall"), Toast.LENGTH_SHORT).show();
                            //***รอแก้การส่งค่า***//
                            String sStall= chk_slStall.getTag().toString();
                            //Intent newActivity = new Intent(Select_StallMilk.this,SetMilkFeeders.class);
                            //***รอแก้การส่งค่า***//
                            newActivity.putExtra("Stall", sStall);
                            //startActivity(newActivity);


                        }

                    }
                    startActivity(newActivity);
                    finish();

                }
            });

            // Collist_Stall
            TextView txtStall = (TextView) convertView.findViewById(R.id.Collist_stall);
            txtStall.setPadding(5, 0, 0, 0);
            txtStall.setText(MyArrList.get(position).get("Stall"));

            // ColChk
            CheckBox Chk_Stall = (CheckBox) convertView.findViewById(R.id.chk_slStall);
            Chk_Stall.setTag(MyArrList.get(position).get("Stall"));



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
                    map.put("Stall", c.getString("Stall"));


                    MyArrList.add(map);

                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            return null;
        }

        protected void onPostExecute(Void unused) {
            ShowListStallWashing(); // When Finish Show Content
            dismissDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
            removeDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
        }
    }
    private void selectMenu() {

        mButtonBack = (ImageButton) findViewById(R.id.button_back);
        mButtonConfirm = (Button) findViewById(R.id.btnConfirm);
        mButtonCancel = (Button) findViewById(R.id.btnCancel);

    }
}
