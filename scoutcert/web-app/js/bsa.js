function leaderQuery() {
    var searchTerm = jQuery("#leaderQuery").val();
    jQuery("#content").load("/scoutcert/permissions/leaderQuery", {leaderQuery:searchTerm});
}


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

jQuery(document).ready(function() {
    jQuery("#leaderQuery").keypress(jQuery.throttle(250, leaderQuery)).focus();
    decorate()
});

function createDialog(url, data, config) {
    jQuery("#dialog").remove();
    jQuery("<div id='dialog'></div>").load(url, data, function(result) {
        jQuery(this).dialog(config)
    });
}

function decorate() {
    jQueryBridge();
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
    jQuery(".progress").each(function() {
        var jthis = jQuery(this);
        var pct = parseInt(jthis.attr("value"));
        jthis.progressbar({value:pct});
        jthis.prepend("<span class='progressMsg'>" + pct + "%</span>")
    });
    jQuery(".ui-button").button().removeClass("ui-button");
    jQuery(".datePicker").datepicker();
    jQuery(".selecter").selectBox();
//    jQuery(".unitSelectTree")
//            .bind("open_node.jstree loaded.jstree", nodeOpened)
//            .jstree(jsTreeConfig())
//            .delegate("a", "click", delegateClicks);
//    jQuery(".unitSelectTree a").live("click", function(e) {
//
//    });

    jQuery(".unitSelector").each(function() {
        var jthis = jQuery(this);
        var idField = jQuery("#" + jthis.attr("idField"));
        var positionField = jQuery("#" + jthis.attr("positionField"))
        jthis.autocomplete({
            source:getUnitData,
            autoFill: true,
            mustMatch: true,
            matchContains: false,
            scrollHeight: 220,
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
                getApplicablePositions(positionField, ui.item.key)
                return false;
            }
        });


    });

    function jQueryBridge() {
        jQuery(".nav a").button();
        jQuery(":submit").button()
        //jQuery("h1").addClass("ui-corner-all ui-widget-header ui-state-active");
        jQuery("th").addClass("ui-widget-header");
    }

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

//    {dateFormat: 'mm/dd/yy', changeYear:true, yearRange: '-11:+1'}).removeClass("datePicker");

}

//source:function(term, callback)
//        {
//            jQuery.getJSON("/cubtrail/secUser/findByUsername", term, function(json)
//            {
//                callback(json);
//            });
//        }

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


function getUnitData(term, callback) {
    jQuery.getJSON("/scoutcert/scoutGroup/findUnits", term, function(json) {
        callback(json)
    })
}

jQuery(document).bind("ajaxComplete", function() {
    var show = function() {
        jQuery(this).datepicker("show")
    };
    decorate();
    jQuery(".datePicker").datepicker().click(show).keypress(show);


//    {dateFormat: 'mm/dd/yy', changeYear:true, yearRange: '-11:+1'}).removeClass("datePicker");

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






