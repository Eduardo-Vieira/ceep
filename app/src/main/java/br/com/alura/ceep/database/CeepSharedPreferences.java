package br.com.alura.ceep.database;

import android.content.Context;
import android.content.SharedPreferences;

import static br.com.alura.ceep.constantes.Constantes.CEEP_SHARED_PREFERENCES;
import static br.com.alura.ceep.constantes.Constantes.CEEP_SHARED_PREF_DEFVALUE;
import static br.com.alura.ceep.constantes.Constantes.PRIMEIRO_ACESSO_DEFVALUE;
import static br.com.alura.ceep.constantes.Constantes.PRIMEIRO_ACESSO_KEY;
import static br.com.alura.ceep.constantes.Constantes.TYPE_RECYCLE_LAYOUT_VIEW_KEY;

public abstract class CeepSharedPreferences {

    public static String setTypeRecyclerVLayoutView(Context context, String prefValue){
        SharedPreferences ceepSharedPreferences = context.getSharedPreferences(CEEP_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = ceepSharedPreferences.edit();
        editor.putString(TYPE_RECYCLE_LAYOUT_VIEW_KEY, prefValue);
        editor.apply();
        return ceepSharedPreferences.getString(TYPE_RECYCLE_LAYOUT_VIEW_KEY, CEEP_SHARED_PREF_DEFVALUE);
    }

    public static String getTypeRecyclerVLayoutView(Context context){
        SharedPreferences ceepSharedPreferences = context.getSharedPreferences(CEEP_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return ceepSharedPreferences.getString(TYPE_RECYCLE_LAYOUT_VIEW_KEY, CEEP_SHARED_PREF_DEFVALUE);
    }

    public static Boolean setPrimeiroAcesso(Context context, Boolean prefValue){
        SharedPreferences ceepSharedPreferences = context.getSharedPreferences(CEEP_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = ceepSharedPreferences.edit();
        editor.putString(PRIMEIRO_ACESSO_KEY, String.valueOf(prefValue));
        editor.apply();
        return Boolean.parseBoolean(ceepSharedPreferences.getString(PRIMEIRO_ACESSO_KEY, String.valueOf(PRIMEIRO_ACESSO_DEFVALUE)));
    }

    public static Boolean getPrimeiroAcesso(Context context){
        SharedPreferences ceepSharedPreferences = context.getSharedPreferences(CEEP_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return Boolean.parseBoolean(ceepSharedPreferences.getString(PRIMEIRO_ACESSO_KEY, String.valueOf(PRIMEIRO_ACESSO_DEFVALUE)));
    }
}
