<#import "../parts/common.ftl" as c>

<@c.page>
    <div class="d-flex justify-content-center align-items-center login-container" >
        <form class="login-form text-center" action="/changing-password/${code}" method="post">
            <h1 class="mb-5 font-weight-light text-uppercase">New password:</h1>
            <div class="form-group">
                <input type="password" class="form-control rounded-pill form-control-lg" placeholder="Password" name="password">
            </div>
            <div class="form-group">
                <input type="password" class="form-control rounded-pill form-control-lg" placeholder="Confirm password" name="matchingPassword">
            </div>
            <div class="forgot-link form-group d-flex justify-content-between align-items-center">
                <a href="/login">Log in</a>
                <a href="/registration">Registration</a>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn mt-5 rounded-pill btn-lg btn-custom btn-block text-uppercase">Change password</button>
        </form>
    </div>
    ${message?ifExists}
</@c.page>
