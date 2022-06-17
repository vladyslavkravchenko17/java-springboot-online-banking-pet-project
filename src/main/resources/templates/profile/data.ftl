<#import "../parts/common.ftl" as c>
<#import "navbar.ftl" as n>

<@c.page>
    <@n.navbar></@n.navbar>
    <div class="col-md-8 col-lg-9 content-container align-items-center justify-content-center mt-3">
        <form>
            <h4>My personal data:</h4>
            <div align="left">
                <table>
                    <tr>
                        <th>Full name</th>
                        <td>${user.firstName} ${user.lastName}</td>
                    </tr>
                    <tr>
                        <th>Email</th>
                        <td>${user.email}</td>
                    </tr>
                </table>
            </div>
        </form>
    </div>
</@c.page>