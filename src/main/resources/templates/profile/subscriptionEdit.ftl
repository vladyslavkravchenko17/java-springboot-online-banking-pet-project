<#import "../parts/common.ftl" as c>
<#import "navbar.ftl" as n>

<@c.page>
    <@n.navbar></@n.navbar>
    <div class="col-md-8 col-lg-9 content-container align-items-center justify-content-center mt-3" align="center">
        <#if cards?hasContent>
            <form action="/subscription/edit" method="post"
                  onsubmit="return confirm('Do you want to save changes?');">
                <h4 class="font-weight-light text-uppercase">Edit subscription payment:</h4>
                <#if message??>
                    <div class="bar success">
                        <i class="ico">&#10004;</i> ${message}
                    </div>
                </#if>
                <#if error??>
                    <div class="mb-2 mt-2 bar error">
                        <i class="ico">&#9747;</i> ${error}</div>
                </#if>
                <div align="center">
                    <h5>1 Month of ${subscription.subscriptionInfo.name} - ${subscription.subscriptionInfo.sumPerMonth}
                        UAH</h5>
                    <div class="form-group">
                        <select name="senderNumber" class="form-control rounded-pill form-control-lg">
                            <#list cards as c>
                                <option value=${c.number}>${c.number + " - " + c.balance + " " + c.currency}</option>
                            </#list>
                        </select>
                    </div>
                    <div class="form-group">
                        <input type="checkbox" class="form-check-input" id="repeatable" name="repeatable">
                        <label class="form-check-label" for="repeatable">Purchase this subscription every month</label>
                    </div>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <input type="hidden" name="id" value="${subscription.id}">
                    <button class="btn mt-5 rounded-pill btn-lg btn-custom btn-block text-uppercase"
                            formaction="/subscription/edit">Save changes
                    </button>
                </div>
            </form>
        <#else>
            No credit card. <a href="/profile">Go to profile.</a>
        </#if>
    </div>
</@c.page>