<%@include file="jslt.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>Connexion</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <style>
        .id{
            background-color: white;
            position: absolute;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
            padding: 10px;
            border-radius: 10px;
        }
        tr {
            border-top: none !important;
        }
        .button0{
            text-align: center;
            padding: 5px;
        }

    </style>
</head>

<body style="background-color:#FFF5F2">

<nav class="navbar navbar-light" style="background-color: #B03212;">
    <a class="navbar-brand" href="#"> <h3 style="color:#FFF5F2">Les confinés de canard</h3></a>
    <ul class="nav justify-content-end">
        <div id="ins2"  style="display:block">
            <button type="button" class="btn btn-outline-light" onclick="insToCon()">Se connecter</button>
        </div>
        <div  id="con2"  style="display:none">
            <button type="button" class="btn btn-outline-light" onclick="conToIns()">S'inscrire</button>
        </div>
    </ul>
</nav>


<div class="id" >
    <div id="ins"  style="display:block">
        <h1 style="text-align: center">Inscription</h1>
        <form action="/connexion" method="post">
            <table>
                <label for="username">Nom Utilisateur :</label><br>
                <input type="text" name="username" value="" id="username" required/><br>

                <label for="pass">Mot de passe :  </label><br><br>
                <input type="password" name="password" value="" id="pass" required/><br>
            </table>
            <p class="button0">
                <input class="btn btn-outline-dark" type="submit" value="Se connecter" />
                <input class="btn btn-outline-dark" type="reset" value="Annuler" />
            </p>
        </form>
    </div>
    <div>
        <div class="alert alert-danger" role="alert">
            <span></span>
        </div>
    </div>

</div>

</body>
</html>