using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace AdvertisementApp.Classes
{
    public class Utilities
    {
        public static double UzaklikHesapla(double enlem1, double boylam1, double enlem2, double boylam2)
        {
            var dunyaninYariCapi = 6371;
            var dLat = RadyanHesapla(enlem2 - enlem1);
            var dLon = RadyanHesapla(boylam2 - boylam1);
            var a = Math.Sin(dLat / 2) * Math.Sin(dLat / 2) + Math.Cos(RadyanHesapla(enlem1)) * Math.Cos(RadyanHesapla(enlem2)) * Math.Sin(dLon / 2) * Math.Sin(dLon / 2);
            var c = 2 * Math.Atan2(Math.Sqrt(a), Math.Sqrt(1 - a));
            var d = dunyaninYariCapi * c;

            return d;
        }

        public static double RadyanHesapla(double derece)
        {
            return derece * (Math.PI / 180);
        }
    }
}