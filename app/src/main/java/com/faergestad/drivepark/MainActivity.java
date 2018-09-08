package com.faergestad.drivepark;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    private double forbruk;
    private String bilmerke, modelYear, skilt;
    private final String URL = "https://www.vegvesen.no/kjoretoy/Kjop+og+salg/Kj%C3%B8ret%C3%B8yopplysninger?registreringsnummer=";
    private TextView result;
    private EditText skiltInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);

        skiltInput = findViewById(R.id.editText);
        // TODO fikse formatering til (Char Char)" "(#####)
        skiltInput.addTextChangedListener(new PatternedTextWatcher("## #####"));

        Button getBtn = findViewById(R.id.getBtn);
        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // "Fjerner" tastaturet når brukeren trykker søk
                skiltInput.onEditorAction(EditorInfo.IME_ACTION_DONE);

                // TODO rydde opp i håndteringen og formatteringen av skiltnr
                skilt = skiltInput.getText().toString().toLowerCase().replace(' ', '+');

                if(sjekkInput(skilt)) {
                    finnBil(skilt);
                } else {
                    skiltInput.setError("Skriv skiltnr");
                }
            }
        });
    }

    // TODO mer robust sjekk
    private boolean sjekkInput(String s) {
        return !(s.equals(""));
    }

    private void finnBil(final String s) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                final StringBuilder builder = new StringBuilder();

                try {
                    Document doc = Jsoup.connect(URL + s).get();

                    Elements links = doc.select("dl[class=definisjonsliste]");

                    for (Element link : links) {
                            builder.append(link.text());
                    }
                } catch (IOException e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO bytte ut String[] med ArrayList for å bare legge til relevant informasjon, ikke alt fra resultatet
                        String[] strings = builder.toString().split(" ");

                        // TODO gjøre henting av data mer robust og klar for endringer av kildeoppsett hos vegvesenet
                        for(int i = 0; i < strings.length; i++) {
                            // Henter gjennomsnittlig forbruk
                            if(strings[i].equals("Drivstofforbruk")) { forbruk = Double.parseDouble(strings[i+1]); }
                            // Henter bilmerke
                            if(strings[i].equals("modell")) { bilmerke = strings[i+1]; }
                            // Henter bilmodell
                            if(strings[i].equals("Kjøretøygruppe")) { bilmerke = bilmerke + " " + strings[i-1]; }
                            // Henter årsmodell
                            if(strings[i].equals("Registreringsår")) { modelYear = strings[i+1]; }
                        }
                        // TODO legge inn svar for om man ikke finner skiltnr
                        String ut = bilmerke + " " + modelYear + "\nSnittforbruk: " + String.valueOf(forbruk);
                        result.setText(String.valueOf(ut));

                        Arrays.fill(strings, null);
                    }
                });
            }
        }).start();
    }

}
