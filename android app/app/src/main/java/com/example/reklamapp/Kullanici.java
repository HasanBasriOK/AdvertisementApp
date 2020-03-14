package com.example.reklamapp;

public class Kullanici {
    private String Ad;
    private String Soyad;
    private String Email;
    private String Password;

    public void setAd(String value)
    {
        this.Ad=value;
    }

    public String getAd()
    {
        return this.Ad;
    }

    public void setSoyad(String value)
    {
        this.Soyad=value;
    }

    public String getSoyad()
    {
        return this.Soyad;
    }

    public  void setEmail(String value)
    {
        this.Email=value;
    }

    public String getEmail()
    {
        return this.Email;
    }

    public void setPassword(String value)
    {
        this.Password=value;
    }
    public String getPassword()
    {
        return this.Password;
    }
}
