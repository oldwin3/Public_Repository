<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${post.title}</title>
    <style>
        <#include "../css/style.css">
        <#include "../css/style2.css">
    </style>
</head>
<body>

<div class="content-area">
    <h1>Post: ${post.title}</h1>
    <h2>Return to Home page:</h2>
    <div class="nav-links">
        <a href="/home">Home</a>
    </div>
</div>
<div class="background-image">
</div>
<div class="content-area">
    <p>${post.content}</p>
    <#if flag>
        <div class="nav-links">
            <a href="/posts/${post.id}/edit">Edit</a>
            <form method="post" style="display:inline;">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit">Delete</button>
            </form>
        </div>
    </#if>
    <#include "../Comments/create_comment.ftlh">
    <#include "../Comments/comments.ftlh">
</div>
</body>
</html>