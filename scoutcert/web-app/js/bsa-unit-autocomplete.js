function configureUnitAutocomplete() {

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
        var currVal = selectFld.val();
        selectFld.children().remove().end().append('<option selected value="">Please select a unit</option>')
        jQuery.getJSON("/scoutcert/leaderGroup/getApplicablePositions/" + unitId, null, function(json) {
            selectFld.html("");
            for (i in json) {
                var obj = json[i];
                selectFld.selectBox('options', json);
//            append("<option value='" + obj.objectValue + "'>" + obj.objectLabel + "</option>");
            }
            selectFld.val(currVal);
        });

    }
}

function getUnitData(term, callback) {
    jQuery.getJSON("/scoutcert/scoutGroup/findUnits", term, function(json) {
        callback(json)
    })
}