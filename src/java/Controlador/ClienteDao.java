package Controlador;

import Conexion.ConexionBD;
import java.sql.Connection;
import Modelo.Cliente;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {

    private Connection conn;

    public ClienteDao() {
        ConexionBD conexion = new ConexionBD();
        conn = conexion.Connected();
    }

    public void crearCliente(Cliente cliente) {
        String sql_new = "select max(ID) ID from cliente";
        String sql_insert = "insert into cliente(ID, APELLIDO, NOMBRE, DIRECCION, DNI, TELEFONO) values(?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps_new = conn.prepareStatement(sql_new); PreparedStatement ps_insert = conn.prepareStatement(sql_insert)) {

            String idCliente = "";
            try (ResultSet rs = ps_new.executeQuery()) {
                while (rs.next()) {
                    idCliente = rs.getString("ID");
                }
            }
            idCliente = newCod(idCliente);

            ps_insert.setString(1, idCliente);
            ps_insert.setString(2, cliente.getApellido());
            ps_insert.setString(3, cliente.getNombre());
            ps_insert.setString(4, cliente.getDireccion());
            ps_insert.setString(5, cliente.getDNI());
            ps_insert.setString(6, cliente.getTelefono());
            ps_insert.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error al insertar el cliente: " + ex.getMessage());
        } finally {
        }
    }

    public void actualizaCliente(Cliente cliente) {
        String sql = "update cliente set APELLIDO=?, NOMBRE=?, DIRECCION=?, DNI=?, TELEFONO=? where ID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cliente.getApellido());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getDireccion());
            ps.setString(4, cliente.getDNI());
            ps.setString(5, cliente.getTelefono());
            ps.setString(6, cliente.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error al actualizar al cliente..." + ex.getMessage());
        }
    }

    public void eliminarCliente(String idCliente) {
        String sql = "delete from cliente where ID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idCliente);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error de al eliminar al cliente..." + ex.getMessage());
        }
    }

    public List<Cliente> listarClientes() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getString("Id"));
                cliente.setNombre(rs.getString("Nombre"));
                cliente.setApellido(rs.getString("Apellido"));
                cliente.setDireccion(rs.getString("Direccion"));
                cliente.setDNI(rs.getString("Dni"));
                cliente.setTelefono(rs.getString("Telefono"));
                lista.add(cliente);
            }
        } catch (SQLException ex) {
            System.out.println("Error al listar clientes" + ex.getMessage());
        }
        return lista;
    }
    
    public Cliente consultarCliente(String id) {
        String sql = "select * from cliente where ID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente=new Cliente();
                    cliente.setId(rs.getString("Id"));
                    cliente.setNombre(rs.getString("Nombre"));
                    cliente.setApellido(rs.getString("Apellido"));
                    cliente.setDireccion(rs.getString("Direccion"));
                    cliente.setDNI(rs.getString("Dni"));
                    cliente.setTelefono(rs.getString("Telefono"));
                    return cliente;                  
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error al consultar el cliente: " + ex.getMessage());
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

}
