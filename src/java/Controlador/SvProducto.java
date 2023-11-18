package Controlador;

import Conexion.ConexionBD;
import Modelo.Producto;
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

@WebServlet(name = "SvProducto", urlPatterns = {"/SvProducto"})
public class SvProducto extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String Op = request.getParameter("Op");
        ArrayList<Producto> listaProductos = new ArrayList<>();
        ConexionBD conexion = new ConexionBD();
        Connection conn = conexion.Connected();
        PreparedStatement ps;
        ResultSet rs;

        switch (Op) {
            case "Listar":
                try {
                    String sql = "SELECT * FROM producto";
                    ps = conn.prepareStatement(sql);
                    rs = ps.executeQuery();

                    while (rs.next()) {
                        Producto producto = new Producto();
                        producto.setId(rs.getString("id"));
                        producto.setDes(rs.getString("descripcion"));
                        producto.setPre(rs.getDouble("precio"));
                        producto.setSto(rs.getInt("stock"));
                        listaProductos.add(producto);
                    }

                    request.setAttribute("listaProductos", listaProductos);
                    request.getRequestDispatcher("listarProducto.jsp").forward(request, response);
                } catch (SQLException ex) {
                    System.out.println("Error de SQL: " + ex.getMessage());
                } finally {
                    conexion.Discconet();
                }
                break;

            case "Consultar":
                try {
                    String id = request.getParameter("id");
                    String sql = "SELECT * FROM producto WHERE ID=?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, id);
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        Producto producto = new Producto();
                        producto.setId(rs.getString("id"));
                        producto.setDes(rs.getString("descripcion"));
                        producto.setPre(rs.getDouble("precio"));
                        producto.setSto(rs.getInt("stock"));
                        request.setAttribute("producto", producto);
                    }

                    request.getRequestDispatcher("consultarProducto.jsp").forward(request, response);
                } catch (SQLException ex) {
                    System.out.println("Error de SQL: " + ex.getMessage());
                } finally {
                    conexion.Discconet();
                }
                break;

            case "Nuevo":
                // Lógica para mostrar la página de nuevo producto
                request.getRequestDispatcher("SvProducto?Op=Listar").forward(request, response);
                break;

            case "Modificar":
                try {
                    String id = request.getParameter("id");
                    String sql = "SELECT * FROM producto WHERE ID=?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, id);
                    rs = ps.executeQuery();
                    Producto producto = new Producto();
                    if (rs.next()) {
                        producto.setId(rs.getString("id"));
                        producto.setDes(rs.getString("descripcion"));
                        producto.setPre(rs.getDouble("precio"));
                        producto.setSto(rs.getInt("stock"));
                    }
                    request.setAttribute("productoModificado", producto);
                    request.getRequestDispatcher("SvProducto?Op=Listar").forward(request, response);
                } catch (SQLException ex) {
                    System.out.println("Error de SQL: " + ex.getMessage());
                } finally {
                    conexion.Discconet();
                }
                break;

            case "Eliminar":
                try {
                    String id = request.getParameter("id");
                    String sql = "DELETE FROM producto WHERE ID=?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, id);
                    ps.executeUpdate();
                    response.sendRedirect("SvProducto?Op=Listar");
                } catch (SQLException ex) {
                    System.out.println("Error de SQL: " + ex.getMessage());
                } finally {
                    conexion.Discconet();
                }
                break;

            default:
                response.sendRedirect("SvProducto?op=listar");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String Id =request.getParameter("id");
        String Descripcion = request.getParameter("descripcion");
        double Precio = Double.parseDouble(request.getParameter("precio"));
        int Stock = Integer.parseInt(request.getParameter("stock"));
        
        Producto producto = new Producto();
        producto.setId(Id);
        producto.setDes(Descripcion);
        producto.setPre(Precio);
        producto.setSto(Stock);
        ConexionBD conexion = new ConexionBD();
        Connection conn = conexion.Connected();
        PreparedStatement ps;
        ResultSet rs;      
        
        if(Id.isEmpty()){
            String sql_new="select max(ID) ID from producto";
            String sql="insert into producto(ID, DESCRIPCION, PRECIO, STOCK) values(?, ?, ?, ?)";

            try{
                /*Algoritmo para autogenerar el código*/
                String idProducto = "";
                ps = conn.prepareStatement(sql_new);
                rs = ps.executeQuery();
                while (rs.next()) {
                    idProducto = rs.getString("ID");
                }

                idProducto = newCod(idProducto);
                ps = conn.prepareStatement(sql);
                ps.setString(1, idProducto);
                ps.setString(2, producto.getDes());
                ps.setDouble(3, producto.getPre());
                ps.setInt(4, producto.getSto());
                ps.executeUpdate();

            } catch (SQLException ex) {
                System.out.println("Error actualizando tabla..." + ex.getMessage());
            } finally {
                conexion.Discconet();
            }
        }else {
            String sql = "update producto set DESCRIPCION=?, PRECIO=?, STOCK=? where ID=?";

            try {
                ps = conn.prepareStatement(sql);
                ps.setString(1, producto.getDes());
                ps.setDouble(2, producto.getPre());
                ps.setInt(3, producto.getSto());
                ps.setString(4, producto.getId());
                ps.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Error actualizando tabla..." + ex.getMessage());
            } finally {
                conexion.Discconet();
            }
        }
        response.sendRedirect("SvProducto?Op=Listar");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
    
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
