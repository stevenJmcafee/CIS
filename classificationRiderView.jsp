<%--created smcafee, 8/30/2009, artf 1371, security--%>
<%--Modified smcafee, 6/17/2010, artf 1672--%>
<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/touch.tld" prefix="touch"%>

<bean:define id="thisForm" name="offenderCustodyLevelForm"
	type="otrack.dio.classification.OffenderCustodyLevelForm" />

<html:html>
<head>
	<title>Classification Rider View</title>
	<html:base />
	<link href="<%=request.getContextPath()%>/jsp/style.css"
		rel="stylesheet" type="text/css">
	<jsp:include page="/jsp/style.txt" flush="true" />
	<script language="javascript"
		src="<%=request.getContextPath()%>/jsp/std_lib.js"
		type="text/javascript"></script>
	<script language="javascript"
		src="<%=request.getContextPath()%>/jsp/popup.js"
		type="text/javascript"></script>
	<%--		<script language="javascript" src="<%=request.getContextPath()%>/jsp/jQuery/jquery.js"></script>--%>
	<script language="javascript" type="text/javascript">
			function currentUser(){
				var currUser = "<bean:write name="appUser" property="userID"/>";		 
				document.getElementById('riderEntryVO.prepUser').value = currUser;
			}	
			
			function currentUserAppr(){
				var currUser = "<bean:write name="appUser" property="userID"/>";		 
				document.getElementById('riderEntryVO.authUser').value = currUser;
			}	
		
			function findUser(caller){
				newWin = popup(600, 600, '<%=request.getContextPath()%>/userSearch.do?method=show&opener=classificationRider' + caller, null, null, true);
				newWin.focus();	
			}
			function checkName(obj){
				if(obj.value != null && obj.value != ""){
					document.getElementById('entryObject.userName'). value = "false";
					document.getElementById('entryObject.userNameCaller'). value = obj.name;
					document.forms[0].action="<%=request.getContextPath()%>/offenderCustodyLevelAction.do?method=checkName&name=" + obj.value + "&clsTyp=D";
					document.forms[0].submit();
				}
			}
		function doAction() 
		{
			  if(checkForm())
			  {
				var locationIndex = "<%=thisForm.getLocationIndex()%>" 	
        		if(locationIndex<0)
						{
							document.forms[0].action="<%=request.getContextPath()%>/offenderCustodyLevelAction.do?method=add";
						}
						else
						{
							document.forms[0].action="<%=request.getContextPath()%>/offenderCustodyLevelAction.do?method=edit";
						}
					    return true;
			  }
			  else
			   return false;		
		}//end of doAction function

		function checkForm()
	  	{	
	  		var err = new Array();
			var dis1 = document.getElementById('riderEntryVO.firstRider').value;
			if(dis1 == "false"){
				if(isWhitespace (document.getElementById('riderEntryVO.subsquntRiderDates').value)){
		  		 	err[err.length]="Please enter Retained Jurisdiction Dates.";
				}
			}
			var dis2 = document.getElementById('riderEntryVO.firstIncarceration').value;
			if(dis2 == "false"){
				if(isWhitespace (document.getElementById('riderEntryVO.subsquntIncarcerationDates').value)){
		  		 	err[err.length]="Please enter Incarceration Dates.";
				}
			}
			
	  		if(isWhitespace (document.getElementById('riderEntryVO.recommendedHousingCd').value)){
	  		 	err[err.length]="Please Select a Housing Option.";
	  		}
	  		var prep = document.getElementById('riderEntryVO.prepUser').value;
	  		if(prep == ""){
	  		 	err[err.length]="Please Enter a Preparer Name.";
	  		}
	  		if(isWhitespace (document.getElementById('riderEntryVO.prepDateAsString').value)){
	  		 	err[err.length]="Please Enter a Preparation Date.";
	  		}
	  		
			if(document.getElementById('apprViewLevel').value == "true"){
	  			if(document.getElementById('entryObject.status').value == "PREPARED"){
	  		
			  		var appr = document.getElementById('riderEntryVO.authUser').value;
			  		if(appr == ""){
			  		 	err[err.length]="Please Enter an Reviewing Authority Name.";
			  		}
			  		if(isWhitespace (document.getElementById('riderEntryVO.authDateAsString').value)){
			  		 	err[err.length]="Please Enter an Reviewing Authority Date.";
			  		}
		  		}
	  		}
	  		if (err.length > 0) {
		  		var msg = "Please correct the following problems\n";
		        for (i=0; i<err.length; i++) {
					msg += " - " + err[i] + "\n";
		        }
				alert(msg);
				return false;
			} else {
				return true;
			}
	  	
	  	}//End of checkForm
	
	function setHousingValue(type, code, name){
		document.getElementById("riderEntryVO.recommendedHousingCd").value = "";
		document.getElementById("riderEntryVO.recommendedHousingDesc").value = "";
		if(type == 1){
			document.getElementById("cwcSelect").style.display = "none";
			document.getElementById("otherText").style.display = "none";
			document.getElementById("recmmdCWCDesc").value = "0";
			document.getElementById("recmmdOtherDesc").value = "";
			document.getElementById("riderEntryVO.recommendedHousingCd").value = code;
			
		} else if(type == 2){
			document.getElementById("cwcSelect").style.display = "inline";
			document.getElementById("otherText").style.display = "none";
			document.getElementById("recmmdOtherDesc").value = "";
			if(code != ""){
				document.getElementById("riderEntryVO.recommendedHousingCd").value = code;
			}
		} else if(type == 3){
			document.getElementById("cwcSelect").style.display = "none";
			document.getElementById("otherText").style.display = "inline";
			document.getElementById("recmmdCWCDesc").value = "0";
			if(code != ""){
				document.getElementById("riderEntryVO.recommendedHousingCd").value = code;
				document.getElementById("riderEntryVO.recommendedHousingDesc").value = name;
			}
		}
	}
	function setRadios(obj, value){
		document.getElementById('riderEntryVO.' + obj).value = value;
	}
	function setFirsts(name, value){
		if(value == 'Yes'){
			document.getElementById('riderEntryVO.' + name).value = true;
		} else {
			document.getElementById('riderEntryVO.' + name).value = false;
		}
		setValues();
	}
	function setValues(){
		try {
			var chkNam = document.getElementById('entryObject.userName').value;
			var chkNamCaller = document.getElementById('entryObject.userNameCaller').value;
			if(chkNam != "true"){
				alert("That name is not a valid user. \n\n Please enter a different name.");
				document.getElementById(chkNamCaller).value = "";
				document.getElementById(chkNamCaller).focus();
			} else {
				if(chkNamCaller == "riderEntryVO.prepUser"){
					document.getElementById('riderEntryVO.prepDateAsString').focus();
				} else if(chkNamCaller == "riderEntryVO.authUser"){
					document.getElementById('riderEntryVO.authDateAsString').focus();
				}
			}
	 	} catch(e){
 		}
 		
		if(document.getElementById('riderEntryVO.firstRider').value == "true"){
			document.getElementById('firstRiderYes').checked = true;
			document.getElementById('firstRiderNo').checked = false;
			document.getElementById('riderEntryVO.subsquntRiderDates').value = "";
			document.getElementById('subsquntRiderDates').style.display = "none";
<%--			document.getElementById('firstRider1').innerText = "Yes";--%>
		} else {
			document.getElementById('firstRiderYes').checked = false;
			document.getElementById('firstRiderNo').checked = true;
			document.getElementById('subsquntRiderDates').style.display = "inline";
<%--			document.getElementById('firstRider1').innerText = "No";--%>
		}
		if(document.getElementById('riderEntryVO.firstIncarceration').value == "true"){
			document.getElementById('firstIncarcerationYes').checked = true;
			document.getElementById('firstIncarcerationNo').checked = false;
			document.getElementById('riderEntryVO.subsquntIncarcerationDates').value = "";
			document.getElementById('subsquntIncarcerationDates').style.display = "none";
<%--			document.getElementById('firstIncarceration1').innerText = "Yes";--%>
		} else {
			document.getElementById('firstIncarcerationYes').checked = false;
			document.getElementById('firstIncarcerationNo').checked = true;
			document.getElementById('subsquntIncarcerationDates').style.display = "inline";
<%--			document.getElementById('firstIncarceration1').innerText = "No";--%>
		}
		var hsCd = document.getElementById('riderEntryVO.recommendedHousingCd').value.replace(/^\s+|\s+$/g,"");
		if(hsCd.substring(0,1) == "0"){
			var rawHTML = document.getElementById('1').outerHTML;
			var checkedHTML = rawHTML.replace(">", " CHECKED>");	
			document.getElementById('1').outerHTML = checkedHTML;
			setHousingValue(3, '0', document.getElementById('riderEntryVO.recommendedHousingDesc').value);
		} else if(hsCd.substring(0,3) == "103"){
			var rawHTML = document.getElementById("cwc").outerHTML;
			var checkedHTML = rawHTML.replace(">", " CHECKED>");	
			document.getElementById('cwc').outerHTML = checkedHTML;
			setHousingValue(2, hsCd, '');
			document.getElementById('recmmdCWCDesc').value = hsCd;
		} else if(hsCd != ""){
			var rawHTML = document.getElementById(hsCd).outerHTML;
			var checkedHTML = rawHTML.replace(">", " CHECKED>");	
			document.getElementById(hsCd).outerHTML = checkedHTML;
		}
	}
	function saveForm()
	{
		if(checkForm()){
			var actionType;
			var addDetail = "N";
			if(<%=thisForm.getLocationIndex()%> < 0){
				actionType = "I";
				if(confirm("Do you wish to add detail for this retained jurisdiction?")){
					addDetail = "Y";
				}
			} else {
				actionType = "U";
			}
		    document.forms[0].action="<%=request.getContextPath()%>/offenderCustodyLevelAction.do?method=saveRiderView&actionType=" + actionType + "&addDetail=" + addDetail;
			document.forms[0].submit();
		} else {
		}
	}

	function getDetails(){
		document.forms[0].action="<%=request.getContextPath()%>/offenderCustodyLevelAction.do?method=viewRiderDetails&locationIndex=-1";
		document.forms[0].submit();
	}
			function printForm(){
				newWin = popup
					(800, 600, "<%=request.getContextPath()%>/offenderCustodyLevelAction.do?method=generateRiderForm", null, null, true);
<%--				newWin.focus();--%>
			}


