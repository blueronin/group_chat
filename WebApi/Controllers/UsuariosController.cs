using System.Web.Http;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using WebApi.Objetos;

namespace WebApi.Controllers
{
    [RoutePrefix("Usuarios")]
    public class UsuariosController : ApiController
    {
        [HttpGet]
        [ActionName("GetUsuarios")]
        public List<BLL.Usuario> GetUsuarios()
        {
            List<BLL.Usuario> usuarios = new List<BLL.Usuario>();

            try
            {
                BLL.Usuario usuario = new BLL.Usuario();
                usuarios = usuario.GetUsuarios();
            }
            catch
            {
                usuarios = null;
            }

            return usuarios;
        }
    }
}