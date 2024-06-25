<!doctype html>
<html>
<head>
    <title>SigIn</title>
    <style>
        <#include "css/style.css">
    </style>
</head>
<body>
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<div class="form-style-3">
    <form method="post">
        <fieldset>
            <legend>Sign In</legend>
            <#if error??>
                <h1>Неправильный логин или пароль</h1>
            </#if>
            <label for="email"><span>Email <span class="required">*</span></span>
                <input type="email"
                       class="input-field"
                       name="email"
                       value=""/></label>
            <label for="password"><span>Password<span class="required">*</span></span>
                <input type="password"
                       class="input-field"
                       name="password"
                       value=""/></label>
            <label for="rememberMe"><span>rememberMe<span>*</span></span><input type="checkbox"
                                                                                name="remember-me-new"/>
            </label>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </fieldset>
        <fieldset>
            <label><span> </span><input type="submit" value="Submit"/></label>
        </fieldset>
    </form>
</div>

</body>
</html>