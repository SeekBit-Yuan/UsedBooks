package com.seek.usedbooks.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.seek.usedbooks.R;
import com.seek.usedbooks.fragments.AddFragment;
import com.seek.usedbooks.fragments.MessageFragment;
import com.seek.usedbooks.fragments.MainFragment;
import com.seek.usedbooks.fragments.MineFragment;
import com.seek.usedbooks.fragments.ShopFragment;

public class MainActivity extends FragmentActivity{
    public static final String INTENT_TAB_INDEX = "index";
    private FragmentTabHost mTabHost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        ininTab();
    }

    private void ininTab(){
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.getTabWidget().setDividerDrawable(null);
        mTabHost.addTab(mTabHost.newTabSpec("mainTab").setIndicator(getTabView(R.drawable.btn_home, R.string.activity_main_tab_home)),
                MainFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("shopTab").setIndicator(getTabView(R.drawable.btn_shop, R.string.activity_main_tab_shop)),
                ShopFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("addTab").setIndicator(getTabView(R.drawable.btn_add, R.string.activity_main_tab_add)),
                AddFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("messageTab").setIndicator(getTabView(R.drawable.btn_message, R.string.activity_main_tab_message)),
                MessageFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("mineTab").setIndicator(getTabView(R.drawable.btn_mine, R.string.activity_main_tab_mine)),
                MineFragment.class, null);
        selectTab(getIntent().getIntExtra(INTENT_TAB_INDEX, 0));
    }

    public void selectTab(int index) {
        mTabHost.setCurrentTab(index);
    }

    private View getTabView(int imgId, int txtId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.view_home_tab, null);
        ((ImageView) view.findViewById(R.id.tab_img)).setImageResource(imgId);
        ((TextView) view.findViewById(R.id.tab_label)).setText(txtId);
        return view;
    }
}
