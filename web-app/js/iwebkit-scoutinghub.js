jQuery(document).ready(commonDecorate).bind("ajaxComplete", commonDecorate);

function closeDialog() {
    jQuery("#body").show();
    jQuery("#dialog").html("");
    jQuery("#leftnav").remove();
}

function openLightbox(url) {
    jQuery("#body").hide();
    jQuery("#dialog").load(url);

    jQuery("#leftnav").remove();
    jQuery("<div id='leftnav'><a href='javascript:closeDialog()'>Back</a></div>").appendTo(jQuery("#topbar"));
}

function createDialogClosure(oldHref, data, config) {
    return function() {
        openLightbox(oldHref)
    }
}

