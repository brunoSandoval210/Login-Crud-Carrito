/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;
import Modelo.Producto;
import Modelo.DetallePedido;
import Modelo.Pedido;
import Conexion.ConexionBD;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;


@WebServlet(name = "SVPedido", urlPatterns = {"/SVPedido"})
public class SVPedido extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
 
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String Op = request.getParameter("Op");
        PedidoDao pedidoDao = new PedidoDao();
        switch (Op) {
            case "Listar":
                List<Pedido> listarPedido = pedidoDao.listarPedidosConDetalles();
                request.setAttribute("listaPedido", listarPedido);
                request.getRequestDispatcher("listaPedido.jsp").forward(request, response);
                break;
            case "Nuevo":
                request.getRequestDispatcher("SVPedido?Op=Listar").forward(request, response);
                break;
            case "Eliminar":
                String idEliminar = request.getParameter("Id");
                System.out.println("ID a eliminar: " + idEliminar); // Agregado para depurar
                pedidoDao.eliminarPedidoConDetalles (idEliminar);
                System.out.println("Pedido eliminado"); // Agregado para depurar
                response.sendRedirect("SVPedido?Op=Listar");
                break;

        }
        pedidoDao.cerrarConexion();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String Op = request.getParameter("Op");
        DetallePedidoDao detalleDao = new DetallePedidoDao();
        ProductoDao productoDao=new ProductoDao();
        PedidoDao pedidoDao = new PedidoDao();
        HttpSession session=request.getSession();
        
        switch (Op) {
            case "agregarCliente":
                String idCliente = request.getParameter("idCliente");
                session.setAttribute("idCliente", idCliente);
                break;
            case "agregarFechaPedido":
                String fechaPedidoStr = request.getParameter("fecha");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  // El formato de tu fecha
                Date fechaPedido = null;

                try {
                    fechaPedido = dateFormat.parse(fechaPedidoStr);
                } catch (ParseException e) {
                    e.printStackTrace();  // Manejo adecuado de la excepción según tu lógica
                }
                session.setAttribute("FechaPedido", fechaPedido);
                break;
            case "AgregarProducto":
                // Obtener la lista de productos y cantidades del atributo de sesión
                List<DetallePedido> listaDetalles = (List<DetallePedido>) request.getSession().getAttribute("listaDetalles");
                if (listaDetalles == null) {
                    listaDetalles = new ArrayList<>();
                }
                String descripcion = request.getParameter("descripcion");
                int cantidad = Integer.parseInt(request.getParameter("cantidad"));

                Producto productoAgregar = productoDao.buscarProductoPorDescripcion(descripcion);
                DetallePedido detalleProducto = new DetallePedido();

                detalleProducto.setDescripcion(productoAgregar.getDes());
                detalleProducto.setPrecio(productoAgregar.getPre());
                detalleProducto.setCantidad(cantidad);

                // Agregar el nuevo detalle a la lista
                listaDetalles.add(detalleProducto);

                // Guardar la lista de detalles en el atributo de sesión
                session.setAttribute("listaDetalles", listaDetalles);
                break;
                
            case "RealizarPedido":
                Pedido nuevoPedido = new Pedido();
                nuevoPedido.setIdCliente((String) session.getAttribute("idCliente"));
                nuevoPedido.setFechaPedido((Date) session.getAttribute("FechaPedido"));
                List<DetallePedido> detalles = (List<DetallePedido>) session.getAttribute("listaDetalles");
                nuevoPedido.setDetalle(detalles);
                nuevoPedido.setTotal(nuevoPedido.getTotal());

                // Insertar el nuevo pedido en la base de datos
                pedidoDao.crearPedido(nuevoPedido);
                session.removeAttribute("idCliente");
                session.removeAttribute("FechaPedido");
                session.removeAttribute("listaDetalles");

                String idPedido = pedidoDao.obtenerIdMasAlto();

                // Insertar los detalles del pedido en la base de datos
                List<DetallePedido> listaDetallesProducto = detalles;
                if (listaDetallesProducto != null) {
                    for (DetallePedido detalle : listaDetallesProducto) {
                        detalle.setIdPedido(idPedido);
                        detalleDao.crearDetallePedido(detalle);
                        Producto producto = productoDao.buscarProductoPorDescripcion(detalle.getDescripcion());
                        // Actualizar el stock del producto
                        int nuevoStock = producto.getSto() - detalle.getCantidad();
                        productoDao.actualizarStockProducto(producto, nuevoStock);
                    }
                }
                break;
            default:
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
