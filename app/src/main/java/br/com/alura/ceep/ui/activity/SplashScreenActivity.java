package br.com.alura.ceep.ui.activity;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


import br.com.alura.ceep.R;
import br.com.alura.ceep.database.CeepSharedPreferences;

import static br.com.alura.ceep.constantes.Constantes.PRIMEIRO_ACESSO;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        verificarPrimeiroAcesso();
    }

    private void verificarPrimeiroAcesso(){
        Boolean primeiroAcesso = CeepSharedPreferences.getPrimeiroAcesso(getApplicationContext());
        if (!primeiroAcesso.equals(PRIMEIRO_ACESSO)) delayedSplashScreen(2000);
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

    private void registarPrimeiroAcesso(){
        CeepSharedPreferences.setPrimeiroAcesso(getApplicationContext(), PRIMEIRO_ACESSO);
    }

    private void mostrarNotas() {
        Intent intent = new Intent(SplashScreenActivity.this, ListaNotasActivity.class);
        startActivity(intent);
        registarPrimeiroAcesso();
        finish();
    }
}

