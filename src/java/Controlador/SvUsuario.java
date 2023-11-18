
package Controlador;

import Conexion.ConexionBD;
import Modelo.Usuario;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SvUsuario", urlPatterns = {"/SvUsuario"})
public class SvUsuario extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String Op = request.getParameter("Op");
        ArrayList<Usuario> Lista = new ArrayList<Usuario>();
        ConexionBD conexion = new ConexionBD();
        Connection conn = conexion.Connected();
        PreparedStatement ps;
        ResultSet rs;
        switch (Op) {
            case "Listar":
                try {
                String sql = "select * from usuario";
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getString("Id"));
                    usuario.setNombre(rs.getString("Nombre"));
                    usuario.setContrasena(rs.getString("Contrasena"));
                    usuario.setIngreso(rs.getString("Ingreso"));
                    Lista.add(usuario);
                }
                request.setAttribute("Lista", Lista);
                request.getRequestDispatcher("listarUsuario.jsp").forward(request, response);
            } catch (SQLException ex) {
                System.out.println("Error de SQL..." + ex.getMessage());
            } finally {
                conexion.Discconet();
            }
            break;
            case "Consultar":
                try {
                String Id = request.getParameter("Id");
                String sql = "select * from usuario where ID=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, Id);
                rs = ps.executeQuery();
                Usuario usuario = new Usuario();
                while (rs.next()) {
                    usuario.setId(rs.getString("Id"));
                    usuario.setNombre(rs.getString("Nombre"));
                    usuario.setContrasena(rs.getString("Contrasena"));
                    usuario.setIngreso(rs.getString("Ingreso"));
                   
                }
                request.setAttribute("Lista", usuario);
                request.getRequestDispatcher("consultarUsuario.jsp").forward(request, response);
            } catch (SQLException ex) {
                System.out.println("Error de SQL..." + ex.getMessage());
            } finally {
                conexion.Discconet();
            }
            break;
            case "Nuevo":
                request.getRequestDispatcher("SvUsuario?Op=Listar").forward(request, response);
                break;
            case "Modificar":
                try {
                String Id = request.getParameter("Id");
                String sql = "select * from usuario where ID=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, Id);
                rs = ps.executeQuery();
                Usuario usuario = new Usuario();
                while (rs.next()) {
                    usuario.setId(rs.getString("Id"));
                    usuario.setNombre(rs.getString("Nombre"));
                    usuario.setContrasena(rs.getString("Contrasena"));
                    usuario.setIngreso(rs.getString("Ingreso"));
                }
                request.setAttribute("usuariomodificado", usuario);
                request.getRequestDispatcher("SvUsuario?Op=Listar").forward(request, response);
            } catch (SQLException ex) {
                System.out.println("Error de SQL..." + ex.getMessage());
            } finally {
                conexion.Discconet();
            }
            break;
            case "Eliminar":
                try {
                String Id = request.getParameter("Id");
                String sql = "delete from usuario where ID=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, Id);
                ps.executeUpdate();
                request.getRequestDispatcher("SvUsuario?Op=Listar").forward(request, response);
            } catch (SQLException ex) {
                System.out.println("Error de SQL..." + ex.getMessage());
            } finally {
                conexion.Discconet();
            }
            break;

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String Id =request.getParameter("Id");
        String Nombre = request.getParameter("Nombre");
        String Ingreso = request.getParameter("Ingreso");
        String Contrasena = request.getParameter("Contrasena");

        Usuario usuario = new Usuario();
        usuario.setId(Id);
        usuario.setNombre(Nombre);
        usuario.setContrasena(Contrasena);
        usuario.setIngreso(Ingreso);
        ConexionBD conexion = new ConexionBD();
        Connection conn = conexion.Connected();
        PreparedStatement ps;
        ResultSet rs;        
        
        if(Id.isEmpty()){
            String sql_new="select max(ID) ID from usuario";
            String sql="insert into usuario(ID, NOMBRE, INGRESO, CONTRASENA) values(?, ?, ?, ?)";

            try{
                /*Algoritmo para autogenerar el código*/
                String idUsuario = "";
                ps = conn.prepareStatement(sql_new);
                rs = ps.executeQuery();
                while (rs.next()) {
                    idUsuario = rs.getString("ID");
                }

                idUsuario = newCod(idUsuario);
                ps = conn.prepareStatement(sql);
                ps.setString(1, idUsuario);
                ps.setString(2, usuario.getNombre());
                ps.setString(3, usuario.getIngreso());
                ps.setString(4, usuario.getContrasena());
                ps.executeUpdate();

            } catch (SQLException ex) {
                System.out.println("Error actualizando tabla..." + ex.getMessage());
            } finally {
                conexion.Discconet();
            }
        } else {
            String sql = "update usuario set NOMBRE=?, INGRESO=?, CONTRASENA=? where ID=?";

            try {
                ps = conn.prepareStatement(sql);
                ps.setString(1, usuario.getNombre());
                ps.setString(2, usuario.getIngreso());
                ps.setString(3, usuario.getContrasena());
                ps.setString(4, usuario.getId());
                ps.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Error actualizando tabla..." + ex.getMessage());
            } finally {
                conexion.Discconet();
            }
        }

        response.sendRedirect("SvUsuario?Op=Listar");

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String newCod(String pCodigo) {
        if (pCodigo != null) {
            int Numero = Integer.parseInt(pCodigo.substring(2));
            Numero = Numero + 1;
            pCodigo = String.valueOf(Numero);
            while (pCodigo.length() < 5) {
                pCodigo = '0' + pCodigo;
            }
            pCodigo = 'C' + pCodigo;
            return pCodigo;
        } else {
            return "C00001";
        }
    }

}
