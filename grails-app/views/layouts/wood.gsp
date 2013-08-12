<!doctype html>
<html>
<head>
    <meta charset="utf-8">

    <!-- Always force latest IE rendering engine or request Chrome Frame -->
    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">

    <title><g:layoutTitle/></title>

    <r:require module="bsa"/>
    <r:require module="application"/>
    <r:require module="wood"/>

    <meta name="viewport" content="width=device-width, maximum-scale=1, initial-scale=1, user-scalable=0">

    <g:layoutHead/>
    <r:layoutResources/>

</head>

<body onload="prettyPrint()" id="body">
<div class="navbar navbar-fixed-top navbar-inverse top-nav">
    <div class="navbar-inner">

        <div class="container">
            <div class="row-fluid">
                <div class="span12">

                    <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>




                    <div class="nav-collapse collapse">
                        <g:menu/>


                        <!-- profile -->
                        <ul class="nav pull-right primary">
                            <li class="dropdown profile">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                    <img class="menu-avatar" src="images/profile-thumb.png"/>
                                    John Smith
                                    <b class="caret"></b>
                                </a>
                                <ul class="dropdown-menu secondary ">
                                    <li class="">
                                        <a href="pages/account_settings.html">
                                            <i class="icon-cog"></i> Account settings
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <i class="icon-lock"></i> Change password
                                        </a>
                                    </li>
                                    <li class="divider"></li>
                                    <li class="">
                                        <a href="pages/login.html">
                                            <i class="icon-off"></i> Sign out
                                        </a>
                                    </li>
                                </ul>
                            </li>
                        </ul>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<section id="main">
    <div class="container">
        <g:layoutBody/>
    </div>


    <div class="container">
        <div class="row-fluid">
            <div class="span12">

                <div class="footer">
                    <h5 class="transparent-text center">
                        <div>2013 &copy; ScoutingHub</div>
                    </h5>
                </div>
            </div>
        </div>
    </div>
</section>
<r:layoutResources/>

</body>
</html>