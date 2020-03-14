package com.example.reklamapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class UyeOlActivity extends AppCompatActivity implements LocationListener {

    LocationManager locationManager;
    List<Kullanici> kullanicilar;
    private DatabaseReference databaseOrders;

    @Override
    protected void onStart() {
        super.onStart();
        databaseOrders.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                kullanicilar.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Kullanici order = postSnapshot.getValue(Kullanici.class);
                    kullanicilar.add(order);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);



        setContentView(R.layout.activity_uye_ol);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        final EditText txtAd=(EditText)findViewById(R.id.uyeOlAd);
        final EditText txtSoyad=(EditText)findViewById(R.id.uyeOlSoyad);
        final EditText txtEmail=(EditText)findViewById(R.id.uyeOlEmail);
        final EditText txtPassword=(EditText)findViewById(R.id.uyeOlPassword);


        Button btnGirisYap = (Button) findViewById(R.id.btnUyeOlKaydet);
        btnGirisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Kullanici kullanici=new Kullanici();
                kullanici.setAd(txtAd.getText().toString());
                kullanici.setSoyad(txtSoyad.getText().toString());
                kullanici.setEmail(txtEmail.getText().toString());
                kullanici.setPassword(txtPassword.getText().toString());

                JSONObject kullaniciJson=new JSONObject();
                try {
                    kullaniciJson.put("Ad",kullanici.getAd());
                    kullaniciJson.put("Soyad",kullanici.getSoyad());
                    kullaniciJson.put("Email",kullanici.getEmail());
                    kullaniciJson.put("Password",kullanici.getPassword());

                    PostUser(kullaniciJson.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 200, 1, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    private void PostUser(String jsonStr)
    {
        String urlString = "https://dene-fe577.firebaseio.com/Kullanici.json?auth=wXfzHDOIFOnZpzsrm6Dpb40Wx9fP2hpDT31BU9lY";
        String data = jsonStr;
        OutputStream out = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);

            DataOutputStream os = new DataOutputStream(urlConnection.getOutputStream());
            //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
            os.writeBytes(data);

            os.flush();
            os.close();

            Log.i("STATUS", String.valueOf(urlConnection.getResponseCode()));
            Log.i("MSG" , urlConnection.getResponseMessage());

            urlConnection.disconnect();

       /*     out = new BufferedOutputStream(urlConnection.getOutputStream());

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            writer.write(data);
            writer.flush();
            writer.close();
            out.close();

            urlConnection.connect();*/
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        double enlem=location.getLatitude();
        double boylam=location.getLongitude();

        Toast.makeText(UyeOlActivity.this,"Enlem:"+enlem+" ,Boylam:"+boylam,Toast.LENGTH_LONG).show();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
