                <div class="msg2">
                    <div class="duplicate-results">
                        <div class="header">
                            <div>Name</div>

                            <div>Phone</div>

                            <div>Email</div>

                            <div>Address</div>

                            <div>BSA ID</div>

                            <div></div>

                            <div></div>
                        </div>
                        <g:each in="${leaders}" var="duplicate">

                            <div>
                                <div>${duplicate}</div>

                                <div>${f.formatPhone(phone: duplicate.phone) ?: "No Phone"}</div>

                                <div>${duplicate.email ?: "No Email"}</div>
                                <div>${duplicate.address1 ?: "No Address"}</div>

                                <div>
                                    <g:if test="${duplicate.myScoutingIds?.size() > 0}">
                                        ${duplicate.myScoutingIds?.iterator()?.next()}
                                    </g:if>
                                    <g:else>No BSA ID</g:else>

                                </div>
                                <g:set var="leaderInList" scope="request" value="${duplicate}" />
                                ${body()}
                                <g:set var="leaderInList" scope="request" value="${null}" />
                            </div>

                        </g:each>
                    </div>

                </div>