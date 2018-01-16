<%@page import="service.CategoryService"%>
<%@page import="entity.User"%>
<%@page import="entity.Category"%>
<%@page import="java.util.ArrayList"%>
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

    List<Category> categories = CategoryService.getInstance().findAll();
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
        <!-------- #FORMSTART -------->
        <div class="container my-4">
            <!--TODO FORM HERE-->
            <form action="EventCRUD" id="createForm">
                <input type="hidden" name="<%= Properties.PARAM_OPCODE%>" value="<%= Properties.OP_CREATE%>">
                <input type="hidden" id="imgInput" name="<%= Properties.PARAM_IMAGE%>">
                <div class="form-group">
                    <label for="nameInput">Nombre</label>
                    <input type="text" class="form-control" id="nameInput" placeholder="Nombre" name="<%= Properties.PARAM_NAME%>">
                </div>
                <div class="form-group">
                    <label for="imgTag">Imagen</label>
                    <input type="text" class="form-control" id="imgTag" aria-describedby="imgHelp" placeholder="Tag de la imagen" name="<%= Properties.PARAM_TAG%>">
                    <small id="imgHelp" class="form-text text-muted">Ha de ser una única palabra descriptiva del evento. Evitar caracteres especiales.</small>
                </div>
                <div class="form-group">
                    <label for="addressInput">Dirección</label>
                    <input type="text" class="form-control" id="addressInput" aria-describedby="addressHelp" placeholder="Dirección" name="<%= Properties.PARAM_ADDRESS%>">
                    <small id="addressHelp" class="form-text text-muted">Ej: Bulevar Louis Pasteur, Malaga, Spain.</small>
                </div>
                <div class="form-group">
                    <label for="descInput">Descripción</label>
                    <textarea type="text" class="form-control" id="descInput" placeholder="Descripción" maxlength="1000" name="<%= Properties.PARAM_DESCRIPTION%>"></textarea>
                </div>
                <div class="form-group">
                    <label for="shopInput">URL de compra</label>
                    <input type="url" class="form-control" id="shopInput" aria-describedby="shopHelp" placeholder="URL de compra" name="<%= Properties.PARAM_SHOPURL%>">
                    <small id="shopHelp" class="form-text text-muted">URL de la página de compra de entradas al evento.</small>
                </div>
                <div class="form-group">
                    <label for="dateInput">Fecha y hora</label>
                    <div class="input-group">
                        <input type="text" class="form-control cal" id="dateInput" placeholder="Fecha y hora" name="<%= Properties.PARAM_DATE%>">
                        <span class="input-group-addon" id="calendarTag"><i class="material-icons">date_range</i></span>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-6">
                        <label for="categoryInput">Categoría</label>
                        <select class="form-control" id="categoryInput" name="<%= Properties.PARAM_CATEGORY%>">
                            <option value="<%= Properties.NIL_CATEGORY%>" disabled selected>Categoría</option>
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
                            <input type="number" step="0.05" class="form-control" id="priceInput" aria-describedby="euroTag" placeholder="Precio" name="<%= Properties.PARAM_PRICE%>">
                            <span class="input-group-addon" id="euroTag">€</span>
                        </div>
                    </div>
                </div>
                <hr>
                <center>
                    <span>
                        <a onclick="acceptEvent()" class="btn btn-warning">Enviar evento</a>
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
                $.ajax({
                    type: 'GET',
                    async: false,
                    dataType: 'json',
                    url: 'https://api.giphy.com/v1/gifs/random',
                    data: 'api_key=8oASGfTQFqfT4wHyh4PAgUphj9wYeimr&tag=' + $('#imgTag').val() + '&rating=R',
                    success: function (msg) {
                        console.log(msg);
                        $('#imgInput').val(msg.data.image_url);

                        var msg =
                        <%
                            if (user != null && user.equals(Properties.USER_USER)) {
                        %>
                        '¿Desea enviar este evento a revisión?\nNo será listado hasta que sea aprobado.';
                        <%
                            } else {
                        %>
                        '¿Desea listar este evento en la agenda?';
                        <%
                            }
                        %>

                        if (confirm(msg)) {
                            $('#createForm').submit();
                        }
                    }
                });
            }

            $(document).ready(function () {
                window.history.pushState({
                    location: 'create'
                }, '', 'create.jsp');
            });
        </script>
    </body>
</html>

