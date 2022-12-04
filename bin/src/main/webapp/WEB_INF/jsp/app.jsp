<%@include file="jslt.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>Acceuil</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>li {padding: 5px ;}
    #ex{
        padding: 5px
    }
    #page{
        border-radius: 10px;
        background-color:#fff;
        padding: 5px;
    }
    #recipeBlock{
        padding: 5px;
        border-radius: 10px;
        background-color:#FFF5F2;
    }
    </style>

</head>

<body style="background-color:#FFF5F2" >

<nav class="navbar navbar-expand navbar-dark flex-column flex-md-row bd-navbar" style="background-color: #B03212;">
    <a class="navbar-brand"> <h3 style="color:#FFF5F2">Les confinés de canard</h3></a>
    <div class="navbar-nav-scroll">
        <ul class="navbar-nav bd-navbar-nav flex-row">
            <li class="nav-item" style="color:#FFF5F2"> Tendances </li>
        </ul>
    </div>
    <ul class="navbar-nav flex-row ml-md-auto d-none d-md-flex">
        <li class="nav-item dropdown">
            <a class="btn btn-outline-light dropdown-toggle" data-toggle="dropdown" href=<c:url value="profil.jsp" />>
                <span style="text-align: center" <c:out value="${pageScope.user.pseudo}"/>/>
            </a>
            <div class="dropdown-menu dropdown-menu-right">
                <a class="dropdown-item" href= <c:url value="profil.jsp"/>>Mes recettes</a>
                <a class="dropdown-item" href= <c:url value="newSurfBoard.jsp"/>>Ajouter une recette</a>
                <a class="dropdown-item" href= <c:url value="favorite.jsp"/>>Recettes favorites</a>
                <a class="dropdown-item" href= <c:url value="modif.jsp"/>>Modifier utilisateur</a>
                <a class="dropdown-item" href= <c:url value="connexion.jsp"/>>Se déconnecter</a>
            </div>
        </li>
    </ul>
</nav>


<div id="ex">
    <div id="page" class="container" >
        <c:forEach var="sb" items="${pageScope.surfBoard}" >
            <div style="padding: 5px">
                <div id="recipeBlock" >
                    <h1 style="text-align: center" <c:out value="${sb.getTitle}"/>/>
                    <div class="row">
                        <div class="col-sm-10" >
                            <article>
                                <p <c:out value="${sb.nom}"/> />
                            </article>
                        </div>
                        <!--
                        <div class="col-sm-2" style="text-align: center" >
                            <c:choose>
                                <c:when test="${sb.id}!=${pageScope.likes.surfboard} and ${sb.userId}!=${pageScope.user.id}">
                                    <button class="btn btn-outline-dark" >&#9829;</button>
                                </c:when>
                                <c:when test="${sb.id}==${pageScope.likes.surfboard} and ${sb.userId}==${pageScope.user.id}">
                                    <button class="btn btn-outline-dark" disabled>&#9829;</button>
                                </c:when>
                            </c:choose>

                        </div>
                        -->

                    </div>
                    <div style="text-align: right">
                        <a <c:out value="${'Planche de surf proposée par : ' + sb.userPseudo}"/> />
                    </div>
                </div>
            </div>
        </c:forEach>

        <script>
            function like(id) {
                fetch(
                    "/like",
                    {
                        headers : {
                            "Content-Type": "application/json",
                            "Accept" : "application/json",

                        },
                        method : "post",
                        body : id

                    }
                ).then((data) => {
                    console.log(data)
                }).catch((error) =>{
                    console.log(error)
                })
            }
        </script>
    </div>
</div>



</body>
</html>