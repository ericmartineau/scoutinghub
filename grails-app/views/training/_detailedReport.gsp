<%@ page import="scoutinghub.CertificationCode" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
    <style type="text/css">


    @page {
        size: 11in 8.5in;
        margin: 2cm;
    }

    h1, h2, h3, h4, h5, h6 {
        margin-bottom: 5px;
    }

    th {
        text-align: left;
    }

    .level1 {
        padding-left: 15px;
    }

    .level2 {
        padding-left: 30px;
    }

    .level3 {
        padding-left: 45px;
    }

    .level4 {
        padding-left: 60px;
    }

    .level5 {
        padding-left: 75px;
    }

    .level6 {
        padding-left: 90px;
    }
    </style>

</head>

<body>



<div>
    * A list of training codes and their descriptions can be found at the end of this report.<br/>
    * This report is best printed in landscape mode
</div>
<table width="100%">
    <f:scoutGroupReport group="${group}"/>
</table>

<p style="page-break-after: always;">&nbsp;</p>

<h1>Training Code List</h1>
<table>
    <tr>
        <th>Code</th>
        <th>Training</th>
    </tr>

    <g:each in="${CertificationCode.list()}" var="code">
        <tr>
            <td>${code.code}</td>
            <td>${code.certification.name}</td>
        </tr>

    </g:each>
</table>
</body>
</html>
