<%@page import="ws.Event"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    
    int EVENTOS = 0;
    final String ROL_ATTRIBUTE = "rol";
    final String ROL_INVITADO = "invitado";
    final String ROL_USUARIO = "usuario";
    final String ROL_SUPER_USUARIO = "super_usuario";
    final String ROL_REDACTOR = "redactor";

    String rol = null;
    if ((rol=request.getParameter(ROL_ATTRIBUTE)) != null) {
        
        session.setAttribute(ROL_ATTRIBUTE, rol);
    } else {
        
        if (session.getAttribute(ROL_ATTRIBUTE) == null) {
            rol = ROL_INVITADO;
            session.setAttribute(ROL_ATTRIBUTE, rol);
        } else {
            rol = (String) session.getAttribute(ROL_ATTRIBUTE);
        }
    }

    
	ws.AgendaWS_Service service = new ws.AgendaWS_Service();
	ws.AgendaWS port = service.getAgendaWSPort();
	// TODO process result here
	java.util.List<ws.Event> result = port.findAllevent();
	out.println("Result = "+result);
   
    %>
    <%-- end web service invocation --%><hr/>


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
                        <%if(session.getAttribute(ROL_ATTRIBUTE)!=ROL_INVITADO){%>
                        <a class="nav-link" href="formEvent.jsp">Crear evento <span class="sr-only">(current)</span></a>
                        <%}%>
                    </li>
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Cambiar Usuario
                        </a>
                        <div class="dropdown-menu" aria-labelledby="userDropdown">
                            <div class="dropdown-item btn-group-vertical">
                                <a class="dropdown-item" href="index.jsp?<%= ROL_ATTRIBUTE%>=<%= ROL_INVITADO%>">Invitado</a>
                                <a class="dropdown-item" href="index.jsp?<%= ROL_ATTRIBUTE%>=<%= ROL_USUARIO%>">Usuario</a>
                                <a class="dropdown-item" href="index.jsp?<%= ROL_ATTRIBUTE%>=<%= ROL_SUPER_USUARIO%>">SuperUsuario</a>
                                <a class="dropdown-item" href="index.jsp?<%= ROL_ATTRIBUTE%>=<%= ROL_REDACTOR%>">Redactor</a>                                    
                            </div>

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
                        <%
                            for (Event e: result) {
                                if(e.getApproved() ==1||rol.equals(ROL_SUPER_USUARIO)||rol.equals(ROL_REDACTOR)){
                           %>
                        <div class="card border-dark">
                            <img class="card-img-top" src="http://33.media.tumblr.com/d3ebcd0a28b27595c8056fa28b2d4466/tumblr_nhf7es5vnV1qfhrgko1_500.gif" alt="Imagen de evento">
                            <div class="card-body">
                                <h4 class="card-title">Evento <%= e.getName()%></h4>
                                <p class="card-text"><%= e.getDescription().substring(0,Math.min(e.getDescription().length(), 140)) %>.</p>
                                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#eventoModal<%= e.getId() %>">
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
            </div>
        </div>

        <%
            for (Event e:result) {
                
        %>
        <div class="modal fade" id="eventoModal<%= e.getId() %>" tabindex="-1" role="dialog" aria-labelledby="eventoModal<%= e.getId() %>Label" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title display-4 ml-auto" id="eventoModal<%= e.getId() %>Label">Evento <%= e.getName()%></h1>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span class="display-4" aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-4">
                                <img class="img-fluid" src="http://33.media.tumblr.com/d3ebcd0a28b27595c8056fa28b2d4466/tumblr_nhf7es5vnV1qfhrgko1_500.gif">
                            </div>
                            <div class="col-8">
                                <p><%= e.getDescription() %></p>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <!-- button type="button" class="btn btn-secondary" data-dismiss="modal">Editar evento</button -->
                        <button type="button" class="btn btn-primary">Editar evento</button>
                        <%if (rol.equals(ROL_SUPER_USUARIO)){ %>
                        <button type="button" class="btn btn-primary"><a href="DeleteEvent?id=<%= e.getId() %>">Eliminar </a></button>  
                        <%}%>
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
