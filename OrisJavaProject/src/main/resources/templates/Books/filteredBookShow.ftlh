<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Filtered Books</title>
    <style>
        <#include "../css/style.css">
        <#include "../css/style2.css">
    </style>
</head>
<body>
<ul>
    <#list books as book>
        <li>
            <strong>Link:</strong> <a href="/books/${book.id}">Book Data</a><br>
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
            <strong>Delete book from your library:</strong>
            <form action="/books/user/${userId}/delete/${book.id}" method="post">
                <button type="submit">Delete</button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
            <br>
        </li>
    </#list>
</ul>
</body>
</html>