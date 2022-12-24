<%@include file="jslt.jsp"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML>
<html lang="en">
<head>
    <meta name="verify-v1" content="7+owJw+vJR47pO6wK1RstVpUqOM8JOHN1pTc3qRnZbw=" />
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- <script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script> -->

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
    <nav class="navbar bg-light" >
        <div class="container-fluid" >
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
                <c:if test="${sessionScope.notYourProfil == true}">
                    <c:if test="${sessionScope.connected == false}">
                        <a class="navbar-brand btn btn-outline-success bg-success text-white" href="/connexion" >
                            Connexion
                        </a>
                    </c:if>
                    <c:if test="${sessionScope.connected != false}">
                        <a class="navbar-brand btn btn-outline-primary bg-primary text-white" href="/profil" >
                            Mon Profil
                        </a>
                    </c:if>
                </c:if>
                <c:if test="${sessionScope.notYourProfil != true}">
                    <a class="navbar-brand btn btn-outline-danger bg-danger text-white" href="/deconnexion" >
                        Déconnexion
                    </a>
                </c:if>
                <a class="navbar-brand" href="/profil">
                    <img src="/WEB_INF/ressources/profil.jpeg" alt="Logo" width="30" height="24" class="d-inline-block align-text-top">
                </a>
            </div>
        </div>
    </nav>
</div>
<br>

