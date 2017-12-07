<%--Created smcafee, 8/25/2009, artf 1371--%>
<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld"  prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld"  prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/touch.tld"        prefix="touch" %>

<html:html>
<bean:define id="thisForm" name="ClassificationReportsForm"/>

	<head>
	    <title>Classification Status Report</title>
	    <html:base />
	    
		<LINK href="<%=request.getContextPath()%>/jsp/style.css" rel=stylesheet type="text/css">
		<jsp:include page="../style.txt" flush="true"/>
		<script language="javascript" src="<%=request.getContextPath()%>/jsp/std_lib.js"></script>
		<script language="javascript" src="<%=request.getContextPath()%>/jsp/popup.js"></script>
		<script type="text/javascript">
			function currentUser(){
				var currUser = "<bean:write name="appUser" property="userID"/>";		 
				document.forms[0].staff.value = currUser;
			}	
		
			function findUser(){
				newWin = popup(600, 600, '<%=request.getContextPath()%>/userSearch.do?method=show&opener=classificationDueReport', null, null, true);
				newWin.focus();	
				document.forms[0].staff.focus();
			}
			
		</script>
	</head>
	<body onload="pageLoad('new')" >
<%--		Modified smcafee, 3/8/2010, artf 1371, atch 1869--%>
		<html:form action="/classificationReportsAction.do?method=getFacilitySearchData">
<%--		End Mod--%>
			<html:hidden name="thisForm" property="userName"/>
			<html:hidden name="thisForm" property="userNameCaller"/>
			<TABLE border=0 cellPadding=0 cellSpacing=0  width="100%">
				<%-- This displays the report sample header--%>
				<%@ include file="classificationReportsPageFunctions.jsp" %>
				<tr><td colspan="2">&nbsp;</td></tr>
				<tr>
					<td width="55%" valign="top">
						<TABLE border=0 cellPadding=1 cellSpacing=2  width="100%">
							<%-- This displays the facility unit selection with start/end option--%>
							<%@ include file="classificationReportsFacUnitSelect.jsp" %>
							<%-- This displays the facility unit selection with start/end option--%>
							<%@ include file="classificationReportsStatusSelect.jsp" %>
							<tr><td colspan="2">&nbsp;</td></tr>
							<%-- This displays the facility unit selection with start/end option--%>
							<%@ include file="classificationReportsFilterSelect.jsp" %>
						</table>
					</td>
					<td valign="top">
						<TABLE border=0 cellPadding=1 cellSpacing=2  width="100%">
							<tr>
								<td width="1%"></td>
								<td class="lbl" width="31%" valign="middle" align="right">Preparer:</td>
								<td>
									<html:text property="staff" size="10" maxlength="8" onblur="checkName(this)"/>
									<a href="javascript:currentUser(), pageLoad()" tabindex="1"><img
											src="<%=request.getContextPath()%>/jsp/images/user2.gif"
											border="0" title="Current User" alt="">
									</a>&nbsp;
									<a href="javascript:findUser()" tabindex="2">
										<img src="<%=request.getContextPath()%>/jsp/images/user.gif"
											border="0" title="User" alt="" >
									</a>
								</td>
							</tr>
							<tr><td>&nbsp;</td></tr>
							<%-- This displays the facility unit selection with start/end option--%>
							<%@ include file="classificationReportsSortOrder.jsp" %>
						</table>
					</td>
				</tr>
				<tr><td colspan="2">&nbsp;</td></tr>
				<%-- This displays the report sample header--%>
				<%@ include file="classificationReportsButtons.jsp" %>
				<%-- This displays the report sample header--%>
				<%@ include file="classificationReportsStatusDataDisplay.jsp" %>
			</table>
				<%-- This displays the report sample header--%>
				<%@ include file="../reportSampleHeader.jsp" %>
				
				<%-- This table displays the report sample--%>
				<table width="100%" border="0">
					<tr>
						<td align="center">
							<font size="-2">Offender Number</font>
						</td>
						<td align="left">
							<font size="-2">Offender Name</font>
						</td>
						<td align="left">
							<font size="-2">Bed Assignment</font>
						</td>
						<td align="left">
							<font size="-2">Status</font>
						</td>
						<td align="left">
							<font size="-2">Preparer</font>
						</td>
						<td align="left">
							<font size="-2">Date Prepared</font>
						</td>
					</tr>
					<tr>
						<td colspan="6">
							<img width="100%" height="2" src="<%=request.getContextPath()%>/jsp/images/TitleBar.gif" title="State Seal">
						</td>
					</tr>
				</table>
				<span class="smallGray">Source File: classificationStatusReport.jsp</span>
		</html:form>
	</body>
</html:html>