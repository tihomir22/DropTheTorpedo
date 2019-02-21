package com.example.sportak.torpedodrop;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import com.example.sportak.torpedodrop.LocaleHelper.LocaleHelper;

public class ResourcesLocale {


    public static Resources getResoruces(Context mcontext) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mcontext);
        String codigo_lenguaje = pref.getString("pais", "");
        Context context = null;
        if (codigo_lenguaje.equalsIgnoreCase("SP")) {
            context = LocaleHelper.setLocale(mcontext, "es");
        } else if(codigo_lenguaje.equalsIgnoreCase("UK")) {
            System.out.println("entro");
            context = LocaleHelper.setLocale(mcontext, "en");
        }else if(codigo_lenguaje.equalsIgnoreCase("BG")){
            System.out.println("entro bg");
            context = LocaleHelper.setLocale(mcontext, "bg");
        }else{
            context = LocaleHelper.setLocale(mcontext, "es");
        }

        Resources resources = context.getResources();
        return resources;
    }


}
