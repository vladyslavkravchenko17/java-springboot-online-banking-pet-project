<#import "../parts/common.ftl" as c>

<@c.page>
    <div class="d-flex justify-content-center align-items-center login-container">
        <form class="login-form text-center" action="/login" method="post">
            <h1 class="mb-5 font-weight-light text-uppercase">Login </h1>
            <#if message??>
                <div class="bar success">
                    <i class="ico">&#10004;</i> ${message}
                </div>
            </#if>
            <#if error??>
                <div class="bar error">
                    <i class="ico">&#9747;</i> ${error}
                </div>
            </#if>
            <div class="form-group">
                <input type="text" class="form-control rounded-pill form-control-lg" placeholder="Email"
                       name="username">
            </div>
            <div class="form-group">
                <input type="password" class="form-control rounded-pill form-control-lg" placeholder="Password"
                       name="password">
            </div>
            <div class="forgot-link form-group d-flex justify-content-between align-items-center">
                <div class="form-check">
                    <input type="checkbox" class="form-check-input" id="remember">
                    <label class="form-check-label" for="remember">Remember Password</label>
                </div>
                <a href="/forgot-password">Forgot Password?</a>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn mt-5 rounded-pill btn-lg btn-custom btn-block text-uppercase">Log in
            </button>
            <p class="mt-3 font-weight-normal">Don't have an account? <a href="/registration"><strong>Register
                        Now</strong></a></p>
        </form>
    </div>
</@c.page>