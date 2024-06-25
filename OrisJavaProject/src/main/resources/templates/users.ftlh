<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User List</title>
    <style>
        <#include "css/style.css">
        <#include "css/style2.css">
    </style>
</head>
<body>
<#include "NavBar/navbar.ftlh">
<div class="content-area">
    <h1>Now you see users list</h1>
</div>
<div class="background-image">
</div>
<div class="content-area">
    <h1> All users </h1>

    <div class="nav-links">
    <ul>
        <#list users as user>
            <li>
                <a href="/users/${user.id}">${user.firstName}</a>
            </li>
        </#list>
    </ul>
    </div>
</div>
</body>
</html>