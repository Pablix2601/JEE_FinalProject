<%@include file="jslt.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>Ajouter recette</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>li {padding: 5px ;}
    #ex{
        padding: 5px
    }
    #page{
        background-color:#fff;
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
        padding: 10px;

    }
    textarea {
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
        border-radius: 10px 10px 10px 10px;
        border-color: rgba(229, 103, 23, 0.8);
        width: 100%;}
    textarea:focus{
        box-shadow: 0 1px 1px rgba(229, 103, 23, 0.075) inset, 0 0 8px rgba(229, 103, 23, 0.6);
    }
    </style>

</head>

<body style="background-color:#FFF5F2">

<nav class="navbar navbar-expand navbar-dark flex-column flex-md-row bd-navbar" style="background-color: #B03212;">
    <a class="navbar-brand" th:href="@{/app}"> <h3 style="color:#FFF5F2">Les confinés de canard</h3></a>
    <div class="navbar-nav-scroll">
        <ul class="navbar-nav bd-navbar-nav flex-row">
            <li class="nav-item" th:href="@{/app}" style="color:#FFF5F2"> Tendances </li>
        </ul>
    </div>
    <ul class="navbar-nav flex-row ml-md-auto d-none d-md-flex">
        <li class="nav-item dropdown">
            <a class="btn btn-outline-light dropdown-toggle" data-toggle="dropdown" th:href="@{/profil}" ><span style="text-align: center" th:utext="${user.pseudo}"></span></a>
            <div class="dropdown-menu dropdown-menu-right">
                <a class="dropdown-item" th:href="@{/profil}">Mes recettes</a>
                <a class="dropdown-item" th:href="@{/favorite}">Recettes favorites</a>
                <a class="dropdown-item" th:href="@{/modif}">Modifier utilisateur</a>
                <a class="dropdown-item" th:href="@{/connexion}">Se déconnecter</a>
            </div>
        </li>
    </ul>
</nav>

<div id="ex">
    <div id="page" class="container" >
        <form action="#" th:action="@{/store_recette}" th:object="${recette}" method="post">
            <div style="text-align: center">
                <span> Titre de la recette : </span>
                <input th:field="*{title}"/>
            </div>
            <div style="padding: 5px" >
                <span> Description de la recette : </span>
                <textarea rows="20" th:field="*{content}"> </textarea>
            </div>
            <div style="text-align: right">
                <input class="btn btn-outline-dark" type="submit" value = "Ajoutez cette recette"/>
            </div>
        </form>
        <div th:block th:each="error : ${errors}">
            <div class="alert alert-danger" role="alert">
                <span th:text="${error}"></span>
            </div>
        </div>
    </div>
</div>

</body>
</html>