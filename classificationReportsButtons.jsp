<%--Created smcafee, 8/25/2009, artf 1371--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld"  prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/touch.tld"        prefix="touch" %>

<bean:define id="thisForm" name="ClassificationReportsForm" />

<script language="javascript" type="text/javascript">

	// This function writes the report
	function generateReport(sReport)
	{
		document.forms[0].action="<%=request.getContextPath()%>/classificationReportsAction.do?method=submitReportJspPage";
		document.forms[0].submit();
		newWin = popup
			(800, 600, "<%=request.getContextPath()%>/classificationReportsAction.do?method=" + sReport + 
				"&subTitle=" + document.getElementById('tdSubTitle').innerText + 
				"&subTitle2=" + document.getElementById('tdSubTitle2').innerText +
				"&subTitle3=" + document.getElementById('tdSubTitle3').innerText +
				"&subTitle4=" + document.getElementById('tdSubTitle4').innerText, null, null, true);
		newWin.focus();
	}
	function clearAll(){
		var name = '<bean:write name="thisForm" property="caller"/>';
		document.forms[0].action="<%=request.getContextPath()%>/classificationReportsAction.do?method=viewPage&caller=" + name;
		document.forms[0].submit();
	}
	function doSearch(){
		document.forms[0].action="<%=request.getContextPath()%>/classificationReportsAction.do?method=getFacilitySearchData";
		document.forms[0].submit();
	}
	function doSearchHist(){
		document.forms[0].action="<%=request.getContextPath()%>/classificationReportsAction.do?method=getFacilityHistSearchData";
		document.forms[0].submit();
	}
	function doSearchStatus(){
		document.forms[0].action="<%=request.getContextPath()%>/classificationReportsAction.do?method=getStatusSearchData";
		document.forms[0].submit();
	}
	
	function goBack(){
		document.forms[0].action="<%=request.getContextPath()%>/classificationReportsAction.do?method=viewPage&caller=MainMenu";
		document.forms[0].submit();
	}
</script>

<tr>
	<td>
		<input TYPE="button" VALUE="Back" CLASS="button" name="Back" onclick="goBack()">
		<input class="lbl" type="button" value="Clear" onclick="clearAll()"/>
		<font size="1" color="#008080">&nbsp;Sample Report Format Below</font>
	</td> 
	<td align="right" colspan="1">
		<html:hidden name="thisForm"  property="searchFlg"/>
		<logic:equal name="thisForm" property="caller" value="FacilityHistory">
			<input TYPE="button" VALUE="Search" CLASS="button" name="Search" onclick="doSearchHist()" style="display: none">
			<logic:notEmpty name="ClassificationReportsForm" property="entryList">
				<input id="printButton" TYPE="button" VALUE="Preview Report" CLASS="button"
					name="Print Report" onclick="generateReport('generateJasperReport')"style="display: inline">
			</logic:notEmpty>
			<logic:empty name="ClassificationReportsForm" property="entryList">
				<span id="printButton" style="display: none"></span>
			</logic:empty>
		</logic:equal>
		<logic:equal name="thisForm" property="caller" value="FacilityReport">
			<input TYPE="button" VALUE="Search" CLASS="button" name="Search" onclick="doSearch()" style="display: none">
			<logic:notEmpty name="ClassificationReportsForm" property="entryList">
				<input id="printButton" TYPE="button" VALUE="Preview Report" CLASS="button"
					name="Print Report" onclick="generateReport('generateJasperReport')"style="display: inline">
			</logic:notEmpty>
			<logic:empty name="ClassificationReportsForm" property="entryList">
				<span id="printButton" style="display: none"></span>
			</logic:empty>
		</logic:equal>
		<logic:equal name="thisForm" property="caller" value="FacilitySummary">
			<span id="Search" style="display: none"></span>
			<input id="printButton" TYPE="button" VALUE="Preview Report" CLASS="button"
				name="Print Report" onclick="generateReport('generateJasperReport')"style="display: inline">
		</logic:equal>
		<logic:equal name="thisForm" property="caller" value="RaceMatrix">
			<span id="Search" style="display: none"></span>
			<input id="printButton" TYPE="button" VALUE="Preview Report" CLASS="button"
				name="Print Report" onclick="generateReport('generateJasperReport')"style="display: inline">
		</logic:equal>
		<logic:equal name="thisForm" property="caller" value="DueReport">
			<span id="Search" style="display: none"></span>
			<input id="printButton" TYPE="button" VALUE="Preview Report" CLASS="button"
				name="Print Report" onclick="generateReport('generateJasperReport')"style="display: inline">
		</logic:equal>
		<logic:equal name="thisForm" property="caller" value="StatusReport">
			<input TYPE="button" VALUE="Search" CLASS="button" name="Search" onclick="doSearchStatus()" style="display: none">
			<logic:notEmpty name="ClassificationReportsForm" property="entryList">
				<input id="printButton" TYPE="button" VALUE="Preview Report" CLASS="button"
					name="Print Report" onclick="generateReport('generateJasperReport')"style="display: inline">
			</logic:notEmpty>
			<logic:empty name="ClassificationReportsForm" property="entryList">
				<span id="printButton" style="display: none"></span>
			</logic:empty>
		</logic:equal>
		
	</td>
</tr>
