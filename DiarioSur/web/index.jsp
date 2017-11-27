<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="java.util.StringTokenizer"%>
<%@page import="java.util.List"%>
<%@page import="util.Properties"%>
<%@page import="ws.*"%>

<%
    User u = (User) session.getAttribute(Properties.SELECTED_ROLE);
    
    List<Event> events = null;

    try {
        EventWS_Service eventService = new EventWS_Service();
        EventWS eventPort = eventService.getEventWSPort();
        events = eventPort.findAllEvents();
    } catch (Exception ex) {
        System.err.println("Error getting events from service");
        ex.printStackTrace();
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Agenda Diario Sur</title>
        <link rel="stylesheet" href="https://bootswatch.com/4/darkly/bootstrap.min.css">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link rel="stylesheet" href="css/app.css">
    </head>
    <body>
        <nav class="navbar navbar-dark bg-warning navbar-expand-lg sticky-top">
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
                            <a class="dropdown-item" href="/">Invitado</a>
                            <a class="dropdown-item" href="/">Usuario</a>
                            <a class="dropdown-item" href="/">SuperUsuario</a>
                            <a class="dropdown-item" href="/">Redactor</a>
                        </div>
                    </li>
                </ul>
            </div>
        </nav>

        <div class="container" style="margin-top: 16px;">
            <div class="jumbotron">
                <h1 class="display-3">Agendita bonita, xaxi pistaxi</h1>
                <p class="lead">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
            </div>

            <div class="row">
                <div class="col-lg-3 mb-4">
                    <h1>Filtritoh</h1>
                    <form>
                        <button type="submit" class="btn btn-warning">Filtrar</button>
                    </form>
                </div>

                <div class="col-lg-9">
                    <div class="card-columns">
                        <%
                            for (int i = 0; events != null && i < events.size(); i++) {
                                Event e = events.get(i);
                        %>
                        <div class="card border-dark">
                            <img class="card-img-top rounded" src="<%= e.getImage()%>" alt="<%= e.getName()%>" data-toggle="modal" data-target="#eventModal<%= e.getId()%>" style="cursor: pointer;">
                            <div class="card-body">
                                <h4 class="card-title"><%= e.getName()%></h4>
                                <p class="card-text"><%= e.getDescription()%></p>
                                <button type="button" class="btn btn-warning" data-toggle="modal" data-target="#eventModal<%= e.getId()%>">
                                    Ver evento
                                </button>
                            </div>
                        </div>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>
        </div>

        <%
            for (int i = 0; events != null && i < events.size(); i++) {
                Event e = events.get(i);
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
                            <a class="nav-item nav-link" id="nav-edit-tab<%= e.getId()%>" data-toggle="tab" href="#nav-edit<%= e.getId()%>" role="tab" aria-controls="nav-edit<%= e.getId()%>" aria-selected="false">Editar</a>
                        </nav>
                        <div class="tab-content mt-4" id="nav-tabContent">
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
                                                <td><%= e.getStartDate()%></td>
                                            </tr>
                                            <tr>
                                                <th class="text-right" scope="row">Fecha de clausura</th>
                                                <td><%= e.getEndDate()%></td>
                                            </tr>
                                            <tr>
                                                <th class="text-right" scope="row">Precio</th>
                                                <td><%= e.getPrice()%>&euro;</td>
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
                                        <iframe width="100%" height="450" frameborder="0" style="border:0" src="<%= gmaps%>" allowfullscreen></iframe>
                                    </div>
                                </div>
                            </div>
                            <div class="tab-pane fade" id="nav-edit<%= e.getId()%>" role="tabpanel" aria-labelledby="nav-edit-tab<%= e.getId()%>">
                                <!-- editor -->
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <span class="btn-group" role="group" aria-label="Basic example">
                            <button type="button" class="btn btn-warning">Aceptar</button>
                            <button type="button" class="btn btn-warning">Rechazar</button>
                        </span>
                        <!-- button type="button" class="btn btn-secondary" data-dismiss="modal">Editar evento</button -->
                        <button type="button" class="btn btn-warning">asdfasdf</button>
                    </div>
                </div>
            </div>
        </div>
        <%
            }
        %>

        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/popper.js"></script>
        <script src="js/bootstrap.min.js"></script>
    </body>
</html>
