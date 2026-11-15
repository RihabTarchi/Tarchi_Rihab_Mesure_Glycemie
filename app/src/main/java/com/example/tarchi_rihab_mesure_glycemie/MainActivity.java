package com.example.tarchi_rihab_mesure_glycemie;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnConsulter;
    private TextView msg;
    private SeekBar sbage;
    private RadioButton rbtoui;
    private RadioButton rbtnon;
    private EditText etValeur;
    private TextView textAge;

    private int age = 0;

    private void init() {
        btnConsulter = findViewById(R.id.btnConsulter);
        sbage        = findViewById(R.id.sbage);
        rbtoui       = findViewById(R.id.rbtoui);
        rbtnon       = findViewById(R.id.rbtnon);
        etValeur     = findViewById(R.id.etValeur);
        msg          = findViewById(R.id.msg);
        textAge      = findViewById(R.id.textAge);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        sbage.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Information", "onProgressChanged: " + progress);
                age = progress;
                textAge.setText("Votre âge : " + progress + " ans");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        btnConsulter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consulterGlycemie();
            }
        });
    }

    private void consulterGlycemie() {
        String valeurString = etValeur.getText().toString().trim();
        if (valeurString.isEmpty()) {
            Toast.makeText(MainActivity.this,
                    "Veuillez entrer la valeur mesurée",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        double valeur = Double.parseDouble(valeurString.replace(',', '.'));
        boolean aJeun = rbtoui.isChecked();
        String resultat;

        if (aJeun) {
            double min, max;
            if (age > 12) {
                min = 5.0;
                max = 7.2;
            } else if (age >= 6) {
                min = 5.0;
                max = 10.0;
            } else {
                min = 5.5;
                max = 10.0;
            }

            if (valeur < min) {
                resultat = "niveau de glycémie est trop bas";
            } else if (valeur > max) {
                resultat = "niveau de glycémie est trop élevée";
            } else {
                resultat = "niveau de glycémie est normale";
            }
        } else {

            if (valeur < 10.5) {
                resultat = "niveau de glycémie est normale";
            } else {
                resultat = "niveau de glycémie est trop élevée";
            }
        }

        msg.setText(resultat);
    }
}