<#import "../parts/common.ftl" as c>
<#import "navbar.ftl" as n>

<@c.page>
    <@n.navbar></@n.navbar>
    <div class="col-md-8 col-lg-9 content-container align-items-center justify-content-center mt-3">
        <form action="/profile/card/create" method="post">
            <h2>Create card:</h2>
            <div class="form-group">
                <select name="cardType" class="form-control rounded-pill form-control-lg" id="select">
                    <option value="DEBIT_CARD">Debit Card</option>
                    <option value="CREDIT_CARD">Credit Card</option>
                    <option value="COMPANY_CARD">Company Card</option>
                </select>
            </div>
            <div class="form-group select-blocks"style="display:none" id="COMPANY_CARD">
                <input type="text" class="form-control rounded-pill form-control-lg" placeholder="Company name"
                       name="companyName">
            </div>
            <div class="form-group">
                <select name="currencyName" class="form-control rounded-pill form-control-lg">
                    <option value="USD">USD</option>
                    <option value="EUR">EUR</option>
                    <option value="UAH">UAH</option>
                </select>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button class="btn mt-2 rounded-pill btn-lg btn-custom btn-block text-uppercase">Create new card</button>
        </form>
    </div>
</@c.page>