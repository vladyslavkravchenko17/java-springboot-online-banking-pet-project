<#import "../parts/common.ftl" as c>
<#import "navbar.ftl" as n>

<@c.page>
    <@n.navbar></@n.navbar>
    <div class="col-md-8 col-lg-9 content-container align-items-center justify-content-center mt-3">
        <form>
            <h4>My subscriptions:</h4>
            <table>
                <tr>
                    <th>Service</th>
                    <th>Card</th>
                    <th>Price(UAH/Month)</th>
                    <th>End date</th>
                    <th>Auto-purchasing every month</th>
                    <th></th>
                </tr>
                <#list subscriptions as s>
                    <tr>
                        <td><b>${s.subscriptionInfo.name}</b></td>
                        <td><a href="/profile/cards/${s.card.id}">${s.card.number}</a></td>
                        <td>${s.subscriptionInfo.sumPerMonth}</td>
                        <td>${s.endDate} 12:00</td>
                        <td><#if s.repeatable>Yes<#else>No</#if></td>
                        <td><a href="/subscription/edit/${s.id}"> Edit</a></td>
                    </tr>
                </#list>
            </table>
        </form>
    </div>
</@c.page>