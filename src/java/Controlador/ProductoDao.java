package Controlador;

import Modelo.Producto;
import Conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDao {

    private Connection conn;

    public ProductoDao() {
        ConexionBD conexion = new ConexionBD();
        conn = conexion.Connected();
    }

    public void crearProducto(Producto producto) {
        String sql_new = "select max(ID) ID from producto";
        String sql_insert = "insert into producto(ID, DESCRIPCION, PRECIO, STOCK) values(?, ?, ?, ?)";

        try (PreparedStatement ps_new = conn.prepareStatement(sql_new); PreparedStatement ps_insert = conn.prepareStatement(sql_insert)) {

            String idProducto = "";
            try (ResultSet rs = ps_new.executeQuery()) {
                while (rs.next()) {
                    idProducto = rs.getString("ID");
                }
            }

            idProducto = newCod(idProducto);
            ps_insert.setString(1, idProducto);
            ps_insert.setString(2, producto.getDes());
            ps_insert.setDouble(3, producto.getPre());
            ps_insert.setInt(4, producto.getSto());
            ps_insert.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error al insertar usuario: " + ex.getMessage());
        }
    }

    public void actualizaCliente(Producto producto) {
        String sql = "update producto set DESCRIPCION=?, PRECIO=?, STOCK=? where ID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, producto.getDes());
            ps.setDouble(2, producto.getPre());
            ps.setInt(3, producto.getSto());
            ps.setString(4, producto.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error al actualizar el producto..." + ex.getMessage());
        }
    }

    public void eliminarProducto(String id) {
        String sql = "DELETE FROM producto WHERE ID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error al eliminar usuario: " + ex.getMessage());
        }
    }

    public List<Producto> listarProducto() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getString("id"));
                producto.setDes(rs.getString("descripcion"));
                producto.setPre(rs.getDouble("precio"));
                producto.setSto(rs.getInt("stock"));
                lista.add(producto);
            }
        } catch (SQLException ex) {
            System.out.println("Error al listar usuarios: " + ex.getMessage());
        }
        return lista;
    }

    public Producto consultarProducto(String id) {
        String sql = "SELECT * FROM producto WHERE ID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Producto producto = new Producto();
                    producto.setId(rs.getString("id"));
                    producto.setDes(rs.getString("descripcion"));
                    producto.setPre(rs.getDouble("precio"));
                    producto.setSto(rs.getInt("stock"));
                    return producto;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al consultar el producto: " + ex.getMessage());
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

    public Producto buscarProductoPorDescripcion(String descripcion) {
        String sql = "SELECT * FROM producto WHERE LOWER(descripcion) LIKE LOWER(?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + descripcion + "%");

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Producto producto = new Producto();
                    producto.setId(rs.getString("id"));
                    producto.setDes(rs.getString("descripcion"));
                    producto.setPre(rs.getDouble("precio"));
                    producto.setSto(rs.getInt("stock"));
                    return producto;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al buscar producto por descripción: " + ex.getMessage());
        }

        return null;
    }
    
    public void actualizarStockProducto(Producto producto, int nuevoStock) {
    String sql = "update producto set STOCK=? where ID=?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, nuevoStock);
        ps.setString(2, producto.getId());
        ps.executeUpdate();
    } catch (SQLException ex) {
        System.out.println("Error al actualizar el stock del producto..." + ex.getMessage());
    }
}

}
