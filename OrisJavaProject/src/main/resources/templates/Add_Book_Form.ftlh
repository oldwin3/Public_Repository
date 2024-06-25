<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add Book to User</title>
    <style>
        <#include "css/style.css">
        <#include "css/style2.css">
    </style>
</head>
<body>

<div class="content-area">
    <h1>Add Book to User</h1>
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
                <label for="bookTitle">Book Title:</label>
                <input type="text" id="bookTitle" name="bookTitle" required>
                <input type="submit" value="Add Book"/>
            </form>
        </div>
    </div>
</div>
</body>
</html>