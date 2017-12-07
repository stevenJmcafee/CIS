<%--created smcafee, 8/30/2009, artf 1371--%>
<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/touch.tld" prefix="touch"%>

<bean:define id="thisForm" name="classificationRiderDetailForm" type="otrack.dio.classification.ClassificationRiderDetailForm" />
<bean:define id="judicialDistrictList" name="classificationRiderDetailForm" property="judicialDistrictList"/>
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
	  		
	  		var recommendLvl = document.getElementById('entryObject.recmmdCstdyLvlCode').value;
	  		var fnlLvl = document.getElementById('entryObject.fnlCstdyLvlCode').value;
	  		var prep = document.getElementById('entryObject.recmmdCstdyLvlCode').value;
	  		var appr = document.getElementById('entryObject.fnlCstdyLvlCode').value;
	  		if(recommendLvl < "0"){
	  		 	err[err.length]="Please Select a Recommended Level.";
	  		}
	  		if(fnlLvl < "0"){
	  		 	err[err.length]="Please Select a Final Level.";
	  		}
	  		if(prep == ""){
	  		 	err[err.length]="Please Enter a Preparer Name.";
	  		}
	  		if(appr == ""){
	  		 	err[err.length]="Please Enter a Approver Name.";
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
		} else {
		}
<%--	    document.forms[0].action="<%=request.getContextPath()%>/offenderCustodyLevelAction.do?method=edit";--%>
<%--		document.forms[0].submit();--%>
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
					<html:select name="classificationRiderDetailForm" property="judicialDistrictId">
			        	<html:options collection="judicialDistrictList" property="value" labelProperty="label"/> 
			    	</html:select>
				</td>
			</tr>
			<tr><td class="formSeperator" colspan="2"></td></tr>
			<tr>
				<td colspan="1">
					<input name="Button3" type="button" class="button" value="Back"
						onclick="history.go(-1)">
					<html:reset value="Clear" styleClass="button" property="clear" />
				</td>
				<td align="right" colspan="1">
					<input name="Button7" type="button" class="button"
						value="Preview Form" onclick="printForm()">
					<input name="Button6" type="button" class="button" value="Save"
						onclick="saveForm()">
				</td>
			</tr>
			<tr>
				<td colspan="2" class="smallGray">
					<hr>
					Source File:classificationRiderEntry.jsp
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
