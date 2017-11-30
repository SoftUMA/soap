<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.StringTokenizer"%>
<%@page import="java.util.List"%>
<%@page import="util.Properties"%>
<%@page import="ws.*"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

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
                        <li class="nav-item dropdown">
                            <button class="btn btn-secondary nav-link dropdown-toggle px-2" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                Cambiar Usuario
                            </button>
                            <div class="dropdown-menu" aria-labelledby="userDropdown">
                                <a class="dropdown-item" href="index.jsp?<%= Properties.USER_SELECTED%>=<%= Properties.USER_GUEST%>">Invitado</a>
                                <a class="dropdown-item" href="index.jsp?<%= Properties.USER_SELECTED%>=<%= URLEncoder.encode(Properties.USER_USER, "UTF-8")%>">Usuario</a>
                                <a class="dropdown-item" href="index.jsp?<%= Properties.USER_SELECTED%>=<%= URLEncoder.encode(Properties.USER_SUPER, "UTF-8")%>">SuperUsuario</a>
                                <a class="dropdown-item" href="index.jsp?<%= Properties.USER_SELECTED%>=<%= URLEncoder.encode(Properties.USER_EDITOR, "UTF-8")%>">Redactor</a>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <!-------- #NAVBAREND -------->

        <div class="container mt-4">
            <!--TODO FORM HERE-->
            <div class="tab-pane fade" id="nav-create" role="tabpanel" aria-labelledby="nav-create-tab">
                <form action="EventCRUD">
                    <input type="hidden" name="opcode" value="0">
                    <input type="hidden" name="id" value="">
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
                        <label for="descInput<%= e.getId()%>">Descripción</label>
                        <textarea type="text" class="form-control" id="descInput<%= e.getId()%>" placeholder="Descripción" maxlength="1000" name="description"><%= e.getDescription()%></textarea>
                    </div>
                    <div class="form-group">
                        <label for="shopInput<%= e.getId()%>">URL de compra</label>
                        <input type="url" class="form-control" id="shopInput<%= e.getId()%>" aria-describedby="shopHelp<%= e.getId()%>" placeholder="URL de compra" value="<%= e.getShopUrl()%>" name="shopurl">
                        <small id="shopHelp<%= e.getId()%>" class="form-text text-muted">URL de la página de compra de entradas al evento.</small>
                    </div>
                    <div class="form-group">
                        <label for="dateInput<%= e.getId()%>">Fecha y hora</label>
                        <div class="input-group">
                            <%
                                String startDate = e.getStartDate();
                                startDate = startDate.substring(0, startDate.length() - 7);
                                String endDate = e.getEndDate();
                                endDate = endDate.substring(0, endDate.length() - 7);
                                String dates = startDate + " ~ " + endDate;
                                System.out.println(dates);
                            %>
                            <input type="text" class="form-control cal<%= e.getId()%>" id="dateInput<%= e.getId()%>" placeholder="Fecha y hora" value="<%= dates%>" names="date">
                            <span class="input-group-addon" id="calendarTag<%= e.getId()%>"><i class="material-icons">date_range</i></span>
                            <script>
                                $('.cal<%= e.getId()%>').daterangepicker({
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
                                    "startDate": "<%= startDate%>",
                                    "endDate": "<%= endDate%>",
                                    "opens": "left",
                                    "drops": "up",
                                    "applyClass": "btn-warning",
                                    "cancelClass": "btn-secondary"
                                }, function (start, end, label) {
                                    /* callback */
                                });
                            </script>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-6">
                            <label for="categoryInput<%= e.getId()%>">Categoría</label>
                            <select class="form-control" id="categoryInput<%= e.getId()%>" name="category">
                                <%
                                    for (int cat = 0; categories != null && cat < categories.size(); cat++) {
                                %>
                                <option value="<%= categories.get(cat).getName()%>"
                                        <%
                                            if (e.getCategory().getName().equals(categories.get(cat).getName())) {
                                        %>
                                        selected
                                        <%
                                            }
                                        %>
                                        ><%= categories.get(cat).getName()%></option>
                                <%
                                    }
                                %>
                            </select>
                        </div>
                        <div class="form-group col-6">
                            <label for="priceInput<%= e.getId()%>">Precio</label>
                            <div class="input-group">
                                <input type="number" step="0.05" class="form-control" id="priceInput<%= e.getId()%>" aria-describedby="euroTag<%= e.getId()%>" placeholder="Precio" value="<%= e.getPrice()%>" name="price">
                                <span class="input-group-addon" id="euroTag<%= e.getId()%>">€</span>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <!-- button type="submit" class="btn btn-warning">Submit</button -->
                    <center>
                        <span>
                            <a class="btn btn-warning" href="/EventCRUD?opcode=2&id=<%= e.getId()%>">Borrar evento</a>
                            <button type="submit" class="btn btn-warning" href="/EventCRUD?opcode=1&id=<%= e.getId()%>">Guardar cambios</button>
                        </span>
                    </center>
                </form>
            </div>
            <%
                }
            %>
        </div>

        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/popper.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/wow.min.js"></script>
        <script>new WOW().init();</script>
        <script src="js/moment.js"></script>
        <script type="text/javascript">
            function acceptEvent(id) {
                if (confirm("¿Desea listar este evento en la agenda?")) {
                    window.location.replace("");
                }
            }

            function rejectEject(id) {
                if (confirm("¿Desea rechazar este evento definitivamente?")) {
                    window.location.replace("");
                }
            }

            function deleteEvent(id) {
                if (confirm("¿Desea eliminar este evento de la agenda definitivamente?")) {
                    window.location.replace("");
                }
            }
        </script>
    </body>
</html>

