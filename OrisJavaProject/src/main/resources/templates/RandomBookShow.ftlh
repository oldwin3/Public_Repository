<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Random Book</title>
    <style>
        <#include "css/style.css">
        <#include "css/style2.css">
    </style>
</head>
<body>

<div class="content-area">

    <h1>Random Book For You</h1>
    <h2>Return to Home page:</h2>

    <div class="nav-links">
        <a href="/home">Home</a>
    </div>
    <h1> </h1>
    <form method="post" action="/test">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button type="submit">Another Random Test from our partners</button>
    </form>
</div>

<div class="content-area">
    <div class="form-style-3">

        Book name: ${book}
        <#if !bookSaved>
            <form method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit">Save Book</button>
            </form>
        </#if>
    </div>
</div>
<div class="background-image">
</div>

</body>
</html>