<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Book Details</title>
    <style>
        <#include "../css/style.css">
        <#include "../css/style2.css">
    </style>
</head>
<body>

<div class="content-area">
    <h1>Book Details</h1>
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
            <div>
                <p>Title: <span>${book.title}</span></p>
                <p>Authors:
                    <#list book.authors as author>
                        <span>${author.name}</span><#if author_has_next>, </#if>
                    </#list>
                </p>
                <p>Subjects:
                    <#list book.subjects as subject>
                        <span>${subject.name}</span><#if subject_has_next>, </#if>
                    </#list>
                </p>
            </div>
        </div>
    </div>
</div>
</body>
</html>