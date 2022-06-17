<#import "style.ftl" as s>
<#macro page>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Vl-Bank</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
              integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
              crossorigin="anonymous">
        <@s.style></@s.style>
    </head>
    <body>
    <script src="/js/jquery-3.6.0.min.js"></script>
    <#include "navbar.ftl">
    <#nested>
    <script src="/js/main.js"></script>
    </body>
    </html>
</#macro>