<!-- Profile  card -->
<c:set var="u" value="${sessionScope.profilUser}" scope="page" />
<c:set var="ul" value="${sessionScope.userList}" scope="page" />
<div class="container py-5">
    <div class="row">
        <div class="col-lg-4">
            <div class="card mb-4" style="background-color:#D3D3D3 ;">
                <div class="card-body text-center">
                    <c:if test="${u.image == null}">
                        <span class="bg-primary text-white rounded-circle text-uppercase btn btn-outline-primary align-middle" style="font-size: 150%">
                            <c:out value='${u.imgEncoded}' />
                        </span>
                    </c:if>
                    <c:if test="${u.image != null}">
                        <img src="data:image/jpg;base64,${u.imgEncoded}" alt="avatar" class="rounded-circle img-fluid align-middle" style="width: 100px;">
                    </c:if>
                    <h5 class="my-3"> <c:out value='${u.nom} ${u.prenom}' /> </h5>
                    <p class="text-muted mb-1"><c:out value='${u.username}' /></p>
                    <div class="d-flex justify-content-center mb-2">
                        <c:if test="${sessionScope.notYourProfil == true}">
                            <button type="button" class="btn btn-outline-primary ms-1">Suivre</button>
                        </c:if>
                        <c:if test="${sessionScope.notYourProfil == false}">
                            <button type="button" class="btn btn-outline-primary ms-1 visually-hidden">Suivre</button>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>


        <!-- Profile card info perso -->

        <div class="col-lg-6">
            <div class="card mb-4">
                <div class="card-body">
                    <div class="row">
                        <div class="col-sm-3">
                            <p class="mb-0">Full Name</p>
                        </div>
                        <div class="col-sm-9">
                            <p class="text-muted mb-0"><c:out value='${u.nom} ${u.prenom}' /></p>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col-sm-3">
                            <p class="mb-0">Username</p>
                        </div>
                        <div class="col-sm-9">
                            <p class="text-muted mb-0"><c:out value='${u.username}' /></p>
                        </div>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col-sm-3">
                            <p class="mb-0">Email</p>
                        </div>
                        <div class="col-sm-9">
                            <p class="text-muted mb-0"><c:out value='${u.email}' /></p>
                        </div>
                    </div>
                </div>
            </div>
            <c:if test="${sessionScope.notYourProfil == true}">
                <button type="button" class="btn btn-success btn-rounded btn-block btn-lg visually-hidden">Modifier profil</button>
            </c:if>
            <c:if test="${sessionScope.notYourProfil == false}">
                <a href="/modifProfil/${sessionScope.user.id}" class="btn btn-success btn-rounded btn-block btn-lg">Modifier profil</a>
            </c:if>
        </div>

        <!-- catalogue -->
        <c:set var="lYSb" value="${sessionScope.likedYourSbList}" scope="page" />
        <c:if test="${sessionScope.noYourSurfBoard == false}">
            <h2 class="title">Vous avez posté aucune planche de surf
                <c:if test="${sessionScope.notYourProfil == false}">
                    <a href="/newSurfBoard" class="btn btn-info btn-sm mb-2" style="float: right;">Ajouter une planche</a>
                </c:if>
            </h2>

        </c:if>
        <c:if test="${sessionScope.noYourSurfBoard == true}">
            <h2 class="title ">Planche de surf posté
                <c:if test="${sessionScope.notYourProfil == false}">
                    <a href="/newSurfBoard" class="btn btn-info btn-sm mb-2" style="float: right;">Ajouter une planche</a>
                </c:if>
            </h2>

            <table class="table table-bordered table-responsive">
                <thead>
                <tr>
                    <th scope="col">Produit</th>
                    <th scope="col">Nom</th>
                    <th scope="col">Image</th>
                    <th scope="col">Description</th>
                    <c:if test="${sessionScope.notYourProfil == true}">
                        <th scope="col">Like <i class='far fa-star'></i></th>
                    </c:if>
                    <c:if test="${sessionScope.notYourProfil == false}">
                        <th scope="col">Action <i class='far fa-star'></i></th>
                    </c:if>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="sb" items="${sessionScope.yourSurfBoard}" varStatus="count">
                    <tr>
                        <th scope="row"><c:out value="${count.count}"/></th>
                        <td><c:out value="${sb.nom}"/></td>
                        <td><img src="data:image/jpg;base64,${sb.imgEncoded}" class="d-block w-100" alt="No image" width="50" height="42"></td>
                        <td><c:out value="${sb.content}"/></td>
                        <c:if test="${sessionScope.notYourProfil == true}">
                            <td>
                                <c:if test="${sessionScope.user == null}">
                                    <a href="/connexion" class="btn btn-outline-success">Like</a>
                                </c:if>
                                <c:if test="${sessionScope.user != null}">
                                    <c:if test="${sessionScope.user != null}">
                                        <c:if test="${lYSb[count.index].client == null}">
                                            <a href="/profil/${u.id}/like/${sb.id}" class="btn btn-outline-success">Like</a>
                                        </c:if>
                                        <c:if test="${lYSb[count.index].client != null}">
                                            <a href="/profil/${u.id}/dislike/${lYSb[count.index].id}" class="btn btn-outline-warning">Dislike</a>
                                        </c:if>
                                    </c:if>
                                </c:if>
                            </td>
                        </c:if>
                        <c:if test="${sessionScope.notYourProfil == false}">
                            <td>
                                <a href="/profil/${sessionScope.user.id}/modifSurfBoard/${sb.id}" class="btn btn-outline-primary">Modifier</a>
                                <a href="/profil/${sessionScope.user.id}/deleteSurfBoard/${sb.id}" class="btn btn-outline-danger">Supprimer</a>
                            </td>
                        </c:if>

                    </tr>
                </c:forEach>
                </tbody>

            </table>
        </c:if>
        <c:set var="lLSb" value="${sessionScope.listLikedSbList}" scope="page" />
        <c:if test="${sessionScope.noLikedSurfBoard == false}">
            <h2 class="title">Vous avez liké aucune planche de surf</h2>
        </c:if>
        <c:if test="${sessionScope.noLikedSurfBoard == true}">
            <h2 class="title">Vos planche de surf liké</h2>
            <table class="table table-bordered table-responsive">
                <thead>
                <tr>
                    <th scope="col">Utilisateur</th>
                    <th scope="col">Nom</th>
                    <th scope="col">Image</th>
                    <th scope="col">Description</th>
                    <th scope="col">Action <i class='far fa-star'></i></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="lsb" items="${sessionScope.likedSurfBoard}" varStatus="count">
                    <tr>
                        <td class="text-sm-center" style="padding-top: 1%">
                            <a href="/profil/${ul[count.index].id}" class="align-self-center">
                                <c:if test="${ul[count.index].image == null}">
                            <span class="bg-primary text-white rounded-circle text-uppercase btn btn-outline-primary align-middle" style="font-size: 150%">
                                <c:out value='${ul[count.index].imgEncoded}' />
                            </span>
                                </c:if>
                                <c:if test="${ul[count.index].image != null}">
                                    <img src="data:image/jpg;base64,${ul[count.index].imgEncoded}" alt="avatar" class="rounded-circle img-fluid align-middle" style="width: 60px;">
                                </c:if>
                                <br><c:out value="${ul[count.index].username}"/>
                            </a>
                        </td>
                        <td><c:out value="${lsb.nom}"/></td>
                        <td><img src="data:image/jpg;base64,${lsb.imgEncoded}" class="d-block w-100" alt="No image" width="50" height="85"></td>
                        <td><c:out value="${lsb.content}"/></td>
                        <td>
                            <c:if test="${sessionScope.user == null}">
                                <a href="/connexion" class="btn btn-outline-success">Like</a>
                            </c:if>
                            <c:if test="${sessionScope.user != null}">
                                <c:if test="${sessionScope.user != null}">
                                    <c:if test="${lLSb[count.index].client == null}">
                                        <a href="/profil/${u.id}/like/${lsb.id}" class="btn btn-outline-success">Like</a>
                                    </c:if>
                                    <c:if test="${lLSb[count.index].client != null}">
                                        <a href="/profil/${u.id}/dislike/${lLSb[count.index].id}" class="btn btn-outline-warning">Dislike</a>
                                    </c:if>
                                </c:if>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>

            </table>
        </c:if>

    </div>
</div>



</body>
</html>