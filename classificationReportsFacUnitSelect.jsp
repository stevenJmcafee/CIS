<%--Added smcafee, 8/26/2009, artf 1371--%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld"  prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<bean:define id="thisForm" name="ClassificationReportsForm"/>

<bean:define id="locTypList" name="ClassificationReportsForm" property="locTypList" />
<bean:define id="facilityList" name="ClassificationReportsForm" property="facilityList" />
<bean:define id="unitList" name="ClassificationReportsForm" property="unitList" />

<script language="javascript">
	function loadFacUnit(sMethod) 
	{
		document.forms[0].action="<%=request.getContextPath()%>/classificationReportsAction.do?method=" + sMethod;
		document.forms[0].submit();
	}		
</script>
				<tr>
					<td class="lbl" align="right" colspan="2">Location Type:</td>
					<td>
						<html:select name="thisForm" property="locTypCd" onchange="loadFacUnit('resetFac')">
				        	<html:options collection="locTypList" property="value" labelProperty="label" /> 
				    	</html:select>
					</td>
				</tr >
				<logic:equal property="locTypCd" name="thisForm" value="1">   
					<tr>
						<td colspan="1">&nbsp;
					    </td>
					</tr >
    			</logic:equal>
 	    		<logic:greaterEqual property="locTypCd" name="thisForm" value="2">   
 	    		<logic:lessEqual property="locTypCd" name="thisForm" value="9">   
					<tr id="typLabel">
						<logic:equal property="locTypCd" name="thisForm" value="2"> 
							<td class="lbl" align="right" colspan="2">Facility:</td>
						</logic:equal>
						<logic:equal property="locTypCd" name="thisForm" value="3"> 
							<td class="lbl" align="right" colspan="2">Work Center:</td>
						</logic:equal>
						<logic:equal property="locTypCd" name="thisForm" value="4"> 
							<td class="lbl" align="right" colspan="2">District:</td>
						</logic:equal>
						<logic:equal property="locTypCd" name="thisForm" value="5"> 
							<td class="lbl" align="right" colspan="2">Status:</td>
						</logic:equal>
						<logic:equal property="locTypCd" name="thisForm" value="6"> 
							<td class="lbl" align="right" colspan="2">Hospital:</td>
						</logic:equal>
						<logic:equal property="locTypCd" name="thisForm" value="7"> 
							<td class="lbl" align="right" colspan="2">Agency:</td>
						</logic:equal>
						<logic:equal property="locTypCd" name="thisForm" value="8"> 
							<td class="lbl" align="right" colspan="2">Facility:</td>
						</logic:equal>
						<logic:equal property="locTypCd" name="thisForm" value="9"> 
							<td class="lbl" align="right" colspan="2">Jail:</td>
						</logic:equal>
						<td>
							<html:select name="thisForm" property="facilityCd" onchange="pageLoad()">
					        	<html:options collection="facilityList" property="value" labelProperty="label" /> 
					    	</html:select>
	
						</td>
					</tr >
		    		</logic:lessEqual>
		    		</logic:greaterEqual>
 