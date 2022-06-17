<#import "../parts/common.ftl" as c>
<#import "/spring.ftl" as spring />
<@c.page>
    <div align="center" class="d-flex justify-content-center align-items-center mt-5">
        <#if cards?hasContent>
            <form action="/transfer/scheduled" method="post" onsubmit="return confirm('Do you want to create scheduled payment?');">
                <h3 class="font-weight-light text-uppercase">Create planned payment:</h3>
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
                            <option value=${c.number}>${c.number + " - " + c.balance + " " + c.currency}</option>
                        </#list>
                    </select>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control rounded-pill form-control-lg ${(receiverNumberError??)?string('is-invalid', '')}" placeholder="Card number"
                           name="receiverNumber" value="<#if payment??>${payment.receiverNumber}</#if>">
                    <#if receiverNumberError??>
                        <div class="invalid-feedback">${receiverNumberError}</div>
                    </#if>
                </div>
                <div class="form-group">
                    <input type="number" class="form-control rounded-pill form-control-lg ${(sumError??)?string('is-invalid', '')}" placeholder="Sum"
                           name="sum" value="<#if payment??>${payment.sum}<#else>0</#if>" id="asking">
                    <#if sumError??>
                        <div class="invalid-feedback">${sumError}</div>
                    </#if>
                </div>
                <div class="form-group">
                    <input type="date" id="date" name="date" class="form-control rounded-pill form-control-lg"
                           value="${dateNow}" , min="${dateNow}">
                </div>
                <div class="form-group">
                    <input type="time" id="time" name="time" class="form-control rounded-pill form-control-lg"
                           value="${timeNow}">
                </div>
                <div class="form-group">
                    <select name="repeatable" class="form-control rounded-pill form-control-lg" id="select">
                        <option value="false">Non-Repeatable payment</option>
                        <option value="true">Repeatable payment</option>
                    </select>
                </div>
                <div class="form-group select-blocks"style="display:none" id="true">
                    <select name="period" class="form-control rounded-pill form-control-lg">
                        <option value="1">Repeat every day</option>
                        <option value="7">Repeat every 7 days</option>
                        <option value="30">Repeat every 30 days</option>
                    </select>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control rounded-pill form-control-lg" placeholder="Comment"
                           name="description">
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button class="btn mt-5 rounded-pill btn-lg btn-custom btn-block text-uppercase">Create</button>
                <div class="form-group">
                    <span id="commission">Commission: <span id="com">0.00</span>(${commission}%)</span><br>
                    <span id="total">Total: <span id="tot">0.00</span></span>
                </div>
            </form>
        <#else>
            No credit card. <a href="/profile">Go to profile.</a>
        </#if>
    </div>
</@c.page>