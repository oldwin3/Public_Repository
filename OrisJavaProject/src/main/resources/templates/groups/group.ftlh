<!DOCTYPE html>
<html>
<head>
    <title>Детали группы</title>
    <style>
        <#include "../css/style.css">
        <#include "../css/style2.css">
    </style>
</head>
<body>

<div class="content-area">
    <h1>${group.name}</h1>
    <h2>Return to Home page:</h2>
    <div class="nav-links">
        <a href="/home">Home</a>
    </div>
</div>
<div class="background-image">
</div>
<div class="content-area">

    <#if groupPictureBase64??>
        <img src="data:image/png;base64,${groupPictureBase64}" alt="Group Picture"
             style="width: 400px; height: 400px; "/>
    </#if>
    <ul>
        <#list group.users as user>
            <li>
                <span>${user.firstName}</span>
            </li>
        </#list>
        <#if flag == 0>
            <form method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <button type="submit">Join</button>
            </form>
        </#if>
        <#if flag == 1>
            <form method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit">Leave</button>
            </form>
        </#if>
        <#if flag == 2>
            <form method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <button type="submit">Delete Group</button>
            </form>
        </#if>
    </ul>
</div>
</body>
</html>