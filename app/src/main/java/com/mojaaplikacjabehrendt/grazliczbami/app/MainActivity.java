package com.mojaaplikacjabehrendt.grazliczbami.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity<Powtorz> extends AppCompatActivity {
                                                                //stworzenie zmiennych//
    int wylosowanaLiczba;
    int wpisanaLiczba;
    int iloscProb = 0;
    EditText poleZeWpisanaLiczba;
    TextView komunikat1;
    TextView komunikat2;
    SharedPreferences mojePreferencje;
    SharedPreferences.Editor edytor;
    String komunikatDymkowy = "Wpisz LICZBÄ!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        komunikat1 = (TextView)findViewById(R.id.info1);
        komunikat2 = (TextView)findViewById(R.id.info2);
        poleZeWpisanaLiczba = (EditText)findViewById(R.id.editText);

        mojePreferencje = getSharedPreferences("ustawienia", Context.MODE_PRIVATE);
        edytor = mojePreferencje.edit();
        komunikat2.setText("Najlepszy wynik: " + mojePreferencje.getInt("najlepszyWynik", 100));

        NowaGra(null);
//restart gry//
        poleZeWpisanaLiczba.setText("");
        poleZeWpisanaLiczba.setTextColor(Color.parseColor("#c0c0c0"));
        poleZeWpisanaLiczba.setTypeface(null, Typeface.NORMAL);
        poleZeWpisanaLiczba.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v) {
                finish(); iloscProb=0;
                startActivity(getIntent());
            }
        });
    }

    //NOWA GRA
    public void NowaGra(View view) {
        Random generowanie = new Random();
        wylosowanaLiczba = generowanie.nextInt((100-0)+1)+0;
        iloscProb = 0;
        komunikat1.setText("Number of attempts: " + iloscProb);
        wyczyscPole(null);
    }

                //komunikaty//
    public void wyswietlDymek() {

        Context kontekst = getApplicationContext();
        int czasWyswietlaniaDymku = Toast.LENGTH_LONG;
        Toast dymek = Toast.makeText(kontekst, komunikatDymkowy, czasWyswietlaniaDymku);
        dymek.show();
    }
                // zgadywanie liczb//
    public void wyczyscPole(View view) {
        EditText poleDoWyczyszcenia = (EditText) findViewById(R.id.editText);
        poleDoWyczyszcenia.setTextColor(Color.parseColor("#ff0000"));
        poleDoWyczyszcenia.setTypeface(null, Typeface.BOLD);
        poleDoWyczyszcenia.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
        poleDoWyczyszcenia.setText(null);
    }
// nakierowanie na cel(liczbe konocowa//
    public void onClick1(View view) {
        iloscProb = iloscProb+1; // lub "liczbaProb++"
        if (!TextUtils.isEmpty(poleZeWpisanaLiczba.getText()) && (!TextUtils.equals(poleZeWpisanaLiczba.getText(), ""))) {
            wpisanaLiczba = Integer.parseInt(poleZeWpisanaLiczba.getText().toString());


            if (wpisanaLiczba > wylosowanaLiczba) {
                komunikatDymkowy = "My number is less!";
            } else if (wpisanaLiczba < wylosowanaLiczba) {
                komunikatDymkowy = "My number is bigger!";
            } else if (wpisanaLiczba == wylosowanaLiczba) {
                komunikatDymkowy = "Congratulation!";

                if (iloscProb < mojePreferencje.getInt("najlepszyWynik", 100)) {
                    edytor.putInt("najlepszyWynik", iloscProb);
                    edytor.commit();
                    komunikat2.setText("Najlepszy wynik: " + mojePreferencje.getInt("najlepszyWynik", 100));
                }
            }
        }
        komunikat1.setText("Number of attempts: " + iloscProb);
        Context kontekst = getApplicationContext();
        int czasWyswietlaniaDymku = Toast.LENGTH_LONG;
        Toast dymek = Toast.makeText(kontekst, komunikatDymkowy, czasWyswietlaniaDymku);
        dymek.show();
        }

}

