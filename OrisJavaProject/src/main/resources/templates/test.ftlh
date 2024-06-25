<!DOCTYPE html>
<html lang="en">
<head>
    <style>
        <#include "css/style.css">
        <#include "css/style2.css">
    </style>
    <title>Mood Test</title>
</head>
<body>

<div class="content-area">
    <h1>Mood Test</h1>
    <h2>Return to Home page:</h2>
    <div class="nav-links">
        <a href="/home">Home</a>
    </div>
</div>
<div class="background-image">
</div>
<div class="content-area">

<div class="form-style-3">
    <form method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <#list questions as question>
        <h2>${question.text}</h2>
        <#list question.options as option>
            <input type="radio" id="${option}" name="${question.text}" value="${option.text}">
            <label for="${option}">${option.text}</label><br>
        </#list>
    </#list>
    <input type="submit" value="Submit">
</form>
</div>
</div>
</body>
</html>