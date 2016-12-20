package com.cxgk.app.cxgkdemo.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.cxgk.app.cxgkdemo.R;
import com.cxgk.app.cxgkdemo.fragment.AddCarFragment;
import com.cxgk.app.cxgkdemo.fragment.SearchCarFragment;
import com.cxgk.app.cxgkdemo.fragment.StringSortFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {
    BottomNavigationBar mBottomNavigationBar;
    private String TAG = MainActivity.class.getSimpleName();
    AddCarFragment mAddCarFragment;
    StringSortFragment mStringSortFragment;
    SearchCarFragment mSearchCarFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        //1选中的颜色，2未选中的颜色，3背景色
        mBottomNavigationBar.setActiveColor(R.color.bg_title).setInActiveColor(R.color.base_text).setBarBackgroundColor("#FFFFFF");
        mBottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.tab_list, "添加"))
                .addItem(new BottomNavigationItem(R.mipmap.tab_sort, "排序"))
                .addItem(new BottomNavigationItem(R.mipmap.tab_search, "搜索"))
                .setFirstSelectedPosition(0).initialise();
        mBottomNavigationBar.setTabSelectedListener(this);
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mAddCarFragment = new AddCarFragment();
        transaction.add(R.id.fl_content, mAddCarFragment);
        transaction.commit();
    }

    @Override
    public void onTabSelected(int position) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        switch (position) {
            case 0:
                if (mStringSortFragment != null && mStringSortFragment.isAdded()) {
                    transaction.hide(mStringSortFragment);
                }
                if (mSearchCarFragment != null && mSearchCarFragment.isAdded()) {
                    transaction.hide(mSearchCarFragment);
                }
                if (mAddCarFragment == null) {
                    mAddCarFragment = new AddCarFragment();
                    transaction.add(R.id.fl_content, mAddCarFragment);
                } else {
                    transaction.show(mAddCarFragment);
                }
                break;
            case 1:

                if (mAddCarFragment != null && mAddCarFragment.isAdded()) {
                    transaction.hide(mAddCarFragment);
                }
                if (mSearchCarFragment != null && mSearchCarFragment.isAdded()) {
                    transaction.hide(mSearchCarFragment);
                }
                if (mStringSortFragment == null) {
                    mStringSortFragment = new StringSortFragment();
                    transaction.add(R.id.fl_content, mStringSortFragment);
                } else {
                    transaction.show(mStringSortFragment);
                }
                break;
            case 2:

                if (mAddCarFragment != null && mAddCarFragment.isAdded()) {
                    transaction.hide(mAddCarFragment);
                }
                if (mStringSortFragment != null && mStringSortFragment.isAdded()) {
                    transaction.hide(mStringSortFragment);
                }
                if (mSearchCarFragment == null) {
                    mSearchCarFragment = new SearchCarFragment();
                    transaction.add(R.id.fl_content, mSearchCarFragment);
                } else {
                    transaction.show(mSearchCarFragment);
                }
                break;
        }

        transaction.commit();
    }

    //    @Override
    //    public boolean onKeyDown(int keyCode, KeyEvent event) {
    //        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
    //            if (mAddCarFragment != null && mAddCarFragment.hidentSearchView()) {
    //                return true;
    //            }
    //        }
    //        return super.onKeyDown(keyCode, event);
    //    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
