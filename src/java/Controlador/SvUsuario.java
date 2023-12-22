
package Controlador;

import Conexion.ConexionBD;
import Modelo.Usuario;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
        UsuarioDao usuarioDAO = new UsuarioDao();

        switch (Op) {
            case "Listar":
                List<Usuario> listaUsuarios = usuarioDAO.listarUsuarios();
                request.setAttribute("Lista", listaUsuarios);
                request.getRequestDispatcher("listarUsuario.jsp").forward(request, response);
            break;
            case "Consultar":
                String IdConsulta = request.getParameter("Id");
                Usuario usuarioConsulta = usuarioDAO.consultarUsuario(IdConsulta);
                request.setAttribute("Lista", usuarioConsulta);
                request.getRequestDispatcher("consultarUsuario.jsp").forward(request, response);
                break;
            case "Nuevo":
                request.getRequestDispatcher("SvUsuario?Op=Listar").forward(request, response);
                break;
            case "Modificar":
                String IdModificar = request.getParameter("Id");
                Usuario usuarioModificar=usuarioDAO.consultarUsuario(IdModificar);
                request.setAttribute("usuariomodificado", usuarioModificar);
                request.getRequestDispatcher("SvUsuario?Op=Listar").forward(request, response);
                break;
            case "Eliminar":
                String IdElimiar = request.getParameter("Id");
                usuarioDAO.eliminarUsuario(IdElimiar);
                request.getRequestDispatcher("SvUsuario?Op=Listar").forward(request, response);
         
            break;

        }
         usuarioDAO.cerrarConexion();

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
        UsuarioDao usuarioDAO = new UsuarioDao(); 
        
        if(Id.isEmpty()){
            usuarioDAO.crearUsuario(usuario);
        } else {
            usuarioDAO.actualizarUsuario(usuario);
        }
        usuarioDAO.cerrarConexion();
        response.sendRedirect("SvUsuario?Op=Listar");

    }

}
