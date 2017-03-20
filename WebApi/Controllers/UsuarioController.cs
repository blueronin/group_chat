using System;
using System.Web.Http;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using WebApi.Objetos;

namespace WebApi.Controllers
{
    [RoutePrefix("Usuario")]
    public class UsuarioController : ApiController
    {
        [HttpGet]
        [ActionName("LoginUsuario")]
        public BLL.Usuario LoginUsuario(string nickname)
        {
            BLL.Usuario usuario = new BLL.Usuario();
            try
            {
                usuario = usuario.LoginUsuario(nickname);                
            }
            catch (Exception e)
            {
                return null;
            }

            return usuario;
        }        
    }
}