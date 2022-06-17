<#import "../parts/common.ftl" as c>
<#import "navbar.ftl" as n>

<@c.page>
    <@n.navbar bankBalance="${bankBalance}"></@n.navbar>
    <div class="col-md-8 col-lg-9 content-container align-items-center justify-content-center mt-3">
        <h4>Bank day income reports:</h4>
        <table class="tablemanager mb-3">
            <thead>
            <tr>
                <th>Date</th>
                <th>Total gain</th>
                <th>Total income</th>
                <th>Total lose</th>
                <th class="disableFilterBy">Info</th>
            </tr>
            </thead>
            <tbody>
            <#list reports as r>
                <tr>
                    <td>${r.date}</td>
                    <td>${r.getTotalGain()} UAH</td>
                    <td>${r.getTotalIncome()} UAH</td>
                    <td>${r.getTotalLose()} UAH</td>
                    <td><a href="/admin/income/day/${r.date}">More</a></td>
                </tr>
            </#list>
            </tbody>
        </table>
        <form>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button class="btn mt-3 rounded-pill btn-lg btn-custom btn-block text-uppercase"
                    formaction="/admin/download/pdf/daily" formmethod="post">Download PDF
            </button>
            <button class="btn mt-1 mb-3 rounded-pill btn-lg btn-custom btn-block text-uppercase"
                    formaction="/admin/download/excel/daily" formmethod="post">Download Excel
            </button>
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