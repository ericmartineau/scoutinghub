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
        var idField = jQuery("#" + this.id + "Id");
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
                $(this).val(ui.item.label);
                return false;
            },
            select: function(event, ui) {
                $(idField).val(ui.item.key);
                $(this).val(ui.item.label);
                return false;
            }
        });
    });

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

});

//source:function(term, callback)
//        {
//            jQuery.getJSON("/cubtrail/secUser/findByUsername", term, function(json)
//            {
//                callback(json);
//            });
//        }

function getUnitData(term, callback) {
    jQuery.getJSON("/scoutcert/scoutGroup/findUnits", term, function(json) {
        callback(json)
    })
}

jQuery(document).bind("ajaxComplete", function() {
    var show = function() {
        jQuery(this).datepicker("show")
    };
    jQuery(".datePicker").datepicker().click(show).keypress(show);


//    {dateFormat: 'mm/dd/yy', changeYear:true, yearRange: '-11:+1'}).removeClass("datePicker");

});





