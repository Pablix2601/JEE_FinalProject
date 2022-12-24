<%@include file="jslt.jsp"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
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
                <a class="navbar-brand btn btn-outline-primary bg-primary text-white" href="/profil" >
                    Mon Profil
                </a>
                <a class="navbar-brand" href="/profil">
                    <img src="/WEB_INF/ressources/profil.jpeg" alt="Logo" width="30" height="24" class="d-inline-block align-text-top">
                </a>
            </div>
        </div>
    </nav>
</div>

<!-- connexion form -->
<div class="container h-70 w-50" style="margin-top: 100px; background-color: #f0f0f0;">
    <form action="/modifProfil/${sessionScope.user.id}" style=" justify-content: center;" method="post" enctype="multipart/form-data">
        <div class="mb-3">
            <label for="exampleInputEmail1" class="form-label">Nouvel Email</label>
            <input type="email" class="form-control" id="exampleInputEmail1" name="email" value="${sessionScope.user.email}" aria-describedby="emailHelp">
        </div>
        <div class="mb-3 form-label">
            <label class="form-label" for="exampleCheck1">Nouveau Prénom</label>
            <input type="text" class="form-control" id="exampleCheck1" name="firstname" value="${sessionScope.user.prenom}">
        </div>
        <div class="mb-3 form-label">
            <label class="form-label" for="exampleCheck2">Nouveau Nom</label>
            <input type="text" class="form-control" id="exampleCheck2" value="${sessionScope.user.nom}" name="lastname">
        </div>
        <div class="mb-3 form-label">
            <label class="form-label" for="exampleCheck3">Nouveau Username</label>
            <input type="text" class="form-control" id="exampleCheck3" name="username" value="${sessionScope.user.username}">
        </div>
        <div class="mb-3 form-label">
            <label class="form-label" for="exampleCheck4">Ajouter une photos de profil</label>
            <input type="file" class="form-control" id="exampleCheck4" name="image" value="${sessionScope.user.image}">
        </div>
        <button type="submit" class="btn btn-primary" style="display: flex; align-items: center; justify-content: center;">OK</button>
    </form>
    <h6>
        <a href="/profil">Retour au profil (perte des modifications)</a>
    </h6>
</div>



</body>
</html>