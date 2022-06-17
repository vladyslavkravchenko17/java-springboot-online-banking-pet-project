<#import "../parts/common.ftl" as c>

<@c.page>
   <div align="center" class="d-flex justify-content-center align-items-center" style="margin-top: 70px;">
      <#if cards?hasContent>
         <form action="/subscription" method="post" onsubmit="return confirm('Do you want to purchase this subscription?');">
            <#if message??>
               <div class="bar success">
                  <i class="ico">&#10004;</i> ${message}
               </div>
            </#if>
            <#if error??>
               <div class="mb-2 mt-2 bar error">
                  <i class="ico">&#9747;</i> ${error}</div>
            </#if>
            <h5>1 Month of ${info.name} - ${info.sumPerMonth} UAH</h5>
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
            <input type="hidden" name="id" value="${info.id}">
            <input type="hidden" name="sum" value="${info.sumPerMonth}">
            <button class="btn mt-5 rounded-pill btn-lg btn-custom btn-block text-uppercase">Purchase</button>
         </form>
      <#else>
         No credit card. <a href="/profile">Go to profile.</a>
      </#if>
   </div>
</@c.page>