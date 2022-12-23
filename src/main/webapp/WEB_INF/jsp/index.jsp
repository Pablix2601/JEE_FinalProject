<%@include file="jslt.jsp"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML>

<html lang="en">
<head>
    <meta name="verify-v1" content="7+owJw+vJR47pO6wK1RstVpUqOM8JOHN1pTc3qRnZbw=" />
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="/WEB_INF/css/bootstrap.css">
    <link rel="stylesheet" href="/WEB_INF/css/index.css">
    <!-- <link rel="stylesheet" href="../js/bootstrap.js"> -->
    <link rel="stylesheet" href="/WEB_INF/js/bootstrap.bundle.js">
    <!-- <link rel="stylesheet" href="../js/bootstrap.esm.js"> -->

    <title>Acceuil</title>
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
                <form class="d-flex nav-left" role="search">
                    <input class="form-control me-2" type="search" placeholder="Rechercher" aria-label="Search">
                    <button class="btn btn-outline-success" type="submit">OK</button>
                </form>
            </div>
            <div class="right-item">
                <c:if test="${param.Connected == false}">
                    <a class="navbar-brand btn btn-outline-success bg-success text-white" href="/connexion" >
                        Connexion
                    </a>
                </c:if>
                <c:if test="${param.Connected == true}">
                    <a class="navbar-brand btn btn-outline-primary bg-primary text-white" href="/profil" >
                        Mon Profil
                    </a>
                </c:if>
                <a class="navbar-brand" href="/profil">
                    <img src="/WEB_INF/ressources/profil.jpeg" alt="Logo" width="30" height="24" class="d-inline-block align-text-top">
                </a>
            </div>
        </div>
    </nav>
</div>


<c:set var="u" value="${sessionScope.userList}" scope="page" />
<!-- Carousel de présentation -->
<div class="container">
    <!-- catalogue -->
    <table class="table table-bordered table-responsive">
        <thead>
            <tr>
                <th scope="col">Utilisateur</th>
                <th scope="col">Nom</th>
                <th scope="col">Image</th>
                <th scope="col">Description</th>
                <th scope="col">Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="sb" items="${sessionScope.surfBoard}" varStatus="count">
                <tr>
                    <td class="text-sm-center">
                        <a href="/profil/${u[count.index].id}">
                            <c:if test="${u[count.index].image == null}">
                            <span class="bg-primary text-white rounded-circle text-uppercase btn btn-outline-primary align-middle" style="font-size: 150%">
                                <c:out value='${u[count.index].imgEncoded}' />
                            </span>
                            </c:if>
                            <c:if test="${u[count.index].image != null}">
                                <img src="data:image/jpg;base64,${u[count.index].imgEncoded}" alt="avatar" class="rounded-circle img-fluid align-middle" style="width: 60px;">
                            </c:if>
                            <br><c:out value="${u[count.index].username}"/>
                        </a>
                    </td>
                    <td><c:out value="${sb.nom}"/></td>
                    <td><img src="data:image/jpg;base64,${sb.imgEncoded}" class="d-block w-100" alt="No image" width="200" height="170"></td>
                    <td><c:out value="${sb.content}"/></td>
                    <c:if test="${sessionScope.user.id == sb.userId}">
                        <td>
                            <a class="btn btn-outline-primary">Modifier</a>
                            <a href="/deleteSurfBoard/${sb.id}" class="btn btn-outline-danger">Supprimer</a>
                        </td>
                    </c:if>
                    <c:if test="${sessionScope.user.id != sb.userId}">
                        <td> <button class="btn btn-outline-success" type="button" style="display: flex;">Like</button> </td>
                    </c:if>
                </tr>
            </c:forEach>
        </tbody>

    </table>
</div>
</body>
</html>