</script>
</head>
<body onload="setValues()">
	<html:form action="offenderCustodyLevelAction.do"
		onsubmit="return doAction()">
		<html:hidden name="thisForm" property="entryObject.userName" />
		<html:hidden name="thisForm" property="entryObject.userNameCaller" />
		<html:hidden name="thisForm" property="entryObject.status" />
		<table width="100%" border="0" cellPadding="1" cellSpacing="2">
			<tr>
				<td colspan="2" CLASS="formCaption">
					Retained Jurisdiction Placement Evaluation&nbsp; &nbsp;
					<a href="javascript:window.print()"> <img
							src="<%=request.getContextPath()%>/jsp/images/print.gif"
							border="0" title="print" alt=""> </a>&nbsp;
					<a href="javascript:showHelp('w_dio_s9_4.jsp')"> <img
							src="<%=request.getContextPath()%>/jsp/images/help.gif"
							border="0" title="screen help" alt=""> </a>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<jsp:include page="/jsp/offender/OffenderHeader.jsp" flush="true" />
				</td>
			</tr>
			<logic:present name="offenderCustodyLevelForm" property="riderList">
				<bean:define id="riderList" name="offenderCustodyLevelForm"
					property="riderList" />
				<%--					<logic:notEmpty name="offenderCustodyLevelForm" property="riderList">--%>
				<tr>
					<td class="formSeperator" colspan="2" align="center">
						Retained Jurisdictions
						<a href="javascript:getDetails()"> <img
								src="<%=request.getContextPath()%>/jsp/images/add.gif"
								border="0" title="Add Rider" alt=""> </a> &nbsp;
					</td>
				</tr>
