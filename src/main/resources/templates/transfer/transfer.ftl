<#import "../parts/common.ftl" as c>
<#import "/spring.ftl" as spring />
<@c.page>
    <div align="center" class="d-flex justify-content-center align-items-center mt-5">
        <#if cards?hasContent>
            <form onsubmit="return confirm('Do you want to transfer money?');" action="/transfer" method="post">
                <h3 class="font-weight-light text-uppercase">Transfer money:</h3>
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
                </div>
                <div class="form-group">
                    <input type="text"
                           class="form-control rounded-pill form-control-lg ${(receiverNumberError??)?string('is-invalid', '')}"
                           placeholder="Card number"
                           name="receiverNumber" value="<#if transaction??>${transaction.receiverNumber}</#if>">
                    <#if receiverNumberError??>
                        <div class="invalid-feedback">${receiverNumberError}</div>
                    </#if>
                </div>
                <div class="form-group">
                    <input type="number" id="asking"
                           class="form-control rounded-pill form-control-lg ${(receivedSumError??)?string('is-invalid', '')}"
                           placeholder="Sum"
                           name="receivedSum" value="<#if transaction??>${transaction.receivedSum}<#else>0</#if>">
                    <#if receivedSumError??>
                        <div class="invalid-feedback">${receivedSumError}</div>
                    </#if>
                </div>
                <input type="text"
                       class="form-control rounded-pill form-control-lg"
                       placeholder="Comment"
                       name="comment" value="<#if transaction??>${transaction.comment}</#if>">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button class="btn mt-5 rounded-pill btn-lg btn-custom btn-block text-uppercase">Send money</button>
                <div class="form-group">
                    <span id="commission">Commission: <span id="com">0.00</span>(${commission}%)</span><br>
                    <span id="total">Total: <span id="tot">0.00</span></span>
                </div>
                <div class="form-group mt-2">
                    <a href="/transfer/scheduled">Create planned payment.</a>
                </div>
            </form>
        <#else>
            No credit card. <a href="/profile">Go to profile.</a>
        </#if>
    </div>
</@c.page>