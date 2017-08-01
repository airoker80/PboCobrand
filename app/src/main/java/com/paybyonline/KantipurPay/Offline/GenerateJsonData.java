package com.paybyonline.KantipurPay.Offline;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by Anish on 9/26/2016.
 */
public class GenerateJsonData {

    public  static String getJsonData( Context context,int fileName){
        String rechargeData = "";
        try{
            InputStream is = context.getResources().openRawResource(fileName);
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            String jsonString = writer.toString();
            rechargeData = jsonString;


        }catch (Exception ex){
            Log.i("exception ", "get recharge services " + ex + "");
        }
        return rechargeData;
    }

}
