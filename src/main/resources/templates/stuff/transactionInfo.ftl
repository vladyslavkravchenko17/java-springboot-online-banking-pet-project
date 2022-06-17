<#import "../parts/common.ftl" as c>
<#import "navbar.ftl" as n>

<@c.page>
    <@n.navbar></@n.navbar>
    <div class="col-md-8 col-lg-9 content-container align-items-center justify-content-center mt-3">
        <form>
            <h4>Transaction info:</h4>
            <div align="left">
                <table>
                    <tr>
                        <th>Id</th>
                        <td>${transaction.id}</td>
                    </tr>
                    <tr>
                        <th>Date-Time</th>
                        <td>${transaction.getBeautifulDateTime()}</td>
                    </tr>
                    <tr>
                        <th>Sender</th>
                        <td><a href="/stuff/card/${transaction.sender.id}">${transaction.sender.number}</a></td>
                    </tr>
                    <tr>
                        <th>Receiver</th>
                        <td><a href="/stuff/card/${transaction.receiver.id}">${transaction.receiver.number}</a></td>
                    </tr>
                    <tr>
                        <th>Payed sum</th>
                        <td>${transaction.payedSum}</td>
                    </tr>
                    <tr>
                        <th>Commission</th>
                        <td>${transaction.commissionPercent}%</td>
                    </tr>
                    <tr>
                        <th>Commission sum</th>
                        <td>${transaction.commission}</td>
                    </tr>
                    <tr>
                        <th>Received sum</th>
                        <td>${transaction.receivedSum}</td>
                    </tr>
                    <tr>
                        <th>Conversion type</th>
                        <td>${transaction.conversionType}</td>
                    </tr>
                    <tr>
                        <th>Conversion rate</th>
                        <td>${transaction.currentConversionRate}</td>
                    </tr>
                    <tr>
                        <th>Comment</th>
                        <td><#if transaction.comment?has_content>${transaction.comment}<#else>None.</#if></td>
                    </tr>
                    <tr>
                        <th>Type</th>
                        <td>${transaction.type.getValue()}</td>
                    </tr>
                </table>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input type="hidden" name="id" value="${transaction.id}"/>
                <button class="btn mt-3 rounded-pill btn-lg btn-custom btn-block text-uppercase"
                        formmethod="post" formaction="/stuff/download/pdf/transaction">Download PDF
                </button>
                <button class="btn mt-1 rounded-pill btn-lg btn-custom btn-block text-uppercase"
                        formmethod="post" formaction="/stuff/download/excel/transaction">Download Excel
                </button>
            </div>
        </form>
    </div>
</@c.page>