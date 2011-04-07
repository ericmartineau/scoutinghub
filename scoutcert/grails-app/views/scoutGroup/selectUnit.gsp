<g:each in="${parents}" var="parent">
    <a href="javascript:selectUnit('', ${parent.id})">${parent.groupLabel}<br /></a>
</g:each>