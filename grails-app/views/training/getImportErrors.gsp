<table>
    <g:each in="${errors}" var="entry">
        <tr>
            <td></td>
        </tr>
        <h3>${entry.key?.firstName} ${entry.key?.lastName} (${entry.key?.scoutingId})</h3>
        <blockquote style="margin-top:0px; margin-bottom:10px">
            <ul style="list-style:circle">
                <g:each in="${entry.value}" var="error">
                    <li><g:message message="${error}"/></li>
                </g:each>
            </ul>
        </blockquote>
    </g:each>
</table>