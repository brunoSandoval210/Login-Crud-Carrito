<%@page import="Modelo.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@page import="java.util.List"%>
<%@page import="Modelo.Usuario"%>
<%@include file="include/header.jsp" %>
<div class="container">
    <div class="container-table">	
        <div class="titulo">Clientes</div>	
        <table class="table">
            <thead>
                <tr>
                    <th>Id Cliente</th>
                    <th>Nombre</th>
                    <th>Apellido</th>
                    <th>Direccion</th>
                    <th>DNI</th>
                    <th>Telefono</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%  List<Cliente> Lista = (List<Cliente>) request.getAttribute("Lista");
                    if (Lista != null) {

                        for (Cliente cli : Lista) {
                %>
                <tr>
                    <td data-label="id"><%=cli.getId()%></td>
                    <td data-label="nombre"><%=cli.getNombre()%></td>
                    <td data-label="apellidos"><%=cli.getApellido()%></td>
                    <td data-label="direccion"><%=cli.getDireccion()%></td>
                    <td data-label="dni"><%=cli.getDNI()%></td>
                    <td data-label="telefono"><%=cli.getTelefono()%></td>
                    <td>
                        <a class="buttonTabla" href="SvCliente?Op=Modificar&Id=<%=cli.getId()%>" >Modificar</a>
                        <a class="buttonTabla" href="SvCliente?Op=Eliminar&Id=<%=cli.getId()%>" >Eliminar</a>
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
        <%  Cliente clientemodificado = (Cliente) request.getAttribute("clientemodificado");
            if (clientemodificado != null) {%>
        <form action="SvCliente" method="post"> 
            <h2>Modifica Cliente</h2>
            <input type="hidden" name="Id" value="<%=clientemodificado.getId()%>">
            <div class="form-group nombre">
                <label for="Nombre">Nombre</label>
                <input type="text" name="Nombre" value="<%=clientemodificado.getNombre()%>">
            </div>
            <div class="form-group precio">
                <label for="Apellido">Apellido</label>
                <input type="text" name="Apellido" value="<%=clientemodificado.getApellido()%>">
            </div>
            <div class="form-group dechaAlta">
                <label for="Direccion">Direccion</label>
                <input type="text" name="Direccion" value="<%=clientemodificado.getDireccion()%>">
            </div>
            <div class="form-group dechaAlta">
                <label for="Dni">Dni</label>
                <input type="text" name="Dni" value="<%=clientemodificado.getDNI()%>">
            </div>
            <div class="form-group dechaAlta">
                <label for="Telefono">Telefono</label>
                <input type="text" name="Telefono" value="<%=clientemodificado.getTelefono()%>">
            </div>
            <div class="form-group submit-btn">
                <input type="submit" name="Modificar" value="Modificar">
            </div>
            <a class="buttonTabla" href="SvCliente?Op=Nuevo" >Nuevo Cliente</a>
        </form>
        <%} else {%>
        <form action="SvCliente" method="post"> 
            <h2>Crear Cliente</h2>
            <input type="hidden" name="Id">
            <div class="form-group nombre">
                <label for="Nombre">Nombre del cliente</label>
                <input type="text" name="Nombre" placeholder="Ingrese el nombre del cliente" required>
            </div>
            <div class="form-group precio">
                <label for="Apellido">Apellido del cliente</label>
                <input type="text" name="Apellido" placeholder="Ingrese el apellido del cliente" required>
            </div>
            <div class="form-group dechaAlta">
                <label for="Direccion">Direccion del cliente</label>
                <input type="text" name="Direccion" placeholder="Ingrese la direccion del cliente" required maxlength="25">
            </div>
            <div class="form-group dechaAlta">
                <label for="Dni">Dni del cliente</label>
                <input type="text" name="Dni" placeholder="Ingresa el dni del cliente" required maxlength="8">
            </div>
            <div class="form-group dechaAlta">
                <label for="Telefono">Telefono del cliente</label>
                <input type="text" name="Telefono" placeholder="Ingresa el telefono del cliente" required maxlength="9">
            </div>
            <div class="form-group submit-btn">
                <input type="submit" name="crear">
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

