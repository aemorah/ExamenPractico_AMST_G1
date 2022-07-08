package com.example.theheroproyect_g1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Resultados extends AppCompatActivity {
    private String busqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        TextView titulo =  (TextView) findViewById(R.id.resultadosBusqueda);
        TextView contador =  (TextView) findViewById(R.id.contadosEncontrados);
        ScrollView sv = (ScrollView) findViewById((R.id.scrollview)) ;
        LinearLayout ll = (LinearLayout) findViewById(R.id.linearLayout);

        Intent intent= getIntent();
        String ingresado = intent.getStringExtra("Ingresado");
        titulo.setText("Busqueda: "+ingresado);
        String jsonArray = intent.getStringExtra("Valor");
        int contadorExitoso = 0;
        try {
            JSONArray array = new JSONArray(jsonArray);
            for (int i = 0; i<array.length();i++){
                contadorExitoso++;
                JSONObject jsonObject1 = array.getJSONObject(i);
                String nombre = jsonObject1.optString("name");
                String id = jsonObject1.optString("id");
                TextView tv = new TextView(this);
                tv.setPaddingRelative(150,10,0,10);
                tv.setTextSize(24);
                tv.setText(nombre);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(Resultados.this, Habilidades.class);
                        i.putExtra("id",id.toString());
                        startActivity(i);
                    }
                });
                ll.addView(tv);

                //System.out.println(nombre);
            }
            //System.out.println(array.toString(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        contador.setText("Resultado: "+String.valueOf(contadorExitoso));
    }
}