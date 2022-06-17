<#import "../parts/common.ftl" as c>

<@c.page>
    <div align="center" class="d-flex justify-content-center align-items-center" style="margin-top: 70px;">
        <#if cards?hasContent>
            <form action="/atm" method="post"
                  style="border-style: solid; border-width:3px; border-color: #512293;  border-radius: 15px; margin-outside:10px;
                    background:#e8d6ff">
                <div style="margin-top:40px; margin-bottom:30px; margin-left:90px; margin-right:90px;">
                    <h1 class="font-weight-light text-uppercase" style="color: black; margin-bottom:10px;">ATM:</h1>
                    <#if message??>
                        <div class="bar success">
                            <i class="ico">&#10004;</i> ${message}
                        </div>
                    </#if>
                    <#if error?hasContent>
                        <div class="mb-2 mt-2 bar error">
                            <i class="ico">&#9747;</i> ${error}</div>
                    </#if>
                    <div class="form-group">
                        <select name="number" class="form-control rounded-pill form-control-lg">
                            <#list cards as card>
                                <option value=${card.number}>${card.number + " - " + card.balance + " " + card.currency}</option>
                            </#list>
                        </select>
                    </div>
                    <div class="form-group">
                        <select name="amountOfMoney" class="form-control rounded-pill form-control-lg" id="asking">
                            <option value=1>1 UAH</option>
                            <option value=2>2 UAH</option>
                            <option value=5>5 UAH</option>
                            <option value=10>10 UAH</option>
                            <option value=20>20 UAH</option>
                            <option value=50>50 UAH</option>
                            <option value=100>100 UAH</option>
                            <option value=200>200 UAH</option>
                            <option value=500>500 UAH</option>
                            <option value=1000>1000 UAH</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <select name="actionType" class="form-control rounded-pill form-control-lg">
                            <option value="deposit">Deposit</option>
                            <option value="withdraw">Withdraw</option>
                        </select>
                    </div>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button class="btn mt-5 rounded-pill btn-lg btn-custom btn-block text-uppercase">Submit</button>
                    <div style="margin-bottom: 80px;">
                        <span id="commission">Commission: <span id="com">0.00</span>(${commission}%)</span><br>
                        <span id="total">Total: <span id="tot">0.00</span></span>
                    </div>
                </div>
            </form>
        <#else>
            No credit card. <a href="/profile">Go to profile.</a>
        </#if>
    </div>
</@c.page>