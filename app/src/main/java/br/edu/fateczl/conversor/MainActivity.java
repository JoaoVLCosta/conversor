package br.edu.fateczl.conversor;

import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etEntrada;
    private TextView tvSaida;
    private Button btConv;
    private Spinner spEnt;
    private Spinner spSai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etEntrada = findViewById(R.id.etEntrada);

        tvSaida = findViewById(R.id.tvSaida);
        tvSaida.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);

        spEnt = findViewById(R.id.spEnt);
        spSai = findViewById(R.id.spSai);

        btConv = findViewById(R.id.btConv);
        btConv.setOnClickListener(op -> exibirResultado());

        preencherSpinner(spEnt);
        preencherSpinner(spSai);
    }

    private double obterFator(int inicio, int fim, int expoente){
        if (inicio == 0 || fim == 0){
            return Math.pow(1024, expoente - 1) * 8;
        }
        return Math.pow(1024, expoente);
    }

    private long converter(int inicio, int fim, long valor){
        if (inicio == fim || valor == 0){
            return valor;
        }

        if(inicio > fim){
            return (long) (valor * obterFator(inicio, fim, inicio - fim));
        }
        return (long) (valor / obterFator(inicio, fim, fim - inicio));
    }

    private void exibirResultado() {
        int inicio = spEnt.getSelectedItemPosition();
        int fim = spSai.getSelectedItemPosition();
        long valor = Long.parseLong(etEntrada.getText().toString());

        tvSaida.setText(String.format("%d", converter(inicio, fim, valor)));
    }

    private void preencherSpinner(Spinner genericSpinner){
        List<String> lista = new ArrayList<>();

        lista.add("BIT");
        lista.add("BYTES");
        lista.add("KILOBYTES");
        lista.add("MEGABYTES");
        lista.add("GIGABYTES");
        lista.add("TERABYTES");

        definirAdaptador(genericSpinner, lista);
    }

    private void definirAdaptador(Spinner genericSpinner, List<String> lista){
        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_spinner_item, lista);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genericSpinner.setAdapter(adaptador);
    }

}