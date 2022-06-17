<#import "../parts/common.ftl" as c>
<#import "navbar.ftl" as n>

<@c.page>
    <@n.navbar></@n.navbar>
    <div class="col-md-8 col-lg-9 content-container align-items-center justify-content-center mt-3">
        <h4>List of users:</h4>
        <#if info??>
            <div class="mb-2 mt-2 bar info">
                <i class="ico">&#8505;</i> ${info}</div>
        </#if>
        <table class="tablemanager mb-3">
            <thead>
            <tr>
                <th class="disableSort">Id</th>
                <th class="disableSort">Email</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Role</th>
                <th class="disableFilterBy">Info</th>
            </tr>
            </thead>
            <tbody>
            <#list users as u>
                <#if u.firstName?has_content>
                    <tr>
                        <td>${u.id}</td>
                        <td>${u.email}</td>
                        <td>${u.firstName}</td>
                        <td>${u.lastName}</td>
                        <td>${u.role.name()}</td>
                        <td><a href="/stuff/user/${u.id}">More</a></td>
                    </tr>
                </#if>
            </#list>
            </tbody>
        </table>
        <form>
            <div class="form-group mt-4">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button class="btn mt-1 rounded-pill btn-lg btn-custom btn-block text-uppercase"
                        formaction="/stuff/download/pdf/users" formmethod="post"
                >Download PDF
                </button>
                <button class="btn mt-1 rounded-pill btn-lg btn-custom btn-block text-uppercase"
                        formaction="/stuff/download/excel/users" formmethod="post"
                >Download Excel
                </button>
            </div>
        </form>
    </div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script type="text/javascript" src="/js/tableManager.js"></script>
    <script type="text/javascript">
        $('.tablemanager').tablemanager({
            disable: ["last"],
            appendFilterby: true,
            dateFormat: [[4, "dd-mm-yyyy"]],
            debug: true,
            vocabulary: {
                voc_filter_by: 'Filter by',
                voc_type_here_filter: 'Enter filter value...',
                voc_show_rows: 'Amount of rows'
            },
            pagination: true,
            showrows: [5, 10, 20, 50, 100]
        });
    </script>
</@c.page>