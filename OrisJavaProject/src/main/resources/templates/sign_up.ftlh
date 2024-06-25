<!doctype html>
<html>
<head>
    <style>
        <#include "css/style.css">
    </style>
    <title>SigUp</title>
</head>
<body>
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />

<div class="form-style-3">
    <form method="post">
        <fieldset>
            <legend>Sign Up</legend>
            <label for="firstName"><span>First Name<span class="required">*</span></span><input type="text"
                                                                                                class="input-field"
                                                                                                name="firstName"
                                                                                                value=""/></label>
            <label for="lastName"><span>Last Name <span class="required">*</span></span><input type="text"
                                                                                               class="input-field"
                                                                                               name="lastName"
                                                                                               value=""/></label>
            <label for="email"><span>Email <span class="required">*</span></span><input type="email"
                                                                                        class="input-field"
                                                                                        name="email"
                                                                                        value=""/></label>
            <label for="password"><span>Password<span class="required">*</span></span><input type="password"
                                                                                             class="input-field"
                                                                                             name="password"
                                                                                             value=""/></label>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        </fieldset>
        <fieldset>
            <label><span> </span><input type="submit" value="Submit"/></label>
        </fieldset>
    </form>
</div>

</body>
</html>