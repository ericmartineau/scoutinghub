function leaderQuery() {

    var searchTerm = jQuery("#leaderQuery").val();
    jQuery("#content").load("/scoutcert/permissions/leaderQuery", {leaderQuery:searchTerm});
}


jQuery(document).ready(function() {
    jQuery("#leaderQuery").keypress(jQuery.throttle(250, leaderQuery)).focus();
    jQuery(".datePicker").datepicker();
//    {dateFormat: 'mm/dd/yy', changeYear:true, yearRange: '-11:+1'}).removeClass("datePicker");

});

jQuery(document).bind("ajaxComplete", function() {
    var show = function() {
        jQuery(this).datepicker("show")
    };
    jQuery(".datePicker").datepicker().click(show).keypress(show);



//    {dateFormat: 'mm/dd/yy', changeYear:true, yearRange: '-11:+1'}).removeClass("datePicker");

});




