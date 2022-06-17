<#import "../parts/common.ftl" as c>
<#import "navbar.ftl" as n>

<@c.page>
    <@n.navbar></@n.navbar>
    <div class="col-md-8 col-lg-9 content-container align-items-center justify-content-center mt-3">
        <form>
            <h4>Credit info:</h4>
            <div align="left">
                <table>
                    <tr>
                        <th>Id</th>
                        <td>${credit.id}</td>
                    </tr>
                    <tr>
                        <th>Start date</th>
                        <td>${credit.startDate}</td>
                    </tr>
                    <tr>
                        <th>Card</th>
                        <td> <a href="/stuff/card/${credit.card.id}">${credit.card.number}</a></td>
                    </tr>
                    <tr>
                        <th>Received sum</th>
                        <td>${credit.cardReceivedSum} ${credit.card.currency}</td>
                    </tr>
                    <tr>
                        <th>Sum to repay</th>
                        <td>${credit.sumToRepay} ${credit.card.currency}</td>
                    </tr>
                    <tr>
                        <th>Amount of months</th>
                        <td>${credit.months}</td>
                    </tr>
                    <tr>
                        <th>Sum per month</th>
                        <td>${credit.sumPerMonth} ${credit.card.currency}</td>
                    </tr>
                    <tr>
                        <th>Percent</th>
                        <td>${credit.percent}%</td>
                    </tr>
                    <tr>
                        <th>Next payment date</th>
                        <td><#if credit.repaid>Credit is repaid<#else>${credit.nextPayment}</#if></td>
                    </tr>
                    <tr>
                        <th>Current month</th>
                        <td>${credit.currentMonth}</td>
                    </tr>
                    <tr>
                        <th>Current repaid sum</th>
                        <td>${credit.currentRepaidSum} ${credit.card.currency}</td>
                    </tr>
                    <tr>
                        <th>Repay progress</th>
                        <td><progress value="${credit.currentMonth}" max="${credit.months}"></progress>${credit.currentMonth / credit.months * 100}%</td>
                    </tr>
                </table>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input type="hidden" name="id" value="${credit.id}"/>
                <button class="btn mt-3 rounded-pill btn-lg btn-custom btn-block text-uppercase"
                        formmethod="post" formaction="/stuff/download/pdf/credit">Download PDF
                </button>
                <button class="btn mt-1 rounded-pill btn-lg btn-custom btn-block text-uppercase"
                        formmethod="post" formaction="/stuff/download/excel/credit">Download Excel
                </button>
            </div>
        </form>
    </div>
</@c.page>