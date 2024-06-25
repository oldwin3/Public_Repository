<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Post</title>
    <style>
        <#include "css/style.css">
        <#include "css/style2.css">
    </style>
</head>
<body>
<#include "NavBar/navbar.ftlh">

<div class="content-area">
    <h1>Tests</h1>
    <div class="form-style-3">
        <div class="nav-links">

            <a href="/mood-test/${userId}">Mood Test</a>
            <form method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit">Random Test from our partners</button>
            </form>
        </div>
    </div>
</div>

<div class="background-image">
</div>
</body>
</html>