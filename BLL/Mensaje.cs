using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data;

namespace BLL
{
    public class Mensaje
    {
        //Propiedades
        private int idMensaje;
        private string nickname;
        private string descripcion;

        public int IdMensaje 
        {
            get { return idMensaje; }
            set { idMensaje = value; }
        }

        public string Nickname
        {
            get { return nickname; }
            set { nickname = value; }
        }

        public string Descripcion
        {
            get { return descripcion; }
            set { descripcion = value; }
        }

        //Métodos

        public List<Mensaje> GetMensajes()
        {
            List<BLL.Mensaje> mensajes = new List<BLL.Mensaje>();
            DAL.Mensaje mensajeDAL = new DAL.Mensaje();
            DataTable tableResult = mensajeDAL.GetMensajes();
            foreach (DataRow item in tableResult.Rows)
            {
                Mensaje mensaje = new Mensaje();
                mensaje.IdMensaje = int.Parse(item["idMensaje"].ToString());
                mensaje.Nickname = item["nickname"].ToString();
                mensaje.Descripcion = item["mensaje"].ToString();
                mensajes.Add(mensaje);
            }
            return mensajes;
        }

        public void InsertMensaje(string mensaje, int idUsuario)
        {
            DAL.Mensaje mensajeDAL = new DAL.Mensaje();
            mensajeDAL.InsertMensaje(mensaje, idUsuario);
        }
    }
}
