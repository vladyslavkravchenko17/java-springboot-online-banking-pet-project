<#include "security.ftl">

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <#if known>
        <a class="navbar-brand" href="/">Vl-Bank
        <img class="flag flag-ua"></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/transfer">Transfer</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/atm">ATM</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/credit">Credit</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/deposit">Deposit</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/subscription">Subscriptions</a>
                </li>
            </ul>
            <#if isAdmin>
                <div class="navbar-text mr-3"><a href="/admin">Admin</a></div>
            </#if>
            <#if isStuff || isAdmin>
                <div class="navbar-text mr-3"><a href="/stuff">Stuff</a></div>
            </#if>
            <div class="navbar-text mr-3"><a href="/profile">${user.firstName} ${user.lastName}</a></div>
            <form action="/logout" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button class="btn btn-lg btn-custom btn-block">Logout</button>
            </form>
        </div>
    <#else>
        <a class="navbar-brand" href="/welcome">Vl-Bank</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
            </ul>

            <form action="/login" method="get">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button class="btn btn-lg btn-custom btn-block">Login</button>
            </form>
        </div>
    </#if>
</nav>