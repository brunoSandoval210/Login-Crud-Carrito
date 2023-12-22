package Controlador;
import Modelo.DetallePedido;
import Conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetallePedidoDao {
    private Connection conn;

    public DetallePedidoDao() {
        ConexionBD conexion = new ConexionBD();
        conn = conexion.Connected();
    }

    public void crearDetallePedido(DetallePedido detallePedido) {
        String sql_new = "SELECT MAX(ID) ID FROM DetallePedido";
        String sql_insert = "INSERT INTO DetallePedido (ID, ID_PEDIDO, DESCRIPCION, PRECIO, CANTIDAD) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps_new = conn.prepareStatement(sql_new);
             PreparedStatement ps_insert = conn.prepareStatement(sql_insert)) {

            String idDetallePedido = "";
            try (ResultSet rs = ps_new.executeQuery()) {
                while (rs.next()) {
                    idDetallePedido = rs.getString("ID");
                }
            }

            idDetallePedido = newCod(idDetallePedido);

            ps_insert.setString(1, idDetallePedido);
            ps_insert.setString(2, detallePedido.getIdPedido());
            ps_insert.setString(3, detallePedido.getDescripcion());
            ps_insert.setDouble(4, detallePedido.getPrecio());
            ps_insert.setInt(5, detallePedido.getCantidad());
            ps_insert.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error al insertar detalle pedido: " + ex.getMessage());
        }
    }
    public void eliminarDetallesPorPedido(String idPedido) {
        String sql = "DELETE FROM detallepedido WHERE ID_PEDIDO = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idPedido);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error al eliminar detalles por pedido: " + ex.getMessage());
        }
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
    
    private String newCod(String pCodigo) {
        if (pCodigo != null) {
            int Numero = Integer.parseInt(pCodigo.substring(1));
            Numero = Numero + 1;
            pCodigo = String.valueOf(Numero);
            while (pCodigo.length() < 5) {
                pCodigo = '0' + pCodigo;
            }
            pCodigo = 'D' + pCodigo;
            return pCodigo;
        } else {
            return "D00001";
        }
    }

}
