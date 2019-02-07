package com.example.sportak.torpedodrop.Adaptadores;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdaptador extends FragmentPagerAdapter {

    private ArrayList<Fragment>fragments;
    private ArrayList<String>titulos;

    public ViewPagerAdaptador(FragmentManager fm){
        super(fm);
        this.fragments=new ArrayList<>();
        this.titulos=new ArrayList<>();
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }



    public void addFragment(Fragment frag,String title){
        fragments.add(frag);
        titulos.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titulos.get(position);
    }
}
