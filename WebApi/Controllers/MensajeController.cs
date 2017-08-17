using System;
using System.Web.Http;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using WebApi.Objetos;

namespace WebApi.Controllers
{
    [RoutePrefix("Mensaje")]
    public class MensajeController : ApiController
    {
        [HttpPost]
        [ActionName("InsertMensaje")]
        public void InsertMensaje(PeticionController peticion)
        {
            var oResponse = new APIResponse();

            BLL.Mensaje categoriaBLL = new BLL.Mensaje();
            categoriaBLL.InsertMensaje(peticion.Descripcion, peticion.IdUsuario);          
            
        }
    }
}