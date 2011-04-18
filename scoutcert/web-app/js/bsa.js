/**
 * Main function on page load.  The leaderQuery keypress should only run once (as opposed to each time ajax is completed
 */
var closeTimeout;
var queryTimeout;
jQuery(document).ready(function() {
    jQuery("#leaderQuery").keypress(function() {
        if (queryTimeout) {
            clearTimeout(queryTimeout);
            queryTimeout = null;
        }
        queryTimeout = setTimeout(leaderQuery, 150);
    });


    jQuery(window).bind('click', function() {
        if (!closeTimeout) {
            closeTimeout = setTimeout(closeAllPopups, 200);
        }
    });
    decorate();
});

function closeAllPopups() {
    jQuery("div.ctx-menu").each(closePopUp);
    closeTimeout = null;
}


/**
 * Initiates the top-right leader search
 */
function leaderQuery() {
    var searchTerm = jQuery("#leaderQuery").val();
    jQuery("#content").html("<div class='spinner'>Loading...<br /><img src='/scoutcert/images/loading.gif' /></div>");
    jQuery("#content").load("/scoutcert/permissions/leaderQuery", {leaderQuery:searchTerm});
}


/**
 * Used for a pop-up selector for a unit - don't think it's actually being used
 * @param name
 * @param unitid
 */
function selectUnit(name, unitid) {
    jQuery("#dialog").remove();
    var params = unitid ? {unitId:unitid} : "";
    jQuery("<div id='dialog'></div>").load("/scoutcert/scoutGroup/selectUnit", params, function() {
        jQuery(this).dialog({
            modal:true,
            title:'Select a Unit',
            buttons: {
                "OK": function() {
                    jQuery(this).dialog("close");
                },
                "Cancel": function() {
                    jQuery(this).dialog("close");
                }
            }

        })
    });
}

var currSelectId
function addAddress(selectId) {
    currSelectId = selectId
    createDialog("/scoutcert/address/create", {}, {
        title: 'New Address',
        width:450
    })
}

function refreshAddressList() {
    if (currSelectId) {
        jQuery.getJSON("/scoutcert/address/listJSON", {}, function(json) {
            var $select = jQuery("#" + currSelectId);
            $select.get(0).options.length = 0
            jQuery.each(json, function(index, item) {
                $select.get(0).options[$select.get(0).options.length] = new Option(item, index);
            });
        });

    }
}


/**
 * The following three functions are for the jstree implementation for the scouting group tree browser.
 */
function nodeOpened(event, data) {
    jQuery(this).find("a").attr("href", "javascript:void(0)");
}

function jsTreeConfig(url) {
    return {
        "json_data" : {
            ajax : {
                url : "/scoutcert/scoutGroup/selectUnitTree",
                data : function (n) {
                    return {
                        id : n.attr ? n.attr("id") : "0"
                    };
                }
            }},

        plugins : [ "themes", "json_data", "ui" ,"search"],
        themes : {
            theme : "default",
            dots : false,
            icons : false
        }
    }
}


function delegateClicks(e) {
    jQuery("#achievementTree").jstree("toggle_node", this);
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

//    if(!config.height || config.height < 1) {
//        config.width = 300;
//    }
    jQuery("<div id='dialog'></div>").load(url, data, function(result) {
        jQuery(this).dialog(config)
    });
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

    //Make all the training registration links open jquery dialogs
    jQuery(".registerForTraining").each(function() {
        var oldHref = this.href;
        this.href = "javascript:void(0)";
        jQuery(this).click(
                function() {
                    createDialog(oldHref, {}, {
                        title: "Training Class",
                        width: 500,
                        modal:true


                    });
                }).removeClass("registerForTraining")

    });

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
                if($this.hasClass("ui-state-active")) {
                    $this.mouseout(applyStyleClosure("ui-state-active"));
                    $this.mouseover(removeStyleClosure("ui-state-active"));
                }
                var config;
                if($this.attr("buttonicon")) {
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

        createDialog("/scoutcert/leader/merge", {leaderA:leaderidA, leaderB:leaderidB}, {title:'Merge', modal:true, width: 500});

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

    //Creates unit selector widget (autocomplete) (that communicates with position drop-down boxes)
    jQuery(".unitSelector").each(function() {
        var jthis = jQuery(this);

        //This prevents it from running again
        jthis.removeClass("unitSelector");


        var idField = jQuery("#" + jthis.attr("idField"));
        var positionField = jQuery("#" + jthis.attr("positionField"));

        //Let's initialize the drop-down right now
        if (idField.val()) {
            getApplicablePositions(positionField, idField.val());
        }


        jthis.autocomplete({
            source:getUnitData,
//            autoFill: true,
            mustMatch: true,
            matchContains: false,
//            scrollHeight: 220,
            formatItem: function(data, i, total) {
                return data[0]
            },
            focus: function(event, ui) {
                $(idField).val(ui.item.key);
                jthis.val(ui.item.label);
                return false;
            },
            select: function(event, ui) {
                $(idField).val(ui.item.key);
                jthis.val(ui.item.label);
                getApplicablePositions(positionField, ui.item.key);
                return false;
            }
        });


    });

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
                .menu()
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


    /**
     * Next three functions help out with the autocomplete text control
     */
    function findValue(li) {
        if (li == null)
            return alert("No match!");

        if (!!li.extra)
            var sValue = li.extra[0];
        else
            var sValue = li.selectValue;

        alert(sValue);
    }


    function selectItem(li) {
        findValue(li);
    }

    function formatItem(row) {
        return row[0]; //value
    }

    /**
     * Ajax call to retrieve positions that apply based on the unit type
     * @param selectFld
     * @param unitId
     */
    function getApplicablePositions(selectFld, unitId) {
        selectFld.children().remove().end().append('<option selected value="">Please select a unit</option>')
        jQuery.getJSON("/scoutcert/leaderGroup/getApplicablePositions/" + unitId, null, function(json) {
            selectFld.html("");
            for (i in json) {
                var obj = json[i];
                selectFld.selectBox('options', json);
//            append("<option value='" + obj.objectValue + "'>" + obj.objectLabel + "</option>");
            }
        });
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


function getUnitData(term, callback) {
    jQuery.getJSON("/scoutcert/scoutGroup/findUnits", term, function(json) {
        callback(json)
    })
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







