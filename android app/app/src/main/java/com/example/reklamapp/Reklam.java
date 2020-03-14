package com.example.reklamapp;

public class Reklam {

    private int ReklamId;
    private int FirmaId;
    private String FirmaAdi;
    private double FirmaLokasyonEnlem;
    private double FirmaLokasyonBoylam;
    private String KampanyaIcerik;
    private int KampanyaSuresi;
    private String Kategori;

    public void setReklamId(int value)
    {
        this.ReklamId=value;
    }

    public int getReklamId()
    {
        return this.ReklamId;
    }

    public void setFirmaId(int value)
    {
        this.FirmaId=value;
    }

    public int getFirmaId()
    {
        return this.FirmaId;
    }

    public void setFirmaAdi(String value)
    {
        this.FirmaAdi=value;
    }

    public String getFirmaAdi()
    {
        return this.FirmaAdi;
    }

    public void setFirmaEnlem(double value)
    {
        this.FirmaLokasyonEnlem=value;
    }

    public double getFirmaEnlem()
    {
        return this.FirmaLokasyonEnlem;
    }

    public void setFirmaBoylam(double value)
    {
        this.FirmaLokasyonBoylam=value;
    }

    public double getFirmaBoylam()
    {
        return  this.FirmaLokasyonBoylam;
    }

    public void setKampanyaIcerik(String value)
    {
        this.KampanyaIcerik=value;
    }

    public String getKampanyaIcerik()
    {
        return  this.KampanyaIcerik;
    }

    public void setKampanyaSuresi(int value)
    {
        this.KampanyaSuresi=value;
    }

    public int getKampanyaSuresi()
    {
        return  this.KampanyaSuresi;
    }

    public void setKategori(String value)
    {
        this.Kategori=value;
    }

    public String getKategori()
    {
        return this.Kategori;
    }
}
