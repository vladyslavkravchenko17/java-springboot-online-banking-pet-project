<#import "../parts/common.ftl" as c>
<#import "navbar.ftl" as n>

<@c.page>
    <@n.navbar bankBalance="${bankBalance}"></@n.navbar>
    <div class="col-md-8 col-lg-9 content-container align-items-center justify-content-center mt-3">
        <h4>Monthly income report:</h4>
        <div class="mb-3">
            <table class="mb-3">
                <tr>
                    <th>Date</th>
                    <td>${date}</td>
                </tr>
                <tr>
                    <th>Total gain</th>
                    <td>${monthReport.getTotalGain()} UAH</td>
                </tr>
                <tr>
                    <th>Total income</th>
                    <td>${monthReport.getTotalIncome()} UAH</td>
                </tr>
                <tr>
                    <th>Total lose</th>
                    <td>${monthReport.getTotalLose()} UAH</td>
                </tr>
            </table>
            <table>
                <tr>
                    <th>Commission income</th>
                    <td>${monthReport.commissionIncome} UAH</td>
                </tr>
                <tr>
                    <th>Penalty income</th>
                    <td>${monthReport.penaltyIncome} UAH</td>
                </tr>
                <tr>
                    <th>Credit income</th>
                    <td>${monthReport.creditIncome} UAH</td>
                </tr>
                <tr>
                    <th>Credit lose</th>
                    <td>${monthReport.creditLose} UAH</td>
                </tr>
                <tr>
                    <th>Deposit income</th>
                    <td>${monthReport.depositIncome} UAH</td>
                </tr>
                <tr>
                    <th>Deposit lose</th>
                    <td>${monthReport.depositLose} UAH</td>
                </tr>
                <tr>
                    <th>Month</th>
                    <td>
                        <progress value="${size}" max="30"></progress>
                    </td>
                </tr>
            </table>
        </div>
        <h4>Days:</h4>
        <table class="tablemanager mt-3">
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
        <form class="mt-4">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="hidden" name="date" value="${date}"/>
            <button class="btn mt-2 rounded-pill btn-lg btn-custom btn-block text-uppercase mb-2"
                    formaction="/admin/download/pdf/month" formmethod="post">Download PDF
            </button>
            <button class="btn mt-2 rounded-pill btn-lg btn-custom btn-block text-uppercase mb-2"
                    formaction="/admin/download/excel/month" formmethod="post">Download Excel
            </button>
        </form>
        <form>
            <button class="btn mt-2 rounded-pill btn-lg btn-custom btn-block text-uppercase mb-2"
                    formaction="/admin/income/graph/${date}" formmethod="get">Show Graph
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
            showrows: [5, 31]
        });
    </script>
</@c.page>