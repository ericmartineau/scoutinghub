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
                        <ul class="nav primary">
                            <li class="brand">
                                <span class="title"><i class="icon-cogs"></i> ScoutingHub</span>
                            </li>

                            <li class="">
                                <a href="pages/dashboard.html">
                                    <i class="icon-dashboard"></i> Dashboard
                                </a>
                            </li>

                            <!-- components -->
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                    <i class="icon-list"></i> Components
                                    <b class="caret"></b>
                                </a>

                                <ul class="dropdown-menu secondary">
                                    <li class="">
                                        <a href="pages/forms.html">
                                            <i class="icon-tasks"></i> Forms
                                        </a>
                                    </li>

                                    <li class="">
                                        <a href="pages/buttons.html">
                                            <i class="icon-ok-sign"></i> Buttons
                                        </a>
                                    </li>

                                    <li class="">
                                        <a href="pages/widgets.html">
                                            <i class="icon-gift"></i> Widgets
                                        </a>
                                    </li>

                                    <li class="">
                                        <a href="pages/tables.html">
                                            <i class="icon-table"></i> Tables
                                        </a>
                                    </li>

                                    <li class="">
                                        <a href="pages/charts.html">
                                            <i class="icon-bar-chart"></i> Charts
                                        </a>
                                    </li>
                                    <li class="">
                                        <a href="pages/grid.html">
                                            <i class="icon-th"></i> Grid
                                        </a>
                                    </li>

                                </ul>
                            </li>

                            <!-- pages -->
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                    <i class="icon-copy"></i> Pages
                                    <b class="caret"></b>
                                </a>
                                <ul class="dropdown-menu secondary">
                                    <li class="">
                                        <a href="pages/gallery.html">
                                            <i class="icon-picture"></i> Gallery
                                        </a>
                                    </li>
                                    <li class="">
                                        <a href="pages/faq.html">
                                            <i class="icon-question-sign"></i> FAQ
                                        </a>
                                    </li>
                                    <li class="">
                                        <a href="pages/pricing_plans.html">
                                            <i class="icon-money"></i> Pricing plans
                                        </a>
                                    </li>
                                    <li class="">
                                        <a href="pages/login.html">
                                            <i class="icon-briefcase"></i> Sign in
                                        </a>
                                    </li>
                                    <li class="">
                                        <a href="pages/register.html">
                                            <i class="icon-unlock"></i> Register
                                        </a>
                                    </li>
                                    <li class="">
                                        <a href="pages/error404.html">
                                            <i class="icon-exclamation-sign"></i> Error 404
                                        </a>
                                    </li>
                                </ul>
                            </li>

                            <!-- extras -->
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                    <i class="icon-gift"></i> Extras
                                    <b class="caret"></b>
                                </a>
                                <ul class="dropdown-menu secondary">
                                    <li class="">
                                        <a href="pages/icons.html">
                                            <i class="icon-picture"></i> Icons
                                        </a>
                                    </li>
                                    <li class="">
                                        <a href="pages/fullcalendar_events.html">
                                            <i class="icon-calendar"></i> Calendar with events
                                        </a>
                                    </li>
                                </ul>
                            </li>

                            %{--<li id="docs-trigger">--}%
                            %{--<button style="padding-left: 10px; margin-top: 14px" class="btn btn-inverse btn-mini"--}%
                            %{--type="button" onclick="showDocs()">--}%
                            %{--<span id="dtrigger">Show Docs</span>--}%
                            %{--</button>--}%
                            %{--</li>--}%
                        </ul>

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

    <g:layoutBody/>



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