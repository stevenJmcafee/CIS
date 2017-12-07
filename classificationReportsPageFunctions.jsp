<%--Created smcafee, 8/25/2009, artf 1371--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld"  prefix="bean" %>

<script language="javascript">
			function checkName(obj){
				if(obj.value != null && obj.value != ""){
					document.getElementById('userName'). value = "false";
					document.getElementById('userNameCaller'). value = obj.name;
					document.forms[0].action="<%=request.getContextPath()%>/classificationReportsAction.do?method=checkName&name=" + obj.value;
					document.forms[0].submit();
				}
			}
			function pageLoad(caller) {
			
				try {
					var chkNam = document.getElementById('userName').value;
					var chkNamCaller = document.getElementById('userNameCaller').value;
					if(chkNam != "true"){
						alert("That name is not a valid user. \n\n Please enter a different name.");
						document.getElementById(chkNamCaller).value = "";
						document.getElementById(chkNamCaller).focus();
					}
					document.getElementById('userName').value = "true";
			 	} catch(e){
		 		}
			
 				document.getElementById('tdTitle').innerHTML = '<h4>' + document.title + '</h4>';
 				document.getElementById('tdTopCaption').innerHTML = document.title;
 				try{
 					document.getElementById('Search').style.display = 'none';
<%-- 					document.forms[0].printButton.style.display = 'none';--%>
				} catch (e){
				}
				
 				var loc = document.getElementById('locTypCd').innerHTML;
 				var locStart = loc.indexOf("selected>");
 				
 				if(locStart > 20) {
 					var locEnd = loc.indexOf("<",locStart);
 					var locname = loc.substring(locStart + 9, locEnd);
 					if(locname == "INSTITUTION"){
 						if(document.getElementById('facilityCd').value == 0){
 							loc = "Location: All Institutions";
 							document.getElementById('Search').style.display = 'inline';
<%-- 							document.forms[0].printButton.style.display = "inline";--%>
 						}else{
 							loc = "Location: " + locname;
 							document.getElementById('Search').style.display = 'inline';
 						}
 					} else if(locname == "COMMUNITY CORRECTION CENTERS"){
 						if(document.getElementById('facilityCd').value == 0){
 							loc = "Location: All Work Centers";
 							document.getElementById('Search').style.display = 'inline';
<%-- 							document.getElementById('printButton').style.display = 'none';--%>
 						}else{
 							loc = "Location: " + locname;
 							document.getElementById('Search').style.display = "inline";
<%-- 							document.getElementById('printButton').style.display = 'none';--%>
 						}
 					}else{
 						loc = "Location: " + locname;
 						document.getElementById('printButton').style.display = 'none';
 						document.getElementById('Search').style.display = 'inline';
 					}
 				} else {
 					loc = "<h5><font color='red'>Please Select a Location</font></h5>";
 					document.getElementById('printButton').style.display = 'none';
 				}
				var subLocStart = "1";
				try{
 					var subLoc = document.getElementById('facilityCd').innerHTML;
 					var subLocStart = subLoc.indexOf("selected>");
 				} catch(e){
 				}
 				if(subLocStart > 20) {
 					var subLocEnd = subLoc.indexOf("<",subLocStart);
 					subLoc = "Location: " + subLoc.substring(subLocStart + 9, subLocEnd);
 				} else {
 					subLoc = "";
 				}
 				if(subLoc != ""){
 					loc = subLoc;
 				}

				var sort = null;
 				
				var filter = null;
 				try{
	 				filter = document.getElementById('filter').innerHTML;
	 				var startPlace = filter.indexOf("selected>");
	 				startPlace = startPlace + 9;
	 				var endPlace = filter.indexOf("</",startPlace);
	 				filter = filter.substring(startPlace, endPlace);
	 				if(filter == "..."){
	 					filter = "All"
	 				}
	 				filter = 'Filter: ' + filter;
	 				
	 			} catch(e){
 				}
 				
 				if(sort != null && filter != null){
 					document.getElementById('tdSubTitle').innerHTML = "<h6>" + loc + "<BR><BR>" + filter + "<BR><BR>" + sort + "</h6>";
 				} else if(sort != null && filter == null){
 					document.getElementById('tdSubTitle').innerHTML = "<h6>" + loc + "<BR><BR>" + sort + "</h6>";
 				} else if(sort == null && filter != null){
 					document.getElementById('tdSubTitle').innerHTML = "<h6>" + loc + "<BR><BR>" + filter + "</h6>";
 				} else {
 					document.getElementById('tdSubTitle').innerHTML = "<h6>" + loc + "</h6>";
 				}
 				
<%-- 				Used in the History jsp--%>
 				try{
	 				var startDate = document.getElementById('startDate').innerHTML;
	 				var startPlace = startDate.indexOf("value=");
	 				startDate = '<h6>From: ' + startDate.substring(startPlace + 6, startPlace + 16);
	 				var endDate = document.getElementById('endDate').innerHTML;
	 				var endPlace = endDate.indexOf("value=");
	 				endDate = ' To: ' + endDate.substring(endPlace + 6, endPlace + 16) + '</h6>';
	 				if(startDate.substring(0, 11) == "<h6>From: T" || endDate.substring(0, 6) == " To: T"){
	 					document.getElementById('tdSubTitle2').innerHTML = "<h5><font color='red'>Please Select Dates</font></h5>";
 						document.getElementById('Search').style.display = 'none';
	 				} else {
	 					document.getElementById('tdSubTitle2').innerHTML = startDate + endDate;
	 				}
	 			} catch(e){
 				}
<%-- 				Used in the Due Reclassification jsp--%>
 				try{
	 				var caseMan = document.getElementById('staff').value;
	 				if(caseMan == null || caseMan == ""){
	 					caseMan = "All";
	 				}
	 				var days = document.getElementById('reClassDays').value;
	 				document.getElementById('tdSubTitle2').innerHTML = 
	 				"<h6>Case Manager: " + caseMan + "<BR><BR>Days Until Reclassification Due: " + days + "</h6>";
	 			} catch(e){
	 			//"<h6><font color='red'>
 				}
 				
<%-- 				Used in the Race Matrix jsp--%>
 				try{
	 				var scored = null;
	 				var indxScrd = document.getElementById('scrd').innerHTML.indexOf("CHECKED")
	 				var indxFnl = document.getElementById('fnl').innerHTML.indexOf("CHECKED")
 					if(indxScrd > 0){
	 					scored =  'Scored Custody Level ';
 					}
 					if(indxFnl > 0){
	 					scored =  'Final Custody Level';
 					}
	 				if(scored == null){
	 					document.getElementById('tdSubTitle2').innerHTML = 
	 						"<h5><font color='red'>Please Select a Custody Level</font></h5>";
 						document.getElementById('printButton').style.display = 'none';
	 				} else {
	 					document.getElementById('tdSubTitle2').innerHTML = 
	 					"<h6>" + scored + "</h6>";
 						document.getElementById('printButton').style.display = 'inline';
	 				}
	 			} catch(e){
 				}
 				
<%-- 				Used in the Status jsp--%>
				var status = null;
 				try{
	 				status = document.getElementById('status').innerHTML;
	 				var startPlace = status.indexOf("selected>");
	 				startPlace = startPlace + 9;
	 				var endPlace = status.indexOf("</",startPlace);
	 				status = status.substring(startPlace, endPlace);
	 				if(status == "..."){
	 					status = "All"
	 				}
	 				status = 'Status: ' + status;
	 				
	 			} catch(e){
 				}
	 			if(status == null){
	 				document.getElementById('tdSubTitle3').innerHTML ="";
	 			} else {
	 				var caseMan = document.getElementById('staff').value;
	 				if(caseMan == null || caseMan == ""){
	 					caseMan = "Preparer: All";
	 				} else {
	 					caseMan = "Preparer: " + caseMan;
	 				}
	 				document.getElementById('tdSubTitle3').innerHTML = 
	 				"<h6>" + caseMan + "<BR><BR>" + status + "</h6>";
	 			}
	 			
 				try{
	 				sort = document.getElementById('sortorder').innerHTML;
	 				var startPlace = sort.indexOf("selected>");
	 				startPlace = startPlace + 9;
	 				var endPlace = sort.indexOf("</",startPlace);
	 				sort = 'Sort Order: ' + sort.substring(startPlace, endPlace);
		 			if(sort == null){
		 				document.getElementById('tdSubTitle4').innerHTML ="<h6>Sort Order: All</h6>";
		 			} else {
	 				document.getElementById('tdSubTitle4').innerHTML = 
	 				"<h6>" + sort + "</h6>";
		 			}
	 			} catch(e){
 				}
 				
 				if(document.getElementById('searchFlg').value == 'true'){
					document.getElementById('searchFlg').value = false;
 					doSearch();
 				}
 			}

</script>
					<tr>
						<td class="formCaption" colspan="4" align="center" width=100%>
							<label id="tdTopCaption"></label> 
							&nbsp;
							<a href="javascript:window.print()">
								<img border="0" src="<%=request.getContextPath()%>/jsp/images/print.gif" title="Print Screen">
							</a>
							&nbsp;
							<a href="javascript:showHelp('classificationReportsMenu.jsp')">
								<img src="<%=request.getContextPath()%>/jsp/images/help.gif" border="0" title="Screen Help">
							</a>
						</td>
					</tr>
				
