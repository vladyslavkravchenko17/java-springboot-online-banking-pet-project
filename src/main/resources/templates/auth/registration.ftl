<#import "../parts/common.ftl" as c>
<#import "/spring.ftl" as spring />

<@c.page>
    <div class="d-flex justify-content-center align-items-center login-container">
        <form class="login-form text-center"
              action="/registration" method="post">
            <h1 class="mb-5 font-weight-light text-uppercase">Registration</h1>
            <#if error??>
                <div class="mb-2 mt-2 bar error">
                    <i class="ico">&#9747;</i> ${error}</div>
            </#if>
            <div class="form-group">
                <input type="email"
                       class="form-control rounded-pill form-control-lg ${(emailError??)?string('is-invalid', '')}"
                       value="<#if user??>${user.email}</#if>" placeholder="Email" name="email">
                <#if emailError??>
                    <div class="invalid-feedback">${emailError}</div>
                </#if>
            </div>
            <div class="form-group">
                <input type="text"
                       class="form-control rounded-pill form-control-lg ${(firstNameError??)?string('is-invalid', '')}"
                       placeholder="First name"
                       name="firstName" value="<#if user??>${user.firstName}</#if>">
                <#if firstNameError??>
                    <div class="invalid-feedback">${firstNameError}</div>
                </#if>
            </div>
            <div class="form-group">
                <input type="text"
                       class="form-control rounded-pill form-control-lg ${(lastNameError??)?string('is-invalid', '')}"
                       placeholder="Last name"
                       name="lastName" value="<#if user??>${user.lastName}</#if>">
                <#if lastNameError??>
                    <div class="invalid-feedback">${lastNameError}</div>
                </#if>
            </div>
            <div class="form-group">
                <input type="password"
                       class="form-control rounded-pill form-control-lg ${(passwordError??)?string('is-invalid', '')}"
                       placeholder="Password"
                       name="password">
                <#if passwordError??>
                    <div class="invalid-feedback">${passwordError}</div>
                </#if>
            </div>
            <div class="form-group">
                <input type="password"
                       class="form-control rounded-pill form-control-lg ${(matchingPasswordError??)?string('is-invalid', '')}"
                       placeholder="Confirm password"
                       name="matchingPassword">
                <#if matchingPasswordError??>
                    <div class="invalid-feedback">${matchingPasswordError}</div>
                </#if>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn mt-5 rounded-pill btn-lg btn-custom btn-block text-uppercase">Register
            </button>
            <p class="mt-3 font-weight-normal">Already have an account? <a href="/login"><strong>Log in</strong></a></p>
            <div class="form-group">
            </div>
        </form>
    </div>
</@c.page>