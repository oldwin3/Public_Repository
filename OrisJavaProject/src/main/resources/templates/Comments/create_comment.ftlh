<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Comment</title>
</head>
<body>
<h2>Create New Comment</h2>
<form id="commentForm" method="post" action="/posts/${post.id}/comments/new">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div>
        <label for="content">Content:</label>
        <textarea id="content" name="content" required></textarea>
    </div>
    <button type="submit">Create Comment</button>
</form>
</body>
</html>