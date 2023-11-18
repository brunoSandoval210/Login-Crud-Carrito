<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@page import="java.util.List"%>
<%@page import="Modelo.Producto"%>
<%@include file="include/header.jsp" %>
<div class="container">
    <div class="container-table">	
        <div class="titulo">Productos</div>	
        <table class="table">
            <thead>
                <tr>
                    <th>Id Producto</th>
                    <th>Descripcion</th>
                    <th>Precio</th>
                    <th>Stock</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%  List<Producto> Lista = (List<Producto>) request.getAttribute("listaProductos");
                    if (Lista != null) {

                        for (Producto prod : Lista) {
                %>
                <tr>
                    <td data-label="id"><%=prod.getId()%></td>
                    <td data-label="nombre"><%=prod.getDes()%></td>
                    <td data-label="ingreso"><%=prod.getPre()%></td>
                    <td data-label="contrasena"><%=prod.getSto()%></td>
                    <td>
                        <a class="buttonTabla" href="SvProducto?Op=Modificar&id=<%=prod.getId()%>" >Modificar</a>
                        <a class="buttonTabla" href="SvProducto?Op=Eliminar&id=<%=prod.getId()%>" >Eliminar</a>
                    </td>
                </tr>
                <%}
                } else {
                %>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>

                </tr>
                <%}%>
            </tbody>
        </table>
    </div>
    <div class="form-container">
        <%  Producto productomodificado = (Producto) request.getAttribute("productoModificado");
            if (productomodificado != null) {%>
        <form action="SvProducto" method="post"> 
            <h2>Modificar producto</h2> 
            <input type="hidden" name="id" value="<%=productomodificado.getId()%>">
            <div class="form-group descripcion">
                <label for="nombre">Descripcion del producto</label>
                <input type="text" name="descripcion" value="<%=productomodificado.getDes()%>">
            </div>
            <div class="form-group precio">
                <label for="precio">Precio del producto</label>
                <input type="text" name="precio" value="<%=productomodificado.getPre()%>">
            </div>
            <div class="form-group stock">
                <label for="stock">Stock del producto</label>
                <input type="text" name="stock" value="<%=productomodificado.getSto()%>">
            </div>
            <div class="form-group submit-btn">
                <input type="submit" name="Modificar" value="Modificar">
            </div>
            <a class="buttonTabla" href="SvProducto?Op=Nuevo" >Nuevo Producto</a>
        </form>
        <%} else {%>
        <form action="SvProducto" method="post"> 
            <h2>Crear producto</h2>
            <input type="hidden" name="id">
            <div class="form-group descripcion">
                <label for="nombre">Descripcion del producto</label>
                <input type="text" name="descripcion" placeholder="Ingrese el nombre del servicio" required>
            </div>
            <div class="form-group precio">
                <label for="precio">Precio del producto</label>
                <input type="number" name="precio" placeholder="Ingrese el precio del servicio" required>
            </div>
            <div class="form-group stock">
                <label for="stock">Stock del producto</label>
                <input type="number" name="stock" placeholder="Ingrese la contrasena" required>
            </div>
            <div class="form-group submit-btn">
                <input type="submit" name="Crear" value="Crear">
            </div>
        </form>
        <% }%>
    </div>
</div>
<script src="js/jquery-3.7.0.js"></script>
<script src="js/jquery.dataTables.min.js"></script>
<script src="js/HeaderScript.js"></script>
<script src="js/TablaScript.js"></script>

</body>

</html>

