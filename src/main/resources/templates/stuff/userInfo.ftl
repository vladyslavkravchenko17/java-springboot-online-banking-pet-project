<#import "../parts/common.ftl" as c>
<#import "navbar.ftl" as n>

<@c.page>
    <@n.navbar></@n.navbar>
    <div class="col-md-8 col-lg-9 content-container align-items-center justify-content-center mt-3">
        <form>
            <h4>User info:</h4>
            <div align="left">
                <table>
                    <tr>
                        <th>Id</th>
                        <td>${user.id}</td>
                    </tr>
                    <tr>
                        <th>Email</th>
                        <td>${user.email}</td>
                    </tr>
                    <tr>
                        <th>First name</th>
                        <td>${user.firstName}</td>
                    </tr>
                    <tr>
                        <th>Last name</th>
                        <td>${user.lastName}</td>
                    </tr>
                    <tr>
                        <th>Role</th>
                        <td>${user.role.name()}</td>
                    </tr>
                </table>
                <table class="mt-3">
                    <tr>
                        <th>Cards</th>
                    </tr>
                    <tr>
                        <th>Id</th>
                        <th>Number</th>
                        <th>Type</th>
                        <th>Currency</th>
                        <th>Info</th>
                    </tr>
                    <#list user.cards as c>
                        <tr>
                            <td>${c.id}</td>
                            <td>${c.number}</td>
                            <td>${c.type.getValue()}</td>
                            <td>${c.currency.name()}</td>
                            <td><a href="/stuff/card/${c.id}">More</a></td>
                        </tr>
                    <#else>
                        <tr>
                            <td>No cards.</td>
                        </tr>
                    </#list>
                </table>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input type="hidden" name="id" value="${user.id}"/>
                <button class="btn mt-3 rounded-pill btn-lg btn-custom btn-block text-uppercase"
                        formmethod="post" formaction="/stuff/download/pdf/user">Download PDF
                </button>
                <button class="btn mt-1 rounded-pill btn-lg btn-custom btn-block text-uppercase"
                        formmethod="post" formaction="/stuff/download/excel/user">Download Excel
                </button>
            </div>
        </form>
    </div>
</@c.page>