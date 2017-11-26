<%-- 
    Document   : editarPerfil
    Created on : 26-nov-2017, 17:37:28
    Author     : Lorena
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


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
                <form>
                 <div class="form-row">
                   <div class="form-group col-md-6">
                     <label for="inputUser">User</label>
                     <input type="nameUser" class="form-control" id="inputUser" placeholder="User">
                   </div>
                   <div class="form-group col-md-6">
                    <label for="inputNac">Nacimieto</label>
                    <input type="text" class="form-control" id="inputNac" placeholder="Nacimiento">
                   </div>
                 </div>
                 <button type="submit" class="btn btn-primary">Guardar Perfil</button>
               </form>
            </div>    
            <div class="container" style="margin-top: 16px;">
                <form>
                 <div class="form-row">
                   <div class="form-group col-md-6">
                    <label for="inputPassword4">Password</label>
                     <input type="password" class="form-control" id="inputPassword4" placeholder="Password">
                   </div>
                 </div>    
                <div class="form-row">   
                   <div class="form-group col-md-6">
                     <label for="inputNewPassword4">New Password</label>
                     <input type="newPassword" class="form-control" id="inputNewPassword4" placeholder="New Password">
                   </div>
                   <div class="form-group col-md-6">
                     <label for="inputConfirmPassword4">Confirm Password</label>
                     <input type="confirmPassword" class="form-control" id="inputConfirmPassword4" placeholder="Confirm Password">
                   </div>
                </div>
                   <div class="form-group">  
                    <button type="submit" class="btn btn-primary">Guardar Password</button>
                   </div>   
               </form>
            </div>
            
            
        
            <script src="js/jquery-3.2.1.min.js"></script>
            <script src="js/popper.js"></script>
            <script src="js/bootstrap.min.js"></script>
        </body>
    </html>

