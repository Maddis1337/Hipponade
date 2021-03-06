<%@ page import="hipponade.Product" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'product.label', default: 'Product')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
</head>

<body>
<div class="schnack container">
    <div class="nav" role="navigation">
        <ul>
            <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
            <li><g:link class="list" action="index"><g:message code="default.list.label"
                                                               args="[entityName]"/></g:link></li>
            <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                                  args="[entityName]"/></g:link></li>
        </ul>
    </div>

    <div id="edit-product" class="content scaffold-edit" role="main">
        <h1><g:message code="default.edit.label" args="[entityName]"/></h1>

        <g:hasErrors bean="${productInstance}">
            <ul class="errors" role="alert">
                <g:eachError bean="${productInstance}" var="error">
                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                            error="${error}"/></li>
                </g:eachError>
            </ul>
        </g:hasErrors>
        <g:form url="[resource: productInstance, action: 'update']"
                method="PUT"  class="form-horizontal">
        <g:hiddenField name="version" value="${productInstance?.version}"/>
        <fieldset class="form">
            <g:render template="form"/>
        </fieldset>
        <fieldset class="buttons pull-right">
            <g:actionSubmit class="save btn btn-success" action="update"
                            value="${message(code: 'default.button.update.label', default: 'Update')}"/>
        </fieldset>
        </g:form>
    </div>
</div>
</body>
</html>
