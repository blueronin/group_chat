using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;

namespace DAL
{
    public class Usuario
    {
        private static string cadena = ConfigurationManager.ConnectionStrings["csChat"].ConnectionString;
        private SqlConnection conexion = new SqlConnection(cadena);

        string sp = "Usuarios_SPAdmin";

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

        public DataTable LoginUsuario(string nickname)
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
                cmd.Parameters.Add("@nickname", SqlDbType.NVarChar, 50).Value = nickname;                

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

        public DataTable GetUsuarios()
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
                cmd.Parameters.Add("@sOperacion", SqlDbType.NVarChar, 10).Value = "GET";                

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


    }
}
