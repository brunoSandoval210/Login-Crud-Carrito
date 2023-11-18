
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<%@page import="java.util.List"%>
<%@page import="Modelo.Usuario"%>
<!DOCTYPE html>
<html>
    <%  response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");  
            if (session.getAttribute("usuario")==null){
                response.sendRedirect("Login.jsp");
            }
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    </head>

    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container">
                <a class="navbar-brand" href="index.jsp">Menú Principal</a>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <a class="nav-link" href="Clientes.jsp">Clientes</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="Usuarios.jsp">Usuarios</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="Productos.jsp">Productos</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="Pedidos.jsp">Pedidos</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="Login.jsp?cerrar=true">Cerrar Sesión</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <div class="container mt-4">
            <h1>Datos del Usuario</h1>
            <table class="table table-bordered">
                <%  Usuario Lista = (Usuario) request.getAttribute("Lista");
                %>

                <tr>
                    <td>Id Usuario</td>
                    <td><%=Lista.getId()%></td>
                </tr>
                <tr>
                    <td>Nombre</td>
                    <td><%=Lista.getNombre()%></td>
                </tr>
                <tr>
                    <td>Ingreso</td>
                    <td><%=Lista.getIngreso()%></td>
                </tr>     
                <tr>
                    <td>Contrasena</td>
                    <td><%=Lista.getContrasena()%></td>
                </tr>          
                <%

                %>
            </table>
            <a class="btn btn-primary" href="SvUsuario?Op=Listar">Retroceder</a>
        </div>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>
</html>
