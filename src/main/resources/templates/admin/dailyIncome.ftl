<#import "../parts/common.ftl" as c>
<#import "navbar.ftl" as n>

<@c.page>
    <@n.navbar bankBalance="${bankBalance}"></@n.navbar>
    <div class="col-md-8 col-lg-9 content-container align-items-center justify-content-center mt-3">
        <h4>Daily income report:</h4>
        <div align="left" class="mb-3">
            <table class="mb-3">
                <tr>
                    <th>Date</th>
                    <td>${report.getDate()} UAH</td>
                </tr>
                <tr>
                    <th>Total gain</th>
                    <td>${report.getTotalGain()} UAH</td>
                </tr>
                <tr>
                    <th>Total income</th>
                    <td>${report.getTotalIncome()} UAH</td>
                </tr>
                <tr>
                    <th>Total lose</th>
                    <td>${report.getTotalLose()} UAH</td>
                </tr>
            </table>
            <table>
                <tr>
                    <th>Commission income</th>
                    <td>${report.commissionIncome} UAH</td>
                </tr>
                <tr>
                    <th>Penalty income</th>
                    <td>${report.penaltyIncome} UAH</td>
                </tr>
                <tr>
                    <th>Credit income</th>
                    <td>${report.creditIncome} UAH</td>
                </tr>
                <tr>
                    <th>Credit lose</th>
                    <td>${report.creditLose} UAH</td>
                </tr>
                <tr>
                    <th>Deposit income</th>
                    <td>${report.depositIncome} UAH</td>
                </tr>
                <tr>
                    <th>Deposit lose</th>
                    <td>${report.depositLose} UAH</td>
                </tr>
            </table>
        </div>
        <form>
            <input type="hidden" name="date" value="${report.date}"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button class="btn mt-3 rounded-pill btn-lg btn-custom btn-block text-uppercase"
                    formaction="/admin/download/pdf/day" formmethod="post">Download PDF
            </button>
            <button class="btn mt-1 mb-3 rounded-pill btn-lg btn-custom btn-block text-uppercase"
                    formaction="/admin/download/excel/day" formmethod="post">Download Excel
            </button>
        </form>
    </div>
</@c.page>