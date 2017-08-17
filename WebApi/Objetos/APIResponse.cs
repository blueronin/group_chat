using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WebApi.Objetos
{
    public class APIResponse
    {
        public string Error { get; set; }
        public Object Datos { get; set; }
        public List<Object> ListaDatos { get; set; }
        public string Mensaje { get; set; }

        public APIResponse()
        {
            Error = null;
            Datos = null;
            ListaDatos = null;
            Mensaje = null;
            Error = null;
            Mensaje = null;
        }

        public void ResponseNull()
        {
            Datos = null;
            ListaDatos = null;
            Mensaje = null;
        }
    }
}