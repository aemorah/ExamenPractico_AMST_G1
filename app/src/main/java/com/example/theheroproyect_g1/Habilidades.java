package com.example.theheroproyect_g1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Habilidades extends AppCompatActivity {

    private RequestQueue mQueue = null;
    final private String token = "10221822331728368";
    BarChart barchart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habilidades);

        TextView nombreHeroes =  (TextView) findViewById(R.id.nombreHeroe);
        TextView nombreCompleto =  (TextView) findViewById(R.id.nombreCompleto);
        ImageView imv_photo = (ImageView)  findViewById(R.id.imagenHeroe);
        barchart = findViewById(R.id.idBarChart);

        Intent intent= getIntent();
        String idIngresado = intent.getStringExtra("id");

        mQueue = Volley.newRequestQueue(this);

        String solicitud2 = "https://www.superheroapi.com/api.php/"+token+"/"+idIngresado;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, solicitud2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String nombreSH = response.getString("name");
                    String nombreCH = response.getJSONObject("biography").getString("full-name");
                    String urlImagen = response.getJSONObject("image").getString("url");
                    String intelligencia = response.getJSONObject("powerstats").getString("intelligence");
                    String fuerza = response.getJSONObject("powerstats").getString("strength");
                    String velocidad = response.getJSONObject("powerstats").getString("speed");
                    String durabilidad = response.getJSONObject("powerstats").getString("durability");
                    String poder = response.getJSONObject("powerstats").getString("power");
                    String combate = response.getJSONObject("powerstats").getString("combat");

                    nombreHeroes.setText(nombreSH);
                    nombreCompleto.setText(nombreCH);
                    Picasso.with(getApplicationContext()).load(urlImagen).fit().into(imv_photo);

                    ArrayList<BarEntry> dataVals = new ArrayList<>();
                    dataVals.add(new BarEntry(0,Integer.parseInt(intelligencia)));
                    dataVals.add(new BarEntry(1,Integer.parseInt(fuerza)));
                    dataVals.add(new BarEntry(2,Integer.parseInt(velocidad)));
                    dataVals.add(new BarEntry(3,Integer.parseInt(durabilidad)));
                    dataVals.add(new BarEntry(4,Integer.parseInt(poder)));
                    dataVals.add(new BarEntry(5,Integer.parseInt(combate)));

                    BarDataSet barDataSet1 = new BarDataSet(dataVals,"DataSet 1");
                    BarData barData = new BarData();
                    barData.addDataSet(barDataSet1);

                    barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);
                    barDataSet1.setValueTextColor(Color.BLACK);
                    barDataSet1.setValueTextSize(16f);
                    String[] xAxisLables = new String[]{"Inteligencia","Fuerza","Velocidad","Durabilidad","Poder","Combate"};

                    barchart.setData(barData);
                    barchart.invalidate();
                    barchart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLables));
                    barchart.getXAxis().setLabelRotationAngle(45);
                    barchart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                    barchart.getXAxis().setTextSize(16f);
                    barchart.setExtraBottomOffset(50);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });
        mQueue.add(request);

    }
}