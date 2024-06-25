<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Post</title>
    <style>
        <#include "../css/style.css">
        <#include "../css/style2.css">
    </style>
</head>
<body>

<div class="content-area">
    <h2>Create New Post</h2>
    <h2>Return to Home page:</h2>
    <div class="nav-links">
        <a href="/home">Home</a>
    </div>
</div>
<div class="background-image">
</div>
<div class="content-area">
    <div class="form-style-3">
        <div class="nav-links">
            <form method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div>
                    <label for="title">Title:</label>
                    <input type="text" id="title" name="title" required>
                </div>
                <div>
                    <label for="content">Content:</label>
                    <textarea id="content" name="content" required></textarea>
                </div>
                <button type="submit">Create Post</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>