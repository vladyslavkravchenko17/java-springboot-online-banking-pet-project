<#import "../parts/common.ftl" as c>
<#import "navbar.ftl" as n>

<@c.page>
    <@n.navbar></@n.navbar>
    <div class="col-md-8 col-lg-9 content-container align-items-center justify-content-center mt-3">
        <h4>List of cards:</h4>
        <table class="tablemanager mb-3">
            <thead>
            <tr>
                <th class="disableSort">Id</th>
                <th class="disableSort">Number</th>
                <th>Balance</th>
                <th>Currency</th>
                <th class="disableFilterBy">Info</th>
            </tr>
            </thead>
            <tbody>
            <#list cards as c>
                <tr>
                    <td>${c.id}</td>
                    <td>${c.number}</td>
                    <td>${c.balance}</td>
                    <td>${c.currency.name()}</td>
                    <td><a href="/stuff/card/${c.id}">More</a></td>
                </tr>
            </#list>
            </tbody>
        </table>
        <form>
            <div class="form-group mt-4">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button class="btn mt-1 rounded-pill btn-lg btn-custom btn-block text-uppercase"
                        formaction="/stuff/download/pdf/cards" formmethod="post"
                >Download PDF
                </button>
                <button class="btn mt-1 rounded-pill btn-lg btn-custom btn-block text-uppercase"
                        formaction="/stuff/download/excel/cards" formmethod="post"
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