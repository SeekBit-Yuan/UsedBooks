package com.seek.usedbooks.activities;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seek.usedbooks.R;
import com.seek.usedbooks.ui.CarouselViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 一丝不狗 on 2017/3/29.
 */

public class ProductDetailActivity extends BaseActivity implements View.OnClickListener{

    private CarouselViewPager mCarouselView;
    private List<ImageView> ivList = new ArrayList<ImageView>();
    private int[] ivIds={R.mipmap.ic_pic1,R.mipmap.ic_pic2,R.mipmap.ic_pic3,R.mipmap.ic_pic4};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.activity_product_detail_title);
//        initViews();
    }

    private void initViews(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.activity_product_detail_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView tv = (TextView) findViewById(R.id.originPrice);
        tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        TextView review = (TextView) findViewById(R.id.review);
        TextView spec = (TextView) findViewById(R.id.spec);
        TextView detail = (TextView) findViewById(R.id.detail);
        ImageView increaseQuantity = (ImageView) findViewById(R.id.increase_quantity);
        ImageView decreaseQuantity = (ImageView) findViewById(R.id.decrease_quantity);
        TextView fav = (TextView) findViewById(R.id.fav);
        TextView cart = (TextView) findViewById(R.id.cart);
        TextView add_to_cart = (TextView) findViewById(R.id.add_to_cart);
        RelativeLayout info1Layout = (RelativeLayout) findViewById(R.id.info1_layout);
        RelativeLayout info2Layout = (RelativeLayout) findViewById(R.id.info2_layout);
        RelativeLayout info3Layout = (RelativeLayout) findViewById(R.id.info3_layout);
    }

    @Override
    public void onClick(View view) {

    }
}
