/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controlador;

import Conexion.ConexionBD;
import Modelo.Cliente;
import Modelo.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet(name = "SvCliente", urlPatterns = {"/SvCliente"})
public class SvCliente extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String Op = request.getParameter("Op");
        ArrayList<Cliente> Lista = new ArrayList<Cliente>();
        ConexionBD conexion = new ConexionBD();
        Connection conn = conexion.Connected();
        PreparedStatement ps;
        ResultSet rs;
        switch (Op) {
            case "Listar":
                try {
                String sql = "select * from cliente";
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getString("Id"));
                    cliente.setNombre(rs.getString("Nombre"));
                    cliente.setApellido(rs.getString("Apellido"));
                    cliente.setDireccion(rs.getString("Direccion"));
                    cliente.setDNI(rs.getString("Dni"));
                    cliente.setTelefono(rs.getString("Telefono"));
                    Lista.add(cliente);
                }
                request.setAttribute("Lista", Lista);
                request.getRequestDispatcher("listarCliente.jsp").forward(request, response);
            } catch (SQLException ex) {
                System.out.println("Error de SQL..." + ex.getMessage());
            } finally {
                conexion.Discconet();
            }
            break;
            case "Consultar":
                try {
                String Id = request.getParameter("Id");
                String sql = "select * from cliente where ID=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, Id);
                rs = ps.executeQuery();
                Cliente cliente = new Cliente();
                while (rs.next()) {
                    cliente.setId(rs.getString("Id"));
                    cliente.setNombre(rs.getString("Nombre"));
                    cliente.setApellido(rs.getString("Apellido"));
                    cliente.setDireccion(rs.getString("Direccion"));
                    cliente.setDNI(rs.getString("Dni"));
                    cliente.setTelefono(rs.getString("Telefono"));
                   
                }
                request.setAttribute("Lista", cliente);
                request.getRequestDispatcher("consultarCliente.jsp").forward(request, response);
            } catch (SQLException ex) {
                System.out.println("Error de SQL..." + ex.getMessage());
            } finally {
                conexion.Discconet();
            }
            break;
            case "Nuevo":
                request.getRequestDispatcher("SvCliente?Op=Listar").forward(request, response);
                break;
            case "Modificar":
                try {
                String Id = request.getParameter("Id");
                String sql = "select * from cliente where ID=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, Id);
                rs = ps.executeQuery();
                Cliente cliente = new Cliente();
                while (rs.next()) {
                    cliente.setId(rs.getString("Id"));
                    cliente.setNombre(rs.getString("Nombre"));
                    cliente.setApellido(rs.getString("Apellido"));
                    cliente.setDireccion(rs.getString("Direccion"));
                    cliente.setDNI(rs.getString("Dni"));
                    cliente.setTelefono(rs.getString("Telefono"));
                }
                request.setAttribute("clientemodificado", cliente);
                request.getRequestDispatcher("SvCliente?Op=Listar").forward(request, response);
            } catch (SQLException ex) {
                System.out.println("Error de SQL..." + ex.getMessage());
            } finally {
                conexion.Discconet();
            }
            break;
            case "Eliminar":
                try {
                String Id = request.getParameter("Id");
                String sql = "delete from cliente where ID=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, Id);
                ps.executeUpdate();
                request.getRequestDispatcher("SvCliente?Op=Listar").forward(request, response);
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
        String Apellido = request.getParameter("Apellido");
        String Direccion = request.getParameter("Direccion");
        String Dni = request.getParameter("Dni");
        String Telefono = request.getParameter("Telefono");

        Cliente cliente = new Cliente();
        cliente.setId(Id);
        cliente.setNombre(Nombre);
        cliente.setApellido(Apellido);
        cliente.setDireccion(Direccion);
        cliente.setDNI(Dni);
        cliente.setTelefono(Telefono);
        ConexionBD conexion = new ConexionBD();
        Connection conn = conexion.Connected();
        PreparedStatement ps;
        ResultSet rs;     
        
        if(Id.isEmpty()){
            String sql_new="select max(ID) ID from cliente";
            String sql="insert into cliente(ID, APELLIDO, NOMBRE, DIRECCION, DNI, TELEFONO) values(?, ?, ?, ?, ?, ?)";

            try{
                /*Algoritmo para autogenerar el código*/
                String idCliente = "";
                ps = conn.prepareStatement(sql_new);
                rs = ps.executeQuery();
                while (rs.next()) {
                    idCliente = rs.getString("ID");
                }

                idCliente = newCod(idCliente);
                ps = conn.prepareStatement(sql);
                ps.setString(1, idCliente);
                ps.setString(2, cliente.getApellido());
                ps.setString(3, cliente.getNombre());
                ps.setString(4, cliente.getDireccion());
                ps.setString(5, cliente.getDNI());
                ps.setString(6, cliente.getTelefono());
                ps.executeUpdate();

            } catch (SQLException ex) {
                System.out.println("Error actualizando tabla..." + ex.getMessage());
            } finally {
                conexion.Discconet();
            }
        } else {
            String sql = "update cliente set APELLIDO=?, NOMBRE=?, DIRECCION=?, DNI=?, TELEFONO=? where ID=?";

            try {
                ps = conn.prepareStatement(sql);
                ps.setString(1, cliente.getApellido());
                ps.setString(2, cliente.getNombre());
                ps.setString(3, cliente.getDireccion());
                ps.setString(4, cliente.getDNI());
                ps.setString(5, cliente.getTelefono());
                ps.setString(6, cliente.getId());
                ps.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Error actualizando tabla..." + ex.getMessage());
            } finally {
                conexion.Discconet();
            }
        }

        response.sendRedirect("SvCliente?Op=Listar");
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
