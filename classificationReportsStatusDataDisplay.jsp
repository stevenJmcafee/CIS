<%--Created smcafee, 8/25/2009, artf 1371--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld"  prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld"  prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/touch.tld"        prefix="touch" %>

<script language="javascript">
			function viewEntry(ofndrNum, clssId, clssTyp, caller){
				document.forms[0].action="<%=request.getContextPath()%>/offenderCustodyLevelAction.do?method=viewEntryFromReport&clssId=" + clssId + "&ofndrNum=" + ofndrNum + "&clssTyp=" + clssTyp + "&caller=" + caller;
				document.forms[0].submit();
			}

</script>
			<logic:present name="ClassificationReportsForm" property="entryList">
				<logic:notEmpty name="ClassificationReportsForm" property="entryList">
					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="2" class="formSeperator"></td>
					</tr>
					<tr>
						<td colspan="2">
							<table border="0" width="100%">
								<tr valign="top">
									<th width="3%">
										&nbsp;
									</th>
									<th align="left">
										<font size="-2">Offender<br>Number</font>
									</th>
									<th align="left">
										<font size="-2">Offender<br>Name</font>
									</th>
									<th align="left">
										<font size="-2">Bed<br>Assignment</font>
									</th>
									<th align="left">
										<font size="-2">Status</font>
									</th>
									<th align="left">
										<font size="-2">Custody<br>Level</font>
									</th>
									<th align="left">
										<font size="-2">Preparer</font>
									</th>
									<th align="left">
										<font size="-2">Date<br>Prepared</font>
									</th>
								</tr>
								<%
								int i = 0;
								%>
								<touch:list id="row" name="ClassificationReportsForm" property="entryList" scope="session" size="15">
									<tr class='<%=(i % 2 == 0 ? "cellBase" : "cellLite")%>' height="20">
										<logic:equal name="row" property="scrdCstdyDesc" value="RIDER">
											<logic:equal name="row" property="updtUserId" value="convert ">
												<td>&nbsp;</td>
											</logic:equal>
											<logic:notEqual name="row" property="updtUserId" value="convert ">
												<td>
													<a href='javascript:viewEntry(<touch:property propertyName="offenderNum"/>, <touch:property propertyName="ofndrClfnId"/>, "D","<bean:write name="thisForm" property="caller"/>")'>
														<img src="<%=request.getContextPath()%>/jsp/images/view.gif"
															title="Edit/View Rider Info" border="0" alt="">
													</a>
												</td>
											</logic:notEqual>
										</logic:equal>
										<logic:notEqual name="row" property="scrdCstdyDesc" value="RIDER">
											<logic:equal name="row" property="updtUserId" value="convert ">
												<td>&nbsp;</td>
											</logic:equal>
											<logic:notEqual name="row" property="updtUserId" value="convert ">
												<logic:equal name="row" property="classificationType" value="A">
													<td>
														<a href='javascript:viewEntry(<touch:property propertyName="offenderNum"/>, <touch:property propertyName="ofndrClfnId"/>, "A","<bean:write name="thisForm" property="caller"/>")'>
															<img src="<%=request.getContextPath()%>/jsp/images/view.gif"
																title="Edit/View Classification" border="0" alt="">
														</a>
													</td>
												</logic:equal>
												<logic:notEqual name="row" property="classificationType" value="A">
													<td>
														<a href='javascript:viewEntry(<touch:property propertyName="offenderNum"/>, <touch:property propertyName="ofndrClfnId"/>, "B","<bean:write name="thisForm" property="caller"/>")'>
															<img src="<%=request.getContextPath()%>/jsp/images/view.gif"
																title="Edit/View Classification" border="0" alt="">
														</a>
													</td>
												</logic:notEqual>
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
										<td align="left">
											<touch:property propertyName="status" />
										</td>
										<logic:equal name="row" property="scrdCstdyDesc" value="RIDER">
											<td align="left">
												<touch:property propertyName="scrdCstdyDesc" />
											</td>
										</logic:equal>
										<logic:notEqual name="row" property="scrdCstdyDesc" value="RIDER">
											<logic:equal name="row" property="status" value="PREPARED">
												<td align="left">
													<touch:property propertyName="scrdCstdyDesc" />
												</td>
											</logic:equal>
											<logic:equal name="row" property="status" value="DENIED">
												<td align="left">
													<touch:property propertyName="recmmdCstdyLvlDesc" />
												</td>
											</logic:equal>
											<logic:equal name="row" property="status" value="REVIEWED">
												<td align="left">
													<touch:property propertyName="fnlCstdyLvlDesc" />
												</td>
											</logic:equal>
											<logic:equal name="row" property="status" value="READY FOR SERVICE">
												<td align="left">
													<touch:property propertyName="fnlCstdyLvlDesc" />
												</td>
											</logic:equal>
<%--										Added smcafee, 3/2/2010, artf 1371, atch 1864, item 6--%>
											<logic:equal name="row" property="status" value="READY FOR FACILITY HEAD">
												<td align="left">
													<touch:property propertyName="fnlCstdyLvlDesc" />
												</td>
											</logic:equal>
<%--										End Add--%>
											<logic:equal name="row" property="status" value="SERVED">
												<td align="left">
													<touch:property propertyName="fnlCstdyLvlDesc" />
												</td>
											</logic:equal>
										</logic:notEqual>
										<td align="left">
											<touch:property propertyName="prepUser" />
										</td>
										<td align="left">
											<touch:property propertyName="prepDateAsString" />
										</td>
									</tr>
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
										Page (s):
										<touch:page />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</logic:notEmpty>
				<logic:empty name="ClassificationReportsForm" property="entryList">
					<tr><td colspan="2">&nbsp;</td></tr>
					<tr>
						<td colspan="2" class="lbl" align="center" height="20" valign="bottom">There were no records returned matching this search criteria.</td>
					</tr>
				</logic:empty>
			</logic:present>
