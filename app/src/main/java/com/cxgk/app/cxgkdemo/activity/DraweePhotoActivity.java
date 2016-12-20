package com.cxgk.app.cxgkdemo.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cxgk.app.cxgkdemo.utils.Constants;
import com.cxgk.app.cxgkdemo.views.DraweePhotoView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by lenovo on 2016/12/20.
 */

public class DraweePhotoActivity extends AppCompatActivity {
    //    private PhotoViewAttacher mAttacher;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String uriStr = getIntent().getStringExtra(Constants.KEY_DEFAULE);
        DraweePhotoView draweePhotoView = new DraweePhotoView(this);
        try {
            draweePhotoView.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(new File(uriStr))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        setContentView(draweePhotoView);
    }

}
