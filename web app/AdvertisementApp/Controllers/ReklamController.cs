using AdvertisementApp.Classes;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Web;
using System.Web.Mvc;

namespace AdvertisementApp.Controllers
{
    public class ReklamController : Controller
    {
        // GET: Reklam
        public ActionResult List()
        {
            string URL = "https://dene-fe577.firebaseio.com/Reklam.json?auth=wXfzHDOIFOnZpzsrm6Dpb40Wx9fP2hpDT31BU9lY";
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

            var reklamlar = new List<Reklam>();
            foreach (var itemDynamic in data)
            {
                reklamlar.Add(JsonConvert.DeserializeObject<Reklam>(((JProperty)itemDynamic).Value.ToString()));
            }

            return View(reklamlar);
        }

        public ActionResult Add()
        {
            return View();
        }

        [HttpPost]
        public ActionResult Add(Reklam reklam)
        {
            var jsonStr = Newtonsoft.Json.JsonConvert.SerializeObject(reklam);

            var request = (HttpWebRequest)WebRequest.Create("https://dene-fe577.firebaseio.com/Reklam.json?auth=wXfzHDOIFOnZpzsrm6Dpb40Wx9fP2hpDT31BU9lY");
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
            return RedirectToAction(actionName: "List", controllerName: "Reklam");
        }
    }
}