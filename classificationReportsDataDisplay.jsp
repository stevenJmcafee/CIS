<%--Created smcafee, 8/25/2009, artf 1371--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld"  prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld"  prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/touch.tld"        prefix="touch" %>

<script language="javascript">
			function viewEntry(ofndrNum, clssId, clssTyp, caller){
				document.getElementById('searchFlg').value = true;
				
				document.forms[0].action="<%=request.getContextPath()%>/classificationReportsAction.do?method=setSearchValue";
				document.forms[0].submit();
				
				document.forms[0].action="<%=request.getContextPath()%>/offenderCustodyLevelAction.do?method=viewEntryFromReport&clssId=" + clssId + "&ofndrNum=" + ofndrNum + "&clssTyp=" + clssTyp + "&caller=" + caller;
				document.forms[0].submit();
			}

</script>
			<logic:present name="ClassificationReportsForm" property="entryList">
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="2" class="formSeperator"></td>
				</tr>
				<tr>
					<td colspan="2">
						<table border="0" width="100%">
							<tr>
								<th rowspan="2" width="3%">
									&nbsp;
								</th>
								<th align="left">
									<font size="-2">Offender<br>Number</font>
								</th>
								<th align="left">
									<font size="-2">Offender<br>Name</font>
								</th>
								<th align="left">
									<font size="-2"><br>Location</font>
								</th>
								<th align="left">
									<font size="-2">Scored<br>Custody Level</font>
								</th>
								<th align="left">
									<font size="-2">Recommended<br>Custody Level</font>
								</th>
								<th align="left">
									<font size="-2">Final<br>Custody Level</font>
								</th>
<%--								<th align="left">--%>
<%--									<font size="-2"><br>Override Reason</font>--%>
<%--								</th>--%>
								<th align="left">
									<font size="-2">Date of<br>Classification</font>
								</th>
							</tr>
							<tr>
								<th colspan="3" align="left">
									<font size="-2">&nbsp;&nbsp;&nbsp;Discretionary Overrides</font>
								</th>
								<th colspan="4" align="left">
									<font size="-2">&nbsp;&nbsp;&nbsp;Mandatory Overrides</font>
								</th>
							</tr>
							<%
							int i = 0;
							%>
							<touch:list id="row" name="ClassificationReportsForm" property="entryList" scope="session" size="25">
								<tr class='<%=(i % 2 == 0 ? "cellBase" : "cellLite")%>'>
									<logic:equal name="row" property="updtUserId" value="convert ">
<%--									Modified smcafee, 4/14/2010, artf 1628--%>
										<logic:notEqual name="row" property="scrdCstdyDesc" value="RIDER">
											<td rowspan="2">&nbsp;</td>
										</logic:notEqual>
										<logic:equal name="row" property="scrdCstdyDesc" value="RIDER">
											<td>&nbsp;</td>
										</logic:equal>
<%--										End Mod--%>
									</logic:equal>
									<logic:notEqual name="row" property="updtUserId" value="convert ">
										<logic:equal name="row" property="scrdCstdyDesc" value="RIDER">
											<td>
												<a href='javascript:viewEntry(<touch:property propertyName="offenderNum"/>, <touch:property propertyName="ofndrClfnId"/>, "D","<bean:write name="thisForm" property="caller"/>")'>
													<img src="<%=request.getContextPath()%>/jsp/images/view.gif"
														title="Edit/View Rider Info" border="0" alt="">
												</a>
											</td>
										</logic:equal>
										<logic:notEqual name="row" property="scrdCstdyDesc" value="RIDER">
											<td rowspan="2">
<%--											Removed, smcafee, 5/18/2010, artf 1633, atch 1954, item 1--%>
												<logic:notEqual name="row" property="ofndrClfnId" value="-1">
													<a href='javascript:viewEntry(<touch:property propertyName="offenderNum"/>, <touch:property propertyName="ofndrClfnId"/>, "<touch:property propertyName="classificationType"/>","<bean:write name="thisForm" property="caller"/>")'>
														<img src="<%=request.getContextPath()%>/jsp/images/view.gif"
															title="Edit/View Classification" border="0" alt="">
													</a>
												</logic:notEqual>
											</td>
											
										</logic:notEqual>
									</logic:notEqual>
									<td>
										<touch:property propertyName="offenderNum" />
									</td>
									<td >
										<touch:property propertyName="offenderName" />
									</td>
									<td >
										<touch:property propertyName="offenderLoc" />
									</td>
									<logic:equal name="row" property="scrdCstdyDesc" value="RIDER">
										<logic:present name="row" property="approvalDate">
											<td align="left" colspan="2">
												<touch:property propertyName="scrdCstdyDesc" />
											</td>
											<td align="left" >RIDER</td>
										</logic:present>
										<logic:notPresent name="row" property="approvalDate">
											<td align="left" colspan="3">
												<touch:property propertyName="scrdCstdyDesc" />
											</td>
										</logic:notPresent>
									</logic:equal>
									<logic:notEqual name="row" property="scrdCstdyDesc" value="RIDER">
										<td align="left">
											<touch:property propertyName="scrdCstdyDesc" />
										</td>
										<td align="left">
											<touch:property propertyName="recmmdCstdyLvlDesc" />
										</td>
										<td align="left">
											<touch:property propertyName="fnlCstdyLvlDesc" />
										</td>
									</logic:notEqual>
									<td align="left">
										<touch:property propertyName="approvalDateAsString" />
									</td>
								</tr>
								<logic:notEqual name="row" property="scrdCstdyDesc" value="RIDER">
									<tr class='<%=(i % 2 == 0 ? "cellBase" : "cellLite")%>' valign="top">
										<td colspan="3" align="left">
											<logic:equal name="row" property="discrtnry1" value="true">
												&nbsp;&nbsp;&nbsp;<bean:write name="thisForm" property="discrtnry1Text"/><br>
											</logic:equal>
											<logic:equal name="row" property="discrtnry2" value="true">
												&nbsp;&nbsp;&nbsp;<bean:write name="thisForm" property="discrtnry2Text"/><br>
											</logic:equal>
											<logic:equal name="row" property="discrtnry3" value="true">
												&nbsp;&nbsp;&nbsp;<bean:write name="thisForm" property="discrtnry3Text"/><br>
											</logic:equal>
											<logic:equal name="row" property="discrtnry4" value="true">
												&nbsp;&nbsp;&nbsp;<bean:write name="thisForm" property="discrtnry4Text"/><br>
											</logic:equal>
											<logic:equal name="row" property="discrtnry5" value="true">
												&nbsp;&nbsp;&nbsp;<bean:write name="thisForm" property="discrtnry5Text"/>
											</logic:equal>
										</td>
										<td colspan="4" align="left">
											<logic:equal name="row" property="mandtry1" value="true">
												&nbsp;&nbsp;&nbsp;<bean:write name="thisForm" property="mandtry1Text" /><br>
											</logic:equal>
											<logic:equal name="row" property="mandtry2" value="true">
												&nbsp;&nbsp;&nbsp;<bean:write name="thisForm" property="mandtry2Text" /><br>
											</logic:equal>
											<logic:equal name="row" property="mandtry3" value="true">
												&nbsp;&nbsp;&nbsp;<bean:write name="thisForm" property="mandtry3Text" />
											</logic:equal>
										</td>
									</tr>
								</logic:notEqual>
								<%
								i++;
								%>
							</touch:list>
							<tr>
								<td colspan="5">
									Record(s) displayed/found:
									<touch:record />
								</td>
							</tr>
							<tr>
								<td colspan="5">
									Page(s): 
									<touch:page />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</logic:present>
