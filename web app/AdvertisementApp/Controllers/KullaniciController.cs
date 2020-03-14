using AdvertisementApp.Classes;
using FireSharp;
using FireSharp.Config;
using FireSharp.Interfaces;
using FireSharp.Response;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.Web;
using System.Web.Mvc;

namespace AdvertisementApp.Controllers
{
    public class KullaniciController : Controller
    {
        public ActionResult List()
        {
            string URL = "https://dene-fe577.firebaseio.com/Kullanici.json?auth=wXfzHDOIFOnZpzsrm6Dpb40Wx9fP2hpDT31BU9lY";
            HttpWebRequest request = (HttpWebRequest)WebRequest.Create(URL);
            request.ContentType = "application/json; charset=utf-8";
            HttpWebResponse response = request.GetResponse() as HttpWebResponse;

            string jsonResult = string.Empty;
            using (Stream responseStream = response.GetResponseStream())
            {
                StreamReader reader = new StreamReader(responseStream, Encoding.UTF8);
                jsonResult = reader.ReadToEnd();
            }

            dynamic data = Newtonsoft.Json.JsonConvert.DeserializeObject<dynamic>(jsonResult);

            var kullanicilar = new List<Kullanici>();
            foreach (var itemDynamic in data)
            {
                kullanicilar.Add(JsonConvert.DeserializeObject<Kullanici>(((JProperty)itemDynamic).Value.ToString()));
            }

            return View(kullanicilar);
        }



        public ActionResult Add()
        {
            return View();
        }

        [HttpPost]
        public ActionResult Add(Kullanici kullanici)
        {
            string jsonStr=Newtonsoft.Json.JsonConvert.SerializeObject(kullanici);

            var request = (HttpWebRequest)WebRequest.Create("https://dene-fe577.firebaseio.com/Kullanici.json?auth=wXfzHDOIFOnZpzsrm6Dpb40Wx9fP2hpDT31BU9lY");
            var data = Encoding.ASCII.GetBytes(jsonStr);

            request.Method = "POST";
            request.ContentType = "application/json";
            request.ContentLength = data.Length;

            using (var stream = request.GetRequestStream())
            {
                stream.Write(data, 0, data.Length);
            }

            var response = (HttpWebResponse)request.GetResponse();
            var responseString = new StreamReader(response.GetResponseStream()).ReadToEnd();
            return RedirectToAction(actionName: "List",controllerName: "Kullanici");
        }

        public ActionResult Delete()
        {
            return View();
        }
    }
}