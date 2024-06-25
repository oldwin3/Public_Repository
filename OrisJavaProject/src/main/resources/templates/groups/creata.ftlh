<!DOCTYPE html>
<html>
<head>
    <title>New Group</title>
    <style>
        <#include "../css/style.css">
        <#include "../css/style2.css">
    </style>
</head>
<body>

<div class="content-area">
    <h1>New Group</h1>
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
            <form method="post" enctype="multipart/form-data">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <div>
                    <label for="groupName">Name :</label>
                    <input type="text" id="groupName" name="groupName" required>
                </div>
                <div>
                    <label for="groupPicture">Profile Picture:</label>
                    <input type="file" id="groupPicture" name="groupPicture"/>
                </div>
                <button type="submit">Creata</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>