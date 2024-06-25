<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Groups</title>
    <style>
        <#include "../css/style.css">
        <#include "../css/style2.css">
    </style>
</head>
<body>
<#include "../NavBar/navbar.ftlh">
<div class="nav-links">
    <#if flag == 0>
        <a href="/groups/new">create New Group</a>
    </#if>
    <#if flag == 1>
        <a href="/groups/upd">update your Group</a>
    </#if>
</div>
<div class="content-area">
    <h1>Now you see Groups</h1>
</div>
<div class="background-image">
</div>

<div class="content-area">
    <h1> All Groups </h1>
    <div class="form-style-3">
        <div class="nav-links">
            <ul>
                <#list groups as group>
                    <li>
                        <a href="/groups/${group.id}">${group.name}</a>
                    </li>
                </#list>
            </ul>
        </div>
    </div>
</div>
</body>
</html>