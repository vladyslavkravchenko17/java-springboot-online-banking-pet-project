<#import "../parts/common.ftl" as c>

<@c.page>
    <div class="col-md-8 col-lg-9 content-container align-items-center justify-content-center mt-5"
         style="width: 30%; margin-left: 550px">
        <#if cards?has_content>
            <form>
                <h3 class="font-weight-light text-uppercase" align="center">Deposits:</h3>
                <#if message??>
                    <div class="bar success">
                        <i class="ico">&#10004;</i> ${message}
                    </div>
                </#if>
                <#if error??>
                    <div class="mb-2 mt-2 bar error">
                        <i class="ico">&#9747;</i> ${error}</div>
                </#if>
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
                            formaction="/deposit" formmethod="post">Show deposits
                    </button>
                    <button class="btn mt-1 rounded-pill btn-lg btn-custom btn-block text-uppercase"
                            formaction="/create-deposit/number" formmethod="post">Make deposit
                    </button>
                </div>
            </form>
            <div class="container form-group">
                <#if deposits?has_content>
                    <table class="tablemanager mb-3 noborder">
                        <thead>
                        <tr>
                        </tr>
                        </thead>
                        <tbody>
                        <#list deposits as d>
                            <tr class="noborder">
                                <td class="noborder">
                                    <div class="mb-3" align="left">
                                        Deposit sum: <b>${d.cardPayedSum + " " + card.currency}</b><br>
                                        Received sum: ${d.currentReceivedSum}
                                        /<b>${d.sumToReceive + " " + card.currency}</b><br>
                                        <#if d.repaid>
                                            <#if d.sumToReceive gt d.currentReceivedSum>
                                                <b>Deposit was prematurely returned.</b>
                                            <#else>
                                                <b>Deposit is repaid.</b><br>
                                            </#if>
                                        <#else>
                                            Month: ${d.currentMonth}/<b>${d.months}</b><br>
                                            Next payment: ${d.nextPayment + " 12:00"} (
                                            <b>${d.sumPerMonth + " " + card.currency}</b>)<br>
                                            <progress value="${d.currentMonth}" max="${d.months}"></progress>
                                            <a href="/deposit/premature-return/${d.id}"
                                               onclick="return premature_return(this);">Premature return</a>
                                        </#if>
                                    </div>
                                </td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                <#else>
                    <h4>No deposit history.</h4>
                </#if>
            </div>
            </form>
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
            showrows: [1, 5, 10]
        });    </script>
    <script src="/js/jquery-3.6.0.min.js"></script>
    <script src="/js/main.js"></script>
</@c.page>