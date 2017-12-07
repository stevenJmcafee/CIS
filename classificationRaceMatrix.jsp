<%--Created smcafee, 8/25/2009, artf 1371--%>
<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld"  prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld"  prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/touch.tld"        prefix="touch" %>

<html:html>
<bean:define id="thisForm" name="ClassificationReportsForm"/>

	<head>
	    <title>Classification Race Matrix</title>
	    <html:base />
	    
		<LINK href="<%=request.getContextPath()%>/jsp/style.css" rel=stylesheet type="text/css">
		<jsp:include page="../style.txt" flush="true"/>
		<script language="javascript" src="<%=request.getContextPath()%>/jsp/std_lib.js"></script>
		<script language="javascript" src="<%=request.getContextPath()%>/jsp/popup.js"></script>
		<script type="text/javascript">
		</script>
	</head>
	<body onload="pageLoad()" >
		<html:form action="/classificationReportsAction.do?">
			<TABLE border=0 cellPadding=0 cellSpacing=0  width="100%">
				<%-- This displays the report sample header--%>
				<%@ include file="classificationReportsPageFunctions.jsp" %>
				<tr><td colspan="2">&nbsp;</td></tr>
				<tr>
					<td width="55%" valign="top">
						<TABLE border=0 cellPadding=1 cellSpacing=2  width="100%">
							<%-- This displays the facility unit selection with start/end option--%>
							<%@ include file="classificationReportsFacUnitSelect.jsp" %>
						</table>
					</td>
					<td width="45%" valign="top">
						<TABLE border=0 cellPadding=1 cellSpacing=2  width="100%">
							<tr>
								<td class="lbl" align="left" colspan="2">
									<label>Custody Level</label> 
								</td>
							</tr>
							<tr>
								<td id="scrd">
									<html:radio property="scrdFlg" name="thisForm" value="S" onclick="pageLoad()"/>
									Scored:
								<td>
							</tr>
							<tr>
								<td id="fnl">
									<html:radio property="scrdFlg" name="thisForm" value="A" onclick="pageLoad()"/>
									Final:
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<%-- This displays the report sample header--%>
				<%@ include file="classificationReportsButtons.jsp" %>
			</table>
				<%-- This displays the report sample header--%>
				<%@ include file="../reportSampleHeader.jsp" %>
			
				
			<%-- This table displays the report sample--%>
			<table width="100%" border="0" cellPadding=0 cellSpacing=0>
				<tr>
					<td width="1%" rowspan="2">&nbsp;</td>
					<td align="left" colspan="3">
						<font size="-1">Total Classifications for Location:<br><br></font>
					</td>
				</tr>
				<tr>
					<td width="55%">
						<table border="0" cellPadding=0 cellSpacing=0 style="border:solid;border-width:1px;border-color:#336699">
							<tr style="background-color:#6699cc" align="center" valign="bottom">
								<td height="20" width="10%">
									<font size="-2">&nbsp;</font>
								</td>
								<td width="9%">
									<font size="-2">Unentered</font>
								</td>
								<td width="6%">
									<font size="-2">Asian</font>
								</td>
								<td width="8%">
									<font size="-2">Black or<br>African<br>American</font>
								</td>
								<td width="9%">
									<font size="-2">Hispanic<br>or Latino</font>
								</td>
								<td width="9%">
									<font size="-2">Native<br>Hawaiian<br>or Other<br>Pacific<br>Islander</font>
								</td>
								<td width="8%">
									<font size="-2">American<br>Indian<br>or Alaska<br>Native</font>
								</td>
								<td width="6%">
									<font size="-2">Other</font>
								</td>
								<td width="7%">
									<font size="-2">Unknown</font>
								</td>
								<td width="6%">
									<font size="-2">White</font>
								</td>
								<td width="6%">
									<font size="-2">Total</font>
								</td>
								<td width="7%">
									<font size="-2">Percent</font>
								</td>
							</tr>
							<tr>
								<td  style="background-color:#6699cc">
									<font size="-2">Unclassified</font>
								</td>
							</tr>
							<tr>
								<td style="background-color:#6699cc">
									<font size="-2">Close</font>
								</td>
							</tr>
							<tr>
								<td style="background-color:#6699cc">
									<font size="-2">Medium</font>
								</td>
							</tr>
							<tr>
								<td style="background-color:#6699cc">
									<font size="-2">Minimum</font>
								</td>
							</tr>
							<tr>
								<td style="background-color:#6699cc">
									<font size="-2">Community</font>
								</td>
							</tr>
							<tr>
								<td style="background-color:#6699cc">
									<font size="-2">Rider</font>
								</td>
							</tr>
<%--							<tr>--%>
<%--								<td colspan="11"><img width="100%" height="1" src="<%=request.getContextPath()%>/jsp/images/TitleBar.gif" title="State Seal"></td>--%>
<%--							</tr>--%>
							<tr height="20">
								<td align="right" style="border-top:solid;border-top-width:1px;border-top-color:#336699;background-color:#6699cc">
									<font size="-2">Total&nbsp;&nbsp;</font>
								</td>
								<td align="right" colspan="11" style="border-top:solid;border-top-width:1px;border-top-color:#336699">
									&nbsp;
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="12">
						<img width="100%" height="2" src="<%=request.getContextPath()%>/jsp/images/TitleBar.gif" title="State Seal">
					</td>
				</tr>
			</table>
			<span class="smallGray">Source File: classificationRaceMatrix.jsp</span>
		</html:form>
	</body>
</html:html>