<#macro navbar>
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-4 col-lg-3 navbar-container ">
                <nav class="navbar navbar-expand-md navbar-light">
                    <a class="logo navbar-brand"><h4>Profile controls:</h4></a>
                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbar">
                        <form>
                            <ul class="navbar-nav">
                                <li class="nav-item">
                                    <button class="btn mt-1 rounded-pill btn-lg btn-custom btn-block"
                                            formaction="/profile/data">Personal data
                                    </button>
                                </li>
                                <li class="nav-item">
                                    <button class="btn mt-1 rounded-pill btn-lg btn-custom btn-block"
                                            formaction="/profile/cards">Cards
                                    </button>
                                </li>
                                <li class="nav-item">
                                    <button class="btn mt-1 rounded-pill btn-lg btn-custom btn-block"
                                            formaction="/profile/subscriptions">Subscriptions
                                    </button>
                                </li>
                            </ul>
                        </form>
                    </div>
                </nav>
            </div>
</#macro>