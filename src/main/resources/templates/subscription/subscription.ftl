<#import "../parts/common.ftl" as c>

<@c.page>
    <div class="col-md-8 products mt-5" style="margin-left: 200px; margin-bottom: 50px">
        <h2 class="font-weight-light text-uppercase" align="center">Subscriptions:</h2>
        <#if message??>
            <div class="bar success">
                <i class="ico">&#10004;</i> ${message}
            </div>
        </#if>
        <#if error??>
            <div class="mb-2 mt-2 bar error">
                <i class="ico">&#9747;</i> ${error}</div>
        </#if>
        <#if info??>
            <div class="mb-2 mt-2 bar info">
                <i class="ico">&#8505;</i> ${info}</div>
        </#if>
        <div class="row">
            <#list infos as i>
                <div class="col-sm-4">
                    <div class="product">
                        <div class="product-img">
                            <a href="/subscription/${i.id}"><img src="../images/${i.imageUrl}" alt=""></a>
                        </div>
                        <p class="product-title">
                            <a href="/subscription/${i.id}">${i.name}</a>
                        </p>
                        <p class="product-desc">${i.description}</p>
                        <p class="product-price">${i.sumPerMonth} UAH/Month</p>
                    </div>
                </div>
            </#list>
        </div>
    </div>
</@c.page>