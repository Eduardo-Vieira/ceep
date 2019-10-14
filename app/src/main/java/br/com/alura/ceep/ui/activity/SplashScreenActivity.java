package br.com.alura.ceep.ui.activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


import br.com.alura.ceep.R;
public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        String primeiroAcesso = ceepSharedPreferences("");
        if (!primeiroAcesso.equals("true")) delayedSplashScreen(2000);
        else delayedSplashScreen(500);
    }

    private  void delayedSplashScreen(int delayMillis){
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarNotas();
            }
        }, delayMillis);
    }

    private void mostrarNotas() {
        Intent intent = new Intent(SplashScreenActivity.this, ListaNotasActivity.class);
        startActivity(intent);
        String primeiroAcesso = ceepSharedPreferences("true");
        finish();
    }
    private String ceepSharedPreferences(String prefValue) {
        SharedPreferences ceepSharedPreferences = getSharedPreferences(getString(R.string.pref_key), Context.MODE_PRIVATE);
        if(!prefValue.equals("")){
            SharedPreferences.Editor editor = ceepSharedPreferences.edit();
            editor.putString(getString(R.string.pref_text_primeiro_acesso),prefValue);
            editor.apply();
        }
        return ceepSharedPreferences.getString(getString(R.string.pref_text_primeiro_acesso),"false");
    }
}