<%--				<logic:notEmpty name="offenderCustodyLevelForm" property="riderList">--%>
					<tr>
						<td colspan="2" valign="top"
							style="border:solid;border-width:1px;border-color:#6699cc">

							<table width="100%" border="0" cellPadding="3" cellSpacing="2">
								<tr>
									<th align="left" width="3%" rowspan="3">
										&nbsp;
									</th>
									<logic:equal name="offenderCustodyLevelForm"
										property="apprViewLevel" value="true">
										<th align="left" width="3%" rowspan="3">
											&nbsp;
										</th>
									</logic:equal>
									<th align="left" width="20%">
										Judicial District
									</th>
									<th align="left" width="20%">
										Judge
									</th>
									<th align="left" width="10%">
										End Date
									</th>
									<th align="left" width="7%">
										Days
									</th>
									<th align="left" width="17%">
										Completion Order
									</th>
								</tr>
								<tr>
									<th colspan="6" align="left">
										Crime:
									</th>
								</tr>
								<tr>
									<th colspan="6" align="left">
										Recommendations:
									</th>
								</tr>
								<%
								int i = 0;
								%>
								<touch:list id="row" name="offenderCustodyLevelForm"
									property="riderList" scope="session" size="25">
									<tr class='<%=(i % 2 == 0 ? "cellBase" : "cellLite")%>'>
										<td rowspan="3">
											<touch:link href="offenderCustodyLevelAction.do">
												<touch:paramList>
													<touch:param name="method" value="viewRiderDetails" />
												</touch:paramList>
												<img src="<%=request.getContextPath()%>/jsp/images/view.gif"
													title="Edit/View Rider Detail" border="0" alt="">
											</touch:link>
										</td>
										<logic:equal name="offenderCustodyLevelForm"
											property="apprViewLevel" value="true">
											<td rowspan="3">
												<touch:link href="offenderCustodyLevelAction.do">
													<touch:paramList>
														<touch:param name="method" value="deleteRiderDetail" />
													</touch:paramList>
													<img
														src="<%=request.getContextPath()%>/jsp/images/trash.gif"
														title="Delete Rider Detail" border="0" alt="">
												</touch:link>
											</td>
										</logic:equal>
										<td>
											<touch:property propertyName="judicialDistrictDesc" />
										</td>
										<td>
											<touch:property propertyName="judgeDesc" />
										</td>
										<td>
											<touch:property propertyName="endDateAsString" />
										</td>
										<td>
											<touch:property propertyName="days" />
										</td>
										<td>
											<logic:equal name="row" property="completion" value="true">
														Yes
													</logic:equal>
											<logic:notEqual name="row" property="completion" value="true">
														No
													</logic:notEqual>
										</td>
									</tr>
									<tr class='<%=(i % 2 == 0 ? "cellBase" : "cellLite")%>'>
										<td colspan=6>
											<touch:property propertyName="crimeDesc" />
										</td>
									</tr>
									<tr class='<%=(i % 2 == 0 ? "cellBase" : "cellLite")%>'>
										<td colspan=6>
											<touch:property propertyName="comment" />
										</td>
									</tr>
									<%
									i++;
									%>
								</touch:list>
							</table>
						</td>
					</tr>
