<script type="text/javascript">
    jQuery(document).ready(
            (function() {

                var $this = jQuery("${selector}");
                if (jQuery.fn.qtip) {
                    $this.qtip(
                            {
                                content: "<g:escapeJavascript><g:message code="${code}"/></g:escapeJavascript>",
//                        show: {ready:true, event:false},
                                position: {my: "top center", at: "bottom center"},
                                hide: {event:"blur"},
                                style: {
                                    classes: "ui-tooltip-rounded tooltip-display"
                                }
                            }
                    )
                }
                )
                ;
            }));

</script>