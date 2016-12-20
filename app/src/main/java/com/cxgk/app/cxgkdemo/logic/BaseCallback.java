package com.cxgk.app.cxgkdemo.logic;

import android.util.Log;

import com.cxgk.app.cxgkdemo.utils.LogUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Huang on 2016/7/28.
 */
public abstract class BaseCallback implements Callback<ResponseBody> {
    public abstract void onResponse(String result);

    public abstract void onFailure(String error);

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        ResponseBody body = response.body();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(body.byteStream()));
            StringBuilder out = new StringBuilder();
            String newLine = System.getProperty("line.separator");//换行符号
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
                out.append(newLine);
            }

            // Prints the correct String representation of body.
            Log.i("onResponse", URLEncoder.encode(out.toString(), "utf-8"));
            onResponse(out.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onFailure(Call call, Throwable t) {
        try {
            LogUtils.e("onFailure", t.getMessage());
            t.printStackTrace();
            onFailure(t.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
