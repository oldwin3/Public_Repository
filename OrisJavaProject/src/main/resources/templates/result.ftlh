<!DOCTYPE html>
<html lang="en">
<head>
    <style>
        <#include "css/style.css">
        <#include "css/style2.css">
    </style>
    <meta charset="UTF-8">
    <title>Mood Test Result</title>
</head>
<body>

<div class="content-area">
    <h1>Mood Test Result</h1>
    <h2>Return to Home page:</h2>
    <div class="nav-links">
    <a href="/home">Home</a>
    </div>
</div>
<div class="content-area">
    <div class="form-style-3">
        <div class="nav-links">

            <h1>I recommend you: ${recommendedBooks[currentBookIndex].title}</h1>
            <#if currentBookIndex gt 0>
                <a href="/mood-test/${userId}/${currentBookIndex - 1}">Previous Book</a>
            </#if>
            <#if currentBookIndex lt (recommendedBooks?size - 1)>
                <a href="/mood-test/${userId}/${currentBookIndex + 1}">Next Book</a>
            </#if>
        </div>
        <#if !bookSaved>
            <form method="post">
                <input type="hidden" name="bookIndex" value="${currentBookIndex}"/>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit">Save Book</button>
            </form>
        </#if>
    </div>
</div>
<div class="background-image">
</div>

</body>
</html>