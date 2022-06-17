<#import "../parts/common.ftl" as c>

<@c.page>
    <div align="center" class="d-flex justify-content-center align-items-center mb-5" style="margin-top: 70px;">
        <form>
            <h3 class="font-weight-light text-uppercase">Transaction info:</h3>
            <div align="left">
                <div>Date-Time: <b>${transaction.getBeautifulDateTime()}</b></div>
                <div>Sender: <b>${transaction.sender.number}</b></div>
                <div>Receiver: <b>${transaction.receiver.number}</b></div>
                <div>Payed sum: <b>${transaction.payedSum}</b></div>
                <div>Commission: <b>${transaction.commissionPercent}%</b></div>
                <div>Commission sum: <b>${transaction.commission}</b></div>
                <div>Received sum: <b>${transaction.receivedSum}</b></div>
                <div>Conversion type: <b>${transaction.conversionType.name()}</b></div>
                <div>Conversion rate: <b>${transaction.currentConversionRate}</b></div>
                <div>Comment: <b><#if transaction.comment?has_content>${transaction.comment}<#else>None</#if></b></div>
                <div>Transaction type: <b>${transaction.type.getValue()}</b></div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="hidden" name="id" value="${transaction.id}"/>
            <button class="btn mt-1 rounded-pill btn-lg btn-custom btn-block text-uppercase"
                    formaction="/transaction/download/pdf" formmethod="post">Download PDF
            </button>
            <button class="btn mt-1 rounded-pill btn-lg btn-custom btn-block text-uppercase"
                    formaction="/transaction/download/excel" formmethod="post">Download Excel
            </button>
        </form>
    </div>
</@c.page>