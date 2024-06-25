<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Comments for Post ${post.id}</title>
</head>
<body>
<h1>Comments</h1>
<ul>
    <#list comments as comment>
        <li>
            ${comment.name}: ${comment.content}
        </li>
    </#list>

</ul>
</body>
</html>