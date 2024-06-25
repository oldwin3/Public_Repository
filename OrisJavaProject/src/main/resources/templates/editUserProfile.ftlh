<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit User Profile</title>
    <style>
        <#include "css/style.css">
        <#include "css/style2.css">
    </style>
</head>
<body>


<div class="content-area">
    <h1>Edit User Profile</h1>
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

            <form action="/users/${user.id}/edit" method="post" enctype="multipart/form-data">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div>
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" value="${user.email}"/>
                </div>
                <div>
                    <label for="firstName">First Name:</label>
                    <input type="text" id="firstName" name="firstName" value="${user.firstName}"/>
                </div>
                <div>
                    <label for="lastName">Last Name:</label>
                    <input type="text" id="lastName" name="lastName" value="${user.lastName}"/>
                </div>
                <div>
                    <label for="profilePicture">Profile Picture:</label>
                    <input type="file" id="profilePicture" name="profilePicture"/>
                </div>
                <button type="submit">Update Profile</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>