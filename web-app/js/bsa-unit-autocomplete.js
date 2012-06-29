function commonDecorate() {
    jQuery("a.lightbox").each(
        function() {
            var oldHref = this.href;
            this.href = "javascript:void(0)";
            var config = {};
            var $this = jQuery(this);
            if ($this.attr("title")) {
                config.title = $this.attr("title");
            }
            if ($this.attr("lbheight")) {
                config.height = parseInt($this.attr("lbheight"));
            }
            if ($this.attr("lbwidth")) {
                config.width = parseInt($this.attr("lbwidth"));
            }

            $this.click(createDialogClosure(oldHref, {}, config));
        }).removeClass("lightbox");

    //Dialog forms operate in a lightbox - we want to submit the form via ajax, and read the json response.
    jQuery("form.dialog-form").each(
        function() {
            var $this = jQuery(this);
            $this.submit(function(event) { //hijack form submission
                event.preventDefault(); //prevent form from submitting
                // Send the request
                jQuery.post($this.attr("action"), $this.serialize(), function(json) {
                    if (json.success) {
                        closeDialog();
                        if ($this.attr("reload") != "false") {
                            window.location.reload();
                        }
                    } else {
                        jQuery("div.errors", "#dialog").remove();
                        var errDiv = jQuery("<div class='errors'></div>");
                        errDiv
                            .hide()
                            .prependTo($this)
                            .load("/scoutinghub/errorRendering/show", {}, function() {
                                errDiv.show("blind", { direction: "vertical" }, 200);
                            });
                    }
                }, 'json');
                return false;
            });
        }).removeClass("dialog-form");

    configureUnitAutocomplete();
}

function configureUnitAutocomplete() {

    //Creates unit selector widget (autocomplete) (that communicates with position drop-down boxes)
    jQuery(".unitSelector").each(function() {
        var jthis = jQuery(this);

        //This prevents it from running again
        jthis.removeClass("unitSelector");

        var idName = jthis.attr("idfield");
        if (idName) {
            idName = idName.replace(".", "\\.");
        }

        var idField = jQuery("#" + idName);
        var positionField = jQuery("#" + jthis.attr("positionfield"));

        //This nonsense is here because the selectBox component we are using
        //doesn't seem to fire or bind to the original select box - so
        //we need a different implementation for mobile.
        configurePositionField(positionField, function() {
            getApplicablePositions(positionField, jthis, idField);
        });


        //Let's initialize the drop-down right now (this will be called on page load)
        if (idField.val()) {
            getApplicablePositions(positionField, jthis, idField);
        }

        //Configurable function to allow a callback on change
        var onChange = jthis.attr("data-onchange");

        jthis.autocomplete({
            source:getUnitDataClosure(positionField),
            mustMatch: true,
            matchContains: false,
            formatItem: function(data, i, total) {
                return data[0]
            },
            focus: function(event, ui) {
                idField.val(ui.item.key);
                jthis.val(ui.item.label);
                return false;
            },
            select: function(event, ui) {
                idField.val(ui.item.key);
                jthis.val(ui.item.label);
                if(onChange) {
                    eval(onChange + "()");
                }
                getApplicablePositions(positionField, jthis, idField);
                return false;
            }
        });
    });

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
     */
    function getApplicablePositions(selectFld, unitNameFld, unitIdFld) {
        var currVal = selectFld.val();
        if (currVal && unitIdFld.val()) {

            jQuery.getJSON("/scoutinghub/leaderGroup/checkPositionAndUnit/" + unitIdFld.val(), {position:currVal}, function(json) {

                if (json.mismatch) {
                    //Clear out the current values for unit in the case of a mismatch
                    unitNameFld.val("");
                    unitIdFld.val("");

                    //Add tooltip
                    if (jQuery.fn.qtip) {
                        unitNameFld.qtip(
                            {
                                content: json.msgText,
                                show: {ready:true, event:false, solo:true},
                                position: {my: "right center", at: "left center"},
                                hide: {event:"click"},
                                style: {
                                    classes: "ui-tooltip-rounded tooltip-display"
                                }
                            }
                        )
                    }
                }
            });
        }
    }
}

/*
 * This closure allows us to put the position select box in scope for the callback.
 */
function getUnitDataClosure($position) {
    return function(term, callback) {
        term.position = $position.val();
        jQuery.getJSON("/scoutinghub/findScoutGroup/findUnits", term, function(json) {
            callback(json)
        });
    }
}

function configureDrillDown(parentSelect, childSelect, url, runNow) {
    setTimeout(function() {
        var $parentSelect = jQuery(parentSelect);
        var $childSelect = jQuery(childSelect);
        getSelectTarget($parentSelect).change(function() {
            jQuery.getJSON(url, {selectedValue: $parentSelect.val()}, function(json) {
                var currVal = $childSelect.val();
                $childSelect.html("");
                setSelectOptions($childSelect, json);
                $childSelect.val(currVal);
            });
        });
    }, 500);


}



