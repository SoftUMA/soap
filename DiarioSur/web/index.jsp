<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    final int EVENTOS = 32;
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Agenda Diario Sur</title>
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/app.css">
    </head>
    <body>
        <nav class="navbar navbar-light bg-light navbar-expand-lg">
            <a class="navbar-brand" href="/">
                <img src="img/logo.svg" width="30" height="30" class="d-inline-block align-top" alt="logo">
                <span style="margin-left: 16px;">Agenda Diario Sur</span>
            </a>

            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarContent" aria-controls="navbarContent" aria-expanded="false" aria-label="Expandir navegación">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
                    </li>
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Cambiar Usuario
                        </a>
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

                        <button type="submit" class="btn btn-primary">Filtrar</button>
                    </form>
                </div>

                <div class="col-lg-9">
                    <div class="card-columns">
                        <%                            for (int i = 0; i < EVENTOS; i++) {
                        %>
                        <div class="card border-dark">
                            <img class="card-img-top" src="http://33.media.tumblr.com/d3ebcd0a28b27595c8056fa28b2d4466/tumblr_nhf7es5vnV1qfhrgko1_500.gif" alt="Imagen de evento">
                            <div class="card-body">
                                <h4 class="card-title">Evento <%= i%></h4>
                                <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
                                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#eventoModal<%= i%>">
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
            for (int i = 0; i < EVENTOS; i++) {
        %>
        <div class="modal fade" id="eventoModal<%= i%>" tabindex="-1" role="dialog" aria-labelledby="eventoModal<%= i%>Label" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title display-4 ml-auto" id="eventoModal<%= i%>Label">Evento <%= i%></h1>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span class="display-4" aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <nav class="nav nav-tabs nav-fill" id="myTab" role="tablist">
                            <!-- TODO: add event id -->
                            <a class="nav-item nav-link active" id="nav-info-tab<%= i%>" data-toggle="tab" href="#nav-info<%= i%>" role="tab" aria-controls="nav-info<%= i%>" aria-selected="true">Info</a>
                            <a class="nav-item nav-link" id="nav-edit-tab<%= i%>" data-toggle="tab" href="#nav-edit<%= i%>" role="tab" aria-controls="nav-edit<%= i%>" aria-selected="false">Editar</a>
                        </nav>
                        <div class="tab-content mt-4" id="nav-tabContent">
                            <!-- TODO: add event id -->
                            <div class="tab-pane fade show active" id="nav-info<%= i%>" role="tabpanel" aria-labelledby="nav-info-tab<%= i%>">
                                <div class="row">
                                    <div class="col-4">
                                        <img class="img-fluid" src="http://33.media.tumblr.com/d3ebcd0a28b27595c8056fa28b2d4466/tumblr_nhf7es5vnV1qfhrgko1_500.gif">
                                    </div>
                                    <div class="col-8">
                                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                                    </div>
                                </div>
                                <div class="row mt-4">
                                    <div class="col-12">
                                        <iframe width="100%" height="450" frameborder="0" style="border:0" src="https://www.google.com/maps/embed/v1/place?key=AIzaSyBvwu9R5x0YwukwkoaynDNNKVR2z2RH6p4&q=Calle+Ramirez+de+Madrid,+9,+Malaga,+Spain" allowfullscreen></iframe>
                                    </div>
                                </div>
                            </div>
                            <div class="tab-pane fade" id="nav-edit<%= i%>" role="tabpanel" aria-labelledby="nav-edit-tab<%= i%>">
                                <!-- editor -->
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <!-- button type="button" class="btn btn-secondary" data-dismiss="modal">Editar evento</button -->
                        <button type="button" class="btn btn-primary">Editar evento</button>
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
