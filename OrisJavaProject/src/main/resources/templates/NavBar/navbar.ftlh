<nav>
    <#if user??>
        <div class="nav-links">
            <a href="/home">Home</a>
            <a href="/users">Users</a>
            <a href="/users/${user.id}">User Profile</a>
            <a href="/groups">Groups</a>
            <a href="/test">Tests</a>
            <#if user.role == "ADMIN">
                <a href="/admin-links">Admins Links</a>
            </#if>
            <form action="/logout" method="post" class="logout-form">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <button type="submit">Logout</button>
            </form>
        </div>
    <#else>
        <div class="nav-links">
            <a href="/signIn">Sign In</a>
            <a href="/signUp">Sign Up</a>
        </div>
    </#if>
</nav>