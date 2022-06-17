<#import "../parts/common.ftl" as c>
<#import "navbar.ftl" as n>

<@c.page>
    <@n.navbar></@n.navbar>
    <div class="col-md-8 col-lg-9 content-container align-items-center justify-content-center mt-3">
        <form>
            <div class="form-group">
                <h4>Card info:</h4>
                <#if message??>
                    <div class="mb-2 mt-2 bar success">
                        <i class="ico">&#10004;</i> ${message}</div>
                </#if>
                <#if error??>
                    <div class="mb-2 mt-2 bar error">
                        <i class="ico">&#9747;</i> ${error}</div>
                </#if>
                <table>
                    <tr>
                        <th>Card type</th>
                        <td>${card.type.getValue()}</td>
                    </tr>
                    <tr>
                        <th>Number</th>
                        <td>${card.number}</td>
                    </tr>
                    <#if card.companyName??>
                        <tr>
                            <th>Company name</th>
                            <td>${card.companyName}</td>
                        </tr>
                    </#if>
                    <tr>
                        <th>Balance</th>
                        <td>${card.balance} ${card.currency}</td>
                    </tr>
                    <tr>
                        <th>Credit limit</th>
                        <td>
                            <#if card.minCreditSum gt card.limit>
                                Card credit limit exceeded.
                            <#else>
                                ${card.limit} ${card.currency}
                            </#if>
                        </td>
                    </tr>
                </table>
            </div>
            <#if plannedPayments?has_content>
                <h4>Planned Payments:</h4>
                <div class="container" id="plannedPayments">
                    <table>
                        <tr>
                            <th>Date-time</th>
                            <th>Card</th>
                            <th>Sum</th>
                            <th>Receiver name</th>
                            <th>Comment</th>
                            <th>Repeatable</th>
                            <th></th>
                        </tr>
                        <#list plannedPayments as p>
                                <tr>
                                    <td>${p.paymentDateTime}</td>
                                    <td>${p.receiver.number}</td>
                                    <td>${p.sum} ${card.currency}</td>
                                    <td>${p.receiverName}</td>
                                    <td><#if p.description??>${p.description}<#else>None</#if></td>
                                    <td><#if p.repeatable??>Yes<#else>No</#if></td>
                                    <td><a href="/transfer/scheduled/edit/${p.id}">Edit</a></td>
                                </tr>
                        </#list>
                    </table>
                </div>
                <a onclick="showPlannedPayments()">Show/Hide</a>
            <#else>
                <h4>No planned payments.</h4>
            </#if>

            <#if credits?has_content>
                <h4>Credits:</h4>
                <div class="container" id="credits">
                    <table>
                        <tr>
                            <th>Start date</th>
                            <th>Credit sum</th>
                            <th>Current repaid sum</th>
                            <th>Sum per month</th>
                            <th>Current month</th>
                            <th>Next payment</th>
                            <th></th>
                        </tr>
                        <#list credits as c>
                            <#if !c.repaid>
                                <tr>
                                    <td>${c.startDate}</td>
                                    <td>${c.cardReceivedSum} ${card.currency}</td>
                                    <td>${c.currentRepaidSum}/${c.sumToRepay} ${card.currency}</td>
                                    <td>${c.sumPerMonth} ${card.currency}</td>
                                    <td>${c.currentMonth}/${c.months}</td>
                                    <td>${c.nextPayment}</td>
                                    <td><a href="/credit/premature-repay/${c.id}"
                                           onclick="return premature_repay(this);">Repay premature</a></td>
                                </tr>
                            </#if>
                        </#list>
                    </table>
                </div>
                <a onclick="showCredits()">Show/Hide</a>
            <#else>
                <h4>No credits.</h4>
            </#if>

            <#if deposits?has_content>
                <h4>Deposits:</h4>
                <div class="container" id="deposits">
                    <table>
                        <tr>
                            <th>Start date</th>
                            <th>Deposit sum</th>
                            <th>Current received sum</th>
                            <th>Sum per month</th>
                            <th>Current month</th>
                            <th>Next payment</th>
                            <th></th>
                        </tr>
                        <#list deposits as d>
                            <#if !d.repaid>
                                <tr>
                                    <td>${d.startDate}</td>
                                    <td>${d.cardPayedSum} ${card.currency}</td>
                                    <td>${d.currentReceivedSum}/${d.sumToReceive} ${card.currency}</td>
                                    <td>${d.sumPerMonth} ${card.currency}</td>
                                    <td>${d.currentMonth}/${d.months}</td>
                                    <td>${d.nextPayment}</td>
                                    <td><a href="/deposit/premature-return/${d.id}" onclick="return premature_return(this);">Premature return</a></td>
                                </tr>
                            </#if>
                        </#list>
                    </table>
                </div>
                <div id="showDeposits"><a onclick="showDeposits()" class="mb-5">Show/Hide</a></div>
            <#else>
                <h4>No deposits.</h4>
            </#if>
        </form>
    </div>
</@c.page>