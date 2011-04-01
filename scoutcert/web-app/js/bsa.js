function leaderQuery() {
    var searchTerm = jQuery("#leaderQuery").val();
    jQuery("#content").load("/scoutcert/permissions/leaderQuery", {leaderQuery:searchTerm});
}


jQuery(document).ready(function() {
    jQuery("#leaderQuery").keypress(jQuery.throttle(250, leaderQuery)).focus();
    jQuery(".datePicker").datepicker({dateFormat: 'mm/dd/yy', changeYear:true, yearRange: '-11:+1'}).removeClass("datePicker");

});

jQuery(document).bind("ajaxComplete", function() {
    jQuery(".datePicker").datepicker({dateFormat: 'mm/dd/yy', changeYear:true, yearRange: '-11:+1'}).removeClass("datePicker");

});




