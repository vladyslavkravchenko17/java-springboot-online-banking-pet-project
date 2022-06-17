<#import "../parts/common.ftl" as c>
<#import "navbar.ftl" as n>

<@c.page>
    <@n.navbar></@n.navbar>
    <div class="col-md-8 col-lg-9 content-container align-items-center justify-content-center mt-3">
        <form action="/profile/card/create" method="get">
            <h4>My cards:</h4>
            <#if error??>
                <div class="mb-2 mt-2 bar error">
                    <i class="ico">&#9747;</i> ${error}</div>
            </#if>
            Taken card slots ${size}/5
            <table>
                <tr>
                    <th>Card type</th>
                    <th>Number</th>
                    <th>Company name</th>
                    <th>Balance</th>
                    <th>Credit limit</th>
                    <th>Info</th>
                </tr>
                <#list cards as c>
                    <tr>
                        <td>${c.type.getValue()}</td>
                        <td>${c.number}</td>
                        <td><#if c.companyName??>${c.companyName}<#else>None</#if></td>
                        <td>${c.balance} ${c.currency}</td>
                        <td>${c.limit} ${c.currency}</td>
                        <td><a href="/profile/cards/${c.id}">More</a></td>
                    </tr>
                </#list>
            </table>
            <#if size = 5>
                <div class="mb-2 mt-2 bar warn">
                    <i class="ico">&#9888;</i>No slots for new cards</div>
            <#else>
                <button class="btn mt-2 mb-2 rounded-pill btn-lg btn-custom btn-block text-uppercase">Create new card
                </button>
            </#if>
        </form>
    </div>
</@c.page>