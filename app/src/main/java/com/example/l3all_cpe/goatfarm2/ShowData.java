package com.example.l3all_cpe.goatfarm2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowData extends Activity {

    ArrayList<HashMap<String, String>> MyArrList;
    //String[] Cmd = {"View","Update","Delete"};
    public static final int DIALOG_DOWNLOAD_JSON_PROGRESS = 0;
    private ProgressDialog mProgressDialog;

    private ImageButton mButtonBack;
    private ImageButton mButtonAdd;
    String strIP = "";

    //ArrayList<HashMap<String, String>> GoatList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_show_data);
        setProgressBarIndeterminateVisibility(false);
        selectMenu();
        setWidgetEvenListener();

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
        //ShowlistData();
        //ShowListData();
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

    public void ShowlistData(){

        final ListView lisView1 = (ListView)findViewById(R.id.listView1);

        String ww = "http://";
        String fphp ="/showAllGoatData.php";
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
                map.put("GoatID", c.getString("GoatID"));
                map.put("Stall", c.getString("Stall"));
                map.put("Age", c.getString("Age"));
                map.put("Genetic", c.getString("Genetic"));
                map.put("Sex", c.getString("Sex"));
                map.put("Date_start", c.getString("Date_start"));

                MyArrList.add(map);

            }
            lisView1.setAdapter(new ImageAdapter(this));
            registerForContextMenu(lisView1);
            // OnClick Item
            lisView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> myAdapter, View myView,
                                        int position, long mylng) {

                    String sGoatID = MyArrList.get(position).get("GoatID").toString();
                    String sStall = MyArrList.get(position).get("Stall").toString();
                    String sAge = MyArrList.get(position).get("Age").toString();
                    String sGenetic = MyArrList.get(position).get("Genetic").toString();
                    String sSex = MyArrList.get(position).get("Sex").toString();
                    String sDate_start = MyArrList.get(position).get("Date_start").toString();

                    Intent newActivity = new Intent(ShowData.this,GoatDetail.class);
                    newActivity.putExtra("GoatID", sGoatID);
                    startActivity(newActivity);

                }
            });

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
                convertView = inflater.inflate(R.layout.setcolum, null);
            }

            // ColGoatID
            TextView txtGoatID = (TextView) convertView.findViewById(R.id.ColGoatID);
            txtGoatID.setPadding(5, 0, 0, 0);
            txtGoatID.setText(MyArrList.get(position).get("GoatID") +".");

            // R.id.ColStall
            TextView txtStall = (TextView) convertView.findViewById(R.id.ColStall);
            txtStall.setPadding(5, 0, 0, 0);
            txtStall.setText(MyArrList.get(position).get("Stall"));

            // R.id.ColAge
            TextView txtAge = (TextView) convertView.findViewById(R.id.ColAge);
            txtAge.setPadding(5, 0, 0, 0);
            txtAge.setText(MyArrList.get(position).get("Age"));

            // R.id.ColGenetic
            TextView txtGenetic = (TextView) convertView.findViewById(R.id.ColGenetic);
            txtGenetic.setPadding(5, 0, 0, 0);
            txtGenetic.setText(MyArrList.get(position).get("Genetic"));

            // R.id.ColAge
            TextView txtSex = (TextView) convertView.findViewById(R.id.ColSex);
            txtSex.setPadding(5, 0, 0, 0);
            txtSex.setText(MyArrList.get(position).get("Sex"));

            // R.id.ColAge
            TextView txtDate_start = (TextView) convertView.findViewById(R.id.ColDate_start);
            txtDate_start.setPadding(5, 0, 0, 0);
            txtDate_start.setText(MyArrList.get(position).get("Date_start"));


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
            String fphp ="/showAllGoatData.php";
            String url = ww+strIP+fphp;

            //String url = "http://www.thaicreate.com/android/getJSON.php";
            String resultServer  = NetConnect.getHttpGet(url);
            try {


                JSONArray data = new JSONArray(resultServer);

                MyArrList = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> map;

                for(int i = 0; i < data.length(); i++){
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
            ShowlistData(); // When Finish Show Content
            dismissDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
            removeDialog(DIALOG_DOWNLOAD_JSON_PROGRESS);
        }

    }



    /*@Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String[] menuItems = Cmd;
        String CmdName = menuItems[menuItemIndex];

        // Check Event Command
        if ("แก้ไข".equals(CmdName)) {
            Toast.makeText(ShowData.this,"Your Selected Update",Toast.LENGTH_LONG).show();

            String sMemberID = MyArrList.get(info.position).get("GoatID").toString();
            String sStall = MyArrList.get(info.position).get("Name").toString();
            String sAge = MyArrList.get(info.position).get("Tel").toString();

            Intent newActivity = new Intent(ShowData.this,UpdateData.class);
            newActivity.putExtra("MemberID", sMemberID);
            startActivity(newActivity);

        } else if ("Delete".equals(CmdName)) {
            Toast.makeText(ShowData.this,"Your Selected Delete",Toast.LENGTH_LONG).show();
            /**
             * Call the mthod
             */
      /*  }
        return true;
    }*/


    // Show List data
    /*public void ShowListData()
    {
        Goat_Database myDb = new Goat_Database(this);
        GoatList = myDb.SelectAllData();

        // listView1
        ListView lisView1 = (ListView)findViewById(R.id.listView1);

        SimpleAdapter sAdap;
        sAdap = new SimpleAdapter(ShowData.this, GoatList, R.layout.setcolum,
                new String[] {"ID","Stall","Age","Genetic","Sex","Date_start"}, new int[] {R.id.ColGoatID, R.id.ColStall, R.id.ColAge, R.id.ColGenetic, R.id.ColSex, R.id.ColDate_start});
        lisView1.setAdapter(sAdap);
        registerForContextMenu(lisView1);

        lisView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int position, long mylng) {

                //Show on new activity
                Intent newActivity = new Intent(ShowData.this,GoatDetail.class);
                newActivity.putExtra("GID", GoatList.get(position).get("ID").toString());
                startActivity(newActivity);
            }
        });
    }*/

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        //if (v.getId()==R.id.list) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        menu.setHeaderTitle("แพะคอกที่ : " + MyArrList.get(info.position).get("Stall").toString());
        String[] menuItems = getResources().getStringArray(R.array.CmdMenu);
        for (int i = 0; i<menuItems.length; i++) {
            menu.add(Menu.NONE, i, i, menuItems[i]);
        }
        //}
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String[] menuItems = getResources().getStringArray(R.array.CmdMenu);
        String CmdName = menuItems[menuItemIndex];
        //String GID = GoatList.get(info.position).get("ID").toString();
        //String MemName = MebmerList.get(info.position).get("Name").toString();

        // Check Event Command
        if ("แก้ไข".equals(CmdName)) {

            // Show on new activity
            Toast.makeText(ShowData.this,"แก้ไข",Toast.LENGTH_LONG).show();
            //Intent newActivity = new Intent(ShowData.this,UpdateData.class);
            String sGoatID = MyArrList.get(info.position).get("GoatID").toString();
            String sStall = MyArrList.get(info.position).get("Stall").toString();
            String sAge = MyArrList.get(info.position).get("Age").toString();
            String sGenetic = MyArrList.get(info.position).get("Genetic").toString();
            String sSex = MyArrList.get(info.position).get("Sex").toString();
            String sDate_start = MyArrList.get(info.position).get("Date_start").toString();

            Intent newActivity = new Intent(ShowData.this,UpdateData.class);
            newActivity.putExtra("GoatID", sGoatID);
            finish();
            startActivity(newActivity);
            //newActivity.putExtra("GID", GoatList.get(info.position).get("ID").toString());
            //startActivity(newActivity);

            // for Delete Command
        } else if ("ลบ".equals(CmdName)) {

            final AlertDialog.Builder adb1 = new AlertDialog.Builder(ShowData.this);
            final AlertDialog.Builder adb2 = new AlertDialog.Builder(ShowData.this);
            adb1.setTitle("ลบข้อมูล?");
            adb1.setMessage("คุณแน่ใจหรือว่าจะลบข้อมูล [" + MyArrList.get(info.position).get("Stall") + "]");
            adb1.setNegativeButton("ยกเลิก", null);
            adb1.setPositiveButton("ยืนยัน", new AlertDialog.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    // Request to Delete data.
                    String ww = "http://";
                    String fphp = "/deleteGoatData.php";
                    String url = ww + strIP + fphp;

                    //String url = "http://www.thaicreate.com/android/deleteData.php";

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("sGoatID", MyArrList.get(info.position).get("GoatID")));

                    String resultServer = NetConnect.getHttpPost(url, params);

                    /** Get result delete data from Server (Return the JSON Code)
                     * StatusID = ? [0=Failed,1=Complete]
                     * Error	= ?	[On case error return custom error message]
                     *
                     * Eg Login Failed = {"StatusID":"0","Error":"Cannot delete data!"}
                     * Eg Login Complete = {"StatusID":"1","Error":""}
                     */
                    String strStatusID = "0";
                    String strError = "Unknow Status";

                    try {
                        JSONObject c = new JSONObject(resultServer);
                        strStatusID = c.getString("StatusID");
                        strError = c.getString("Error");
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    // Prepare Delete
                    if (strStatusID.equals("0")) {
                        // Dialog
                        adb2.setTitle("ผิดพลาด! ");
                        adb2.setIcon(android.R.drawable.btn_star_big_on);
                        adb2.setPositiveButton("ปิด", null);
                        adb2.setMessage(strError);
                        adb2.show();
                    } else {
                        Toast.makeText(ShowData.this, "ลบข้อมูลสำเร็จ.", Toast.LENGTH_SHORT).show();
                        ShowlistData(); // reload data again
                    }

                }
            });
            adb1.show();


            /*Goat_Database myDb = new Goat_Database(this);

            long flg = myDb.DeleteData(GID);
            if(flg > 0)
            {
                Toast.makeText(ShowData.this,"ลบข้อมูลสำเร็จ",
                        Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(ShowData.this,"ลบข้อมูลไม่สำเร็จ! ",
                        Toast.LENGTH_LONG).show();
            }*/
        }
            // Call Show Data again
            ShowlistData();


        return true;
    }


    private void setWidgetEvenListener() {
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "ย้อนกลับไป", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "เพิ่มข้อมูล", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(getApplication(), Add_Data.class));
            }
        });
    }

    private void selectMenu() {
        mButtonBack = (ImageButton) findViewById(R.id.button_back);
        mButtonAdd = (ImageButton) findViewById(R.id.button_add);
    }
}
