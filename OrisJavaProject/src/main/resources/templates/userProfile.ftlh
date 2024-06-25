<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Profile</title>
    <style>
        <#include "css/style.css">
        <#include "css/style2.css">
    </style>
</head>
<body>
<#include "NavBar/navbar.ftlh">
<div class="nav-links">
    <#if flag>
        <a href="/posts/new">Create New Post</a>
        <a href="/users/${user.id}/edit">Edit Profile</a>
        <a href="/books/user/${userId}">Get My Books</a>
    </#if>
</div>
<div class="content-area">
    <h1>User Profile</h1>
</div>
<div class="background-image">
</div>
<div class="content-area">
    <p>First Name: ${user.firstName}</p>
    <#if profilePictureBase64??>
        <img src="data:image/png;base64,${profilePictureBase64}" alt="Profile Picture" style="width: 400px; height: 400px; "/>
    </#if>
    <p>Email: ${user.email}</p>

    <p>Last Name: ${user.lastName}</p>

    <h2>User Posts:</h2>
    <div class="nav-links">

    <ul>
        <#list user.posts as post>
            <li>
                <a href="/posts/${post.id}">${post.title}</a>
            </li>
        </#list>
    </ul>
    </div>
</div>


</body>
</html>