<%@include file="jslt.jsp"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML>
<html lang="fr">
<head>
    <meta name="verify-v1" content="7+owJw+vJR47pO6wK1RstVpUqOM8JOHN1pTc3qRnZbw=" />
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="/WEB_INF/css/bootstrap.css">
    <link rel="stylesheet" href="/WEB_INF/css/index.css">
    <!-- <link rel="stylesheet" href="../js/bootstrap.js"> -->
    <link rel="stylesheet" href="/WEB_INF/js/bootstrap.bundle.js">
    <!-- <link rel="stylesheet" href="../js/bootstrap.esm.js"> -->

    <title>Connexion</title>

</head>
<body>
<!-- Nav Bar Acceuil -->
<div class="container-fluid">
    <nav class="navbar bg-light">
        <div class="container-fluid">
            <div class="left-item">
                <a class="navbar-brand btn btn-outline-dark bg-white" href="/">
                    <img src="/WEB_INF/ressources/acceuil.jpeg" alt="Logo" width="30" height="24" class="d-inline-block align-text-top">
                    Acceuil
                </a>
            </div>
            <div class="right-item">
                <a class="navbar-brand btn btn-outline-success bg-success text-white" href="/connexion" >
                    Connexion
                </a>
                <a class="navbar-brand" href="/profil">
                    <img src="/WEB_INF/ressources/profil.jpeg" alt="Logo" width="30" height="24" class="d-inline-block align-text-top">
                </a>
            </div>
        </div>
    </nav>
</div>

<!-- inscription form -->
<div class="container h-70 w-50" name="formConnexion" style="margin-top: 100px; background-color: #f0f0f0;">
    <form action="/register" style=" justify-content: center;" method="post">
        <div class="mb-3">
            <label for="exampleInputEmail1" class="form-label">Adresse Email</label>
            <input type="email" class="form-control" id="exampleInputEmail1" name="email" aria-describedby="emailHelp">
            <div id="emailHelp" class="form-text">Ces informations ne seront pas divulgées</div>
        </div>
        <div class="mb-3">
            <label for="exampleInputPassword1" class="form-label">Password</label>
            <input type="password" class="form-control" id="exampleInputPassword1" name="password">
        </div>
        <div class="mb-3 form-label">
            <label class="form-label" for="exampleCheck1">Prénom</label>
            <input type="text" class="form-control" id="exampleCheck1" name="firstname">
        </div>
        <div class="mb-3 form-label">
            <label class="form-label" for="exampleCheck2">Nom</label>
            <input type="text" class="form-control" id="exampleCheck2" name="lastname">
        </div>

        <div class="mb-3 form-label">
            <label class="form-label" for="exampleCheck3">Nom d'utilisateur</label>
            <input type="text" class="form-control" id="exampleCheck3" name="username">
        </div>

        <button type="submit" class="btn btn-primary" style="display: flex; align-items: center; justify-content: center;">OK</button>
    </form>
    <h6>
        <a href="/connexion">Cliquez ici si vous disposez déjà d'un compte.</a>
    </h6>
</div>


</body>
</html>