<%--created smcafee, 8/30/2009, artf 1371, security--%>
<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/touch.tld" prefix="touch"%>

<bean:define id="thisForm" name="offenderCustodyLevelForm" type="otrack.dio.classification.OffenderCustodyLevelForm" />
<bean:define id="judicialDistrictList" name="offenderCustodyLevelForm" property="judicialDistrictList"/>
<html:html>
<head>
	<title>Classification Rider Entry</title>
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
				document.forms[0].staffPrep.value = currUser;
			}	
			
			function currentUserServe(){
				var currUser = "<bean:write name="appUser" property="userID"/>";		 
				document.forms[0].staffServer.value = currUser;
			}	
			function currentUserFacHead(){
				var currUser = "<bean:write name="appUser" property="userID"/>";		 
				document.forms[0].staffFacHead.value = currUser;
			}	
			function currentUserAppr(){
				var currUser = "<bean:write name="appUser" property="userID"/>";		 
				document.forms[0].entryObject.appByUserId.value = currUser;
			}	
		
			function findUser(caller){
				newWin = popup(600, 600, '<%=request.getContextPath()%>/userSearch.do?method=show&opener=classificationEntry' + caller, null, null, true);
				newWin.focus();	
			}
	

		function checkForm()
	  	{	
	  		var err = new Array();
	  		
	  		var distId = document.getElementById('detailObject.judicialDistrictId').value;
	  		var judgeId = document.getElementById('detailObject.judgeId').value;
	  		var endDate = document.getElementById('detailObject.endDateAsString').value;
	  		var crime = document.getElementById('detailObject.crimeDesc').value;
	  		if(distId == "0"){
	  		 	err[err.length]="Please Select a Judicial District.";
	  		}
	  		if(judgeId == 0){
	  		 	err[err.length]="Please Select a Judge.";
	  		}
	  		if(isWhitespace (endDate)){
	  		 	err[err.length]="Please Enter an End Date.";
	  		}
	  		if(isWhitespace (crime)){
	  		 	err[err.length]="Please Enter a Crime.";
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
	
	
	function loadButton(){
		var approvebtn ="approveButton";
		//var flag = window.confirm("Are you sure you want to save this record");
		//if(flag)
		//{
			if(checkForm())
			{  		var locationIndex = "<%=thisForm.getLocationIndex()%>"
					if(locationIndex<0)
					{
							document.forms[0].action="<%=request.getContextPath()%>/offenderCustodyLevelAction.do?method=add&aprovBttn="+approvebtn;
							document.forms[0].submit();
					}
					else
					{
							document.forms[0].action="<%=request.getContextPath()%>/offenderCustodyLevelAction.do?method=edit&aprovBttn="+approvebtn;
							document.forms[0].submit();
					}
				
			}
		//}
	}
	
	function setHousingValue(type){
		if(type == 1){
			document.getElementById("cwcSelect").style.display = "none";
			document.getElementById("otherText").style.display = "none";
			document.getElementById("recmmdCWCDesc").value = "0";
			document.getElementById("recmmdOtherDesc").value = "";
		} else if(type == 2){
			document.getElementById("cwcSelect").style.display = "inline";
			document.getElementById("otherText").style.display = "none";
			document.getElementById("recmmdOtherDesc").value = "";
		} else if(type == 3){
			document.getElementById("cwcSelect").style.display = "none";
			document.getElementById("otherText").style.display = "inline";
			document.getElementById("recmmdCWCDesc").value = "0";
		}
	}
	
	function saveForm()
	{
		if(checkForm()){
			var actionType;
			if(<%= thisForm.getLocationIndex()%> < 0){
				actionType = "I";
			} else {
				actionType = "U";
			}
		    document.forms[0].action="<%=request.getContextPath()%>/offenderCustodyLevelAction.do?method=saveRiderDetail&actionType=" + actionType;
			document.forms[0].submit();
		} else {
		}
	}

	function findJudge()
	{
       	newWin = popup(600, 600, '<%=request.getContextPath()%>/judgeCommissionerSearch.do?method=show&courtPersonnelType=J&opener=classificationRiderDetails', null, null, true);
		newWin.focus();	
	}

</script>
</head>
<body>
	<html:form action="offenderCustodyLevelAction.do" onsubmit="return doAction()">
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
			<tr>
				<td class="formSeperator" colspan="2">
					Retained Jurisdiction Details
				</td>
			</tr>
			<tr>
				<td class="lbl" align="right">Judicial District: </td>
				<td>
					<html:select name="offenderCustodyLevelForm" property="detailObject.judicialDistrictId">
			        	<html:options name="offenderCustodyLevelForm" collection="judicialDistrictList" property="value" labelProperty="label"/> 
			    	</html:select>
				</td>
			</tr>
			<tr>
				<td align="right" class="lbl">Judge: </td>
				<td>
					<html:text name="thisForm" readonly="true" property="detailObject.judgeDesc" />
					<html:hidden name="thisForm" property="detailObject.judgeId"/>
					<a href="javascript:findJudge()">
						<img src="<%=request.getContextPath()%>/jsp/images/user.gif" border="0" title="Find Judge">
					</a>
					
				</td>
			</tr>
			<tr>
				<td align="right" class="lbl">End Date:
				</td>
				<td>
					<html:text name="thisForm" property="detailObject.endDateAsString" size="10" maxlength="10" onfocus="setDefaultDate(this)" onblur="checkDate(this)"/>
				</td>
			</tr>
			<tr>
				<td align="right" class="lbl">Days:
				</td>
				<td>
					<html:text name="thisForm" property="detailObject.days" size="3" maxlength="3"/>
				</td>
			</tr>
			<tr>
				<td align="right" class="lbl">Crime:
				</td>
				<td>
					<html:text name="thisForm" property="detailObject.crimeDesc" size="50" maxlength="50"/>
				</td>
			</tr>
			<tr>
				<td align="right" class="lbl">Completion Order:
				</td>
				<td>
					<html:radio name="thisForm" property="detailObject.completion" value="true"/>Yes
					<html:radio name="thisForm" property="detailObject.completion" value="false"/>No
				</td>
			</tr>
			<tr>
				<td align="right" class="lbl">Recommendations:
				</td>
				<td>
					<html:textarea name="thisForm" property="detailObject.comment" cols="50" rows="3" onkeydown="textCounter(this, 1000)" onkeyup="textCounter(this, 1000)"/>
				</td>
			</tr>
			<tr><td class="formSeperator" colspan="2"></td></tr>
			<tr>
			    <td align="right" colspan="2"><span class="lbl">By: </span><bean:write property="detailObject.updtUser" name="thisForm"/> 
			    			<bean:write property="detailObject.updtDateAsString" name="thisForm"/>
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
						<input name="Button6" type="button" class="button" value="Save" onclick="saveForm()">
					</logic:equal>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="smallGray">
					<hr>
					Source File:classificationRiderDetails.jsp
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
