<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@page import="java.util.List"%>
<%@page import="Modelo.Usuario"%>
<%@include file="include/header.jsp" %>
<div class="container">
    <div class="container-table">	
        <div class="titulo">Usuarios</div>	
        <table class="table">
            <thead>
                <tr>
                    <th>Id Usuario</th>
                    <th>Nombre</th>
                    <th>Ingreso</th>
                    <th>Contraseña</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%  List<Usuario> Lista = (List<Usuario>) request.getAttribute("Lista");
                    if (Lista != null) {

                        for (Usuario usu : Lista) {
                %>
                <tr>
                    <td data-label="id"><%=usu.getId()%></td>
                    <td data-label="nombre"><%=usu.getNombre()%></td>
                    <td data-label="ingreso"><%=usu.getIngreso()%></td>
                    <td data-label="contrasena"><%=usu.getContrasena()%></td>
                    <td>
                        <a class="buttonTabla" href="SvUsuario?Op=Modificar&Id=<%=usu.getId()%>" >Modificar</a>
                        <a class="buttonTabla" href="SvUsuario?Op=Eliminar&Id=<%=usu.getId()%>" >Eliminar</a>
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
        <%  Usuario usuariomodificado = (Usuario) request.getAttribute("usuariomodificado");
            if (usuariomodificado != null) {%>
        <form action="SvUsuario" method="post"> 
            <h2>Modifica Usuario</h2> 
            <input type="hidden" name="Id" value="<%=usuariomodificado.getId()%>">
            <div class="form-group nombre">
                <label for="nombre">Nombre del usuario</label>
                <input type="text" name="Nombre" value="<%=usuariomodificado.getNombre()%>">
            </div>
            <div class="form-group precio">
                <label for="precio">Usuario para ingresar</label>
                <input type="text" name="Ingreso" value="<%=usuariomodificado.getIngreso()%>">
            </div>
            <div class="form-group dechaAlta">
                <label for="dechaAlta">Contrasena</label>
                <input type="text" name="Contrasena" value="<%=usuariomodificado.getContrasena()%>">
            </div>
            <div class="form-group submit-btn">
                <input type="submit" name="Modificar" value="Modificar">
            </div>
            <a class="buttonTabla" href="SvUsuario?Op=Nuevo" >Nuevo Usuario</a>
        </form>
        <%} else {%>
        <form action="SvUsuario" method="post"> 
            <h2>Crear usuario Usuario</h2>
            <input type="hidden" name="Id">
            <div class="form-group nombre">
                <label for="nombre">Nombre del usuario</label>
                <input type="text" name="Nombre" placeholder="Ingrese el nombre del servicio" required>
            </div>
            <div class="form-group precio">
                <label for="precio">Usuario para ingresar</label>
                <input type="text" name="Ingreso" placeholder="Ingrese el precio del servicio" required>
            </div>
            <div class="form-group dechaAlta">
                <label for="dechaAlta">Contrasena</label>
                <input type="password" name="Contrasena" placeholder="Ingrese la contrasena" required>
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

