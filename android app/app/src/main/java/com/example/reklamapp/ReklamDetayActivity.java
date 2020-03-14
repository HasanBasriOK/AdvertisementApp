package com.example.reklamapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ReklamDetayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reklam_detay);

        TextView txtKategori=(TextView)findViewById(R.id.txtReklamDetayKategori);
        TextView txtFirmaAd=(TextView)findViewById(R.id.txtReklamDetayMagazaAd);
        TextView txtEnlem=(TextView)findViewById(R.id.txtReklamDetayMagazaEnlem);
        TextView txtBoylam=(TextView)findViewById(R.id.txtReklamDetayMagazaBoylam);
        TextView txtSure=(TextView)findViewById(R.id.txtReklamDetayKampanyaSuresi);
        TextView txtIcerik=(TextView)findViewById(R.id.txtReklamDetayKampanyaIcerik);

        Intent intent=getIntent();

        String kategori=intent.getStringExtra("Kategori");
        String firmaAd=intent.getStringExtra("FirmaAdi");
        double enlem=intent.getDoubleExtra("LokasyonEnlem",0);
        double boylam=intent.getDoubleExtra("LokasyonBoylam",0);
        int sure=intent.getIntExtra("KampanyaSuresi",0);
        String icerik=intent.getStringExtra("KampanyaIcerik");


        txtBoylam.setText(Double.toString(boylam));
        txtEnlem.setText(Double.toString(enlem));
        txtSure.setText(Integer.toString(sure));
        txtIcerik.setText(icerik);
        txtFirmaAd.setText(firmaAd);
        txtKategori.setText(kategori);


    }
}
