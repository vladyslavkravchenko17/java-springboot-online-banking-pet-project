<#assign
known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    isAdmin = user.isAdmin()
    isStuff = user.isStuff()
    >
<#else>
    <#assign
    isAdmin = false
    >
</#if>