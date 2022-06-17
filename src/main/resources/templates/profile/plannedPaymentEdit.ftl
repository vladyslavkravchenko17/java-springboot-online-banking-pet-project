<#import "../parts/common.ftl" as c>
<#import "navbar.ftl" as n>

<@c.page>
    <@n.navbar></@n.navbar>
    <div class="col-md-8 col-lg-9 content-container align-items-center justify-content-center mt-3">
        <#if payment?hasContent>
            <form onsubmit="return confirm('Do you want to save changes?');">
                <h3 class="font-weight-light text-uppercase">Edit planned payment:</h3>
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
                    <select name="senderNumber" class="form-control rounded-pill form-control-lg">
                        <#list cards as c>
                            <option <#if c.number = payment.sender.number>selected="selected" </#if>
                                    value=${c.number}>${c.number + " - " + c.balance + " " + c.currency}</option>
                        </#list>
                    </select>
                </div>
                <div class="form-group">
                    <input type="text"
                           class="form-control rounded-pill form-control-lg ${(receiverNumberError??)?string('is-invalid', '')}"
                           placeholder="Card number"
                           name="receiverNumber" value="${receiverNumber}">
                    <#if receiverNumberError??>
                        <div class="invalid-feedback">${receiverNumberError}</div>
                    </#if>
                </div>
                <div class="form-group">
                    <input type="number"
                           class="form-control rounded-pill form-control-lg ${(sumError??)?string('is-invalid', '')}"
                           placeholder="Sum"
                           name="sum" value="${payment.sum}">
                    <#if sumError??>
                        <div class="invalid-feedback">${sumError}</div>
                    </#if>
                </div>
                <div class="form-group">
                    <input type="date" id="date" name="date" class="form-control rounded-pill form-control-lg"
                           value="${payment.paymentDateTime.toLocalDate()}">
                </div>
                <div class="form-group">
                    <input type="time" id="time" name="time" class="form-control rounded-pill form-control-lg"
                           value="${payment.paymentDateTime.toLocalTime()}">
                </div>
                <div class="form-group">
                    <select name="repeatable" class="form-control rounded-pill form-control-lg" id="select">
                        <option value="false">Non-Repeatable payment</option>
                        <option value="true" <#if payment.repeatable>selected="selected"</#if>>Repeatable payment
                        </option>
                    </select>
                </div>
                <div class="form-group select-blocks"style="display:none" id="true">
                    <select name="period" class="form-control rounded-pill form-control-lg">
                        <option value="1" <#if payment.period = 1>selected="selected"</#if>>Repeat every day</option>
                        <option value="7" <#if payment.period = 7>selected="selected"</#if>>Repeat every 7 days</option>
                        <option value="30" <#if payment.period = 30>selected="selected"</#if>>Repeat every 30 days
                        </option>
                    </select>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control rounded-pill form-control-lg" placeholder="Comment"
                           name="description" value="<#if payment.description??>${payment.description}</#if>">
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input type="hidden" name="id" value="${payment.id}"/>
                <button class="btn mt-5 rounded-pill btn-lg btn-custom btn-block text-uppercase"
                        formaction="/transfer/scheduled/edit" formmethod="post">Save</button>
                <button class="btn rounded-pill btn-lg btn-custom btn-block text-uppercase"
                        formaction="/transfer/scheduled/delete" formmethod="post">Delete</button>
            </form>
        <#else>
            Planned payment is not found. <a href="/profile">Go to profile.</a>
        </#if>
    </div>
</@c.page>