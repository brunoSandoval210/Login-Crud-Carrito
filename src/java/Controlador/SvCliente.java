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
import java.util.List;
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
        ClienteDao clienteDAO=new ClienteDao();
        switch (Op) {
            case "Listar":
                List<Cliente> listaClientes=clienteDAO.listarClientes();
                request.setAttribute("Lista", listaClientes);
                request.getRequestDispatcher("listarCliente.jsp").forward(request, response);   
            break;
            case "Consultar":
                String Id = request.getParameter("Id");
                Cliente clienteConsulta=clienteDAO.consultarCliente(Id);
                request.setAttribute("Lista", clienteConsulta);
                request.getRequestDispatcher("consultarCliente.jsp").forward(request, response);
            break;
            case "Nuevo":
                request.getRequestDispatcher("SvCliente?Op=Listar").forward(request, response);
                break;
            case "Modificar":
                String IdModificar = request.getParameter("Id");
                Cliente clienteModificar=clienteDAO.consultarCliente(IdModificar);
                request.setAttribute("clientemodificado", clienteModificar);
                request.getRequestDispatcher("SvCliente?Op=Listar").forward(request, response);
            break;
            case "Eliminar":
                String IdEliminar = request.getParameter("Id");
                clienteDAO.eliminarCliente(IdEliminar);
                request.getRequestDispatcher("SvCliente?Op=Listar").forward(request, response);
            break;
        }
        clienteDAO.cerrarConexion();
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
        ClienteDao clienteDAO=new ClienteDao();
        
        if(Id.isEmpty()){
            clienteDAO.crearCliente(cliente);

        } else {
            clienteDAO.actualizaCliente(cliente);
        }
        clienteDAO.cerrarConexion();
        response.sendRedirect("SvCliente?Op=Listar");
    }

}
