<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="java.util.List"%>
<%@page import="util.Properties"%>
<%@page import="ws.*"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String user = request.getParameter(Properties.USER_SELECTED);
    if (user != null) {
        user = URLDecoder.decode(user, "UTF-8");
        session.setAttribute(Properties.USER_SELECTED, user);
    } else if (session.getAttribute(Properties.USER_SELECTED) == null) {
        session.setAttribute(Properties.USER_SELECTED, user = Properties.USER_GUEST);
    } else {
        user = (String) session.getAttribute(Properties.USER_SELECTED);
    }

    if (user == null || user.equals(Properties.USER_GUEST)) {
        response.sendRedirect("index.jsp");
        return;
    }

    List<Category> categories = null;
    try {
        CategoryWS_Service categoryService = new CategoryWS_Service();
        CategoryWS categoryPort = categoryService.getCategoryWSPort();
        categories = categoryPort.findAllCategories();
    } catch (Exception ex) {
        System.err.println("Error getting categories from service");
        ex.printStackTrace();
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
        <link rel="stylesheet" href="css/material-icons.css">
        <link rel="stylesheet" href="css/animate.css">
        <link rel="stylesheet" href="css/daterangepicker.css">
        <link rel="stylesheet" href="css/app.css">

        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/popper.js"></script>
        <script src="js/bootstrap.min.js"></script>
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
                                    if (user.equals(Properties.USER_GUEST)) {
                                %>
                                <%= user.substring(0, 1).toUpperCase() + user.substring(1)%>
                                <%
                                } else {
                                %>
                                <%= user%>
                                <%
                                    }
                                %>
                            </span>
                        </li>
                        <li class="nav-item">
                            <div class="btn-group">
                                <a class="btn btn-secondary btn-lg" href="profile.jsp">Ver perfil</a>
                                <button type="button" class="btn btn-lg btn-secondary dropdown-toggle dropdown-toggle-split" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <span class="sr-only">Desplegar</span>
                                </button>
                                <div class="dropdown-menu">
                                    <a class="dropdown-item" href="create.jsp?<%= Properties.USER_SELECTED%>=<%= Properties.USER_GUEST%>">Invitado</a>
                                    <a class="dropdown-item" href="create.jsp?<%= Properties.USER_SELECTED%>=<%= URLEncoder.encode(Properties.USER_USER, "UTF-8")%>">Usuario</a>
                                    <a class="dropdown-item" href="create.jsp?<%= Properties.USER_SELECTED%>=<%= URLEncoder.encode(Properties.USER_SUPER, "UTF-8")%>">SuperUsuario</a>
                                    <a class="dropdown-item" href="create.jsp?<%= Properties.USER_SELECTED%>=<%= URLEncoder.encode(Properties.USER_EDITOR, "UTF-8")%>">Redactor</a>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <!-------- #NAVBAREND -------->
        <!-------- #FORMSTART -------->
        <div class="container my-4">
            <!--TODO FORM HERE-->
            <form action="EventCRUD" id="createForm">
                <input type="hidden" name="opcode" value="<%= Properties.OP_CREATE%>">
                <div class="form-group">
                    <label for="nameInput">Nombre</label>
                    <input type="text" class="form-control" id="nameInput" placeholder="Nombre" name="name">
                </div>
                <div class="form-group">
                    <label for="imgInput">Imagen</label>
                    <input type="url" class="form-control" id="imgInput" aria-describedby="imgHelp" placeholder="URL de la imagen" name="image">
                    <small id="imgHelp" class="form-text text-muted">Ha de ser una URL a una imagen PNG o JPG. Preferiblemente de 500x500px.</small>
                </div>
                <div class="form-group">
                    <label for="addressInput">Dirección</label>
                    <input type="text" class="form-control" id="addressInput" aria-describedby="addressHelp" placeholder="Dirección" name="address">
                    <small id="addressHelp" class="form-text text-muted">Ej: Bulevar Louis Pasteur, Malaga, Spain.</small>
                </div>
                <div class="form-group">
                    <label for="descInput">Descripción</label>
                    <textarea type="text" class="form-control" id="descInput" placeholder="Descripción" maxlength="1000" name="description"></textarea>
                </div>
                <div class="form-group">
                    <label for="shopInput">URL de compra</label>
                    <input type="url" class="form-control" id="shopInput" aria-describedby="shopHelp" placeholder="URL de compra" name="shopurl">
                    <small id="shopHelp" class="form-text text-muted">URL de la página de compra de entradas al evento.</small>
                </div>
                <div class="form-group">
                    <label for="dateInput">Fecha y hora</label>
                    <div class="input-group">
                        <input type="text" class="form-control cal" id="dateInput" placeholder="Fecha y hora" name="date">
                        <span class="input-group-addon" id="calendarTag"><i class="material-icons">date_range</i></span>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-6">
                        <label for="categoryInput">Categoría</label>
                        <select class="form-control" id="categoryInput" name="category">
                            <option value="nil" disabled selected>Categoría</option>
                            <%
                                for (int cat = 0; categories != null && cat < categories.size(); cat++) {
                            %>
                            <option value="<%= categories.get(cat).getName()%>"><%= categories.get(cat).getName()%></option>
                            <%
                                }
                            %>
                        </select>
                    </div>
                    <div class="form-group col-6">
                        <label for="priceInput">Precio</label>
                        <div class="input-group">
                            <input type="number" step="0.05" class="form-control" id="priceInput" aria-describedby="euroTag" placeholder="Precio" name="price">
                            <span class="input-group-addon" id="euroTag">€</span>
                        </div>
                    </div>
                </div>
                <hr>
                <center>
                    <span>
                        <button onclick="acceptEvent()" class="btn btn-warning">Enviar evento</button>
                    </span>
                </center>
            </form>
        </div>
        <!-------- #FORMEND -------->
        <script>new WOW().init();</script>
        <script>
            $('.cal').daterangepicker({
                "timePicker": true,
                "timePicker24Hour": true,
                "locale": {
                    "format": "YYYY-MM-DD HH:mm",
                    "separator": " ~ ",
                    "applyLabel": "Aceptar",
                    "cancelLabel": "Cancelar",
                    "fromLabel": "Desde",
                    "toLabel": "Hasta",
                    "customRangeLabel": "Custom",
                    "weekLabel": "W",
                    "daysOfWeek": [
                        "Do",
                        "Lu",
                        "Ma",
                        "Mi",
                        "Ju",
                        "Vi",
                        "Sa"
                    ],
                    "monthNames": [
                        "Enero",
                        "Febrero",
                        "Marzo",
                        "Abril",
                        "Mayo",
                        "Junio",
                        "Julio",
                        "Agosto",
                        "Septiembre",
                        "Octubre",
                        "Noviembre",
                        "Diciembre"
                    ],
                    "firstDay": 1
                },
                "startDate": "2018-01-01 00:00",
                "endDate": "2018-01-07 00:00",
                "opens": "center",
                "drops": "up",
                "applyClass": "btn-warning",
                "cancelClass": "btn-secondary"
            }, function (start, end, label) {
                /* callback */
            });
        </script>
        <script type="text/javascript">
            function acceptEvent() {
                var msg =
                <%
                    if (user != null && user.equals(Properties.USER_USER)) {
                %>
                "¿Desea enviar este evento a revisión?\nNo será listado hasta que sea aprobado.";
                <%
                    } else {
                %>
                "¿Desea listar este evento en la agenda?";
                <%
                    }
                %>

                if (confirm(msg)) {
                    $('#createForm').submit();
                }
            }

            $(document).ready(function () {
                window.history.pushState({
                    location: "create"
                }, "", "create.jsp");
            });
        </script>
    </body>
</html>

