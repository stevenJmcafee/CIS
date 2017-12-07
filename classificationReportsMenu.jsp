<%--Created smcafee, 8/25/2009, artf 1371 - Displays the classification report menu--%>
<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld"  prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld"  prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/touch.tld"        prefix="touch" %>

<html:html>

	<head>
	    <title>Classification Reports</title>
	    <html:base />
	    
		<LINK href="<%=request.getContextPath()%>/jsp/style.css" rel=stylesheet type="text/css">
		<jsp:include page="../style.txt" flush="true"/>
		<script language="javascript" src="<%=request.getContextPath()%>/jsp/std_lib.js"></script>
		<script language="javascript" src="<%=request.getContextPath()%>/jsp/popup.js"></script>
		
		<script type="text/javascript">
			function openReport(name){
<%--				alert("<%=request.getContextPath()%>/classificationReportsAction.do?method=viewPage&caller=" + name);--%>
				document.forms[0].action = "<%=request.getContextPath()%>/classificationReportsAction.do?method=viewPage&caller=" + name;
				document.forms[0].submit();
			}
			function pageLoad(){
				try{
<%--					document.getElementById('tdTitle').innerHTML = '<h4>' + document.title + '</h4>';--%>
					document.getElementById('tdTopCaption').innerHTML = document.title;
				} catch(err) {
				}
			}
			
		</script>
	</head>
	<body onload="pageLoad()">
		<html:form action="/classificationReportsAction.do">
				
				<%-- This table displays the report sample--%>
				<table width="100%" border="0">
					<tr>
						<td class="formCaption" colspan="4" align="center" width=100%>
							<label id="tdTopCaption"></label> 
							&nbsp;
							<a href="javascript:window.print()">
								<img border="0" src="<%=request.getContextPath()%>/jsp/images/print.gif" title="Print Screen">
							</a>
							&nbsp;
							<a href="javascript:showHelp('w_ftk_s4_2.jsp')">
								<img src="<%=request.getContextPath()%>/jsp/images/help.gif" border="0" title="Screen Help">
							</a>
						</td>
					</tr>
					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td width="20%" rowspan="6" align="center" valign="top">
							<img width="90" height="90" src="<%=request.getContextPath()%>/jsp/images/idaho_seal.jpg" title="State Seal">
						</td>
						<td align="left" height="20">
							<a href="javascript:openReport('FacilityReport');">
								<span class="lbl">Facility Classification Report</span>
							</a>
						</td>
					</tr>
					<tr>
						<td align="left" height="20">
							<a href="javascript:openReport('FacilitySummary');">
								<span class="lbl">Facility Classification Summary</span>
							</a>
						</td>
					</tr>
					<tr>
						<td align="left" height="20">
							<a href="javascript:openReport('FacilityHistory');">
								<span class="lbl">Facility Classification History</span>
							</a>
						</td>
					</tr>
					<tr>
						<td align="left" height="20">
							<a href="javascript:openReport('RaceMatrix');">
								<span class="lbl">Classification Race Matrix</span>
							</a>
						</td>
					</tr>
					<tr>
						<td align="left" height="20">
							<a href="javascript:openReport('DueReport');">
								<span class="lbl">Classification Due Report</span>
							</a>
						</td>
					</tr>
					<tr>
						<td align="left" height="20">
							<a href="javascript:openReport('StatusReport');">
								<span class="lbl">Classification Status Report</span>
							</a>
						</td>
					</tr>
				</table>
				<hr><span class="smallGray">Source File: classificationReportsMenu.jsp</span>
		</html:form>
	</body>
</html:html>