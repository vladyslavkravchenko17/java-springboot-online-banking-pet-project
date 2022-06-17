<#import "../parts/common.ftl" as c>

<@c.page>
    <div class="col-md-8 col-lg-9 content-container align-items-center justify-content-center mt-5"
         style="width: 30%; margin-left: 550px">
        <#if cards?has_content>
            <form>
                <h3 class="font-weight-light text-uppercase" align="center">Transactions:</h3>
                <div class="form-group">
                    <select name="number" class="form-control rounded-pill form-control-lg">
                        <#list cards as c>
                            <#if c.number = card.number>
                                <option selected="selected"
                                        value=${c.number}>${c.number + " - " + c.balance + " " + c.currency}</option>
                            <#else>
                                <option value=${c.number}>${c.number + " - " + c.balance + " " + c.currency}</option>
                            </#if>
                        </#list>
                    </select>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button class="btn mt-1 rounded-pill btn-lg btn-custom btn-block text-uppercase"
                            formaction="/" formmethod="post">Show transactions
                    </button>
                </div>
            </form>
            <div class="container mb-5">
                <#if transactions?has_content>
                    <table class="tablemanager mb-3 noborder">
                        <thead>
                        <tr>
                        </tr>
                        </thead>
                        <tbody>
                        <#list transactions as t>
                            <#if t.receivedSum gt 0>
                                <tr class="noborder">
                                    <td class="noborder">
                                        <div align="left">
                                            ${t.getBeautifulDateTime()} <br>
                                            <#if t.received>
                                                <#if t.partnerName?has_content>
                                                    <b>${t.partnerName}</b>
                                                    <b style="color:green">+${t.receivedSum+ " " + card.currency}</b>
                                                    <br>
                                                    <b>${t.sender.number}</b>
                                                <#else>
                                                    <b>${t.sender.number}</b>
                                                    <b style="color:green">+${t.receivedSum+ " " + card.currency}</b>
                                                </#if>
                                            <#else>
                                                <#if t.partnerName?has_content>
                                                    <b>${t.partnerName}</b>
                                                    <b style="color:red">-${t.payedSum + " " + card.currency}</b><br>
                                                    <b>${t.receiver.number}</b><br>
                                                    Commission is <i
                                                        style="color:red">${t.commission + " " + card.currency}</i>(${t.commissionPercent}%)
                                                <#else>
                                                    <b>${t.receiver.number}</b>
                                                    <b style="color:red">-${t.payedSum + " " + card.currency}</b>
                                                    <#if t.commission gt 0>
                                                        <br>
                                                        Commission is <i
                                                            style="color:red">${t.commission + " " + card.currency}</i>(${t.commissionPercent}%)</#if>
                                                </#if>
                                            </#if>
                                            <#if t.comment?has_content><br><i>Comment: ${t.comment}</i></#if>
                                            <br>
                                            ${t.getType().getValue()}<br>
                                            <a href="/transaction/${t.id}">More</a>
                                            <br><br>
                                        </div>
                                    </td>
                                </tr>
                            </#if>
                        </#list>
                        </tbody>
                    </table>
                <#else>
                    <h4>No transactions.</h4>
                </#if>
            </div>
        <#else>
            No credit card. <a href="/profile">Go to profile.</a>
        </#if>
    </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
    <script type="text/javascript" src="/js/tableManager.js"></script>
    <script type="text/javascript">
        $('.tablemanager').tablemanager({
            appendFilterby: false,
            pagination: true,
            showrows: [10, 20, 50]
        });    </script>
</@c.page>