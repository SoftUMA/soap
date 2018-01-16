<%@page import="service.CategoryService"%>
<%@page import="service.EventService"%>
<%@page import="util.Coordinates"%>
<%@page import="entity.User"%>

<%@page import="entity.Category"%>
<%@page import="entity.Event"%> 
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

    List<Event> events =EventService.getInstance().findAll();
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
                            <span class="badge badge-pill badge-secondary mt-3 mr-4" id="user-pill">
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
                        <li class="nav-item mr-1" style="display: <% if (user != null) { %>block<% } else { %>none<% } %>;" id="create-group">
                            <a class="btn btn-secondary btn-lg" href="create.jsp">Crear evento</a>
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

        <div class="container mt-4">
            <!-------- #JUMBOTRON -------->
            <div class="jumbotron py-4">
                <h1 class="display-4">¡Bienvenido!</h1>
                <p class="lead">En esta página podrás descubrir todo tipo de eventos en tu zona.</p>
                <p>Desde conciertos, eventos de gastronomía, tecnología, exposiciones, eSports, deportes, intercambio de idiomas... ¡hasta convenciones de cualquier tipo!</p>
                <!-- button class="btn btn-warning btn-lg" type="button" data-toggle="collapse" data-target="#mapContainer" aria-expanded="false" aria-controls="mapContainer">
                    <i class="material-icons">map</i>
                </button -->
                <!-- div id="mapContainer" class="collapse mt-4" -->
                <div id="map" style="height: 500px; border-radius: 5px;"></div>
                <!-- /div -->
            </div>
            <!-------- #JUMBOTRONEND -------->

            <div class="row">
                <!-------- #FILTERS -------->
                <div class="col-lg-3 mb-4">
                    <h1>Buscador</h1>
                    <div class="form-group">
                        <!-- label for="filterKeywords"></label -->
                        <input type="text" class="form-control" id="filterKeywords" placeholder="Buscar..." name="<%= Properties.PARAM_KEYWORDS%>">
                    </div>
                    <p id="filterRadiusLabel" class="badge badge-secondary mb-2">Radio: <span id="filterRadiusValue"><%= (int) Properties.DEFAULT_RADIUS%> Km</span></p>
                    <div class="ml-2 my-2">
                        <input id="filterRadius" data-slider-id="filterRadiusSlider" type="text" data-slider-min="<%= Properties.MINIMUM_RADIUS%>" data-slider-max="<%= Properties.MAXIMUM_RADIUS%>" data-slider-step="<%= Properties.RADIUS_STEP%>" data-slider-value="<%= Properties.DEFAULT_RADIUS%>"/>
                    </div>
                    <div class="form-group">
                        <!-- label for="filterCategory">Categoría</label -->
                        <select class="form-control" id="filterCategory" name="<%= Properties.PARAM_CATEGORY%>">
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
                    <div class="form-group">
                        <label class="custom-control custom-checkbox">
                            <input type="checkbox" class="custom-control-input" id="filterFree" name="<%= Properties.PARAM_FREE%>">
                            <span class="custom-control-indicator"></span>
                            <span class="custom-control-description">Gratuitos</span>
                        </label>
                    </div>
                    <%
                        if (user != null) {
                    %>
                    <div class="form-group">
                        <label class="custom-control custom-checkbox">
                            <input type="checkbox" class="custom-control-input" id="filterOwn" name="<%= Properties.PARAM_OWN%>">
                            <span class="custom-control-indicator"></span>
                            <span class="custom-control-description">Mis eventos</span>
                        </label>
                    </div>
                    <%
                        }
                    %>
                    <button class="btn btn-warning" onclick="filterEvents()">Filtrar</button>
                </div>
                <!-------- #FILTERSEND -------->

                <!-------- #CARDS -------->
                <div class="col-lg-9">
                    <div class="card-columns">
                        <%
                            for (int i = 0; events != null && i < events.size(); i++) {
                                Event e = events.get(i);
                                System.out.println(i);   
                                System.out.println(e);
                                System.out.println(e.getAddress());
								if(e.getAuthor()==null){
	                                System.out.println("wtf");
								}
                                if (e.getAuthor().equals(user) || e.getApproved().equals("1") || (user != null && user.getRole().equals(Properties.ROLE_EDITOR))) {
                        %>
                        <div class="card
                             <%
                                 if (e.getApproved().equals("0") && (e.getAuthor().equals(user) || (user != null && user.getRole().equals(Properties.ROLE_EDITOR)))) {
                             %>
                             border-danger
                             <%
                             } else {
                             %>
                             border-dark
                             <%
                                 }
                             %>
                             wow zoomIn" data-wow-delay="0.5s">
                            <img class="card-img-top rounded" src="<%= e.getImage()%>" alt="<%= e.getName()%>" data-toggle="modal" data-target="#eventModal<%= e.getId()%>" style="cursor: pointer;">
                            <div class="card-body">
                                <h4 class="card-title"><%= e.getName()%></h4>
                                <%
                                    if (e.getApproved().equals("0") && (e.getAuthor().equals(user) || (user != null && user.getRole().equals(Properties.ROLE_EDITOR)))) {
                                %>
                                <p class="card-text text-danger">Revisión pendiente</p>
                                <%
                                    }
                                %>
                                <p class="card-text">
                                    <%
                                        if (e.getDescription().length() < 80) {
                                    %>
                                    <%= e.getDescription()%>
                                    <%
                                    } else {
                                    %>
                                    <%= e.getDescription().substring(0, 80) + "..."%>
                                    <%
                                        }
                                    %>
                                </p>
                                <button type="button" class="btn btn-warning" data-toggle="modal" data-target="#eventModal<%= e.getId()%>">
                                    Ver evento
                                </button>
                            </div>
                        </div>
                        <%
                                }
                            }
                        %>
                    </div>
                </div>
                <!-------- #CARDSEND -------->
            </div>
        </div>

        <!-------- #MODALS -------->
        <%
            for (int i = 0; events != null && i < events.size(); i++) {
                Event e = events.get(i);
                if (e.getAuthor().equals(user) || e.getApproved().equals("1") || (user != null && user.getRole().equals(Properties.ROLE_EDITOR))) {
        %>
        <div class="modal fade" id="eventModal<%= e.getId()%>" tabindex="-1" role="dialog" aria-labelledby="eventModal<%= e.getId()%>Label" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title display-4 ml-auto" id="eventModal<%= e.getId()%>Label"><%= e.getName()%></h1>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span class="display-4" aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <nav class="nav nav-tabs nav-fill" id="eventModalTab<%= e.getId()%>" role="tablist">
                            <a class="nav-item nav-link active" id="nav-info-tab<%= e.getId()%>" data-toggle="tab" href="#nav-info<%= e.getId()%>" role="tab" aria-controls="nav-info<%= e.getId()%>" aria-selected="true">Info</a>
                            <%
                                if (e.getAuthor().equals(user) || (user != null && user.getRole().equals(Properties.ROLE_EDITOR))) {
                            %>
                            <a class="nav-item nav-link" id="nav-edit-tab<%= e.getId()%>" data-toggle="tab" href="#nav-edit<%= e.getId()%>" role="tab" aria-controls="nav-edit<%= e.getId()%>" aria-selected="false">Editar</a>
                            <%
                                }
                            %>
                        </nav>
                        <div class="tab-content mt-4" id="nav-tabContent<%= e.getId()%>">
                            <!-------- #MODALINFO -------->
                            <div class="tab-pane fade show active" id="nav-info<%= e.getId()%>" role="tabpanel" aria-labelledby="nav-info-tab<%= e.getId()%>">
                                <div class="row">
                                    <div class="col-4">
                                        <img class="img-fluid rounded" src="<%= e.getImage()%>">
                                    </div>
                                    <div class="col-8">
                                        <p><%= e.getDescription()%></p>
                                    </div>
                                </div>
                                <div class="row mt-4">
                                    <table class="table table-hover">
                                        <tbody>
                                            <tr>
                                                <th class="text-right" scope="row">Autor</th>
                                                <td><%= e.getAuthor().getEmail()%></td>
                                            </tr>
                                            <tr>
                                                <th class="text-right" scope="row">Fecha de inicio</th>
                                                <td><%= e.getStartDate().substring(0, e.getStartDate().length() - 7)%></td>
                                            </tr>
                                            <tr>
                                                <th class="text-right" scope="row">Fecha de clausura</th>
                                                <td><%= e.getEndDate().substring(0, e.getStartDate().length() - 7)%></td>
                                            </tr>
                                            <tr>
                                                <th class="text-right" scope="row">Precio</th>
                                                <td><%= e.getPrice()%>&euro;</td>
                                            </tr>
                                            <tr>
                                                <th class="text-right" scope="row">Categoría</th>
                                                <td><%= e.getCategory().getName()%></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="row mt-4">
                                    <div class="col-12">
                                        <%
                                            StringTokenizer st = new StringTokenizer(e.getAddress());
                                            String gmaps = "https://www.google.com/maps/embed/v1/place?key=AIzaSyBvwu9R5x0YwukwkoaynDNNKVR2z2RH6p4&q=";

                                            while (st.hasMoreTokens()) {
                                                gmaps += st.nextToken();
                                                if (st.hasMoreTokens())
                                                    gmaps += "+";
                                            }
                                        %>
                                        <iframe width="100%" height="450" frameborder="0" style="border: 0;" src="<%= gmaps%>" allowfullscreen></iframe>
                                    </div>
                                </div>
                                <hr>
                                <center>
                                    <%
                                        if (e.getApproved().equals("0") && (user != null && user.getRole().equals(Properties.ROLE_EDITOR))) {
                                    %>
                                    <span class="btn-group mr-4" role="group" aria-label="Basic example">
                                        <button class="btn btn-warning" onclick="acceptEvent(<%= e.getId()%>)">Aceptar</button>
                                        <button class="btn btn-warning" onclick="rejectEvent(<%= e.getId()%>)">Rechazar</button>
                                    </span>
                                    <%
                                        }
                                    %>
                                    <a class="btn btn-warning" href="<%= e.getShopUrl()%>" target="_blank">Comprar entradas</a>
                                </center>
                            </div>
                            <!-------- #MODALINFOEND -------->
                            <!-------- #MODALEDIT -------->
                            <%
                                if (e.getAuthor().equals(user) || (user != null && user.getRole().equals(Properties.ROLE_EDITOR))) {
                            %>
                            <div class="tab-pane fade" id="nav-edit<%= e.getId()%>" role="tabpanel" aria-labelledby="nav-edit-tab<%= e.getId()%>">
                                <form action="EventCRUD" id="editForm<%= e.getId()%>">
                                    <input type="hidden" name="<%= Properties.PARAM_OPCODE%>" value="<%= Properties.OP_EDIT%>">
                                    <input type="hidden" name="<%= Properties.PARAM_ID%>" value="<%= e.getId()%>">
                                    <input type="hidden" id="imgInput<%= e.getId()%>" name="<%= Properties.PARAM_IMAGE%>">
                                    <div class="form-group">
                                        <label for="nameInput<%= e.getId()%>">Nombre</label>
                                        <input type="text" class="form-control" id="nameInput<%= e.getId()%>" placeholder="Nombre" value="<%= e.getName()%>" name="<%= Properties.PARAM_NAME%>">
                                    </div>
                                    <div class="form-group">
                                        <label for="imgTag<%= e.getId()%>">Cambiar imagen</label>
                                        <input type="text" class="form-control" id="imgTag<%= e.getId()%>" aria-describedby="imgHelp<%= e.getId()%>" placeholder="Tag de la imagen" name="<%= Properties.PARAM_TAG%>">
                                        <small id="imgHelp<%= e.getId()%>" class="form-text text-muted">Ha de ser una única palabra descriptiva del evento. Evitar caracteres especiales. Dejar vacío para mantener la imagen actual.</small>
                                    </div>
                                    <div class="form-group">
                                        <label for="addressInput<%= e.getId()%>">Dirección</label>
                                        <input type="text" class="form-control" id="addressInput<%= e.getId()%>" aria-describedby="addressHelp<%= e.getId()%>" placeholder="Dirección" value="<%= e.getAddress()%>" name="<%= Properties.PARAM_ADDRESS%>">
                                        <small id="addressHelp<%= e.getId()%>" class="form-text text-muted">Ej: Bulevar Louis Pasteur, Malaga, Spain.</small>
                                    </div>
                                    <div class="form-group">
                                        <label for="descInput<%= e.getId()%>">Descripción</label>
                                        <textarea type="text" class="form-control" id="descInput<%= e.getId()%>" placeholder="Descripción" maxlength="1000" name="<%= Properties.PARAM_DESCRIPTION%>"><%= e.getDescription()%></textarea>
                                    </div>
                                    <div class="form-group">
                                        <label for="shopInput<%= e.getId()%>">URL de compra</label>
                                        <input type="url" class="form-control" id="shopInput<%= e.getId()%>" aria-describedby="shopHelp<%= e.getId()%>" placeholder="URL de compra" value="<%= e.getShopUrl()%>" name="<%= Properties.PARAM_SHOPURL%>">
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
                                            %>
                                            <input type="text" class="form-control cal<%= e.getId()%>" id="dateInput<%= e.getId()%>" placeholder="Fecha y hora" value="<%= dates%>" name="<%= Properties.PARAM_DATE%>">
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
                                            <select class="form-control" id="categoryInput<%= e.getId()%>" name="<%= Properties.PARAM_CATEGORY%>">
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
                                                <input type="number" step="0.05" class="form-control" id="priceInput<%= e.getId()%>" aria-describedby="euroTag<%= e.getId()%>" placeholder="Precio" value="<%= e.getPrice()%>" name="<%= Properties.PARAM_PRICE%>">
                                                <span class="input-group-addon" id="euroTag<%= e.getId()%>">€</span>
                                            </div>
                                        </div>
                                    </div>
                                    <hr>
                                    <center>
                                        <span>
                                            <a class="btn btn-warning" onclick="deleteEvent(<%= e.getId()%>)">Borrar evento</a>
                                            <a class="btn btn-warning" onclick="editEvent(<%= e.getId()%>)">Guardar cambios</a>
                                        </span>
                                    </center>
                                </form>
                            </div>
                            <%
                                }
                            %>
                            <!-------- #MODALEDITEND -------->
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%
                }
            }
        %>
        <!-------- #MODALSEND -------->

        <script>new WOW().init();</script>
        <script>
            var userLocation = {
                lat: <%= Properties.DEFAULT_LATITUDE%>,
                lng: <%= Properties.DEFAULT_LONGITUDE%>
            };

            if ("geolocation" in navigator) {
                navigator.permissions.query({
                    name: 'geolocation'
                }).then(function (result) {
                    if (result.state === 'prompt') {
                        alert('Para poder usar la característica de filtrado de eventos en base a la distancia, necesitamos que nos des permiso para acceder a tu ubicación.\n\nSi estás de acuerdo con ello, acepta el diálogo que aparecerá después de éste.');
                    }
                });

                navigator.geolocation.getCurrentPosition(function (position) {
                    userLocation = {
                        lat: position.coords.latitude,
                        lng: position.coords.longitude
                    };
                });
            } else {
                alert('Tu navegador no soporta geolocalización.\n\nAlgunas características de esta aplicación, como el filtrado de los eventos en base a la distancia, no estará disponible.');
            }
        </script>
        <script type="text/javascript">
            <%
                if (user != null && user.getRole().equals(Properties.ROLE_EDITOR)) {
            %>
            function acceptEvent(id) {
                if (confirm('¿Desea listar este evento en la agenda?')) {
                    window.location.replace('EventCRUD?opcode=<%= Properties.OP_APPROVE%>&id=' + id);
                }
            }

            function rejectEvent(id) {
                if (confirm('¿Desea rechazar este evento definitivamente?')) {
                    window.location.replace('EventCRUD?opcode=<%= Properties.OP_DELETE%>&id=' + id);
                }
            }
            <%
                }
            %>

            <%
                if (user != null) {
            %>
            function deleteEvent(id) {
                if (confirm('¿Desea eliminar este evento de la agenda definitivamente?')) {
                    window.location.replace('EventCRUD?opcode=<%= Properties.OP_DELETE%>&id=' + id);
                }
            }

            function editEvent(id) {
                $.ajax({
                    type: 'GET',
                    async: false,
                    dataType: 'json',
                    url: 'https://api.giphy.com/v1/gifs/random',
                    data: 'api_key=8oASGfTQFqfT4wHyh4PAgUphj9wYeimr&tag=' + $('#imgTag' + id).val() + '&rating=R',
                    success: function (msg) {
                        $('#imgInput' + id).val(msg.data.image_url);
                        if (confirm('¿Desea guardar los cambios efectuados?')) {
                            $('#editForm' + id).submit();
                        }
                    }
                });
            }
            <%
                }
            %>

            function filterEvents() {
                $.ajax({
                    type: 'post',
                    url: 'EventCRUD',
                    data: {
                        <%= Properties.PARAM_OPCODE%>: <%= Properties.OP_FILTER%>,
                        <%= Properties.PARAM_LATITUDE%>: userLocation.lat,
                        <%= Properties.PARAM_LONGITUDE%>: userLocation.lng,
                        <%= Properties.PARAM_RADIUS%>: $('#filterRadius').val(),
                        <%= Properties.PARAM_KEYWORDS%>: $('#filterKeywords').val(),
                        <%= Properties.PARAM_CATEGORY%>: $('#filterCategory').val(),
                        <%= Properties.PARAM_FREE%>: $('#filterFree').is(':checked') ? '1' : '0',
                        <%= Properties.PARAM_OWN%>: $('#filterOwn').is(':checked') ? '1' : '0'
                    },
                    success: function (msg) {
                        var response = JSON.parse(msg);
                        removeMarkers();

                        mapCenter = userLocation;
                        map.panTo(mapCenter);
                        setCircle(mapCenter, parseFloat($('#filterRadius').val()) * 1000);
                        showCircle();

                        response.map.forEach(function (event) {
                            addMarker(event.title, event.desc, event.own, {
                                lat: event.lat,
                                lng: event.lng
                            });
                        });

                        $('.card-columns').empty();
                        $('.card-columns').append(response.cards);
                    }
                });
            }
        </script>
        <script>
            $('#filterRadius').slider({
                tooltip: 'show',
                ticks: [1, 50, 100],
                ticks_labels: ['1', '50', '100'],
                ticks_snap_bounds: 5,
                formatter: function(value) {
                    return 'Radio: ' + value;
                }
            });

            $('#filterRadius').on('slide', function(event) {
                $('#filterRadiusValue').text(event.value + ' Km');
            });
        </script>
        <script>
            var map;
            var mapCenter;
            var markers = [];
            var circle;

            function initMap() {
                mapCenter = {
                    lat: <%= Properties.DEFAULT_LATITUDE%>,
                    lng: <%= Properties.DEFAULT_LONGITUDE%>
                };

                map = new google.maps.Map($("#map")[0], {
                    center: mapCenter,
                    zoom: 12,
                    styles: [
                        {elementType: 'geometry', stylers: [{color: '#242f3e'}]},
                        {elementType: 'labels.text.stroke', stylers: [{color: '#242f3e'}]},
                        {elementType: 'labels.text.fill', stylers: [{color: '#746855'}]},
                        {
                            featureType: 'administrative.locality',
                            elementType: 'labels.text.fill',
                            stylers: [{color: '#d59563'}]
                        },{
                            featureType: 'poi',
                            elementType: 'labels.text.fill',
                            stylers: [{color: '#d59563'}]
                        },{
                            featureType: 'poi.park',
                            elementType: 'geometry',
                            stylers: [{color: '#263c3f'}]
                        },{
                            featureType: 'poi.park',
                            elementType: 'labels.text.fill',
                            stylers: [{color: '#6b9a76'}]
                        },{
                            featureType: 'road',
                            elementType: 'geometry',
                            stylers: [{color: '#38414e'}]
                        },{
                            featureType: 'road',
                            elementType: 'geometry.stroke',
                            stylers: [{color: '#212a37'}]
                        },{
                            featureType: 'road',
                            elementType: 'labels.text.fill',
                            stylers: [{color: '#9ca5b3'}]
                        },{
                            featureType: 'road.highway',
                            elementType: 'geometry',
                            stylers: [{color: '#746855'}]
                        },{
                            featureType: 'road.highway',
                            elementType: 'geometry.stroke',
                            stylers: [{color: '#1f2835'}]
                        },{
                            featureType: 'road.highway',
                            elementType: 'labels.text.fill',
                            stylers: [{color: '#f3d19c'}]
                        },{
                            featureType: 'transit',
                            elementType: 'geometry',
                            stylers: [{color: '#2f3948'}]
                        },{
                            featureType: 'transit.station',
                            elementType: 'labels.text.fill',
                            stylers: [{color: '#d59563'}]
                        },{
                            featureType: 'water',
                            elementType: 'geometry',
                            stylers: [{color: '#17263c'}]
                        },{
                            featureType: 'water',
                            elementType: 'labels.text.fill',
                            stylers: [{color: '#515c6d'}]
                        },{
                            featureType: 'water',
                            elementType: 'labels.text.stroke',
                            stylers: [{color: '#17263c'}]
                        }
                    ]
                });

                circle = new google.maps.Circle({
                    strokeColor: '#F39C12',
                    strokeOpacity: 0.8,
                    strokeWeight: 2,
                    fillColor: '#F39C12',
                    fillOpacity: 0.15,
                    map: null,
                    center: mapCenter,
                    radius: parseFloat($('#filterRadius').val()) * 1000
                });
            }

            function setCircle(center, radius) {
                circle.setCenter(center);
                circle.setRadius(radius);
            }

            function showCircle() {
                circle.setMap(map);
            }

            function hideCircle() {
                circle.setMap(null);
            }

            function addMarker(title, desc, own, location) {
                var tmp = {
                    marker: new google.maps.Marker({
                        title: title,
                        position: location,
                        map: map,
                        animation: google.maps.Animation.DROP
                    }),
                    infoWindow: new google.maps.InfoWindow({
                        content: '<div style="color: #000;"><h6>' + title + '</h6><p>' + desc + '</p></div>'
                    })
                };

                tmp.marker.addListener('click', function() {
                    tmp.infoWindow.open(map, tmp.marker);
                });

                markers.push(tmp);
            }

            function showMarkers() {
                for (var i = 0; i < markers.length; i++)
                    markers[i].marker.setMap(map);
            }

            function hideMarkers() {
                for (var i = 0; i < markers.length; i++)
                    markers[i].marker.setMap(null);
            }

            function removeMarkers() {
                hideMarkers();
                markers = [];
            }
        </script>
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBvwu9R5x0YwukwkoaynDNNKVR2z2RH6p4&callback=initMap"></script>
        <script>
            $(document).ready(function () {
                window.history.pushState({
                    location: 'index'
                }, '', 'index.jsp');

                // TODO: place 'own' properly...
                <%
                    for (Event e : events) {
                        Coordinates coords = new Coordinates(e.getAddress().replaceAll("(\\s+)", "+"));
                %>
                addMarker("<%= e.getName()%>", "<%= e.getDescription()%>", false, {
                    lat: <%= coords.getLatitude()%>,
                    lng: <%= coords.getLongitude()%>
                });
                <%
                    }
                %>
            });
        </script>
        <script src="js/oauth.js"></script>
        <script async defer src="js/google-api.js" onload="this.onload=function(){};handleClientLoad()" onreadystatechange="if (this.readyState === 'complete') this.onload()"></script>
    </body>
</html>
