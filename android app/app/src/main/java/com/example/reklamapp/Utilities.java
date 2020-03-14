package com.example.reklamapp;

public class Utilities {

    public static double UzaklikHesapla(double enlem1, double boylam1, double enlem2, double boylam2)
    {
        double dunyaninYariCapi = 6371;
        double dLat = RadyanHesapla(enlem2 - enlem1);
        double dLon = RadyanHesapla(boylam2 - boylam1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(RadyanHesapla(enlem1)) * Math.cos(RadyanHesapla(enlem2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = dunyaninYariCapi * c;

        return d;
    }

    public static double RadyanHesapla(double derece)
    {
        return derece * (Math.PI / 180);
    }
}
