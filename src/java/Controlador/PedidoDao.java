package Controlador;

import Modelo.Pedido;
import Modelo.DetallePedido;
import Conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PedidoDao {

    private Connection conn;

    public PedidoDao() {
        ConexionBD conexion = new ConexionBD();
        conn = conexion.Connected();
    }

    public void crearPedido(Pedido pedido) {
        String sql_new = "SELECT MAX(ID) ID FROM Pedido";
        String sql_insert = "INSERT INTO Pedido (ID, ID_CLIENTE, FECHA_PEDIDO, TOTAL) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps_new = conn.prepareStatement(sql_new); PreparedStatement ps_insert = conn.prepareStatement(sql_insert)) {

            String idPedido = "";
            try (ResultSet rs = ps_new.executeQuery()) {
                while (rs.next()) {
                    idPedido = rs.getString("ID");
                }
            }

            idPedido = newCod(idPedido);

            ps_insert.setString(1, idPedido);
            ps_insert.setString(2, pedido.getIdCliente());
            ps_insert.setDate(3, new java.sql.Date(pedido.getFechaPedido().getTime()));
            ps_insert.setDouble(4, pedido.getTotal());
            ps_insert.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error al insertar pedido: " + ex.getMessage());
        }
    }


    public void eliminarPedidoConDetalles(String idPedido) {
        // Eliminar detalles asociados al pedido
        DetallePedidoDao detallePedidoDao = new DetallePedidoDao();
        detallePedidoDao.eliminarDetallesPorPedido(idPedido);

        // Eliminar el pedido
        String sql = "DELETE FROM pedido WHERE ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idPedido);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error al eliminar pedido con detalles: " + ex.getMessage());
        }
    }


    public Pedido consultarPedido(String id) {
        String sql = "SELECT * FROM Pedido WHERE ID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Pedido pedido = new Pedido();
                    pedido.setId(rs.getString("ID"));
                    pedido.setIdCliente(rs.getString("ID_CLIENTE"));
                    pedido.setFechaPedido(rs.getDate("FECHA_PEDIDO"));
                    pedido.setTotal(rs.getDouble("TOTAL"));
                    return pedido;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al consultar pedido: " + ex.getMessage());
        }
        return null;
    }

    public List<Pedido> listarPedidosConDetalles() {
        List<Pedido> listaPedidos = new ArrayList<>();
        String sql = "SELECT P.ID AS PEDIDO_ID, P.ID_CLIENTE, P.FECHA_PEDIDO, P.TOTAL, "
                + "D.ID AS DETALLE_ID, D.DESCRIPCION, D.PRECIO, D.CANTIDAD "
                + "FROM Pedido P "
                + "LEFT JOIN DetallePedido D ON P.ID = D.ID_PEDIDO";

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String pedidoId = rs.getString("PEDIDO_ID");
                Pedido pedido = encontrarPedidoEnLista(listaPedidos, pedidoId);

                if (pedido == null) {
                    pedido = new Pedido();
                    pedido.setId(pedidoId);
                    pedido.setIdCliente(rs.getString("ID_CLIENTE"));
                    pedido.setFechaPedido(rs.getDate("FECHA_PEDIDO"));
                    pedido.setTotal(rs.getDouble("TOTAL"));
                    pedido.setDetalle(new ArrayList<>());  // Inicializar la lista de detalles
                    listaPedidos.add(pedido);
                }

                // Verificar si hay detalles asociados al pedido
                String detalleId = rs.getString("DETALLE_ID");
                if (detalleId != null) {
                    DetallePedido detalle = new DetallePedido();
                    detalle.setId(detalleId);
                    detalle.setDescripcion(rs.getString("DESCRIPCION"));
                    detalle.setPrecio(rs.getDouble("PRECIO"));
                    detalle.setCantidad(rs.getInt("CANTIDAD"));

                    // Agregar el detalle al pedido correspondiente
                    pedido.getDetalle().add(detalle);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al listar pedidos con detalles: " + ex.getMessage());
        }

        return listaPedidos;
    }
    

    private Pedido encontrarPedidoEnLista(List<Pedido> lista, String pedidoId) {
        for (Pedido pedido : lista) {
            if (pedido.getId().equals(pedidoId)) {
                return pedido;
            }
        }
        return null;
    }

    public void cerrarConexion() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error al cerrar la conexión: " + ex.getMessage());
        }
    }
    public String obtenerIdMasAlto() {
    String sql = "SELECT MAX(ID) AS MAX_ID FROM Pedido";
    String maxId = "";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                maxId = rs.getString("MAX_ID");
            }
        }
    } catch (SQLException ex) {
        System.out.println("Error al obtener el ID más alto: " + ex.getMessage());
    }

    return maxId;
}


    private String newCod(String pCodigo) {
        if (pCodigo != null) {
            int Numero = Integer.parseInt(pCodigo.substring(1));
            Numero = Numero + 1;
            pCodigo = String.valueOf(Numero);
            while (pCodigo.length() < 5) {
                pCodigo = '0' + pCodigo;
            }
            pCodigo = 'P' + pCodigo;
            return pCodigo;
        } else {
            return "P00001";
        }
    }
}
