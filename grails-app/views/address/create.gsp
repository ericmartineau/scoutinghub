<div>
    <div class="body">
        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>
        <g:hasErrors bean="${addressInstance}">
            <g:msgbox type="error">
                <g:renderErrors bean="${addressInstance}" as="list"/>
            </g:msgbox>
        </g:hasErrors>
        <g:form action="save">
            <div class="dialog">
                <table>
                    <tbody>

                    <tr class="prop">
                        <td valign="top" class="name">
                            <label for="locationName"><g:message code="address.locationName.label" default="Location Name"/></label>
                        </td>
                        <td valign="top" class="value ${hasErrors(bean: addressInstance, field: 'locationName', 'errors')}">
                            <g:textField name="locationName" value="${addressInstance?.locationName}"/>
                        </td>
                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">
                            <label for="address"><g:message code="address.address.label" default="Address"/></label>
                        </td>
                        <td valign="top" class="value ${hasErrors(bean: addressInstance, field: 'address', 'errors')}">
                            <g:textField name="address" value="${addressInstance?.address}"/>
                        </td>
                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">
                            <label for="city"><g:message code="address.city.label" default="City"/></label>
                        </td>
                        <td valign="top" class="value ${hasErrors(bean: addressInstance, field: 'city', 'errors')}">
                            <g:textField name="city" value="${addressInstance?.city}"/>
                        </td>
                    </tr>


                    <tr class="prop">
                        <td valign="top" class="name">
                            <label for="state"><g:message code="address.state.label" default="State"/></label>
                        </td>
                        <td valign="top" class="value ${hasErrors(bean: addressInstance, field: 'state', 'errors')}">
                            <g:textField name="state" value="${addressInstance?.state}"/>
                        </td>
                    </tr>

                    <tr class="prop">
                        <td valign="top" class="name">
                            <label for="zip"><g:message code="address.zip.label" default="Zip"/></label>
                        </td>
                        <td valign="top" class="value ${hasErrors(bean: addressInstance, field: 'zip', 'errors')}">
                            <g:textField name="zip" value="${addressInstance?.zip}"/>
                        </td>
                    </tr>

                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <span class="button"><g:submitToRemote class="save ui-button" update="dialog" action="save" name="create" value="${message(code: 'default.button.create.label', default: 'Create')}"/></span>
            </div>
        </g:form>
    </div>
</div>

