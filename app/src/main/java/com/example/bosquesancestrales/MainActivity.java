package com.example.bosquesancestrales;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.preference.PreferenceActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.lang.annotation.Annotation;


public class MainActivity extends AppCompatActivity {

    //Duración del splashScreen en milisegundos
    private static final int DuracionSplash = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inicializamos el splashScreen desde el XLM
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //Despues de ejecutar DuracionSplash se vuelve al MainActivity
                setContentView(R.layout.activity_main);

                //Iniciamos los elementos de la UI del MainActivity aca
                initializeMainUI();

            }
        }, DuracionSplash); //Llamamos la inicialización del splashScrenn
    }

    //Creamos un objetivo para el MainActivity
    private void initializeMainUI(){

        //Variables para los botones
        CardView btnConceptos, btnActividades;
        ImageView InfChahim, InfoBetzy;

        //Inicializamos las variables
        btnConceptos = findViewById(R.id.ButtonConceptos);
        btnActividades = findViewById(R.id.ButtonActividades);

        //Programamos los botones para que vayan a otras actividades
        btnConceptos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Conceptos_Activity.class));
            }
        });

        btnActividades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Actividades_Activity.class));
            }
        });

    }
}