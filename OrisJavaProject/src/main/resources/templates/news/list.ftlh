<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>News List</title>
</head>
<body>
<div class="content-area">
<h1>News List</h1>
</div>

<div class="content-area">
<ul>
    <#list newsList as news>
        <li>
            <div class="nav-links">
            <a href="/news/${news.id}">${news.title}</a>
            <#if user??>
                <#if user.role == "ADMIN">
                    <a href="/news/${news.id}/edit">Edit</a>
                    <form method="post" action="/news/${news.id}">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="hidden" name="_method" value="delete">
                        <button type="submit">Delete</button>
                    </form>
                </#if>
            </#if>
            </div>
        </li>
    </#list>
</ul>
</div>
</body>
</html>