using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;

namespace DAL
{
    public class Mensaje
    {
        private static string cadena = ConfigurationManager.ConnectionStrings["csChat"].ConnectionString;
        private SqlConnection conexion = new SqlConnection(cadena);

        string sp = "Mensajes_SPAdmin";

        private void AbrirConexion()
        {
            if (conexion == null)
                conexion = new SqlConnection(cadena);

            //si la conexión esta cerrada se abre la conexión
            if (conexion.State == ConnectionState.Closed)
            {
                conexion.Open();
            }
        }

        //Métodos

        public DataTable GetMensajes()
        {
            DataTable dtRetorno;
            SqlCommand cmd;
            try
            {
                AbrirConexion();
                cmd = new SqlCommand(sp, conexion);
                cmd.CommandType = CommandType.StoredProcedure;

                dtRetorno = new DataTable();

                //se agregan los parámetros          
                cmd.Parameters.Add("@sOperacion", SqlDbType.NVarChar, 10).Value = "L";                

                using (SqlDataAdapter adapter = new SqlDataAdapter(cmd))
                {
                    adapter.Fill(dtRetorno);
                }
                cmd.Dispose();
            }
            catch (Exception e)
            {
                throw e;
            }
            finally
            {
                if (conexion.State == ConnectionState.Open)
                {
                    conexion.Close();
                }
            }
            return dtRetorno;
        }

        public void InsertMensaje(string mensaje, int idUsuario)
        {
            SqlCommand cmd;
            try
            {
                AbrirConexion();
                cmd = new SqlCommand(sp, conexion);
                cmd.CommandType = CommandType.StoredProcedure;

                //se agregan los parámetros          
                cmd.Parameters.Add("@sOperacion", SqlDbType.NVarChar, 10).Value = "I";
                cmd.Parameters.Add("@mensaje", SqlDbType.NVarChar).Value = mensaje;
                cmd.Parameters.Add("@idUsuario", SqlDbType.Int).Value = idUsuario;

                cmd.ExecuteNonQuery();
                cmd.Dispose();
            }
            catch (Exception e)
            {
                throw e;
            }
            finally
            {
                if (conexion.State == ConnectionState.Open)
                {
                    conexion.Close();
                }
            }
        }
    }
}
