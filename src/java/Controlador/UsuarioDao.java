package Controlador;

import Conexion.ConexionBD;
import java.sql.Connection;
import Modelo.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {
    private Connection conn;
    
    public UsuarioDao() {
        ConexionBD conexion = new ConexionBD();
        conn = conexion.Connected();
    }
    
    public void crearUsuario(Usuario usuario) {
        String sql_new = "SELECT MAX(ID) ID FROM usuario";
        String sql_insert = "INSERT INTO usuario(ID, NOMBRE, INGRESO, CONTRASENA) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps_new = conn.prepareStatement(sql_new); 
             PreparedStatement ps_insert = conn.prepareStatement(sql_insert)) {

            String idUsuario = "";
            try (ResultSet rs = ps_new.executeQuery()) {
                while (rs.next()) {
                    idUsuario = rs.getString("ID");
                }
            }

            idUsuario = newCod(idUsuario);

            ps_insert.setString(1, idUsuario);
            ps_insert.setString(2, usuario.getNombre());
            ps_insert.setString(3, usuario.getIngreso());
            ps_insert.setString(4, usuario.getContrasena());
            ps_insert.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error al insertar usuario: " + ex.getMessage());
        }
    }
    
    public void actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuario SET NOMBRE=?, INGRESO=?, CONTRASENA=? WHERE ID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getIngreso());
            ps.setString(3, usuario.getContrasena());
            ps.setString(4, usuario.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error al actualizar usuario: " + ex.getMessage());
        }
    }
    
    public void eliminarUsuario(String id) {
        String sql = "DELETE FROM usuario WHERE ID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error al eliminar usuario: " + ex.getMessage());
        }
    }
    
    public List<Usuario> listarUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getString("ID"));
                usuario.setNombre(rs.getString("NOMBRE"));
                usuario.setContrasena(rs.getString("CONTRASENA"));
                usuario.setIngreso(rs.getString("INGRESO"));
                lista.add(usuario);
            }
        } catch (SQLException ex) {
            System.out.println("Error al listar usuarios: " + ex.getMessage());
        }
        return lista;
    }
    
    public Usuario consultarUsuario(String id) {
        String sql = "SELECT * FROM usuario WHERE ID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getString("ID"));
                    usuario.setNombre(rs.getString("NOMBRE"));
                    usuario.setContrasena(rs.getString("CONTRASENA"));
                    usuario.setIngreso(rs.getString("INGRESO"));
                    return usuario;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al consultar usuario: " + ex.getMessage());
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
            int Numero = Integer.parseInt(pCodigo.substring(1));
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
