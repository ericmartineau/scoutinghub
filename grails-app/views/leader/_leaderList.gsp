<table class="table table-bordered">
    <tr>
        <th>Name</th>
        <th>Phone</th>
        <th>Email</th>
        <th>Address</th>
        <th>BSA ID</th>
        <th></th>
        <th></th>    
    </tr>

    <g:each in="${leaders}" var="duplicate">

        <tr>
            <td>${duplicate}</td>

            <td>${f.formatPhone(phone: duplicate.phone) ?: "No Phone"}</td>

            <td>${duplicate.email ?: "No Email"}</td>
            <td>${duplicate.address1 ?: "No Address"}</td>

            <td>
                <g:if test="${duplicate.myScoutingIds?.size() > 0}">
                    ${duplicate.myScoutingIds?.iterator()?.next()}
                </g:if>
                <g:else>No BSA ID</g:else>

            </td>
            <g:set var="leaderInList" scope="request" value="${duplicate}" />
            ${body()}
            <g:set var="leaderInList" scope="request" value="${null}" />
        </tr>

    </g:each>
    
</table>