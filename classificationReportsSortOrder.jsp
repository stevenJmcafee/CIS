<%--added smcafee, 8/26/2009, artf 1371--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/touch.tld" prefix="touch"%>


<bean:define id="thisForm" name="ClassificationReportsForm"/>
<bean:define id="sortOrderList" name="ClassificationReportsForm" property="sortOrderList" />

<script language="javascript">
</script>
				<tr>
					<td class="lbl" align="right" colspan="2">Sort By:</td>
					<td id="sortorder">
						<html:select name="thisForm" property="sortOrderId" onchange="pageLoad()">
				        	<html:options collection="sortOrderList" property="value" labelProperty="label" /> 
				    	</html:select><html:hidden name="thisForm" property="sortOrderId"/>
				    </td>
				</tr >
