using System.Web.Http;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using WebApi.Objetos;

namespace WebApi.Controllers
{
    [RoutePrefix("Mensajes")]
    public class MensajesController : ApiController
    {
        [HttpGet]
        [ActionName("GetMensajes")]
        public List<BLL.Mensaje> GetMensajes()
        {
            List<BLL.Mensaje> mensajes = new List<BLL.Mensaje>();

            try
            {
                BLL.Mensaje mensaje = new BLL.Mensaje();
                mensajes = mensaje.GetMensajes();
            }
            catch
            {
                mensajes = null;
            }

            return mensajes;
        }
    }
}