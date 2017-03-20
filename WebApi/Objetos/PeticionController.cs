using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WebApi.Objetos
{
    public class PeticionController
    {
        //Usuario
        public int IdUsuario { get; set; }
        public string Nickname { get; set; }
        public string Password { get; set; }

        //Mensaje
        public string Descripcion { get; set; }
    }
}