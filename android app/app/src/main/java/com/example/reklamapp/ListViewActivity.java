package com.example.reklamapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {

    private DatabaseReference databaseOrders;
    String kategori;
    String magaza;
    int uzaklikHedef;
    public List<Reklam> reklamlar=new ArrayList<Reklam>();
    boolean magazaKullanilacakMi=false;
    boolean kategoriKullanilacakMi=false;
    boolean uzaklikKullanilacakMi=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        try{
            databaseOrders = FirebaseDatabase.getInstance().getReference();
        }
        catch(Exception ex)
        {
            String message=ex.getMessage();
        }
        Intent intent=getIntent();
        kategori=intent.getStringExtra("Kategori");
        magaza=intent.getStringExtra("Magaza");
        uzaklikHedef=intent.getIntExtra("Uzaklik",0);

        if(kategori!=null && !kategori.isEmpty())
        {
            kategoriKullanilacakMi=true;
        }

        if(magaza!=null && !magaza.isEmpty())
        {
            magazaKullanilacakMi=true;
        }

        if(uzaklikHedef!=0)
        {
            uzaklikKullanilacakMi=true;
        }

    }

    public void openListView()
    {
        try{
            final ListView listView=(ListView)findViewById(R.id.listView1);
            final CustomAdapter adapter=new CustomAdapter(reklamlar,ListViewActivity.this);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Reklam reklam=(Reklam) adapter.getItem(position);

                    Intent intent =new Intent(ListViewActivity.this,ReklamDetayActivity.class);
                    intent.putExtra("FirmaAdi",reklam.getFirmaAdi());
                    intent.putExtra("KampanyaIcerik",reklam.getKampanyaIcerik());
                    intent.putExtra("KampanyaSuresi",reklam.getKampanyaSuresi());
                    intent.putExtra("LokasyonEnlem",reklam.getFirmaEnlem());
                    intent.putExtra("LokasyonBoylam",reklam.getFirmaBoylam());
                    intent.putExtra("Kategori",reklam.getKategori());

                    startActivity(intent);
                }
            });
        }
        catch (Exception ex)
        {
            String message=ex.getMessage();
        }
    }

    public boolean uzaklikUygunMu(double enlem1,double boylam1)
    {
        if(!uzaklikKullanilacakMi)
        {
            return  true;
        }


        boolean result=false;

        double uzaklik= Utilities.UzaklikHesapla(enlem1,boylam1,GlobalSettings.enlem,GlobalSettings.boylam);

        if(uzaklikHedef>uzaklik)
        {
            result=true;
        }

        return  result;
    }

    @Override
    protected void onStart() {
        super.onStart();
        try{

            databaseOrders.child("Reklam").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    reklamlar.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Reklam reklam = postSnapshot.getValue(Reklam.class);


                        //sadece magaza kullanılacak
                        if(magazaKullanilacakMi && !kategoriKullanilacakMi)
                        {
                            if(reklam.getFirmaAdi().equals(magaza) && uzaklikUygunMu(reklam.getFirmaEnlem(),reklam.getFirmaBoylam()))
                            {
                                reklamlar.add(reklam);
                            }
                        }
                        //hem magaza hem kategori kullanılacak
                        else if(magazaKullanilacakMi && kategoriKullanilacakMi)
                        {
                            if(reklam.getFirmaAdi().equals(magaza) && reklam.getKategori().equals(kategori) && uzaklikUygunMu(reklam.getFirmaEnlem(),reklam.getFirmaBoylam()))
                            {
                                reklamlar.add(reklam);
                            }
                        }
                        //sadece kategori kullanılacak
                        else if(kategoriKullanilacakMi & !magazaKullanilacakMi)
                        {
                            if(reklam.getKategori().equals(kategori) && uzaklikUygunMu(reklam.getFirmaEnlem(),reklam.getFirmaBoylam()))
                            {
                                reklamlar.add(reklam);
                            }
                        }
                    }
                    openListView();

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        }
        catch(Exception ex){
            String message=ex.getMessage();
        }
    }
}
