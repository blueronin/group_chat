using System;
using System.Web.Http;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using WebApi.Objetos;

using System.Net.Http;
using System.Net.Http.Headers;
using System.Net.Http.Formatting;

namespace WebApi.Controllers
{
    [RoutePrefix("Giphy")]
    public class GiphyController : ApiController
    {
        public class Giphy_image_object
        {
            public string url { get; set; }
            public string width { get; set; }
            public string height { get; set; }
            public string size { get; set; }
            public string mp4 { get; set; }
            public string mp4_size { get; set; }
            public string webp { get; set; }
            public string webp_size { get; set; }  
            public string frames { get; set; }
        }       

        public class Giphy_image 
        {
            public Giphy_image_object fixed_height { get; set; }
            //public Giphy_image_object fixed_height_still { get; set; }
            //public Giphy_image_object fixed_height_downsampled { get; set; }
            //public Giphy_image_object fixed_width { get; set; }
            //public Giphy_image_object fixed_width_still { get; set; }
            //public Giphy_image_object fixed_width_downsampled { get; set; }
            //public Giphy_image_object fixed_height_small { get; set; }
            //public Giphy_image_object fixed_height_small_still { get; set; }
            //public Giphy_image_object fixed_width_small { get; set; }
            //public Giphy_image_object fixed_width_small_still { get; set; }
            //public Giphy_image_object downsized { get; set; }
            //public Giphy_image_object downsized_still { get; set; }
            //public Giphy_image_object downsized_large { get; set; }
            //public Giphy_image_object downsized_medium { get; set; }
            //public Giphy_image_object original { get; set; }
            //public Giphy_image_object original_still { get; set; }
            //public Giphy_image_object looping { get; set; }
            //public Giphy_image_object preview { get; set; }
            //public Giphy_image_object downsized_small { get; set; }
            //public Giphy_image_object preview_gif { get; set; }                  
        }
        
        public class Giphy_object
        {            
            public string id { get; set; }            
            public Giphy_image images { get; set; }            
        }

        public class Giphy_object_list
        {
            public List<Giphy_object> data { get; set; }
        }

        private string URL = "https://giphy.p.mashape.com/v1/gifs/search";                              
        private string api_key = "dc6zaTOxFJmzC";        

        [HttpGet]
        [ActionName("Search")]
        public List<Giphy_object> Search(string busqueda)
        {
            System.Net.Http.HttpClient client = new System.Net.Http.HttpClient();

            client.DefaultRequestHeaders.Add("X-Mashape-Key", "ONPnyQZg5vmshMKEJCDcsTxqUosUp1pphvJjsn4Gu2rXLxL00R");
            client.DefaultRequestHeaders.Add("Accept", "application/json");

            client.BaseAddress = new System.Uri(URL);            
            HttpResponseMessage resp = client.GetAsync(URL + "?api_key=" + api_key + "&limit=9&q=" + busqueda).Result;

            resp.EnsureSuccessStatusCode();
            string result = resp.Content.ReadAsStringAsync().Result;
            var giphys = Newtonsoft.Json.JsonConvert.DeserializeObject<Giphy_object_list>(result).data;

            return giphys;
        }        
    }
}