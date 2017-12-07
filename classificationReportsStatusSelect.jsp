<%--Added smcafee, 8/26/2009, artf 1371--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/touch.tld" prefix="touch"%>


<bean:define id="thisForm" name="ClassificationReportsForm"/>

<bean:define id="statusList" name="ClassificationReportsForm" property="statusList" />

<script language="javascript">
</script>
				<tr>
					<td class="lbl" align="right" colspan="2">Status:</td>
					<td id="status">
						<html:select name="thisForm" property="statusId" onchange="pageLoad()">
				        	<html:options collection="statusList" property="value" labelProperty="label" /> 
				    	</html:select><html:hidden name="thisForm" property="statusId" />
					</td>
				</tr >
 