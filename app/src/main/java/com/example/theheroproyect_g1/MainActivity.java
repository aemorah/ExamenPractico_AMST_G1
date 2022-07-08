package com.example.theheroproyect_g1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RequestQueue mQueue = null;
    final private String token = "10221822331728368";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQueue = Volley.newRequestQueue(this);
    }



    public void irResultados(View v){
        EditText ingresoBusqueda = (EditText) findViewById(R.id.ingreso);
        String str_ingreso = ingresoBusqueda.getText().toString().trim();
        buscarHeroes(str_ingreso);
    }

    public void buscarHeroes(String informacion){
        String solicitud = "https://www.superheroapi.com/api.php/"+token+"/search/"+informacion;
        TextView prueba = (TextView) findViewById(R.id.textView5);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, solicitud, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray resultado = response.getJSONArray("results");
                    Intent i = new Intent(MainActivity.this, Resultados.class);
                    i.putExtra("Ingresado", informacion.toString());
                    i.putExtra("Valor",resultado.toString());
                    startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                prueba.setText(error.toString());
                System.out.println(solicitud);
            }
        });
        mQueue.add(request);

    }
}