<#import "../parts/common.ftl" as c>
<#import "navbar.ftl" as n>

<@c.page>
    <@n.navbar></@n.navbar>
    <div class="col-md-8 col-lg-9 content-container align-items-center justify-content-center mt-3">
        <form>
            <h4>Card info:</h4>
            <div align="left">
                <table>
                    <tr>
                        <th>Id</th>
                        <td>${card.id}</td>
                    </tr>
                    <tr>
                        <th>Number</th>
                        <td>${card.number}</td>
                    </tr>
                    <tr>
                        <th>Type</th>
                        <td>${card.type.getValue()}</td>
                    </tr>
                    <#if card.companyName?has_content>
                       <tr>
                           <th>Company name</th>
                           <td>${card.companyName}</td>
                       </tr>
                    </#if>
                    <tr>
                        <th>Balance</th>
                        <td>${card.balance}</td>
                    </tr>
                    <tr>
                        <th>Currency</th>
                        <td>${card.currency}</td>
                    </tr>
                    <tr>
                        <th>Credit limit</th>
                        <td>${card.limit}</td>
                    </tr>
                    <tr>
                        <th>Owner</th>
                        <td><a href="/stuff/user/${user.id}">${user.firstName} ${user.lastName}</a></td>
                    </tr>
                </table>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input type="hidden" name="id" value="${card.id}"/>
                <button class="btn mt-3 rounded-pill btn-lg btn-custom btn-block text-uppercase"
                        formmethod="post" formaction="/stuff/download/pdf/card">Download PDF
                </button>
                <button class="btn mt-1 rounded-pill btn-lg btn-custom btn-block text-uppercase"
                        formmethod="post" formaction="/stuff/download/excel/card">Download Excel
                </button>
            </div>
        </form>
    </div>
</@c.page>