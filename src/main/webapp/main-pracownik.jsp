
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.*" %>
<html>
  <head>
    <link rel="shortcut icon" href="favicon.ico" />

    <meta charset="utf-8">
    <title>Urlop</title>
<link rel="stylesheet" href="index.css" >

  </head>
  <body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">Urlopik.pl</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarColor02" aria-controls="navbarColor02" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarColor02">
      <ul class="navbar-nav mr-auto">
        <li class="nav-item active">
          <a class="nav-link" href="../../../cssy/urlop_projekt/plan-pracownik.html#planpracownik">Planowanie Urlopu
            <span class="sr-only">(current)</span>
          </a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="../../../cssy/urlop_projekt/mod-pracownik.html#modpracownik">Historia urlopów</a>
        </li>
      </ul>
    </div>

  </nav>
    <h1></h1>



  </p>

    <p class="image"><div style="text-align: center"><img src="images/resort.png" alt="resort" width="150" height="150" position="absolute" top="80px"></div></p>



<br/>

    <p>${nazwisko}</p>

<p >

  <c:url var="dodajUrlop" value="PracownikServlet">
    <c:param name="command" value="ADDURLOP"></c:param>
  </c:url>

  <c:url var="modLink" value="PracownikServlet">
    <c:param name="command" value="LIST"></c:param>
    <c:param name="stan" value="${null}"></c:param>
    <c:param name="zaakceptowane" value="true"></c:param>
  </c:url>

<a href="${dodajUrlop}" class="btn btn-primary" role="button"> zaplanuj urlop</a></button> <br/><br/>
  <c:url var="mainLink" value="PracownikServlet">
    <c:param name="command" value="MAIN"></c:param>
  </c:url>
<a href="${modLink}" class="btn btn-primary" role="button"> historia urlopów</a></button>

</p>
<br/>
<br/>
<br/>
<br/>
<a href="index.html#index" class="btn btn-primary" role="button"> wyloguj się</a></button> <br/><br/>

  </body>
</html>
