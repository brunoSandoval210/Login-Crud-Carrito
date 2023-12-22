/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

package Controlador;

/**
 *
 * @author Bruno Sandoval
 */
import Modelo.Pedido;
import java.util.ArrayList;
import java.util.List;
public class NewMain {
    public static void main(String[] args) {
        PedidoDao p = new PedidoDao();
        List<Pedido> pe = new ArrayList<>();
        
        pe = p.listarPedidosConDetalles();
        
        // Ahora puedes imprimir la lista de pedidos o realizar otras operaciones con ella
        for (Pedido pedido : pe) {
            System.out.println("Pedido ID: " + pedido.getId());
            System.out.println("Cliente ID: " + pedido.getIdCliente());
            System.out.println("total:"+pedido.getTotal());
            // Imprimir más detalles según sea necesario
        }
    }
}
