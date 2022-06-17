<#macro style>
    <style>
        @import url('https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900');
        body{
            font-family: 'Poppins', sans-serif;
            font-size: 16px;
            background: #eee;
            color:#666;
        }
        .login-container{
            height: 100vh;
            width: 100%;
        }
        .login-form{
            margin: auto;
            width: 370px;
            padding: 15px;
            max-width: 100%;
        }
        .login-form .form-control{
            font-size: 15px;
            min-height: 48px;
            font-weight: 500;
        }
        .login-form a{
            text-decoration: none;
            color:#666;
        }
        .login-form a:hover{
            color:#723dbe;
        }
        .forgot-link{
            font-size: 13px;
        }

        .form-control:focus{
            border-color:#723dbe;
            box-shadow: 0 0 0 0.2rem rgba(114,61,190,.25);
        }
        .btn-custom{
            background: #723dbe;
            border-color:#723dbe;
            color:#fff;
            font-size: 15px;
            font-weight: 600;
            min-height: 48px;
        }
        .btn-custom:focus,
        .btn-custom:hover,
        .btn-custom:active,
        .btn-custom:active:focus{
            background: #54229d;
            border-color: #54229d;
            color:#fff;
        }
        .btn-custom:focus{
            box-shadow: 0 0 0 0.2rem rgba(114,61,190,.25);
        }

        .block {
            display: none;
        }

        #load {
            font-size: 20px;
        }

        .bar {
            padding: 10px;
            margin: 10px;
            color: #333;
            background: #fafafa;
            border: 1px solid #ccc;
        }

        .error {
            color: #ba3939;
            background: #ffe0e0;
            border: 1px solid #a33a3a;
        }

        i.ico {
            display: inline-block;
            width: 20px;
            text-align: center;
            font-style: normal;
            font-weight: bold;
        }

        .success {
            color: #2b7515;
            background: #ecffd6;
            border: 1px solid #617c42;
        }

        .warn {
            color: #756e15;
            background: #fffbd1;
            border: 1px solid #87803e;
        }

        .info {
            color: #204a8e;
            background: #c9ddff;
            border: 1px solid #4c699b;
        }

        /*s*/


        img {
            height: auto;
            max-width: 100%;
        }
        .content{
            margin-top: 50px;
        }
        .product {
            background: #fff none repeat scroll 0 0;
            border: 1px solid #c0c0c0;
            height: 390px;
            overflow: hidden;
            padding: 25px 15px;
            position: relative;
            text-align: center;
            transition: all 0.5s ease 0s;
            margin-bottom: 20px;
        }
        .product:hover {
            box-shadow: 0 0 16px rgba(0, 0, 0, 0.5);
        }
        .product-img {
            height: 200px;
        }
        .product-title a {
            color: #000;
            font-weight: 500;
            text-transform: uppercase;
        }
        .product-desc {
            max-height: 60px;
            overflow: hidden;
        }
        .product-price {
            bottom: 15px;
            left: 0;
            position: absolute;
            width: 100%;
            color: #d51e08;
            font-size: 18px;
            font-weight: 500;
        }
        .flag {
            width: 16px;
            height: 11px;
            background:url(https://russianblogs.com/images/804/34f834f205d7ae982a05a71a207a1acc.png) no-repeat
        }

        .flag.flag-ua {background-position: -112px -154px}

        #pages {
            list-style-type: none;
            display: flex;
            justify-content: center;
        }

        .pagination a.active {
            background-color: #4CAF50;
            color: white;
        }

        .pagination a:hover:not(.active) {background-color: #ddd;}

        table, th, td {
            border: 1px solid;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        .noborder {
            border: 0px solid;
        }

        @media (min-width: 768px) {
            .navbar-container {
                position: sticky;
                top: 0;
                overflow-y: auto;
                height: 100vh;
            }
            .navbar-container .navbar {
                align-items: flex-start;
                justify-content: flex-start;
                flex-wrap: nowrap;
                flex-direction: column;
                height: 100%;
            }
            .navbar-container .navbar-collapse {
                align-items: flex-start;
            }
            .navbar-container .nav {
                flex-direction: column;
                flex-wrap: nowrap;
            }
            .navbar-container .navbar-nav {
                flex-direction: column !important;
            }
        }

        .tablemanager th.sorterHeader {
            cursor: pointer;
        }
        .tablemanager th.sorterHeader:after {
            content: " \f0dc";
            font-family: "FontAwesome";
        }
        /*Style sort desc*/
        .tablemanager th.sortingDesc:after {
            content: " \f0dd";
            font-family: "FontAwesome";
        }
        /*Style sort asc*/
        .tablemanager th.sortingAsc:after {
            content: " \f0de";
            font-family: "FontAwesome";
        }
        /*Style disabled*/
        .tablemanager th.disableSort {

        }
        #for_numrows {
            padding: 10px;
            float: left;
        }
        #for_filter_by {
            padding: 10px;
            float: right;
        }
        #pagesControllers {
            display: block;
            text-align: center;
        }
    </style>
</#macro>