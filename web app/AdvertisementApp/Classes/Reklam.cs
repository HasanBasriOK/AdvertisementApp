using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace AdvertisementApp.Classes
{
    public class Reklam
    {
        public int ReklamId { get; set; }
        public int FirmaId { get; set; }
        public string FirmaAdi { get; set; }
        public double FirmaLokasyonEnlem { get; set; }
        public double FirmaLokasyonBoylam { get; set; }
        public string KampanyaIcerik { get; set; }
        public int KampanyaSuresi { get; set; }
        public string Kategori { get; set; }
    }
}