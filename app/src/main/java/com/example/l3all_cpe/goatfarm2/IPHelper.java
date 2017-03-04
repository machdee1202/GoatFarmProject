package com.example.l3all_cpe.goatfarm2;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by l3all_cpe on 7/10/2559.
 */
public class IPHelper {
        Context context;
        SharedPreferences sharedPerfs;
        SharedPreferences.Editor editor;

        // Prefs Keys
        static String perfsName = "IPHelper";
        static int perfsMode = 0;

        public IPHelper(Context context) {
            this.context = context;
            this.sharedPerfs = this.context.getSharedPreferences(perfsName, perfsMode);
            this.editor = sharedPerfs.edit();
        }

        public void createSession(String sIP){//(String sUserName,String sPassword) {

            editor.putBoolean("LoginStatus", true);
            editor.putString("IP", sIP);
            //editor.putString("Username", sUserName);
            //editor.putString("Password", sPassword);

            editor.commit();
        }

        public void deleteSession() {
            editor.clear();
            editor.commit();
        }

        public boolean getLoginStatus() {
            return sharedPerfs.getBoolean("LoginStatus", false);
        }

        public String getIP() {
            return sharedPerfs.getString("IP", null);
        }
        //public String getUserName() {
            //return sharedPerfs.getString("Username", null);
        //}
        //public String getPassword() {
            //return sharedPerfs.getString("Password", null);
        //}

}
