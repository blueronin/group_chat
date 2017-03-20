using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data;

namespace BLL
{
    public class Usuario
    {
        //Propiedades       

        private int idUsuario;
        private string nickname;
        private string password;

        public int IdUsuario
        {
            get { return idUsuario; }
            set { idUsuario = value; }
        }

        public string Nickname
        {
            get { return nickname; }
            set { nickname = value; }
        }

        public string Password
        {
            get { return password; }
            set { password = value; }
        }

        //Métodos

        public Usuario LoginUsuario(string nickname)
        {
            try
            {
                Usuario usuario = new Usuario();
                DAL.Usuario usuarioDAL = new DAL.Usuario();
                DataTable tableResult = usuarioDAL.LoginUsuario(nickname);
                foreach (DataRow item in tableResult.Rows)
                {
                    usuario.IdUsuario = int.Parse(item["idUsuario"].ToString());
                    usuario.Nickname = item["nickname"].ToString();                   
                }
                return usuario;
            }
            catch
            {
                return null;
            }
        }

        public List<Usuario> GetUsuarios()
        {
            List<BLL.Usuario> usuarios = new List<BLL.Usuario>();
            DAL.Usuario mensajeDAL = new DAL.Usuario();
            DataTable tableResult = mensajeDAL.GetUsuarios();
            foreach (DataRow item in tableResult.Rows)
            {
                Usuario usuario = new Usuario();
                usuario.IdUsuario = int.Parse(item["idUsuario"].ToString());
                usuario.Nickname = item["nickname"].ToString();
                usuarios.Add(usuario);
            }
            return usuarios;
        }
    }
}
