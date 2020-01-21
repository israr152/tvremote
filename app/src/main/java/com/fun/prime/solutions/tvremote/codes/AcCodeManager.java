package com.fun.prime.solutions.tvremote.codes;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AcCodeManager {

    public static JSONObject getJSONObjectFromFile(Context c,String str, String str2) {
        try {
            return new JSONObject(getJSONStringFromFile(c,str, str2));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getJSONStringFromFile(Context c, String str, String str2) {
        InputStream inputStream;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
            sb.append("/");
            sb.append(str2);
            sb.append(str.replace(" ", "_"));
            File file = new File(sb.toString());
            if (file.exists()) {
                inputStream = new FileInputStream(file);
            } else {
                AssetManager assets = c.getAssets();
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str2);
                sb2.append(str.replace(" ", "_"));
                inputStream = assets.open(sb2.toString());
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb3 = new StringBuilder();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    sb3.append(readLine);
                } else {
                    bufferedReader.close();
                    inputStream.close();
                    return sb3.toString();
                }
            }
        } catch (Exception e) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append("Error reading file, check JSON format\n");
            sb4.append(str);
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<String> getList(Context c, String str) {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            String[] list = c.getAssets().list(str);
            for (String replace : list) {
                arrayList.add(replace.replace("_", " "));
            }
            String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            StringBuilder sb = new StringBuilder();
            sb.append(absolutePath);
            sb.append("/");
            sb.append(str);
            File file = new File(sb.toString());
            if (file.exists()) {
                String[] list2 = file.list();
                if (list2 != null) {
                    for (String replace2 : list2) {
                        arrayList.add(replace2.replace("_", " "));
                    }
                }
            }
            return arrayList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
