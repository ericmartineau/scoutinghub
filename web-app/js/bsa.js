/**
 * Main function on page load.  The leaderQuery keypress should only run once (as opposed to each time ajax is completed
 */
var closeTimeout;
var queryTimeouts = {};
jQuery(document).ready(function() {

    keypressDelay('leaderQuery', jQuery("#leaderQuery"), leaderQuery, 150);

    jQuery(window).bind('click', function() {
        if (!closeTimeout) {
            closeTimeout = setTimeout(closeAllPopups, 200);
        }
    });
    decorate();
});

function keypressDelay(key, $jq, closure, delay) {
    $jq.keypress(function() {
        if (queryTimeouts[key]) {
            clearTimeout(queryTimeouts[key]);
            delete queryTimeouts[key];
        }
        queryTimeouts[key] = setTimeout(closure, delay);
    });
}

function closeAllPopups() {
    jQuery("div.ctx-menu").each(closePopUp);
    closeTimeout = null;
}


/**
 * Initiates the top-right leader search
 */
function leaderQuery() {
    var searchTerm = jQuery("#leaderQuery").val();
    jQuery("#content").html("<div class='spinner'>Loading...<br /><img src='/scoutinghub/images/loading.gif' /></div>");
    jQuery("#content").load("/scoutinghub/permissions/leaderQuery", {leaderQuery:searchTerm});
}

var currSelectId
function addAddress(selectId) {
    currSelectId = selectId
    createDialog("/scoutinghub/address/create", {}, {
                title: 'New Address',
                width:450
            })
}

function refreshAddressList() {
    if (currSelectId) {
        jQuery.getJSON("/scoutinghub/address/listJSON", {}, function(json) {
            var $select = jQuery("#" + currSelectId);
            $select.get(0).options.length = 0
            jQuery.each(json, function(index, item) {
                $select.get(0).options[$select.get(0).options.length] = new Option(item, index);
            });
        });

    }
}

/**
 * Helper function to create jquery dialog boxes
 */
function createDialog(url, data, config) {
    jQuery("#dialog").remove();
    config.modal = true;
    if (!config.width || config.width < 1) {
        config.width = 400;
    }

    jQuery("<div id='dialog'></div>").load(url, data, function(result) {
        jQuery(this).dialog(config)
    });
}

function createTooltip(selector, message) {
    jQuery(document).ready(
            (function() {

                var $this = jQuery(selector);
                if (jQuery.fn.qtip) {
                    $this.qtip(
                            {
                                content: message,
                                show: {event:'focus', solo:true},
                                position: {my: "right center", at: "left center"},
                                hide: {event:"blur"},
                                style: {
                                    classes: "ui-tooltip-rounded tooltip-display"
                                }
                            }
                    )
                }
            }));
}

function createDialogClosure(url, data, config) {
    return function() {
        createDialog(url, data, config)
    }
}

function configurePositionField(jPosition, fn) {
    jPosition.selectBox().change(fn);
}

function closeDialog() {
    jQuery("#dialog").remove();
}

/**
 * Does all the progressive enhancement stuff.
 */
