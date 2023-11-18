/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controlador;

import Conexion.ConexionBD;
import Modelo.Usuario;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Bruno Sandoval
 */
@WebServlet(name = "SvLogin", urlPatterns = {"/SvLogin"})
public class SvLogin extends HttpServlet {



    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ConexionBD conexion = new ConexionBD();
        Connection conn = conexion.Connected();
        PreparedStatement ps;
        ResultSet rs;
        String usuario = request.getParameter("txtUsuario");
        String contrasena = request.getParameter("txtPassword");
        try {
            String sql = "select * from usuario where INGRESO=? and CONTRASENA=?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, contrasena);
            rs = ps.executeQuery();
            if (rs.next()) {
                Usuario usuarioBD = new Usuario();
                usuarioBD.setId(rs.getString("ID"));
                usuarioBD.setNombre(rs.getString("NOMBRE"));
                usuarioBD.setIngreso(rs.getString("INGRESO"));
                usuarioBD.setContrasena(rs.getString("CONTRASENA"));
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuarioBD);
                response.sendRedirect("SvUsuario?Op=Listar");
            } else {
                response.sendRedirect("Login.jsp");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SvLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