<%--				</logic:notEmpty>--%>
			</logic:present>
			<tr>
				<td class="formSeperator" colspan="2">
					History
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<table width="100%" border="0" cellPadding="1" cellSpacing="2">
						<tr>
							<td class="lbl" width="30%">
								First Retained Jurisdiction?
							</td>
							<td class="lbl">
								<html:hidden property="riderEntryVO.firstRider" />
								<input type="checkbox" name="firstRiderYes"
									onclick="setFirsts('firstRider', 'Yes')" />
								<span id="firstRider1">Yes</span>
								<input type="checkbox" name="firstRiderNo"
									onclick="setFirsts('firstRider', 'No')" />
								<span id="firstRider2">No</span>
							</td>
							<td class="lbl" align="right">
								<span id="subsquntRiderDates" style="display: none">Dates:
									<html:text property="riderEntryVO.subsquntRiderDates" size="51"
										maxlength="50" /> </span>
							</td>
						</tr>
						<tr>
							<td class="lbl">
								First Incarceration?
							</td>
							<td class="lbl">
								<html:hidden property="riderEntryVO.firstIncarceration" />
								<input type="checkbox" name="firstIncarcerationYes"
									onclick="setFirsts('firstIncarceration', 'Yes')" />
								<span id="firstIncarceration1">Yes</span>
								<input type="checkbox" name="firstIncarcerationNo"
									onclick="setFirsts('firstIncarceration', 'No')" />
								<span id="firstIncarceration2">No</span>
							</td>
							<td class="lbl" align="right">
								<span id="subsquntIncarcerationDates" style="display: none">Dates:
									<html:text property="riderEntryVO.subsquntIncarcerationDates"
										size="51" maxlength="50" /> </span>
							</td>
						</tr>
						<tr>
							<td class="lbl" colspan="2" valign="top">
								Significant Prior Criminal History:
							</td>
							<td align="right">
								<html:textarea property="riderEntryVO.priorCrmnlHist" cols="50"
									rows="3" onkeydown="textCounter(this, 1000)"
									onkeyup="textCounter(this, 1000)" />
							</td>
						</tr>
						<tr>
							<td class="lbl" colspan="2" valign="top">
								Significant Behavior History:
							</td>
							<td align="right">
								<html:textarea property="riderEntryVO.behaviorHist" cols="50"
									rows="3" onkeydown="textCounter(this, 1000)"
									onkeyup="textCounter(this, 1000)" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="formSeperator" colspan="2">
					Other Factors
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<table width="100%" border="0" cellPadding="1" cellSpacing="2">
						<tr>
							<td class="lbl" valign="top">
								Medical or Mental Health Concerns:
							</td>
							<td align="right">
								<html:textarea property="riderEntryVO.healthConcerns" cols="50"
									rows="3" onkeydown="textCounter(this, 1000)"
									onkeyup="textCounter(this, 1000)" />
							</td>
						</tr>
						<tr>
							<td class="lbl" valign="top">
								Staffing Comments:
							</td>
							<td align="right">
								<html:textarea property="riderEntryVO.staffComment" cols="50"
									rows="3" onkeydown="textCounter(this, 1000)"
									onkeyup="textCounter(this, 1000)" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="formSeperator" colspan="2">
					Recommended Housing
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<table width="100%" border="0" cellPadding="1" cellSpacing="2">
						<tr>
							<td class="lbl" colspan="4" align="center">
								<html:hidden property="riderEntryVO.recommendedHousingCd" />
								<html:hidden property="riderEntryVO.recommendedHousingDesc" />
								<input type="radio" name="recmmdHousingCd" id="1312"
									value="1312" onclick="setHousingValue(1, this.value, 'CAPP-BOISE')" />
								CAPP-BOISE&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" name="recmmdHousingCd" id="1155"
									value="1155" onclick="setHousingValue(1, this.value, 'NICI')" />
								NICI&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" name="recmmdHousingCd" id="1194"
									value="1194" onclick="setHousingValue(1, this.value, 'SBWCC')" />
								SBWCC&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" name="recmmdHousingCd" id="1112"
									value="1112" onclick="setHousingValue(1, this.value, 'ISCI')" />
								ISCI&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" name="recmmdHousingCd" id="1177"
									value="1177" onclick="setHousingValue(1, this.value, 'PWCC')" />
								PWCC&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" name="recmmdHousingCd" id="cwc" value="0"
									onclick="setHousingValue(2, '', '')" />
								CWC&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<span id="cwcSelect" style="display:none"> <html:select
										property="recmmdCWCDesc"
										onchange="setHousingValue(2, this.value, '')">
										<option value="0">
											...
										</option>
										<option value="1032">
											CWC Boise
										</option>
										<option value="1033">
											CWC Idaho Falls
										</option>
										<option value="1034">
											CWC Nampa
										</option>
										<option value="1035">
											CWC SICI
										</option>
										<option value="1037">
											CWC Twin Falls
										</option>
									</html:select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>
								<input type="radio" name="recmmdHousingCd" id="1" value="1"
									onclick="setHousingValue(3, '', '')" />
								Other
								<span id="otherText" style="display:none"> <html:text
										property="recmmdOtherDesc" size="25" maxlength="25"
										onblur="setHousingValue(3, '0', this.value)" /> </span>
							</td>
						</tr>
						<tr>
							<td width="31%" class="lbl">
								Prepared By:
								<html:text property="riderEntryVO.prepUser" size="8"
									maxlength="8" onblur="checkName(this)" />
								<a href="javascript:currentUser()"> <img
										src="<%=request.getContextPath()%>/jsp/images/user2.gif"
										border="0" title="Current User" alt=""> </a>&nbsp;
								<a href="javascript:findUser('Prep')"> <img
										src="<%=request.getContextPath()%>/jsp/images/user.gif"
										border="0" title="Find User" alt=""> </a>
							</td>
							<td width="16%" class="lbl">
								Date
								<html:text property="riderEntryVO.prepDateAsString" size="10"
									maxlength="10" onfocus="setDefaultDate(this)"
									onblur="checkDate(this)" />
							</td>
							<html:hidden property="apprViewLevel" />
							<logic:equal name="offenderCustodyLevelForm"
								property="apprViewLevel" value="false">
								<td width="37%" class="lbl">
									Reviewing Authority:
									<bean:write name="offenderCustodyLevelForm"
										property="riderEntryVO.authUser" />
								</td>
								<td width="16%" class="lbl">
									Date
									<bean:write name="offenderCustodyLevelForm"
										property="riderEntryVO.authDateAsString" />
								</td>
							</logic:equal>
							<logic:equal name="offenderCustodyLevelForm"
								property="apprViewLevel" value="true">
								<td width="37%" class="lbl">
									Reviewing Authority:
									<html:text property="riderEntryVO.authUser" size="8"
										maxlength="8" onblur="checkName(this)" />
									<a href="javascript:currentUserAppr()"> <img
											src="<%=request.getContextPath()%>/jsp/images/user2.gif"
											border="0" title="Current User" alt=""> </a>&nbsp;
									<a href="javascript:findUser('Appr')"> <img
											src="<%=request.getContextPath()%>/jsp/images/user.gif"
											border="0" title="User" alt=""> </a>
								</td>
								<td width="16%" class="lbl">
									Date
									<html:text property="riderEntryVO.authDateAsString" size="10"
										maxlength="10" onfocus="setDefaultDate(this)"
										onblur="checkDate(this)" />
								</td>
							</logic:equal>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="formSeperator" colspan="2" id="byLine"></td>
			</tr>
			<tr>
				<td align="right" colspan="2">
					<span class="lbl">By: </span>
					<bean:write property="riderEntryVO.updtUserId" name="thisForm" />
					<bean:write property="riderEntryVO.updtDateAsString"
						name="thisForm" />
				</td>
			</tr>
			<tr>
				<td colspan="1">
					<input name="Button3" type="button" class="button" value="Back"
						onclick="history.go(-1)">
					<logic:equal name="offenderCustodyLevelForm" property="apprViewLevel" value="true">
						<html:reset value="Clear" styleClass="button" property="clear" />
					</logic:equal>

				</td>
				<td align="right" colspan="1">
					<logic:equal name="offenderCustodyLevelForm" property="apprViewLevel" value="true">
						<input name="Button7" type="button" class="button"
							value="Preview Form" onclick="printForm()">
						<input name="Button6" type="button" class="button" value="Save"
							onclick="saveForm()">
					</logic:equal>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="smallGray">
					<hr>
					Source File:classificationRiderView.jsp
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
