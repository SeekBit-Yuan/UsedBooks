package com.seek.usedbooks.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seek.usedbooks.R;
import com.seek.usedbooks.activities.MainActivity;

/**
 * Created by 一丝不狗 on 2017/3/23.
 */

public class ShopFragment extends Fragment implements View.OnClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        view.findViewById(R.id.to_main_button).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.to_main_button:
                Intent toAccountIntent = new Intent(getActivity(), MainActivity.class);
                startActivity(toAccountIntent);
        }
    }
}
