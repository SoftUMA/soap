<%@page import="entity.User"%>

<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="java.util.List"%>
<%@page import="util.Properties"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    User user;
    Object tmp;
    if ((tmp = request.getSession().getAttribute(Properties.USER_SELECTED)) != null) {
        user = (User) tmp;
    } else {
        session.setAttribute(Properties.USER_SELECTED, user = null);
    }

    if (user == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Agenda - Diario Sur</title>

        <link rel="icon" href="img/brand/favicon.ico"/>
        <link rel="shortcut icon" href="img/brand/favicon.ico" type="image/x-icon"/>
        <link rel="apple-touch-icon" sizes="57x57" href="img/brand/apple-touch-icon-57x57.png">
        <link rel="apple-touch-icon" sizes="60x60" href="img/brand/apple-touch-icon-60x60.png">
        <link rel="apple-touch-icon" sizes="72x72" href="img/brand/apple-touch-icon-72x72.png">
        <link rel="apple-touch-icon" sizes="76x76" href="img/brand/apple-touch-icon-76x76.png">
        <link rel="apple-touch-icon" sizes="114x114" href="img/brand/apple-touch-icon-114x114.png">
        <link rel="apple-touch-icon" sizes="120x120" href="img/brand/apple-touch-icon-120x120.png">
        <link rel="apple-touch-icon" sizes="144x144" href="img/brand/apple-touch-icon-144x144.png">
        <link rel="apple-touch-icon" sizes="152x152" href="img/brand/apple-touch-icon-152x152.png">
        <link rel="apple-touch-icon" sizes="180x180" href="img/brand/apple-touch-icon-180x180.png">
        <link rel="icon" type="image/png" href="img/brand/favicon-16x16.png" sizes="16x16">
        <link rel="icon" type="image/png" href="img/brand/favicon-32x32.png" sizes="32x32">
        <link rel="icon" type="image/png" href="img/brand/favicon-96x96.png" sizes="96x96">
        <link rel="icon" type="image/png" href="img/brand/android-chrome-192x192.png" sizes="192x192">
        <meta name="msapplication-square70x70logo" content="img/brand/smalltile.png"/>
        <meta name="msapplication-square150x150logo" content="img/brand/mediumtile.png"/>
        <meta name="msapplication-wide310x150logo" content="img/brand/widetile.png"/>
        <meta name="msapplication-square310x310logo" content="img/brand/largetile.png"/>

        <link rel="stylesheet" href="css/bootstrap-darkly.min.css">
        <link rel="stylesheet" href="css/bootstrap-slider.min.css">
        <link rel="stylesheet" href="css/material-icons.css">
        <link rel="stylesheet" href="css/animate.css">
        <link rel="stylesheet" href="css/daterangepicker.css">
        <link rel="stylesheet" href="css/app.css">

        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/popper.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/bootstrap-slider.min.js"></script>
        <script src="js/wow.min.js"></script>
        <script src="js/moment.js"></script>
        <script src="js/daterangepicker.js"></script>
    </head>
    <body>
        <!-------- #NAVBAR -------->
        <nav class="navbar navbar-dark bg-warning navbar-expand-lg sticky-top">
            <div class="container">
                <a class="navbar-brand" href="/DiarioSur/">
                    <img src="img/logo.png" height="30" class="d-inline-block align-top" alt="logo">
                    <span class="text-secondary" style="margin-left: 16px;">Agenda</span>
                </a>

                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarContent" aria-controls="navbarContent" aria-expanded="false" aria-label="Expandir navegación">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarContent">
                    <ul class="navbar-nav mr-auto">
                        <li class="nav-item active">
                            <!-- a class="nav-link text-secondary" href="#">Home <span class="sr-only">(current)</span></a -->
                        </li>
                    </ul>
                    <ul class="navbar-nav">
                        <li class="nav-item">
                            <span class="badge badge-pill badge-secondary mt-3 mr-4">
                                <%
                                    if (user == null) {
                                %>
                                <%= Properties.USER_GUEST%>
                                <%
                                    } else {
                                %>
                                <%= user.getEmail()%>
                                <%
                                    }
                                %>
                            </span>
                        </li>
                        <li class="nav-item" style="display: <% if (user != null) { %>block<% } else { %>none<% } %>;" id="session-group">
                            <div class="btn-group">
                                <a class="btn btn-secondary btn-lg" href="profile.jsp">Ver perfil</a>
                                <a class="btn btn-secondary btn-lg" id="signout-button">Logout</a>
                            </div>
                        </li>
                        <li class="nav-item" style="display: <% if (user == null) { %>block<% } else { %>none<% } %>;" id="nosession-group">
                            <a class="btn btn-secondary btn-lg" id="authorize-button">Login</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <!-------- #NAVBAREND -------->
        <!-------- #INFOSTART -------->
        <div class="container mt-4">
            <table class="table table-hover mx-auto" style="width: 50%;">
                <tbody>
                    <tr>
                        <th class="text-right" scope="row">Nombre</th>
                        <td><%= user.getName()%></td>
                    </tr>
                    <tr>
                        <th class="text-right" scope="row">Apellidos</th>
                        <td><%= user.getSurname()%></td>
                    </tr>
                    <tr>
                        <th class="text-right" scope="row">Email</th>
                        <td><%= user.getEmail()%></td>
                    </tr>
                </tbody>
            </table>
            <center>
                <span>
                    <a class="btn btn-warning mt-4" href="index.jsp">Volver a la agenda</a>
                </span>
            </center>
        </div>
        <!-------- #INFOEND -------->

        <script>
            $(document).ready(function () {
                window.history.pushState({
                    location: "profile"
                }, "", "profile.jsp");
            });
        </script>
    </body>
</html>

