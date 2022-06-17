<#import "../parts/common.ftl" as c>
<#import "navbar.ftl" as n>

<@c.page>
    <@n.navbar></@n.navbar>
    <div class="col-md-8 col-lg-9 content-container align-items-center justify-content-center mt-3">
        <form>
            <h4>Deposit info:</h4>
            <div align="left">
                <table>
                    <tr>
                        <th>Id</th>
                        <td>${deposit.id}</td>
                    </tr>
                    <tr>
                        <th>Start date</th>
                        <td>${deposit.startDate}</td>
                    </tr>
                    <tr>
                        <th>Card</th>
                        <td> <a href="/stuff/card/${deposit.card.id}">${deposit.card.number}</a></td>
                    </tr>
                    <tr>
                        <th>Payed sum</th>
                        <td>${deposit.cardPayedSum} ${deposit.card.currency}</td>
                    </tr>
                    <tr>
                        <th>Sum to receive</th>
                        <td>${deposit.sumToReceive} ${deposit.card.currency}</td>
                    </tr>
                    <tr>
                        <th>Amount of months</th>
                        <td>${deposit.months}</td>
                    </tr>
                    <tr>
                        <th>Sum per month</th>
                        <td>${deposit.sumPerMonth}</td>
                    </tr>
                    <tr>
                        <th>Percent</th>
                        <td>${deposit.percent}%</td>
                    </tr>
                    <tr>
                        <th>Next payment date</th>
                        <td><#if deposit.repaid>Deposit is repaid<#else>${deposit.nextPayment}</#if></td>
                    </tr>
                    <tr>
                        <th>Current month</th>
                        <td>${deposit.currentMonth}</td>
                    </tr>
                    <tr>
                        <th>Current received sum</th>
                        <td>${deposit.currentReceivedSum} ${deposit.card.currency}</td>
                    </tr>
                    <tr>
                        <th>Receive progress</th>
                        <td><progress value="${deposit.currentMonth}" max="${deposit.months}"></progress>${deposit.currentMonth / deposit.months * 100}%</td>
                    </tr>
                </table>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input type="hidden" name="id" value="${deposit.id}"/>
                <button class="btn mt-3 rounded-pill btn-lg btn-custom btn-block text-uppercase"
                        formmethod="post" formaction="/stuff/download/pdf/deposit">Download PDF
                </button>
                <button class="btn mt-1 rounded-pill btn-lg btn-custom btn-block text-uppercase"
                        formmethod="post" formaction="/stuff/download/excel/deposit">Download Excel
                </button>
            </div>
        </form>
    </div>
</@c.page>