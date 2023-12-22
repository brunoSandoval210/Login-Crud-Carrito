package Modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pedido {

    private String id;
    private String idCliente;
    private Date fechaPedido;
    private List<DetallePedido> detalle;
    private double total;

    public Pedido() {
    }

    public Pedido(String id, String idCliente, Date fechaPedido, List<DetallePedido> detalle, double total) {
        this.id = id;
        this.idCliente = idCliente;
        this.fechaPedido = fechaPedido;
        this.detalle = detalle;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public List<DetallePedido> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<DetallePedido> detalle) {
        this.detalle = detalle;
    }

    public double getTotal() {
        double total=0.0;
        if (detalle != null) {
            for (DetallePedido detallePedido : detalle) {
                total += detallePedido.getPrecio() * detallePedido.getCantidad();
            }
        }
        return total;
    }
    
    
    
    public void setTotal(double total) {
        this.total = total;
    }
      
    
}
