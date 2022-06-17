<#import "../parts/common.ftl" as c>

<@c.page>
    <div align="center" class="d-flex justify-content-center align-items-center" style="margin-top: 70px;">
        <#if cards?hasContent>
            <form action="/create-credit" method="post" onsubmit="return confirm('Do you want to take credit?');">
                <h3 class="font-weight-light text-uppercase">Take a credit:</h3>
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
                    <h5>${card.number + " - " + card.balance + " " + card.currency}</h5>
                </div>
                <div class="form-group">
                    <input type="number" class="form-control rounded-pill form-control-lg" placeholder="${card.minCreditSum} - ${card.limit} ${card.currency}"
                           name="sum">
                </div>
                <div class="mb-1 forgot-link form-group d-flex justify-content-between align-items-center">
                    <div class="form-check">
                        <input type="checkbox" class="form-check-input" id="remember" name="agree">
                        <label class="form-check-label" for="remember">I agree with conditions.</label>
                    </div>
                    <a href="/info/credit">Read credit rules.</a>
                </div>
                <input type="hidden" name="number" value="${card.number}">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button class="btn mt-5 rounded-pill btn-lg btn-custom btn-block text-uppercase">Take</button>
                <div class="form-group mt-2">
                    Credit percent is 20% for 12 months.
                </div>
            </form>
        <#else>
            No credit card. <a href="/profile">Go to profile.</a>
        </#if>
    </div>
</@c.page>