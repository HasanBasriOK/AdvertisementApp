package com.example.reklamapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private List<Reklam> _reklamList;
    private LayoutInflater reklamInflater;

    public CustomAdapter(List<Reklam> reklamList, Activity activity)
    {
        reklamInflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this._reklamList=reklamList;
    }

    @Override
    public int getCount() {
        return _reklamList.size();
    }

    @Override
    public Object getItem(int position) {
        return _reklamList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return  Long.valueOf(_reklamList.get(position).getReklamId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View lineview;

        lineview=reklamInflater.inflate(R.layout.satir_layout,null);
        TextView txtSatirFirma=(TextView)lineview.findViewById(R.id.txtSatirFirmaAd);
        TextView txtSatirKategori=(TextView)lineview.findViewById(R.id.txtSatirKategoriAd);

        Reklam reklam=_reklamList.get(position);
        txtSatirFirma.setText(reklam.getFirmaAdi());
        txtSatirKategori.setText(reklam.getKategori());

        return lineview;
    }
}
