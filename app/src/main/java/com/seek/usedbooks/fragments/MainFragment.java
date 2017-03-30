package com.seek.usedbooks.fragments;

/**
 * Created by 一丝不狗 on 2017/3/23.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.seek.usedbooks.R;
import com.seek.usedbooks.activities.ProductDetailActivity;
import com.seek.usedbooks.adapters.ProductTypeAdapter;
import com.seek.usedbooks.adapters.CarouselPagerAdapter;
import com.seek.usedbooks.models.Product;
import com.seek.usedbooks.models.ProductType;
import com.seek.usedbooks.ui.CarouselViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainFragment extends Fragment implements ViewPager.OnPageChangeListener,AdapterView.OnItemClickListener{
    private CarouselViewPager mCarouselView;
    private List<ImageView> ivList = new ArrayList<ImageView>();
    private int[] ivIds={R.mipmap.ic_pic1,R.mipmap.ic_pic2,R.mipmap.ic_pic3,R.mipmap.ic_pic4};

    private List<Map<String,Object>> dataList;
    private int[] imageRes={
            R.mipmap.ic_fenlei,
            R.mipmap.ic_wenxue,
            R.mipmap.ic_shishang,
            R.mipmap.ic_shuxue,
            R.mipmap.ic_shenghuo,
            R.mipmap.ic_jiaofu,
            R.mipmap.ic_ziran,
            R.mipmap.ic_xuexi,
            R.mipmap.ic_jisuanji,
            R.mipmap.ic_yixue};
    private String[] strings={
            "所有分类",
            "文学艺术",
            "时尚前沿",
            "数学",
            "生活休闲",
            "教辅资料",
            "自然科学",
            "考试教育",
            "计算机",
            "医学"};
    private SimpleAdapter adaper;

    private ImageView[] indicationPoint;//指示点控件
    private LinearLayout pointLayout;

    private ListView lvBook;
    private List<Map<String,Object>> dataList1;
    private ArrayList<Product> book = new ArrayList<Product>();
    private ArrayList<ProductType> mProductTypes;
    private ProductTypeAdapter mProductTypeAdapter;
    private SimpleAdapter bookAdapter;
    private int[] books={
            R.mipmap.ic_book1,
            R.mipmap.ic_book2,
            R.mipmap.ic_book3,
            R.mipmap.ic_book4,
            R.mipmap.ic_book1,
            R.mipmap.ic_book2,
            R.mipmap.ic_book3,
            R.mipmap.ic_book4,
            R.mipmap.ic_book1,
            R.mipmap.ic_book2,
            R.mipmap.ic_book3,
            R.mipmap.ic_book4};

    private String[] bookname={
            "Python编程 从入门到实践",
            "TensorFlow实战",
            "大数据时代:生活、工作与思维的大变革",
            "Google:未来之镜",
            "Python编程 从入门到实践",
            "TensorFlow实战",
            "大数据时代:生活、工作与思维的大变革",
            "Google:未来之镜",
            "Python编程 从入门到实践",
            "TensorFlow实战",
            "大数据时代:生活、工作与思维的大变革",
            "Google:未来之镜"};
    private String[] bookprice={
            "70.30",
            "67.20",
            "35.70",
            "35.30",
            "70.30",
            "67.20",
            "35.70",
            "35.30",
            "70.30",
            "67.20",
            "35.70",
            "35.30"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GridView gridView = (GridView) getActivity().findViewById(R.id.gridview);
        dataList = new ArrayList<Map<String,Object>>();
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), getData(), R.layout.item_main_category, new String[]{"image","name"}, new int[]{R.id.itemImage,R.id.itemText});
        gridView.setAdapter(adapter);

        lvBook = (ListView) getActivity().findViewById(R.id.frag_main_list_view);
        dataList1 = new ArrayList<Map<String,Object>>();
        bookAdapter = new SimpleAdapter(getActivity(),getBookData(), R.layout.item_book, new String[]{"image","name","price"}, new int[]{R.id.book_image,R.id.book_title,R.id.book_price});
        lvBook.setAdapter(bookAdapter);
        lvBook.setOnItemClickListener(this);
    }

    private List<? extends Map<String, ?>> getData() {
        for (int i = 0; i < imageRes.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", imageRes[i]);
            map.put("name", strings[i]);
            dataList.add(map);
        }
        return dataList;
    }

    private List<? extends Map<String, ?>> getBookData() {
        for (int i = 0; i < books.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", books[i]);
            map.put("name", bookname[i]);
            map.put("price", bookprice[i]);
            dataList1.add(map);
        }
        return dataList1;

    }

    private void initView(View view){
        mCarouselView = (CarouselViewPager) view.findViewById(R.id.mCarouselView);
        pointLayout = (LinearLayout) view.findViewById(R.id.pointLayout);
    }

    private void initData(){
        for (int i = 0; i < ivIds.length; i++) {
            ImageView iv = new ImageView(getActivity());
            iv.setImageResource(ivIds[i]);
            ivList.add(iv);
        }

        indicationPoint = new ImageView[ivList.size()];
        for (int i = 0; i < indicationPoint.length; i++) {
            ImageView point = new ImageView(getActivity());
            LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(10, 10);
            layout.setMargins(12, 0, 12, 0);
            point.setLayoutParams(layout);

            indicationPoint[i] = point;
            if (i == 0) {
                indicationPoint[i].setBackgroundResource(R.mipmap.page_indicator_focused);
            } else {
                indicationPoint[i].setBackgroundResource(R.mipmap.page_indicator_unfocused);
            }
            pointLayout.addView(point);
        }

        mCarouselView.setAdapter(new CarouselPagerAdapter(ivList));
        mCarouselView.addOnPageChangeListener(this);
        mCarouselView.setDisplayTime(3000);
        mCarouselView.start();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setPointColor(position % ivList.size());

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setPointColor(int selectItem) {
        for (int i = 0; i < indicationPoint.length; i++) {
            if (i == selectItem) {
                indicationPoint[i].setBackgroundResource(R.mipmap.page_indicator_focused);
            } else {
                indicationPoint[i].setBackgroundResource(R.mipmap.page_indicator_unfocused);
            }

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
//        position = position - 1;
//        intent.putExtra(ProductDetailActivity.PRODID, book.get(position).getId());
        startActivity(intent);
    }
}