function decorate() {

    //Bridges local styles with jquery styles
    jQueryBridge();

    //Common decorate
    commonDecorate();

    //Converts progress bar divs to progress bars
    jQuery(".progress").each(function() {
        var jthis = jQuery(this);
        var pct = parseInt(jthis.attr("value"));
        jthis.progressbar({value:pct});
        jthis.prepend("<span class='progressMsg'>" + pct + "%</span>")
    });

    //Creates jquery buttons
    jQuery(".ui-button").each(
            function() {
                var $this = jQuery(this);
                if ($this.hasClass("ui-state-active")) {
                    $this.mouseout(applyStyleClosure("ui-state-active"));
                    $this.mouseover(removeStyleClosure("ui-state-active"));
                }
                var config;
                if ($this.attr("buttonicon")) {
                    config = {
                        icons: {
                            primary: $this.attr("buttonicon")
                        }
                    }
                } else {
                    config = {};
                }
                $this.button(config)
            }).addClass("ui-button-style").removeClass("ui-button");

    //Creates jquery date pickers
    jQuery(".datePicker").datepicker();

    //Creates jquery select boxes
    jQuery(".selecter").selectBox();

    //Drop function that gets called when you drop one leader on top of another
    function mergeLeaders(event, ui) {
        var dragger = jQuery(ui.draggable);
        var dropper = jQuery(this);
        var leaderidA = dragger.attr("leaderid");
        var leaderidB = dropper.attr("leaderid");

        createDialog("/scoutinghub/leader/merge", {leaderA:leaderidA, leaderB:leaderidB}, {title:'Merge', modal:true, width: 500});

    }


    function startDrag() {
        jQuery(this).css("zIndex", 5000);
    }

    function stopDrag() {
        jQuery(this).css("zIndex", 500);
    }

    //Creates drag and drop regions for leader record merging
    jQuery(".leaderResult")
            .draggable({ start:startDrag, stop:stopDrag, axis: "y", opacity:0.35, cursor:'move', handle: ".leaderName", containment: ".leaderResultContainer", revert:true })
            .droppable({hoverClass: 'leaderResultAccept', accept: ".leaderResult", drop: mergeLeaders});

    configureUnitAutocomplete();

    function jQueryBridge() {
        jQuery(".big-button").button();
        jQuery(".nav a").button();
        jQuery(":submit").button().addClass("ui-button-style");
        //jQuery("h1").addClass("ui-corner-all ui-widget-header ui-state-active");
        jQuery("th").addClass("ui-widget-header");
        var $headerMenu = jQuery("div.header-menu-pe");
        $headerMenu
                .mouseover(applyStyleClosure("ui-state-hover"))
                .mouseout(removeStyleClosure("ui-state-hover", "no-bottom-border"))
                .removeClass("header-menu-pe").children("span").click(toggleMenu);
        jQuery("ul.ctx-menu-pe")
//                .menu()
                .addClass("ctx-menu").removeClass("ctx-menu-pe");

    }


    function applyStyleClosure(style) {
        return function() {
            jQuery(this).addClass(style)
        }
    }

    function removeStyleClosure(style, onlyIfNotPresent) {
        return function() {
            if (!jQuery(this).hasClass(onlyIfNotPresent)) {
                jQuery(this).removeClass(style)
            }
        }
    }


}

function closePopUp() {
    var $this = jQuery(this);
    var $menu = $this.find("ul.ctx-menu");

    var $headerMenu = $this.find("div.header-menu");
    $headerMenu.removeClass("no-bottom-border");
    $headerMenu.removeClass("ui-state-hover");
    $headerMenu.children("span").removeClass("ui-icon-circle-triangle-n");
    $headerMenu.children("span").addClass("ui-icon-circle-triangle-s");
//
//            $this.parent().removeClass("ui-state-hover");


    $menu.hide();
}

function toggleMenu() {
    setTimeout(function() {
        if (closeTimeout) {
            clearTimeout(closeTimeout);
            closeTimeout = null
        }
    }, 100);
    var $this = jQuery(this).parent();
    $menu = $this.parent().find("ul.ctx-menu");
    if ($menu.css("display") == "none") {
//            $this.removeClass("ui-corner-all");
//            $this.addClass("ui-corner-tl ui-corner-tr ui-state-hover");
        $this.addClass("no-bottom-border");
        $this.children("span").addClass("ui-icon-circle-triangle-n");
        $this.children("span").removeClass("ui-icon-circle-triangle-s");
//            $this.parent().addClass("ui-state-hover");


        $menu.show();
    } else {
        $this.parent().each(closePopUp);
//            $this.addClass("ui-corner-all");
//            $this.removeClass("ui-corner-tl ui-corner-tr ui-state-hover");

    }
}

/**
 * Makes sure that all the progressive enhancement stuff happens on ajax calls
 */
jQuery(document).bind("ajaxComplete", function() {
    var show = function() {
        jQuery(this).datepicker("show")
    };
    decorate();
    jQuery(".datePicker").datepicker().click(show).keypress(show);
});


/**
 * Google maps API Stuff
 */
var geocoder;

function showMap(address) {

    geocoder.getLatLng(
            address,
            function(point) {
                if (!point) {
                    alert(address + " not found");
                } else {
                    jQuery("#mapper").show();
                    var map = new GMap2(document.getElementById("mapper"));

                    var marker = new GMarker(point);
                    map.addOverlay(marker);
                    map.checkResize();
                    map.setCenter(point, 15);
                    map.setUIToDefault();
                    map.checkResize();


                }
            }
    );
    jQuery("#mapper").dialog({open:true, title: address, width: 800, height:600, modal:true});
}

function initializeMaps() {
    geocoder = new GClientGeocoder();
}

jQuery(document).ready(initializeMaps);







