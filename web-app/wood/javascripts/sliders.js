/* Vertical sliders */

$(function() {
    // setup master volume
    $( "#master" ).slider({
        value: 60,
        orientation: "horizontal",
        range: "min",
        animate: true
    });
    // setup graphic EQ
    $( "#eq > span" ).each(function() {
        // read initial values from markup and remove that
        var value = parseInt( $( this ).text(), 10 );
        $( this ).empty().slider({
            value: value,
            range: "min",
            animate: true,
            orientation: "vertical"
        });
    });
});

$(function() {
    $( "#slider-range" ).slider({
        range: true,
        min: 0,
        max: 500,
        values: [ 75, 300 ],
        slide: function( event, ui ) {
            $( "#amount" ).val( "$" + ui.values[ 0 ] + " - $" + ui.values[ 1 ] );
        }
    });
    $( "#amount" ).val( "$" + $( "#slider-range" ).slider( "values", 0 ) +
        " - $" + $( "#slider-range" ).slider( "values", 1 ) );
});

$(function() {
    $( "#slider-range-min" ).slider({
        range: "min",
        value: 410,
        min: 1,
        max: 700,
        slide: function( event, ui ) {
            $( "#amount1" ).val( "$" + ui.value );
        }
    });
    $( "#amount1" ).val( "$" + $( "#slider-range-min" ).slider( "value" ) );
});


$(function() {
    $( "#slider-range-min2" ).slider({
        range: "min",
        value: 190,
        min: 1,
        max: 700,
        slide: function( event, ui ) {
            $( "#amount1" ).val( "$" + ui.value );
        }
    });
    $( "#amount1" ).val( "$" + $( "#slider-range-min" ).slider( "value" ) );
});

$(function() {
    $( "#slider-range-min3" ).slider({
        range: "min",
        value: 264,
        min: 1,
        max: 700,
        slide: function( event, ui ) {
            $( "#amount1" ).val( "$" + ui.value );
        }
    });
    $( "#amount1" ).val( "$" + $( "#slider-range-min" ).slider( "value" ) );
});
