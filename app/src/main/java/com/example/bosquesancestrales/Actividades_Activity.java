package com.example.bosquesancestrales;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Actividades_Activity extends AppCompatActivity {

    //Variable para la luna
    ImageView imgLunaHoy, btnAtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividades);

        //Inicializamos las variables
        imgLunaHoy = findViewById(R.id.imgLunaHoy);
        btnAtras = findViewById(R.id.btnAtras);

        //Obtenemos la fase lunar de hoy
        Calendar today = Calendar.getInstance();
        int phase = getLunarPhase(today, null, "HOY"); //Obtenemos la fase del objeto donde lo calculamos
        String phaseText =getPhaseText(phase, imgLunaHoy); //Obetemos la imagen de la luna

        TextView txtFaseUsuario = findViewById(R.id.txtFaseUsuario); //Inicializamos la variable donde se mostrata el texto
        txtFaseUsuario.setText(phaseText); // Enviamos el nombre de la fase

        //Obtener la fase lunar que busca el usuario en el calendario
        ImageView BtnBuscarFase = findViewById(R.id.btnBuscarFecha);
        TextView TxtFechaUsuario = findViewById(R.id.TxtFechaUsuario);

        TxtFechaUsuario.setText(ObtenerFechaFormato(new Date()));

        //Programamos el botón para que nos busque una fecha en calendario
        BtnBuscarFase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(TxtFechaUsuario, txtFaseUsuario);
            }
        });

        //Programamos el boton para que nos regrese a la pantalla principal
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //Formato para obtener fecha en formato
    public String ObtenerFechaFormato (Date date)
    {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
        return formatoFecha.format(date);
    }

    private void showDatePickerDialog (TextView TxtFechaUsuario, TextView txtFaseUsuario)
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                Actividades_Activity.this,
                (view, year1, month1, dayOfMonth)->{
                    Calendar calendarioFecha = Calendar.getInstance();
                    calendarioFecha.set(year1, month1, dayOfMonth); //Estamos enviando la fecha
                    Date date = calendarioFecha.getTime(); //Obtenemos la fecha
                    TxtFechaUsuario.setText(ObtenerFechaFormato(date)); //Enviamos la fecha al usuario final en formato

                    // Calcular la fase lunar para la fecha seleccionada
                    int faseUsuario = getLunarPhase(null, ObtenerFechaFormato(date), "FECHA_USUARIO");
                    String faseUsuarioText = getPhaseText(faseUsuario, imgLunaHoy); // cambiar la misma imagen
//                    String faseUsuarioText = getPhaseText(faseUsuario, imgLunaUsuario); imagen separada
                    txtFaseUsuario.setText(faseUsuarioText);
                },
                year, month, day );

        datePickerDialog.show();
    }

    public static int getLunarPhase (Calendar date, String FechaUsuario, String TIPO){
        // TIPO HOY - TIPO USUARIO (fecha ingresada por el usuario)

        int year, month, day;

        if (TIPO.equals("HOY")){
            year = date.get(Calendar.YEAR);
            month = date.get(Calendar.MONTH) + 1;
            day = date.get(Calendar.DAY_OF_MONTH);
        }else {
            String[] ARRAY_FECHA = FechaUsuario.split("-"); // 2024-04-01 [2024, 04,01]
            year = Integer.parseInt(ARRAY_FECHA[2]);
            month = Integer.parseInt(ARRAY_FECHA[1]);
            day = Integer.parseInt(ARRAY_FECHA[0]);
        }

        // Algoritmo para calcular la fase lunar
        double c, e, jd;
        if (month < 3) {
            year--;
            month += 12;
        }
        month++;
        c = 365.25 * year;
        e = 30.6 * month;
        jd = c + e + day - 694039.09; // jd es el total de días transcurridos
        jd /= 29.5305882; // dividir por el ciclo lunar
        int b = (int) jd; // tomar la parte entera de jd
        jd -= b; // restar la parte entera para dejar la parte fraccionaria de jd original
        int phase = (int) Math.round(jd * 8); // escalar la fracción de 0 a 8 y redondear

        // Ajustar la fase en el rango 0-7
        return phase >= 8 ? 0 : phase;
    }

    public static String getPhaseText(int phase, ImageView IMG) {
        String phaseText = "";
        switch(phase) {
            case 0:
                phaseText = "luna Nueva";
                IMG.setImageResource(R.drawable.fase1);
                break;
            case 1:
                phaseText = "Creciente crecida";
                IMG.setImageResource(R.drawable.fase2);
                break;
            case 2:
                phaseText = "Cuarto creciente";
                IMG.setImageResource(R.drawable.fase3);
                break;
            case 3:
                phaseText = "Creciente Menguada";
                IMG.setImageResource(R.drawable.fase4);
                break;
            case 4:
                phaseText = "Luna llena";
                IMG.setImageResource(R.drawable.fase5);
                break;
            case 5:
                phaseText = "Menguate crecida";
                IMG.setImageResource(R.drawable.fase6);
                break;
            case 6:
                phaseText = "Cuarto menguante";
                IMG.setImageResource(R.drawable.fase7);
                break;
            case 7:
                phaseText = "Menguante menguada";
                IMG.setImageResource(R.drawable.fase8);
                break;
        }
        return phaseText;
    }
}