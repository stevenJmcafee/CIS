<%--Created smcafee, 8/25/2009, artf 1371--%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld"  prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<script language="javascript">
			function checkAll() {
				document.forms[0].action = "<%=request.getContextPath()%>/classificationReportsAction.do?method=checkAll";
				document.forms[0].submit();
			}
</script>
					<tr>
						<td class="lbl" align="left" colspan="2">
							<label>Custody Levels</label> 
						</td>
					</tr>
					<tr>
						<td class="lbl" align="left" colspan="2">&nbsp;
							<html:checkbox property="checkAllFlg" value="Y" onclick="checkAll()">All Custody Levels</html:checkbox>
						</td>
					</tr>
					<logic:equal name="thisForm" property="caller" value="FacilityReport">
						<tr>
							<td rowspan="1" align="left">&nbsp;&nbsp;&nbsp;
							<td class="lbl" align="left">
								<html:checkbox property="unClassFlg" value="Y">Unclassified</html:checkbox>
							</td>
						</tr>
					</logic:equal>
					<tr>
						<td rowspan="7" align="left">&nbsp;&nbsp;&nbsp;
						<td class="lbl" align="left">
							<html:checkbox property="closeFlg" value="Y">Close</html:checkbox>
						</td>
					</tr>
					<tr>
						<td class="lbl" align="left">
							<html:checkbox property="mediumFlg" value="Y">Medium</html:checkbox>
						</td>
					</tr>
					<tr>
						<td class="lbl" align="left">
							<html:checkbox property="minFlg" value="Y">Minimum</html:checkbox>
						</td>
					</tr>
					<tr>
						<td class="lbl" align="left">
							<html:checkbox property="commFlg" value="Y">Community</html:checkbox>
						</td>
					</tr>
					<tr>
						<td class="lbl" align="left">
							<html:checkbox property="riderFlg" value="Y">Rider</html:checkbox>
						</td>
					</tr>
				
