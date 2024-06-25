<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>All Posts</title>
    <style>
        <#include "../css/style.css">
        <#include "../css/style2.css">
    </style>
</head>
<body>

<div class="content-area">
    <h1>All Posts</h1>
</div>
<div class="background-image">
</div>
<div class="content-area">
    <div class="form-style-3">
        <div class="nav-links">

            <a href="/posts/new">Create New Post</a>
            <ul>
                <#list posts as post>
                    <li>
                        <a href="/posts/${post.id}">${post.title}</a>
                        <a href="/posts/${post.id}/edit">Edit</a>
                        <form method="post" action="/posts/${post.id}" style="display:inline;">
                            <input type="hidden" name="_method" value="delete"/>
                            <button type="submit">Delete</button>
                        </form>
                    </li>
                </#list>
            </ul>
        </div>
    </div>
</div>


</body>
</html>