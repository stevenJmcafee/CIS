<%--Added smcafee, 8/26/2009, artf 1371--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/touch.tld" prefix="touch"%>


<bean:define id="thisForm" name="ClassificationReportsForm"/>

<bean:define id="filterList" name="ClassificationReportsForm" property="filterList" />

<script language="javascript">
	function loadFacUnit(sMethod) 
	{
		document.forms[0].action="<%=request.getContextPath()%>/classificationReportsAction.do?method=" + sMethod;
		document.forms[0].submit();
	}		
</script>
				<tr>
					<td class="lbl" align="right" colspan="2">Filter Type:</td>
					<td id="filter">
						<html:select name="thisForm" property="filterId" onchange="pageLoad()">
				        	<html:options collection="filterList" property="value" labelProperty="label" /> 
				    	</html:select><html:hidden name="thisForm" property="filterId"/>
					</td>
				</tr >
 