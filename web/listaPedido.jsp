<%@page import="Modelo.DetallePedido"%>
<%@page import="Controlador.PedidoDao"%>
<%@page import="Controlador.ProductoDao"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Controlador.ClienteDao"%>
<%@page import="Modelo.Cliente"%>
<%@page import="Modelo.Pedido"%>
<%@page import="Modelo.Producto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@page import="java.util.List"%>
<%@page import="Modelo.Usuario"%>
<%@include file="include/header.jsp" %>

<div class="container">
    <div class="container-table">	
        <div class="titulo">Pedidos</div>	
        <table class="table">
            <thead>
                <tr>
                    <th>Id Pedido</th>
                    <th>Descripcion del pedido</th>
                    <th>Total</th>
                    <th>Cliente</th>
                    <th>Fecha pedido</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%  PedidoDao pedidoDao = new PedidoDao();
                    ClienteDao clienteDao = new ClienteDao();
                    List<Pedido> listaPedidos = (List<Pedido>) request.getAttribute("listaPedido");//pedidoDao.listarPedidosConDetalles();
                    if (listaPedidos != null && !listaPedidos.isEmpty()) {
                        for (Pedido pedido : listaPedidos) {
                %>
                <tr>
                    <td data-label="id"><%=pedido.getId()%></td>
                    <td data-label="Descripcion">
                        <% for (DetallePedido detalle : pedido.getDetalle()) {%>
                        <%=detalle.getDescripcion()%> (Precio: <%=detalle.getPrecio()%>, Cantidad: <%=detalle.getCantidad()%>)<br/>
                        <% }%>
                    </td>
                    <td data-label="Total"><%=pedido.getTotal()%></td>
                    <td data-label="Cliente">
                        <%
                            String idCliente = pedido.getIdCliente();
                            Cliente cliente = clienteDao.consultarCliente(idCliente);
                        %>
                        <%=cliente.getNombre()%>
                    </td>
                    <td><%=pedido.getFechaPedido()%></td>
                    <td>
                        <a class="buttonTabla" href="SVPedido?Op=Eliminar&Id=<%=pedido.getId()%>" >Eliminar</a>

                    </td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="5">No hay pedidos disponibles.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>

    <div class="form-container">
        <form id="clienteForm">
            <label for="idCliente">ID Cliente:</label>
            <%
                List<Cliente> listaClientes = clienteDao.listarClientes();
            %>
            <select id="idCliente" required>
                <%
                    for (Cliente cliente : listaClientes) {
                %>
                <option value="<%=cliente.getId()%>"><%=cliente.getNombre()%></option>
                <%
                    }
                %>
            </select>
            <button  class="botonPeido" type="button" onclick="agregarCliente()">Agregar Cliente</button>
        </form>

        <form id="fechaForm">
            <label for="fecha">Fecha de Pedido:</label>
            <input type="date" id="fecha" required>
            <button class="botonPeido" type="button" onclick="agregarFechaPedido()">Agregar Fecha de Pedido</button>
        </form>

        <form id="productoForm">
            <label for="idProducto">ID Producto:</label>
            <%
                ProductoDao productoDao = new ProductoDao();
                List<Producto> listaProductos = productoDao.listarProducto();
            %>
            <select id="descripcion" onchange="actualizarMaximo()" required>
                <%
                    for (Producto producto : listaProductos) {
                %>
                <option value="<%=producto.getDes()%>" data-stock="<%=producto.getSto()%>"><%=producto.getDes()%></option>
                <%
                    }
                %>
            </select>
            <label for="cantidad">Cantidad:</label>
            <input type="number" id="cantidad" max="" required>
            
            <button  class="botonPeido" type="button" onclick="agregarProducto()">Agregar Producto</button>
        </form>

        <h3>Datos del pedido</h3>
        <ul id="listaClientesFechas"></ul>
        <ul id="listaProductos"></ul>

        <form id="realizarPedidoForm">
            <button class="botonPeido" type="button" onclick="realizarPedido()">Realizar Pedido</button>
        </form>
    </div>
</div>


<script>
// Datos del pedido almacenados en variables
    let idCliente, fechaPedido;
    let listaDetalles = [];

    function agregarCliente() {
        idCliente = document.getElementById("idCliente").value;
        var url = "SVPedido?Op=agregarCliente";
        $.ajax({
            type: 'post',
            url: url,
            data: "idCliente=" + idCliente,
            success: function (data, textStatus, jqXHR) {
                alert("Cliente agregado: " + idCliente);
                agregarClienteALista(idCliente);
                // Puedes realizar más acciones aquí después de que la solicitud se complete
            }
        });
    }

    function agregarFechaPedido() {
        var fechaPedido = document.getElementById("fecha").value;
        var url = "SVPedido?Op=agregarFechaPedido";
        $.ajax({
            type: 'post',
            url: url,
            data: "fecha=" + fechaPedido,
            success: function (data, textStatus, jqXHR) {
                alert("Fecha de Pedido agregada: " + fechaPedido);
                agregarFechaALista(fechaPedido);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("Error en la solicitud AJAX: " + errorThrown);
            }
        });
    }

    function agregarClienteALista(cliente) {
        let listaClientesFechas = document.getElementById("listaClientesFechas");
        let li = document.createElement("li");
        li.textContent = "Cliente: " + cliente;
        listaClientesFechas.appendChild(li);
    }

    function agregarFechaALista(fecha) {
        let listaClientesFechas = document.getElementById("listaClientesFechas");
        let li = document.createElement("li");
        li.textContent = "Fecha de Pedido: " + fecha;
        listaClientesFechas.appendChild(li);
    }
    function actualizarMaximo() {
        var select = document.getElementById('descripcion');
        var selectedOption = select.options[select.selectedIndex];
        var stock = selectedOption.getAttribute('data-stock');
        document.getElementById('cantidad').max = stock;
    }

    function agregarProducto() {
        let descripcion = document.getElementById("descripcion").value;
        let cantidad = document.getElementById("cantidad").value;

        if (!descripcion || !cantidad) {
            alert("Debes ingresar una descripción y una cantidad.");
            return;
        }

        let detalleProducto = {
            descripcion: descripcion,
            cantidad: cantidad
        };

        listaDetalles.push(detalleProducto);

        document.getElementById("descripcion").value = "";
        document.getElementById("cantidad").value = "";

        var url = "SVPedido?Op=AgregarProducto";
        $.ajax({
            type: 'post',
            url: url,
            data: {
                descripcion: descripcion,
                cantidad: cantidad
            },
            success: function (data, textStatus, jqXHR) {
                alert("Producto agregado con éxito.");
                mostrarListaProductos();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("Error en la solicitud AJAX: " + errorThrown);
            }
        });
    }

    function mostrarListaProductos() {
        let listaProductos = document.getElementById("listaProductos");
        listaProductos.innerHTML = "";

        for (let i = 0; i < listaDetalles.length; i++) {
            let producto = listaDetalles[i];
            let li = document.createElement("li");
            li.textContent = producto.descripcion + ": " + producto.cantidad;
            listaProductos.appendChild(li);
        }
    }

    function limpiarInterfaz() {
        idCliente = "";
        fechaPedido = "";
        listaDetalles = "";

        document.getElementById("idCliente").value = "";
        document.getElementById("fecha").value = "";

        document.getElementById("listaClientesFechas").innerHTML = "";
        document.getElementById("listaProductos").innerHTML = "";
    }

    function realizarPedido() {
        var url = "SVPedido?Op=RealizarPedido";
        $.ajax({
            type: 'post',
            url: url,
            success: function (data, textStatus, jqXHR) {
                alert("Pedido realizado con éxito.");
                limpiarInterfaz();
                window.location.href = "SVPedido?Op=Listar";
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("Error en la solicitud AJAX: " + errorThrown);
            }
        });
    }
</script>


<script src="js/jquery-3.7.0.js"></script>
<script src="js/jquery.dataTables.min.js"></script>
<script src="js/HeaderScript.js"></script>
<script src="js/TablaScript.js"></script>
</body>
</html>
