<%@include file="jslt.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>Profile</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        li {padding: 5px ;}
        button {text-align: center;}
        input {width : 40% }
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
        #text{
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box;
            width: 100%;
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
                <a class="dropdown-item" th:href="@{/create_recette}">Ajouter une recette</a>
                <a class="dropdown-item" th:href="@{/favorite}">Recettes favorites</a>
                <a class="dropdown-item" th:href="@{/modif}">Modifier utilisateur</a>
                <a class="dropdown-item" th:href="@{/connexion}">Se déconnecter</a>

            </div>
        </li>
    </ul>
</nav>


<div id="ex">
    <div id="page" class="container" >
        <th:block th:each="recette : ${myRecipies}">
            <div style="padding: 5px">
                <div id="recipeBlock" >
                    <div id="show" th:class="${'re' + recette.getId}">
                        <h1 th:text="${recette.getTitle}"></h1>
                        <div class="row">
                            <div class="col-sm-12" ><article><p th:text="${recette.getContent}"></p></article></div>
                        </div>
                        <div style="text-align: right"> <a class="btn btn-outline-dark" th:href="${'/delete_recette/' + recette.getId}">Supprimer</a></div>
                    </div>
                    <div class="col-md-10 blogShort" id="modify" th:class="${'re' + recette.getId}" style="display: none">
                        <form method="post" th:action="${'/modifierRecette/' + recette.getId}">
                            <h1 th:text="${recette.getTitle}"></h1>
                            <article>
                                <input id="text" type="textarea" th:value="${recette.getContent}" name="content"/>
                            </article>
                            <div style="text-align: center">
                                <a class="btn btn-outline-dark" th:href="${'/delete_recette/' + recette.getId}">Supprimer</a>
                                <button class="btn btn-outline-dark"> Valider </button>
                            </div>
                        </form>
                    </div>
                    <div style="text-align: right"> <button class="btn btn-outline-dark" th:attr="onclick=|edit('${recette.getId}')|" > Modifier le contenu </button> </div>
                </div>
            </div>
        </th:block>
    </div>
</div>

<script>
    function edit(id){
        block=document.getElementsByClassName("re"+id)
        for (var i = 0; i < block.length ; i++) {
            if(block[i].style.display=="none"){
                block[i].style.display = "block"
            } else {
                block[i].style.display = "none"
            }
        }
    }
</script>

</body>
</html>