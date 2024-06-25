<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${news.title}</title>
    <style>
        <#include "../css/style.css">
        <#include "../css/style2.css">
    </style>
</head>
<body>
<div class="content-area">
    <h1>${news.title}</h1>
    <h2>Return to Home page:</h2>
    <div class="nav-links">
        <a href="/home">Home</a>
    </div>
</div>
<div class="content-area">
    <p>${news.content}</p>
</div>
<div class="background-image">
</div>

</body>
</html>