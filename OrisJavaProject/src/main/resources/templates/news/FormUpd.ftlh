<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create/Edit News</title>
    <style>
        <#include "../css/style.css">
        <#include "../css/style2.css">
    </style>
</head>
<body>
<div class="content-area">
    <h1>Create/Edit News</h1>
</div>
<div class="background-image">
</div>
<div class="content-area">
<div class="form-style-3">
    <form method="post" action="/news">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div>
            <label for="title">Title:</label>
            <input type="text" id="title" name="title" >
        </div>
        <div>
            <label for="content">Content:</label>
            <input type="text" id="content" name="content" >
        </div>
        <button type="submit">Upadte</button>
    </form>
</div></div>
</body>
</html>