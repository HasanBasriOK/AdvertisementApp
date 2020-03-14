package com.example.reklamapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SecimActivity extends AppCompatActivity {


    private DatabaseReference databaseOrders;
    List<Reklam> reklamlar=new ArrayList<Reklam>();
    List<String> magazalar=new ArrayList<String>();
    List<String> kategoriler=new ArrayList<String>();
    Spinner magazaSpinner;
    Spinner kategoriSpinner;

    private ArrayAdapter<String> dataAdapterKategori;
    private ArrayAdapter<String> dataAdapterMagaza;

    @Override
    protected void onStart() {
        super.onStart();
        AddKategori("Seçiniz");
        AddMagaza("Seçiniz");
        try{

            databaseOrders.child("Reklam").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    reklamlar.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        try{
                            Reklam reklam = postSnapshot.getValue(Reklam.class);
                            reklamlar.add(reklam);
                            AddMagaza(reklam.getFirmaAdi());
                            AddKategori(reklam.getKategori());
                        }
                        catch (Exception ex)
                        {
                            String message=ex.getMessage();
                        }
                    }
                    Bind();
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

    public void Bind()
    {
        dataAdapterKategori=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,kategoriler);
        dataAdapterMagaza=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,magazalar);
        magazaSpinner.setAdapter(dataAdapterMagaza);
        kategoriSpinner.setAdapter(dataAdapterKategori);
    }


    public void AddMagaza(String magaza)
    {
        boolean varMi=false;
        for(int i=0;i<magazalar.size();i++)
        {
            if(magazalar.get(i).equals(magaza))
            {
                varMi=true;
            }
        }

        if(!varMi)
        {
            magazalar.add(magaza);
        }
    }

    public void AddKategori(String kategori)
    {
        boolean varMi=false;
        for(int i=0;i<kategoriler.size();i++)
        {
            if(kategoriler.get(i).equals(kategori))
            {
                varMi=true;
            }
        }

        if(!varMi)
        {
            kategoriler.add(kategori);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secim);
        try{
            databaseOrders = FirebaseDatabase.getInstance().getReference();
        }
        catch(Exception ex)
        {
            String message=ex.getMessage();
        }

        magazaSpinner=(Spinner)findViewById(R.id.spnMagaza);
        kategoriSpinner=(Spinner)findViewById(R.id.spnKategori);
        Button btnListele=(Button)findViewById(R.id.btnSecimYapViewListele);
        final EditText txtUzaklik=(EditText)findViewById(R.id.txtSecimUzaklik);

        btnListele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String magaza= magazaSpinner.getSelectedItem().toString();
                String kategori=kategoriSpinner.getSelectedItem().toString();

                if(magaza=="Seçiniz")
                {
                    magaza="";
                }

                if(kategori=="Seçiniz")
                {
                    kategori="";
                }

                boolean magazaKullanilacakMi=false;
                boolean kategoriKullanilacakMi=false;
                boolean uzaklikKullanilacakMi=false;

                Intent intent=new Intent(SecimActivity.this,ListViewActivity.class);


                if (magaza!=null && !magaza.isEmpty())
                {
                    magazaKullanilacakMi=true;
                }

                if(kategori!=null && !magaza.isEmpty())
                {
                    kategoriKullanilacakMi=true;
                }

                if(txtUzaklik.getText()!=null && !txtUzaklik.getText().toString().isEmpty())
                {
                    uzaklikKullanilacakMi=true;
                    intent.putExtra("Uzaklik", Integer.valueOf(txtUzaklik.getText().toString()));
                }


                //sadece magaza kullanılacak
                if(magazaKullanilacakMi && !kategoriKullanilacakMi)
                {
                    intent.putExtra("Magaza",magaza);
                    intent.putExtra("Kategori","");
                }
                //hem magaza hem kategori kullanılacak
                else if(magazaKullanilacakMi && kategoriKullanilacakMi)
                {
                    intent.putExtra("Magaza",magaza);
                    intent.putExtra("Kategori",kategori);
                }
                //sadece kategori kullanılacak
                else if(kategoriKullanilacakMi & !magazaKullanilacakMi)
                {
                    intent.putExtra("Magaza","");
                    intent.putExtra("Kategori",kategori);
                }
                else
                {
                    Toast.makeText(SecimActivity.this,"Kategori veya mağaza seçimi yapılmalı!",Toast.LENGTH_SHORT).show();
                    return;
                }

                startActivity(intent);
            }
        });




    }
}
