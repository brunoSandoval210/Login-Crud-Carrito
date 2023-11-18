<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true"%>
<!DOCTYPE html>
<html lang="en" dir="ltr">
    <%  response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");  
            if (session.getAttribute("usuario")==null){
                response.sendRedirect("Login.jsp");
            }
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> Administrador </title>
        <link href='https://unpkg.com/boxicons@2.0.7/css/boxicons.min.css' rel='stylesheet'>
        <link rel="stylesheet" href="css/HeaderStyle.css">
        <link rel="stylesheet" href="css/TablaStyle.css">
        <link rel="stylesheet" href="css/Formulario.css">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>

    <body>
        <header>
            <div class="navbar">
                <i class='bx bx-menu'></i>
                <div class="logo"><a href="#">Administracion</a></div>
                <div class="nav-links">
                    <div class="sidebar-logo">
                        <span class="logo-name">Administracion</span>
                        <i class='bx bx-x'></i>
                    </div>
                    <ul class="links">
                        <li>
                            <a href="SvUsuario?Op=Listar">Usuarios</a>
                        </li>
                        <li>
                            <a href="SvCliente?Op=Listar">Clientes</a>
                        </li>
                        <li>
                            <a href="SvProducto?Op=Listar"">Productos</a>
                        </li>
                        <li>
                            <a href="#">Log Out</a>
                            <i class='bx bxs-user logout-arrow arrow '></i>
                            <ul class="logout-sub-menu sub-menu">
                                <li><a href="#">Ver Datos</a></li>
                                <li><a href="Login.jsp?cerrar=true">Cerrar Sesion</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </header>