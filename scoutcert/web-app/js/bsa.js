
function leaderQuery() {
    var searchTerm = jQuery("#leaderQuery").val();
    jQuery("#content").load("/scoutcert/permissions/leaderQuery", {leaderQuery:searchTerm});
}


jQuery(document).ready(function() {
    jQuery("#leaderQuery").keypress(jQuery.throttle(250, leaderQuery)).focus();
});

