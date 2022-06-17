<#import "../parts/common.ftl" as c>

<@c.page>
    <div class="col-md-8 col-lg-9 content-container align-items-center justify-content-center mt-5"
         style="width: 30%; margin-left: 550px">
        <#if cards?has_content>
            <form>
                <h3 class="font-weight-light text-uppercase" align="center">Credits:</h3>
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
                            formaction="/credit" formmethod="post">Show credits
                    </button>
                    <#if limit>
                        <div class="bar warn">
                            <i class="ico">&#9888;</i> Limit of this card exceeded!
                        </div>
                    <#else>
                        <button class="btn mt-1 rounded-pill btn-lg btn-custom btn-block text-uppercase"
                                formaction="/create-credit/number" formmethod="post">Take new credit
                        </button>
                    </#if>
                </div>
            </form>
            <div class="container form-group">
                <#if credits?has_content>
                    <table class="tablemanager mb-3 noborder">
                        <thead>
                        <tr>
                        </tr>
                        </thead>
                        <tbody>
                        <#list credits as c>
                            <tr class="noborder">
                                <td class="noborder">
                                    <div class="mb-3" align="left">
                                        Credit sum: <b>${c.cardReceivedSum + " " + card.currency}</b><br>
                                        Payed sum: ${c.currentRepaidSum}
                                        /<b>${c.sumToRepay + " " + card.currency}</b><br>
                                        <#if c.repaid>
                                            <b>Credit is repaid.</b><br>
                                        <#else>
                                            Month: ${c.currentMonth}/<b>${c.months}</b><br>
                                            Next payment: ${c.nextPayment + " 12:00"} (
                                            <b>${c.sumPerMonth + " " + card.currency}</b>)
                                            <br>
                                            <progress value="${c.currentMonth}" max="${c.months}"></progress>
                                            <a href="/credit/premature-repay/${c.id}"
                                               onclick="return premature_repay(this);">Premature repay</a>
                                        </#if>
                                    </div>
                                </td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                <#else>
                    <h4>No credit history.</h4>
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
            showrows: [1, 5, 10]
        });    </script>
    <script src="/js/jquery-3.6.0.min.js"></script>
    <script src="/js/main.js"></script>
</@c.page>