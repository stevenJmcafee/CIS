package otrack.dio.classification;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.data.JRMapArrayDataSource;

import org.apache.struts.action.ActionForm;

import otrack.core.AppException;
import otrack.core.BizObject;
import otrack.core.LookupManager;
import otrack.dao.GenericDAO;
import otrack.shared.BodyLocationCodeWrapper;
import otrack.shared.ClassificationCustodyLevel;
import otrack.shared.ClassificationType;
import otrack.shared.OptionBean;
import otrack.shared.constants.OtrackConstants;
import otrack.shared.wrappers.ClassificationCustodyLevelWrapper;
import otrack.shared.wrappers.ClassificationTypeWrapper;
import otrack.shared.wrappers.GenericListWrapper;
import otrack.shared.wrappers.PrisonUnitWrapper;
import otrack.util.AppUtil;
import otrack.util.ReportUtil;


/**
 *
 * ClassificationReportsProcessorImpl.java
 *
 * @author smcafee
 * @Aug 25, 2009, artf 1371
 *
 * The processor class that handles business processes
 */
public class ClassificationReportsProcessorImpl extends BizObject
    implements ClassificationReportsProcessorIfc {

	private static final String RESOURCE_FILE = "DIO_ReportResources.properties";

	public void flush() {
        //	super.flush();
    }

     public String getObjectState() {
        return null;
    }

    public String print() {
        return null;
    }

     /**
     * get the facility drop down
     */
     public ArrayList getFacility(int iType) throws AppException {
    
      	String method = "getFacility";
          appUser.log(3, this, method, "start");

          ArrayList facilityList = null;

          try 
          {
              //For facilityList drop down 
              if (facilityList == null) 
              {
            	  BodyLocationCodeWrapper wrapper = (BodyLocationCodeWrapper)
              		LookupManager.getInstance().lookup("BodyLocationCode", appUser);
            	  if (iType == 1){
            		  facilityList = (ArrayList) wrapper.getOptions();
            	  }else if(iType == 2){
                	  String[] arrTypeCode = {"I"};
                      facilityList = (ArrayList) wrapper.getBodyLocDescCodeListByLocTypeArray(arrTypeCode);
            	  }else if(iType == 3){
                      facilityList = (ArrayList) wrapper.getBodyLocDescCodeListByLocTypeCode("C");
            	  }else if(iType == 4){
                      facilityList = (ArrayList) wrapper.getBodyLocDescCodeListByLocTypeCode("G");
            	  }else if(iType == 5){
                      facilityList = (ArrayList) wrapper.getBodyLocDescCodeListByLocTypeCode("W");
            	  }else if(iType == 6){
                      facilityList = (ArrayList) wrapper.getBodyLocDescCodeListByLocTypeCode("H");
            	  }else if(iType == 8){
                      facilityList = (ArrayList) wrapper.getBodyLocDescCodeListByLocTypeCode("T");
            	  }else if(iType == 9){
                      facilityList = (ArrayList) wrapper.getBodyLocDescCodeListByLocTypeCode("U");
            	  }else{
                	  String[] arrTypeCode = {"O", "E"};
                      facilityList = (ArrayList) wrapper.getBodyLocDescCodeListByLocTypeArray(arrTypeCode);
            	  }
                  
                  appUser.log(3, this, "initialize", "PrisonLocationWrapper set from wrapper");
              }

              appUser.log(3, this, method, "----End---");
          } 
          catch (Exception e) 
          {
              e.printStackTrace();
              throw new AppException(this, method, "error.screen.not.initialized");
          }

          return facilityList;
      }
     
    /**
    * get the unit drop down
    */
    public ArrayList<OptionBean> getUnitList(int iFacCd, int iLocTyp) throws AppException {
        String method = "getUnitList";
        appUser.log(3, this, method, "start");

        ArrayList<OptionBean> unitList = null;
        GenericDAO generic = appUser.getGenericDAO();

        try 
        {
      	  	
        	BodyLocationCodeWrapper wrapper = 
        		(BodyLocationCodeWrapper)
        			LookupManager.getInstance().lookup("BodyLocationCode", appUser);
        	String sSelect;     
        	ResultSet rsBodyLocCds = null;
        	ArrayList<String> arrCode = new ArrayList<String>();
        	switch(iLocTyp) {
        		case 2:
        			sSelect = "SELECT body_loc_cd FROM body_loc_ldesc_x WHERE body_loc_cd_x = " + iFacCd + " AND body_loc_cd != " + iFacCd;
        			rsBodyLocCds = generic.load(this, method, sSelect);
                	
                    if (rsBodyLocCds != null) {
                        while (rsBodyLocCds.next()) {
                        	arrCode.add(rsBodyLocCds.getInt(1)+"");
                        }
                    }
                 	
                	unitList = (ArrayList<OptionBean>) wrapper.getBodyLocDescCodeListByLocCodeArray(arrCode);
                    break;
        		case 4:
        			sSelect = "SELECT body_loc_cd FROM body_loc_cd WHERE body_loc_desc LIKE 'D" + (iFacCd - 1) + " %'";
        			rsBodyLocCds = generic.load(this, method, sSelect);
                	
                	
                    if (rsBodyLocCds != null) {
                        while (rsBodyLocCds.next()) {
                        	arrCode.add(rsBodyLocCds.getInt(1)+"");
                        }
                    }
                 	
                	unitList = (ArrayList<OptionBean>) wrapper.getBodyLocDescCodeListByLocCodeArray(arrCode);
                    break;
        		default:
                	PrisonUnitWrapper puWrapper = 
                		(PrisonUnitWrapper) 
                			LookupManager.getInstance().lookup("PrisonUnit", appUser);
        		
	                unitList = puWrapper.getOptions(1);
	                break;
        	}
//            unitList = (ArrayList) wrapper.getBodyLocDescCodeListByLocCodeArray(arrCode);

            appUser.log(3, this, "initialize", "Unit List set from wrapper");
        }
        catch (Exception e) 
        {
            e.printStackTrace();
            throw new AppException(this, method, "error.screen.not.initialized");
        }

        return unitList;
    }

     /**
 	* This method runs the query to get the Report Data
 	* Modified / added new methods By: smcafee
 	* Date : 5 June, 2008
 	* BOPP Report.
 	* @param String sStoredProc
 	* @param Vector vParams
 	* @throws AppException
 	*/
     public boolean getReportData(HttpServletResponse response, HttpServletRequest request, ActionForm form)throws AppException{
         String method = "getReportData";
         boolean reportReturn = false;
         ClassificationReportsForm theForm = (ClassificationReportsForm) form;
         String caller = theForm.getCaller();
         appUser.log(OtrackConstants.INFO, this, method, caller + " " + OtrackConstants.START);
         try {
	        //Get the report template
			String appPath = this.getProperty("app.context-path") + this.getProperty("app.context");
			
			String reportPath = null;	        	
			try{
				if(caller.equals("DueReport")){
					reportPath = appPath + getProperty(RESOURCE_FILE, "classification.report." + caller + "." + theForm.getSortOrderId());
				} else {
					reportPath = appPath + getProperty(RESOURCE_FILE, "classification.report." + caller);
				}
	        } catch(IOException ioe) {
	        }
			String subReportPath = null;	        	
			try{
				subReportPath = appPath + getProperty(RESOURCE_FILE, "classification.report." + caller+ ".subreport.path");
	        } catch(IOException ioe) {
	        }
			
			appUser.log(OtrackConstants.INFO, this, method, reportPath);
			
	        Map<String,Object> reportParams = new HashMap<String,Object>();
	        reportParams.put("report_title", getProperty(RESOURCE_FILE, "classification.report." + caller + ".title"));
	        reportParams.put("report_subtitle", request.getParameter("subTitle").replace("Sort", "\nSort"));
	        reportParams.put("report_subtitle2", request.getParameter("subTitle2").replace("Days Un", "\nDays Un"));
	        reportParams.put("report_creator", appUser.getUserID());
	        reportParams.put("report_location", getProperty(RESOURCE_FILE, "classification.location") + "/" + caller);
	        reportParams.put("report_logo", appPath + getProperty(RESOURCE_FILE,"logo"));
	        reportParams.put("SUBREPORT_DIR", subReportPath);
	        reportParams.put("body_loc_cd", theForm.getFacilityCd()+"");
	        reportParams.put("fclty_typ_cd", theForm.getLocTypCd()+"");
	        if(request.getParameter("subTitle2").indexOf("inal") >= 0){
		        reportParams.put("lvl_typ_cd", "A");
	        } else {
		        reportParams.put("lvl_typ_cd", "B");
	        }
	        reportParams.put("days_til_due", theForm.getReClassDays());
	        if(theForm.getStaff() != null && !theForm.getStaff().equals("")){
		        reportParams.put("mgr", theForm.getStaff());
	        } else {
		        reportParams.put("mgr", null);
	        }
			
		        
			appUser.log(OtrackConstants.INFO, this, method, "Ready to Write Report.");
          
  			if (ReportUtil.writeJasperReport(response, reportPath, reportParams, this.dbConnection)) {
  		        appUser.log(OtrackConstants.INFO, this, method, "Report Written:" + reportPath);
  		        reportReturn = true;
  		    } else {
  	 			throw new AppException(this, method, "error.general.processing");
  		    }
  		} catch (Exception e2) {
  			e2.printStackTrace();
  		}
  		
  		appUser.log(OtrackConstants.INFO, this, method, OtrackConstants.END);
  		return reportReturn;
 	}

     public boolean getFacilityReportData(HttpServletResponse response, HttpServletRequest request, ActionForm form)throws AppException{
         String method = "getFacilityReportData";
         boolean reportReturn = false;
         ClassificationReportsForm theForm = (ClassificationReportsForm) form;
         String caller = theForm.getCaller();
         appUser.log(OtrackConstants.INFO, this, method, caller + " " + OtrackConstants.START);
         try {
	        //Get the report template
			String appPath = this.getProperty("app.context-path") + this.getProperty("app.context");
			
			String reportPath;	        	
			try{
				reportPath = appPath + getProperty(RESOURCE_FILE, "classification.report." + caller);
	        } catch(IOException ioe) {
				reportPath = appPath + getProperty(RESOURCE_FILE, "classification.report." + caller);	        	
	        }
			String subReportPath = null;	        	
			try{
				subReportPath = appPath + getProperty(RESOURCE_FILE, "classification.report." + caller+ ".subreport.path");
	        } catch(IOException ioe) {
	        }
			String subTitle = null;
			subTitle = request.getParameter("subTitle").replace("Filter", "\nFilter").replace("Sort", "\nSort");
			String subTitle2 = null;
			subTitle2 = request.getParameter("subTitle4");
			String subTitle3 = null;
			subTitle3 = request.getParameter("subTitle2");
			
			appUser.log(OtrackConstants.INFO, this, method, reportPath);
			
	        Map<String,String> reportParams = new HashMap<String,String>();
	        reportParams.put("report_title", getProperty(RESOURCE_FILE, "classification.report." + caller + ".title"));
	        reportParams.put("report_subtitle", subTitle);
	        reportParams.put("report_subtitle2", subTitle2);
	        reportParams.put("report_subtitle3", subTitle3);
	        reportParams.put("report_creator", appUser.getUserID());
	        reportParams.put("report_location", getProperty(RESOURCE_FILE, "classification.location")+ "/" + caller);
	        reportParams.put("report_logo", appPath + getProperty(RESOURCE_FILE,"logo"));
	        reportParams.put("SUBREPORT_DIR", subReportPath);
	        reportParams.put("body_loc_cd", theForm.getFacilityCd()+"");
	        reportParams.put("fclty_typ_cd", theForm.getLocTypCd()+"");
			
		        
			appUser.log(OtrackConstants.INFO, this, method, "Ready to Write Report.");
		    Map[] reportRows = getFacilityReportList(theForm.getEntryList());
		    JRMapArrayDataSource dataSource = new JRMapArrayDataSource(reportRows);
          
  			if (ReportUtil.writeJasperReport(response, reportPath, reportParams, dataSource)) {
  		        appUser.log(OtrackConstants.INFO, this, method, "Report Written:" + reportPath);
  		        reportReturn = true;
  		    } else {
  	 			throw new AppException(this, method, "error.general.processing");
  		    }
  		} catch (Exception e2) {
  			e2.printStackTrace();
  		}
  		
  		appUser.log(OtrackConstants.INFO, this, method, OtrackConstants.END);
  		return reportReturn;
 	}
 

     /**
  	* This method gets a generic dropdown list
  	* Modified / added new methods By: smcafee
  	* Date : 5 June, 2008
  	* @param ArrayList alList
  	* @throws AppException
  	*/
     public ArrayList<Object> getGenericDropDownList(ArrayList alList)
     throws AppException {
         String sMethod = "getGenericDropDownList";
         appUser.log(3, this, sMethod, "start");
         ArrayList<Object> genericList = null;
         try {
             if (genericList == null) {
             	GenericListWrapper wrapper = 
 	        		(GenericListWrapper) 
 	        			LookupManager.getInstance().lookup("GenericList", appUser);

             	genericList = wrapper.setList(alList.toArray());

                 appUser.log(3, this, "initialize", "Generic List set from wrapper");
             }
             appUser.log(3, this, sMethod, "----End---");
         }catch (Exception e){
             e.printStackTrace();
             throw new AppException(this, sMethod, "error.screen.not.initialized");
         }
         return genericList;
     }
     
	/**
	* This get the report location list formatted for the SPL
	* @author smcafee
	* @param Integer iLocId
	* @param Integer iFaccId
    * @return String
    * @throws AppException
    */
	public String getBLCList(Integer iLocId,Integer iFacId)throws AppException {
		String sReturn = "";
    	 
        String sMethod = "getBLCList";
	  	appUser.log(OtrackConstants.INFO, this, sMethod, "getBLCList");
  		try {
  			String sSelectStmt = null;
  			if(iLocId < 2){
  	  	        sSelectStmt="SELECT body_loc_cd FROM body_loc_cd";
  			}else{
  	  			switch(iLocId){
	  				case 2:
	  					if(iFacId == 0){
	  	  					sSelectStmt = "SELECT body_loc_cd FROM body_loc_cd WHERE loc_typ_cd IN( 'I', 'N') AND vld_flg = 'Y'";
	  					}else{
	  	  					sSelectStmt = "SELECT body_loc_cd FROM body_loc_ldesc_x WHERE body_loc_cd_x = " + iFacId;
	  					}
  	  					break;
  	  				case 3: 
	  					if(iFacId == 0){
	  	  					sSelectStmt = "SELECT body_loc_cd FROM body_loc_cd WHERE loc_typ_cd = 'C' AND vld_flg = 'Y'";
	  					}else{
	  						sReturn =  "" + iFacId;
	  					}
  	  					break;
  	  				case 4: 
	  					if(iFacId <= 1){
	  	  					sSelectStmt = "SELECT body_loc_cd FROM body_loc_cd WHERE loc_typ_cd = 'F' AND vld_flg = 'Y'";
	  					}else{
	  	  					sSelectStmt = "SELECT body_loc_cd FROM body_loc_cd WHERE loc_typ_cd = 'F' AND  body_loc_desc LIKE 'D" + (iFacId - 1) + "%' AND vld_flg = 'Y'";
	  					}
  	  					break;
  	  				case 5: 
	  					if(iFacId == 0){
	  	  					sSelectStmt = "SELECT body_loc_cd FROM body_loc_cd WHERE loc_typ_cd = 'U' AND vld_flg = 'Y'";
	  					}else{
	  						sReturn =  "" + iFacId;
	  					}
  	  					break;
  	  				case 6: 
  	  					sSelectStmt = "SELECT body_loc_cd FROM body_loc_cd WHERE loc_typ_cd IN ('O','H', 'W', 'S', 'T', 'Z') AND vld_flg = 'Y'";
  	  					break;
   	  			}
  			}
  			appUser.log(OtrackConstants.INFO, this, sMethod, "Select: " + sSelectStmt);
  			if(sReturn.equals("")) {
  				GenericDAO generic = appUser.getGenericDAO();
  		        ResultSet rsReportData = null;
  				rsReportData = generic.load(this, sMethod, sSelectStmt);
  				if(rsReportData != null) {
  					while(rsReportData.next()) {
  						sReturn = sReturn + rsReportData.getString(1).trim();
  					}
  				}
  			}
  			
  		}catch(Exception e){
  			e.printStackTrace();
  			
  		}
    	return sReturn;
	}
     
    public ArrayList<ClassificationEntryVO> getSearchData(ClassificationReportsForm theForm) throws AppException{
	    String method = "getSearchData";
	
	    ArrayList<ClassificationEntryVO> resultList = null;
	    Vector<Object> params = new Vector<Object>();
	    Vector<Object> riderParams = new Vector<Object>();
	
	    //code for test purpose-establishing db connection
	    //get the instance of generic dao and set the appuser
	    GenericDAO generic = appUser.getGenericDAO();
	
	    //assign the name of stored proc
	    String storedProc = "get_fclty_clssfctn_rprt";
	    String storedProcRider = "get_fclty_clssfctn_rider_rprt";
	    ResultSet data = null;
        ClassificationTypeWrapper ctWrapper = 
        	(ClassificationTypeWrapper) LookupManager.getInstance().lookup("ClassificationType", appUser);
		ClassificationCustodyLevelWrapper cclWrapper = (ClassificationCustodyLevelWrapper) 
			LookupManager.getInstance().lookup("ClassificationCustodyLevel", appUser);
	
	    try {
	        params.add(theForm.getLocTypCd());
	        params.add(theForm.getFacilityCd());
        	if(theForm.getUnClassFlg().equals("Y")){
        		params.add(true);
        	} else {
            	params.add(false);
        	}
            resultList = new ArrayList<ClassificationEntryVO>();
            //Modified smcafee, 5/21/2010, artf 1639, atch 1969
        	if(theForm.getUnClassFlg().equals("Y") || theForm.getCloseFlg().equals("Y") 
        			|| theForm.getMediumFlg().equals("Y") || theForm.getMinFlg().equals("Y")
        			|| theForm.getCommFlg().equals("Y")){
        		
		        appUser.log(3, this, method, "querying database by executing stored procedure: " + storedProc);
		
		        //call the 1st execute stored Proc method if parameters need to be passed else call 2nd stmt 
		        data = generic.executeStoredProcedure(this, method, storedProc, params);
		
		        // data = generic.executeStoredProcedure(this, method, storedProc);
		        appUser.log(3, this, method, "results returned, parsing ...");
		        
		        if (data != null) {
		            //parse through the data set
		            while (data.next()) {
		            	ClassificationEntryVO vo = new ClassificationEntryVO();
		            	
		            	vo.setOfndrClfnId(data.getInt(2));
		            	vo.setOffenderNum(data.getInt(3));
		            	String clssTyp = null;
		            	clssTyp = data.getString(4);
		            	if(clssTyp != null){
		            		
			            	vo.setClassificationType(data.getString(4));
				            ClassificationType ctObj = ctWrapper.getClassificationType(vo.getClassificationType());
			            	vo.setClassificationDesc(ctObj.getClassificationTypeDesc());
			            	if(vo.getClassificationType().equals("B")){
			            		vo.setReClassType("true");
			            	} else if(vo.getClassificationType().equals("C")){
			            		vo.setReClassType("false");
			            	}
			            	vo.setCat1Code(data.getString(5));
			            	vo.setCat1Points(data.getInt(6)+"");
			            	vo.setCat1Comment(data.getString(7));
			            	vo.setCat2Code(data.getString(8));
			            	vo.setCat2Points(data.getInt(9)+"");
			            	vo.setCat2Comment(data.getString(10));
			            	vo.setCat3Code(data.getString(11));
			            	vo.setCat3Points(data.getInt(12)+"");
			            	vo.setCat3Comment(data.getString(13));
			            	vo.setCat4Points(data.getInt(14));
			            	vo.setCat4Score(data.getInt(15));
			            	
			            	vo.setScrdCstdyPoints(data.getInt(16));
			            	vo.setCat5Code(data.getString(17));
			            	vo.setCat5Comment(data.getString(18));
			            	vo.setCat5Points(data.getInt(19)+"");
			            	vo.setCat6Code(data.getString(20));
			            	vo.setCat6Comment(data.getString(21));
			            	vo.setCat6Points(data.getInt(22)+"");
	//		            	Modified smcafee, 2/16/2010, artf 1371, atch 1843, item 9
	//		            	vo.setTotalPoints(vo.getScrdCstdyPoints() + Integer.parseInt(vo.getCat5Points()) + Integer.parseInt(vo.getCat6Points()));
			            	vo.setTotalPoints(data.getInt(23));
	//		            	End Mod
			            	String clsTyp;
			            	if(vo.getClassificationType().equals("C")){
				            	clsTyp = "B";
			            	} else {
				            	clsTyp = vo.getClassificationType();
			            	}
			            	ArrayList recList = cclWrapper.getOptions(clsTyp);
			            	
			            	for(java.util.Iterator i = recList.iterator(); i.hasNext();){
			            		ClassificationCustodyLevel ccl = (ClassificationCustodyLevel) i.next();
			            		if( vo.getTotalPoints() <= ccl.getCustodyLevelScore()){
				 					vo.setScrdCstdyDesc(ccl.getClassificationCustodyLevelDesc());
				 				}
				 			}
			            	vo.setDiscrtnry1(data.getBoolean(24));
			            	vo.setDiscrtnry2(data.getBoolean(25));
			            	vo.setDiscrtnry3(data.getBoolean(26));
			            	vo.setDiscrtnry4(data.getBoolean(27));
//			            	Added smcafee, 8/10/2010, artf 1702, item 1
			            	vo.setDiscrtnry5(data.getBoolean(28));
//			            	End Add
			            	vo.setOvrdComment(data.getString(29));
			            	vo.setMandtry1(data.getBoolean(30));
			            	vo.setMandtry2(data.getBoolean(31));
			            	vo.setMandtry3(data.getBoolean(32));
			            	if(vo.isDiscrtnry1() || vo.isDiscrtnry2() || vo.isDiscrtnry3() || vo.isDiscrtnry4() || vo.isDiscrtnry5() || vo.isMandtry1() || vo.isMandtry2() || vo.isMandtry3()){
				            	vo.setOvrdDisFactors("Y");
			            	}else{
				            	vo.setOvrdDisFactors("N");
			            	}
			            	vo.setRecmmdCstdyLvlCode(data.getString(33));
			            	ClassificationCustodyLevel cclCode = cclWrapper.getClassificationCustodyLevel(vo.getRecmmdCstdyLvlCode());
			            	vo.setRecmmdCstdyLvlDesc(cclCode.getClassificationCustodyLevelDesc());
		
			            	vo.setFnlCstdyLvlCode(data.getString(34));
			            	if(vo.getFnlCstdyLvlCode() != null){
				            	ClassificationCustodyLevel fnlCode = cclWrapper.getClassificationCustodyLevel(vo.getFnlCstdyLvlCode());
				            	vo.setFnlCstdyLvlDesc(fnlCode.getClassificationCustodyLevelDesc());
			            	} else {
				            	vo.setFnlCstdyLvlDesc(null);
			            	}
			            	
			            	vo.setLevelDenied(data.getBoolean(35));
			            	vo.setDeniedComment(data.getString(36));
			            	
			            	vo.setUpdtUserId(data.getString(37));
			            	vo.setUpdtDate(data.getDate(38));
			            	vo.setPrepUser(data.getString(39));
			            	vo.setPrepDate(data.getDate(40));
			            	vo.setServerId(data.getString(41));
			            	vo.setServeDate(data.getDate(42));
			            	vo.setAppByUserId(data.getString(43));
			            	vo.setApprovalDate(data.getDate(44));
			            	vo.setFacHeadId(data.getString(45));
			            	vo.setFacHeadApprovalDate(data.getDate(46));
		            	}
		            	vo.setOffenderName(data.getString(47));
		            	vo.setOffenderLoc(data.getString(48));
		            	
		            	if(vo.getClassificationType() == null){
			            	if(theForm.getUnClassFlg().equals("Y")){
			            		resultList.add(vo);
			            	}
		            	} else {
		            		String checkLevel = null;
			            	if(theForm.getUnClassFlg().equals("Y")){
			            		if(vo.getFnlCstdyLvlDesc() != null){
			            			checkLevel = vo.getFnlCstdyLvlDesc().trim();
	//		            		} else if(vo.getRecmmdCstdyLvlDesc() != null){
	//		            			checkLevel = vo.getRecmmdCstdyLvlDesc().trim();
			            		} else {
	//		            			checkLevel = vo.getScrdCstdyDesc().trim();
			            			checkLevel = "";
			            			resultList.add(vo);
			            		}
			            	} else {
			            		if(vo.getFnlCstdyLvlDesc() != null){
			            			checkLevel = vo.getFnlCstdyLvlDesc().trim();
			            		} else {
			            			checkLevel = "";
			            		}
			            	}
		            		
			            	if(checkLevel.equals("CLOSE") && theForm.getCloseFlg().equals("Y")){
				                if(checkFilter(theForm.getFilterId(), vo)){
				            		resultList.add(vo);
				                }
			            	}
			            	if(checkLevel.equals("MEDIUM") && theForm.getMediumFlg().equals("Y")){
				                if(checkFilter(theForm.getFilterId(), vo)){
				            		resultList.add(vo);
				                }
			            	}
			            	if(checkLevel.equals("MINIMUM") && theForm.getMinFlg().equals("Y")){
				                if(checkFilter(theForm.getFilterId(), vo)){
				            		resultList.add(vo);
				                }
			            	}
			            	if(checkLevel.equals("COMMUNITY") && theForm.getCommFlg().equals("Y")){
				                if(checkFilter(theForm.getFilterId(), vo)){
				            		resultList.add(vo);
				                }
			            	}
		            	}
		            }
		        }
        	}
        	if(theForm.getRiderFlg().equals("Y") && theForm.getFilterId() < 2){
    	        riderParams.add(theForm.getLocTypCd());
    	        riderParams.add(theForm.getFacilityCd());
		        appUser.log(3, this, method, "querying database by executing stored procedure: " + storedProc);
		    	
		        //call the 1st execute stored Proc method if parameters need to be passed else call 2nd stmt 
		        data = generic.executeStoredProcedure(this, method, storedProcRider, riderParams);
		
		        // data = generic.executeStoredProcedure(this, method, storedProc);
		        appUser.log(3, this, method, "results returned, parsing ...");
		
		        if (data != null) {
		            //parse through the data set
		            while (data.next()) {
		            	ClassificationEntryVO vo = new ClassificationEntryVO();
		            	
		            	vo.setOfndrClfnId(data.getInt(2));
		            	vo.setClassificationType("D");
			            ClassificationType ctObj = ctWrapper.getClassificationType(vo.getClassificationType());
		            	vo.setClassificationDesc(ctObj.getClassificationTypeDesc());
		            	vo.setUpdtUserId(data.getString(3));
		            	vo.setUpdtDate(data.getDate(4));
		            	vo.setPrepUser(data.getString(5));
		            	vo.setPrepDate(data.getDate(6));
		            	vo.setAppByUserId(data.getString(7));
		            	vo.setApprovalDate(data.getDate(8));
		            	vo.setOffenderNum(data.getInt(9));
		            	vo.setOffenderName(data.getString(10));
		            	vo.setOffenderLoc(data.getString(11));
		            	vo.setScrdCstdyDesc("RIDER");
			            resultList.add(vo);
		            }
		        }
        	}
	    } catch (Exception e) {
			e.printStackTrace();
		}
	    return resultList;
	}

    private boolean checkFilter(int filterId, ClassificationEntryVO vo){
    	boolean filterReturn = false;
        if(filterId <= 1){
        	filterReturn = true;
        }else if(filterId == 2 && vo.getScrdCstdyDesc().trim().equals(vo.getFnlCstdyLvlDesc().trim())){
        	filterReturn = true;
        }else if(filterId == 3 && !vo.getScrdCstdyDesc().trim().equals(vo.getRecmmdCstdyLvlDesc().trim())){
        	filterReturn = true;
        }else if(filterId == 4 && !vo.getScrdCstdyDesc().trim().equals(vo.getFnlCstdyLvlDesc().trim())){
        	filterReturn = true;
        }
    	return filterReturn;
    }
    
	private Map[] getFacilityReportList(Collection dataList){
		ArrayList list = (ArrayList)dataList;
		HashMap[] reportRows = new HashMap[dataList.size()];
			int i=0;
			for(Iterator it = list.iterator();it.hasNext();){
				ClassificationEntryVO myVO = (ClassificationEntryVO)it.next();
				HashMap<String, Object> row = new HashMap<String, Object>();
					row.put("ofndr_num", myVO.getOffenderNum());
					row.put("loc", myVO.getOffenderLoc());
					row.put("ofndr_nam", myVO.getOffenderName());
					row.put("score_cust_lvl", myVO.getScrdCstdyDesc());
					row.put("recom_cust_lvl", myVO.getRecmmdCstdyLvlDesc());
//					modified smcafee, 2/2/2010, artf 1371, atch 1823, Item 2
					if(myVO.getScrdCstdyDesc() != null){
						if(myVO.getScrdCstdyDesc().equals("RIDER")){
							row.put("final_cust_lvl", "RIDER");
						} else {
							row.put("final_cust_lvl", myVO.getFnlCstdyLvlDesc());
						}
					}
//					End Mod
					row.put("class_dt", myVO.getApprovalDate());
					row.put("dis_ovrrds", getDisOvrrds(myVO));
					row.put("man_ovrrds", getManOvrrds(myVO));
					reportRows[i] = row;
					i++;
				}
		
		return reportRows;
	}   
	
	private String getDisOvrrds(ClassificationEntryVO myVO){
		StringBuffer sb = new StringBuffer();
		try{
			if(myVO.isDiscrtnry1()){
				sb.append(getProperty("DIO_ReportResources.properties", "classification.discretionary.1.text") + "\n");
			}
			if(myVO.isDiscrtnry2()){
				sb.append(getProperty("DIO_ReportResources.properties", "classification.discretionary.2.text") + "\n");
			}
			if(myVO.isDiscrtnry3()){
				sb.append(getProperty("DIO_ReportResources.properties", "classification.discretionary.3.text") + "\n");
			}
			if(myVO.isDiscrtnry4()){
				sb.append(getProperty("DIO_ReportResources.properties", "classification.discretionary.4.text") + "\n");
			}
			if(myVO.isDiscrtnry5()){
				sb.append(getProperty("DIO_ReportResources.properties", "classification.discretionary.5.text"));
			}		
		}catch(IOException ioe){
		}
		return sb.toString();
	}
	private String getManOvrrds(ClassificationEntryVO myVO){
		StringBuffer sb = new StringBuffer();
		try{
			if(myVO.isMandtry1()){
				sb.append(getProperty("DIO_ReportResources.properties", "classification.mandatory.1.text") + "\n");
			}
			if(myVO.isMandtry2()){
				sb.append(getProperty("DIO_ReportResources.properties", "classification.mandatory.2.text") + "\n");
			}
			if(myVO.isMandtry3()){
				sb.append(getProperty("DIO_ReportResources.properties", "classification.mandatory.3.text"));
			}
		
		}catch(IOException ioe){
		}
		return sb.toString();
	}

    public ArrayList<ClassificationEntryVO> getHistSearchData(ClassificationReportsForm theForm) throws AppException{
	    String method = "getHistSearchData";
	
	    ArrayList<ClassificationEntryVO> resultList = null;
	    Vector<Object> params = new Vector<Object>();
	
	    //code for test purpose-establishing db connection
	    //get the instance of generic dao and set the appuser
	    GenericDAO generic = appUser.getGenericDAO();
	
	    //assign the name of stored proc
	    String storedProc = "get_fclty_clssfctn_hist_rprt";
	    String storedProcRider = "get_fclty_clssfctn_rider_hist_rprt";
	    ResultSet data = null;
        ClassificationTypeWrapper ctWrapper = 
        	(ClassificationTypeWrapper) LookupManager.getInstance().lookup("ClassificationType", appUser);
		ClassificationCustodyLevelWrapper cclWrapper = (ClassificationCustodyLevelWrapper) 
			LookupManager.getInstance().lookup("ClassificationCustodyLevel", appUser);
	
	    try {
	        params.add(theForm.getLocTypCd());
	        params.add(theForm.getFacilityCd());
	        params.add(AppUtil.getSQLDate(theForm.getStrtDateAsString()));
	        params.add(AppUtil.getSQLDate(theForm.getEndDateAsString()));
	
	        appUser.log(3, this, method, "querying database by executing stored procedure: " + storedProc);
	
	        //call the 1st execute stored Proc method if parameters need to be passed else call 2nd stmt 
	        data = generic.executeStoredProcedure(this, method, storedProc, params);
	
	        // data = generic.executeStoredProcedure(this, method, storedProc);
	        appUser.log(3, this, method, "results returned, parsing ...");
	        
	        if (data != null) {
	            resultList = new ArrayList<ClassificationEntryVO>();
	            //parse through the data set
	            while (data.next()) {
	            	ClassificationEntryVO vo = new ClassificationEntryVO();
	            	
	            	vo.setOfndrClfnId(data.getInt(2));
	            	vo.setOffenderNum(data.getInt(3));
//	            	String clssTyp = null;
//	            	clssTyp = data.getString(4);
	            	if(data.getString(4) != null){
	            		
		            	vo.setClassificationType(data.getString(4));
			            ClassificationType ctObj = ctWrapper.getClassificationType(vo.getClassificationType());
		            	vo.setClassificationDesc(ctObj.getClassificationTypeDesc());
		            	if(vo.getClassificationType().equals("B")){
		            		vo.setReClassType("true");
		            	} else if(vo.getClassificationType().equals("C")){
		            		vo.setReClassType("false");
		            	}
		            	vo.setCat1Code(data.getString(5));
		            	vo.setCat1Points(data.getInt(6)+"");
		            	vo.setCat1Comment(data.getString(7));
		            	vo.setCat2Code(data.getString(8));
		            	vo.setCat2Points(data.getInt(9)+"");
		            	vo.setCat2Comment(data.getString(10));
		            	vo.setCat3Code(data.getString(11));
		            	vo.setCat3Points(data.getInt(12)+"");
		            	vo.setCat3Comment(data.getString(13));
		            	vo.setCat4Points(data.getInt(14));
		            	vo.setCat4Score(data.getInt(15));
		            	
		            	vo.setScrdCstdyPoints(data.getInt(16));
		            	vo.setCat5Code(data.getString(17));
		            	vo.setCat5Comment(data.getString(18));
		            	vo.setCat5Points(data.getInt(19)+"");
		            	vo.setCat6Code(data.getString(20));
		            	vo.setCat6Comment(data.getString(21));
		            	vo.setCat6Points(data.getInt(22)+"");
//		            	Modified smcafee, 2/16/2010, artf 1371, atch 1843, item 9
//		            	vo.setTotalPoints(vo.getScrdCstdyPoints() + Integer.parseInt(vo.getCat5Points()) + Integer.parseInt(vo.getCat6Points()));
		            	vo.setTotalPoints(data.getInt(23));
//		            	End Mod
		            	String clsTyp;
		            	if(vo.getClassificationType().equals("C")){
			            	clsTyp = "B";
		            	} else {
			            	clsTyp = vo.getClassificationType();
		            	}
		            	ArrayList recList = cclWrapper.getOptions(clsTyp);
		            	
		            	for(java.util.Iterator i = recList.iterator(); i.hasNext();){
		            		ClassificationCustodyLevel ccl = (ClassificationCustodyLevel) i.next();
		            		if( vo.getTotalPoints() <= ccl.getCustodyLevelScore()){
			 					vo.setScrdCstdyDesc(ccl.getClassificationCustodyLevelDesc());
			 				}
			 			}
		            	vo.setDiscrtnry1(data.getBoolean(24));
		            	vo.setDiscrtnry2(data.getBoolean(25));
		            	vo.setDiscrtnry3(data.getBoolean(26));
		            	vo.setDiscrtnry4(data.getBoolean(27));
//		            	Added smcafee, 8/10/2010, artf 1702, item 1
		            	vo.setDiscrtnry5(data.getBoolean(28));
//		            	End Add
		            	vo.setOvrdComment(data.getString(29));
		            	vo.setMandtry1(data.getBoolean(30));
		            	vo.setMandtry2(data.getBoolean(31));
		            	vo.setMandtry3(data.getBoolean(32));
		            	if(vo.isDiscrtnry1() || vo.isDiscrtnry2() || vo.isDiscrtnry3() || vo.isDiscrtnry4() || vo.isDiscrtnry5() || vo.isMandtry1() || vo.isMandtry2() || vo.isMandtry3()){
			            	vo.setOvrdDisFactors("Y");
		            	}else{
			            	vo.setOvrdDisFactors("N");
		            	}
		            	vo.setRecmmdCstdyLvlCode(data.getString(33));
		            	ClassificationCustodyLevel cclCode = cclWrapper.getClassificationCustodyLevel(vo.getRecmmdCstdyLvlCode());
		            	vo.setRecmmdCstdyLvlDesc(cclCode.getClassificationCustodyLevelDesc());
	
		            	vo.setFnlCstdyLvlCode(data.getString(34));
		            	if(vo.getFnlCstdyLvlCode() != null){
			            	ClassificationCustodyLevel fnlCode = cclWrapper.getClassificationCustodyLevel(vo.getFnlCstdyLvlCode());
			            	vo.setFnlCstdyLvlDesc(fnlCode.getClassificationCustodyLevelDesc());
		            	} else {
			            	vo.setFnlCstdyLvlDesc(null);
		            	}
		            	
		            	vo.setLevelDenied(data.getBoolean(35));
		            	vo.setDeniedComment(data.getString(36));
		            	
		            	vo.setUpdtUserId(data.getString(37));
		            	vo.setUpdtDate(data.getDate(38));
		            	vo.setPrepUser(data.getString(39));
		            	vo.setPrepDate(data.getDate(40));
		            	vo.setServerId(data.getString(41));
		            	vo.setServeDate(data.getDate(42));
		            	vo.setAppByUserId(data.getString(43));
		            	vo.setApprovalDate(data.getDate(44));
		            	vo.setFacHeadId(data.getString(45));
		            	vo.setFacHeadApprovalDate(data.getDate(46));
	            	}
	            	vo.setOffenderName(data.getString(47));
	            	vo.setOffenderLoc(data.getString(48));
	            	
	            	if(vo.getClassificationType() == null){
		            	if(theForm.getUnClassFlg().equals("Y")){
		            		resultList.add(vo);
		            	}
	            	} else {
	            		String checkLevel = null;
		            	if(theForm.getUnClassFlg().equals("Y")){
		            		if(vo.getFnlCstdyLvlDesc() != null){
		            			checkLevel = vo.getFnlCstdyLvlDesc().trim();
		            		} else if(vo.getRecmmdCstdyLvlDesc() != null){
		            			checkLevel = vo.getRecmmdCstdyLvlDesc().trim();
		            		} else {
		            			checkLevel = vo.getScrdCstdyDesc().trim();
		            		}
		            	} else {
		            		if(vo.getFnlCstdyLvlDesc() != null){
		            			checkLevel = vo.getFnlCstdyLvlDesc().trim();
		            		} else {
		            			checkLevel = "";
		            		}
		            	}
	            		
		            	if(checkLevel.equals("CLOSE") && theForm.getCloseFlg().equals("Y")){
			                if(checkFilter(theForm.getFilterId(), vo)){
			            		resultList.add(vo);
			                }
		            	}
		            	if(checkLevel.equals("MEDIUM") && theForm.getMediumFlg().equals("Y")){
			                if(checkFilter(theForm.getFilterId(), vo)){
			            		resultList.add(vo);
			                }
		            	}
		            	if(checkLevel.equals("MINIMUM") && theForm.getMinFlg().equals("Y")){
			                if(checkFilter(theForm.getFilterId(), vo)){
			            		resultList.add(vo);
			                }
		            	}
		            	if(checkLevel.equals("COMMUNITY") && theForm.getCommFlg().equals("Y")){
			                if(checkFilter(theForm.getFilterId(), vo)){
			            		resultList.add(vo);
			                }
		            	}
	            	}
	            	
	            }
	        }
        	if(theForm.getRiderFlg().equals("Y") && theForm.getFilterId() < 2){
		        appUser.log(3, this, method, "querying database by executing stored procedure: " + storedProc);
		    	
		        //call the 1st execute stored Proc method if parameters need to be passed else call 2nd stmt 
		        data = generic.executeStoredProcedure(this, method, storedProcRider, params);
		
		        // data = generic.executeStoredProcedure(this, method, storedProc);
		        appUser.log(3, this, method, "results returned, parsing ...");
		
		        if (data != null) {
		            //parse through the data set
		            while (data.next()) {
		            	ClassificationEntryVO vo = new ClassificationEntryVO();
		            	
		            	vo.setOfndrClfnId(data.getInt(2));
		            	vo.setClassificationType("D");
			            ClassificationType ctObj = ctWrapper.getClassificationType(vo.getClassificationType());
		            	vo.setClassificationDesc(ctObj.getClassificationTypeDesc());
		            	vo.setUpdtUserId(data.getString(3));
		            	vo.setUpdtDate(data.getDate(4));
		            	vo.setPrepUser(data.getString(5));
		            	vo.setPrepDate(data.getDate(6));
		            	vo.setAppByUserId(data.getString(7));
		            	vo.setApprovalDate(data.getDate(8));
		            	vo.setOffenderNum(data.getInt(9));
		            	vo.setOffenderName(data.getString(10));
		            	vo.setOffenderLoc(data.getString(11));
		            	vo.setScrdCstdyDesc("RIDER");
			            resultList.add(vo);
		            }
		        }
        	}
	    } catch (Exception e) {
			e.printStackTrace();
		}
	    return resultList;
	}

    public ArrayList<ClassificationEntryVO> getStatusSearchData(ClassificationReportsForm theForm) throws AppException{
	    String method = "getStatusSearchData";
	
	    ArrayList<ClassificationEntryVO> resultList = null;
	    Vector<Object> params = new Vector<Object>();
	
	    //code for test purpose-establishing db connection
	    //get the instance of generic dao and set the appuser
	    GenericDAO generic = appUser.getGenericDAO();
	
	    //assign the name of stored proc
	    String storedProc = "get_fclty_clssfctn_status_rprt";
	    String storedProcRider = "get_fclty_clssfctn_rider_status_rprt";
	    ResultSet data = null;
        ClassificationTypeWrapper ctWrapper = 
        	(ClassificationTypeWrapper) LookupManager.getInstance().lookup("ClassificationType", appUser);
		ClassificationCustodyLevelWrapper cclWrapper = (ClassificationCustodyLevelWrapper) 
			LookupManager.getInstance().lookup("ClassificationCustodyLevel", appUser);
	
	    try {
	        params.add(theForm.getLocTypCd());
	        params.add(theForm.getFacilityCd());
	
	        appUser.log(3, this, method, "querying database by executing stored procedure: " + storedProc);
	
	        //call the 1st execute stored Proc method if parameters need to be passed else call 2nd stmt 
	        data = generic.executeStoredProcedure(this, method, storedProc, params);
	
	        // data = generic.executeStoredProcedure(this, method, storedProc);
	        appUser.log(3, this, method, "results returned, parsing ...");
	        
	        if (data != null) {
	            resultList = new ArrayList<ClassificationEntryVO>();
	            //parse through the data set
	            while (data.next()) {
	            	ClassificationEntryVO vo = new ClassificationEntryVO();
	            	
	            	vo.setOfndrClfnId(data.getInt(2));
	            	vo.setOffenderNum(data.getInt(3));
//	            	String clssTyp = null;
//	            	clssTyp = data.getString(4);
	            	if(data.getString(4) != null){
	            		
		            	vo.setClassificationType(data.getString(4));
			            ClassificationType ctObj = ctWrapper.getClassificationType(vo.getClassificationType());
		            	vo.setClassificationDesc(ctObj.getClassificationTypeDesc());
		            	if(vo.getClassificationType().equals("B")){
		            		vo.setReClassType("true");
		            	} else if(vo.getClassificationType().equals("C")){
		            		vo.setReClassType("false");
		            	}
		            	vo.setCat1Code(data.getString(5));
		            	vo.setCat1Points(data.getInt(6)+"");
		            	vo.setCat1Comment(data.getString(7));
		            	vo.setCat2Code(data.getString(8));
		            	vo.setCat2Points(data.getInt(9)+"");
		            	vo.setCat2Comment(data.getString(10));
		            	vo.setCat3Code(data.getString(11));
		            	vo.setCat3Points(data.getInt(12)+"");
		            	vo.setCat3Comment(data.getString(13));
		            	vo.setCat4Points(data.getInt(14));
		            	vo.setCat4Score(data.getInt(15));
		            	
		            	vo.setScrdCstdyPoints(data.getInt(16));
		            	vo.setCat5Code(data.getString(17));
		            	vo.setCat5Comment(data.getString(18));
		            	vo.setCat5Points(data.getInt(19)+"");
		            	vo.setCat6Code(data.getString(20));
		            	vo.setCat6Comment(data.getString(21));
		            	vo.setCat6Points(data.getInt(22)+"");
//		            	Modified smcafee, 2/16/2010, artf 1371, atch 1843, item 9
//		            	vo.setTotalPoints(vo.getScrdCstdyPoints() + Integer.parseInt(vo.getCat5Points()) + Integer.parseInt(vo.getCat6Points()));
		            	vo.setTotalPoints(data.getInt(23));
//		            	End Mod
		            	String clsTyp;
		            	if(vo.getClassificationType().equals("C")){
			            	clsTyp = "B";
		            	} else {
			            	clsTyp = vo.getClassificationType();
		            	}
		            	ArrayList recList = cclWrapper.getOptions(clsTyp);
		            	
		            	for(java.util.Iterator i = recList.iterator(); i.hasNext();){
		            		ClassificationCustodyLevel ccl = (ClassificationCustodyLevel) i.next();
		            		if( vo.getTotalPoints() <= ccl.getCustodyLevelScore()){
			 					vo.setScrdCstdyDesc(ccl.getClassificationCustodyLevelDesc());
			 				}
			 			}
		            	vo.setDiscrtnry1(data.getBoolean(24));
		            	vo.setDiscrtnry2(data.getBoolean(25));
		            	vo.setDiscrtnry3(data.getBoolean(26));
//		            	Modified smcafee, 4/22/2010, artf 1630
		            	vo.setDiscrtnry4(data.getBoolean(27));
		            	vo.setDiscrtnry5(data.getBoolean(28));
		            	vo.setOvrdComment(data.getString(29));
		            	vo.setMandtry1(data.getBoolean(30));
		            	vo.setMandtry2(data.getBoolean(31));
		            	vo.setMandtry3(data.getBoolean(32));
		            	if(vo.isDiscrtnry1() || vo.isDiscrtnry2() || vo.isDiscrtnry3() || vo.isDiscrtnry4() || vo.isDiscrtnry5() || vo.isMandtry1() || vo.isMandtry2() || vo.isMandtry3()){
			            	vo.setOvrdDisFactors("Y");
		            	}else{
			            	vo.setOvrdDisFactors("N");
		            	}
		            	vo.setRecmmdCstdyLvlCode(data.getString(33));
		            	ClassificationCustodyLevel cclCode = cclWrapper.getClassificationCustodyLevel(vo.getRecmmdCstdyLvlCode());
		            	vo.setRecmmdCstdyLvlDesc(cclCode.getClassificationCustodyLevelDesc());
	
		            	vo.setFnlCstdyLvlCode(data.getString(34));
		            	if(vo.getFnlCstdyLvlCode() != null){
			            	ClassificationCustodyLevel fnlCode = cclWrapper.getClassificationCustodyLevel(vo.getFnlCstdyLvlCode());
			            	vo.setFnlCstdyLvlDesc(fnlCode.getClassificationCustodyLevelDesc());
		            	} else {
			            	vo.setFnlCstdyLvlDesc(null);
		            	}
		            	
		            	vo.setLevelDenied(data.getBoolean(35));
		            	vo.setDeniedComment(data.getString(36));
		            	
		            	vo.setUpdtUserId(data.getString(37));
		            	vo.setUpdtDate(data.getDate(38));
		            	vo.setPrepUser(data.getString(39));
		            	vo.setPrepDate(data.getDate(40));
		            	vo.setServerId(data.getString(41));
		            	vo.setServeDate(data.getDate(42));
		            	vo.setAppByUserId(data.getString(43));
		            	vo.setApprovalDate(data.getDate(44));
		            	vo.setFacHeadId(data.getString(45));
		            	vo.setFacHeadApprovalDate(data.getDate(46));
	            	}
	            	vo.setOffenderName(data.getString(47));
	            	vo.setOffenderLoc(data.getString(48));
//	            	End Mod artf 1630
	            	vo.setStatus(setStatus(vo));
	            	
	            	if(vo.getClassificationType().equals("A") && theForm.getFilterId() < 3){
		                if(checkStatusFilter(theForm.getStatusId(), vo.getStatus())){
		                	if(theForm.getStaff() == null || theForm.getStaff().trim().equals("")){
			            		resultList.add(vo);
		                	} else if(theForm.getStaff().equals(vo.getPrepUser())){
			            		resultList.add(vo);
		                	}
		                }
	            	}
	            	if((vo.getClassificationType().equals("B") || vo.getClassificationType().equals("C")) && 
	            		(theForm.getFilterId() == 3 || theForm.getFilterId() < 2)){
		                if(checkStatusFilter(theForm.getStatusId(), vo.getStatus())){
		                	if(theForm.getStaff() == null || theForm.getStaff().trim().equals("")){
			            		resultList.add(vo);
		                	} else if(theForm.getStaff().equals(vo.getPrepUser())){
			            		resultList.add(vo);
		                	}
		                }
	            	}            	
	            }
	        }
        	if(theForm.getFilterId() == 4 || theForm.getFilterId() < 2){
		        appUser.log(3, this, method, "querying database by executing stored procedure: " + storedProc);
		    	
		        //call the 1st execute stored Proc method if parameters need to be passed else call 2nd stmt 
		        data = generic.executeStoredProcedure(this, method, storedProcRider, params);
		
		        // data = generic.executeStoredProcedure(this, method, storedProc);
		        appUser.log(3, this, method, "results returned, parsing ...");
		
		        if (data != null) {
		            //parse through the data set
		            while (data.next()) {
		            	ClassificationEntryVO vo = new ClassificationEntryVO();
		            	
		            	vo.setOfndrClfnId(data.getInt(2));
		            	vo.setClassificationType("D");
			            ClassificationType ctObj = ctWrapper.getClassificationType(vo.getClassificationType());
		            	vo.setClassificationDesc(ctObj.getClassificationTypeDesc());
		            	vo.setUpdtUserId(data.getString(3));
		            	vo.setUpdtDate(data.getDate(4));
		            	vo.setPrepUser(data.getString(5));
		            	vo.setPrepDate(data.getDate(6));
		            	vo.setAppByUserId(data.getString(7));
		            	vo.setApprovalDate(data.getDate(8));
		            	vo.setOffenderNum(data.getInt(9));
		            	vo.setOffenderName(data.getString(10));
		            	vo.setOffenderLoc(data.getString(11));
		            	vo.setScrdCstdyDesc("RIDER");
		            	
		            	vo.setStatus(setStatusRider(vo));
		            	
		                if(checkStatusFilterRider(theForm.getStatusId(), vo.getStatus())){
		                	if(theForm.getStaff() == null || theForm.getStaff().trim().equals("")){
			            		resultList.add(vo);
		                	} else if(theForm.getStaff().equals(vo.getPrepUser())){
			            		resultList.add(vo);
		                	}
		                }
		            }
		        }
        	}
	    } catch (Exception e) {
			e.printStackTrace();
		}
	    return resultList;
	}
    private String setStatus(ClassificationEntryVO vo){
    	String statusReturn = null;
    	boolean ovrrd = false;
//    	Modified smcafee, 8/11/2010, artf 1702, item 3
    	if(vo.isDiscrtnry1() || vo.isDiscrtnry2() || vo.isDiscrtnry3() || vo.isDiscrtnry4() || vo.isDiscrtnry5()){
//    	End Add
    		ovrrd = true;
    	}
    	if(vo.isLevelDenied()){
    		statusReturn = "DENIED";
    	}else if(vo.getServerId() != null){
    		statusReturn = "SERVED";
    	}else if(vo.getAppByUserId() == null){
    		if(vo.getPrepUser() != null){
            	statusReturn = "PREPARED";
    		}
        }else{
        	if(ovrrd){
        		if(vo.getFacHeadId() == null){
	            //Modified smcafee, 3/2/2010, artf 1371, atch 1864, item 5
		        	statusReturn = "READY FOR FACILITY HEAD";
		        //End Mod
		        }else {
		        	statusReturn = "READY FOR SERVICE";
		        }
		    } else {
		        statusReturn = "READY FOR SERVICE";
	        }
        }
    	return statusReturn;
    }
 
    private boolean checkStatusFilter(int statusId, String status){
    	boolean filterReturn = false;
    	if(statusId == 1){
        	filterReturn = true;
    	} else if(statusId == 2 && status.equals("PREPARED")){
        	filterReturn = true;
        }else if(statusId == 3 && status.equals("REVIEWED")){
        	filterReturn = true;
        }else if(statusId == 4 && status.equals("DENIED")){
        	filterReturn = true;
        	//Modified smcafee, 3/9/2010, artf 1371, atch 1869, item 3
        }else if(statusId == 5 && status.equals("READY FOR FACILITY HEAD")){
        	filterReturn = true;
        }else if(statusId == 6 && status.equals("READY FOR SERVICE")){
        	filterReturn = true;
        	//End Mod
        }
    	return filterReturn;
    }

    private String setStatusRider(ClassificationEntryVO vo){
    	String statusReturn = null;
        if(vo.getAppByUserId() == null && vo.getPrepUser() != null){
        	statusReturn = "PREPARED";
        }else if(vo.getAppByUserId() != null){
        	statusReturn = "REVIEWED";
        }
    	return statusReturn;
    }

    private boolean checkStatusFilterRider(int statusId, String status){
    	boolean filterReturn = false;
    	if(statusId == 1){
        	filterReturn = true;
    	}else if(statusId <= 2 && status.equals("PREPARED")){
        	filterReturn = true;
        }else if(statusId == 3 && status.equals("REVIEWED")){
        	filterReturn = true;
        }
    	return filterReturn;
    }
    public boolean getStatusReportData(HttpServletResponse response, HttpServletRequest request, ActionForm form)throws AppException{
        String method = "getStatusReportData";
        boolean reportReturn = false;
        ClassificationReportsForm theForm = (ClassificationReportsForm) form;
        String caller = theForm.getCaller();
        appUser.log(OtrackConstants.INFO, this, method, caller + " " + OtrackConstants.START);
        try {
	        //Get the report template
			String appPath = this.getProperty("app.context-path") + this.getProperty("app.context");
			
			String reportPath;	        	
			reportPath = appPath + getProperty(RESOURCE_FILE, "classification.report." + caller);
			String subTitle = null;
			subTitle = request.getParameter("subTitle").replace("Filter", "\nFilter Type");
			String subTitle2 = null;
			subTitle2 = request.getParameter("subTitle2");
			String subTitle3 = null;
			subTitle3 = request.getParameter("subTitle3").replace("Status", "\nStatus");
			String subTitle4 = null;
			subTitle4 = request.getParameter("subTitle4");
			
			appUser.log(OtrackConstants.INFO, this, method, reportPath);
			
	        Map<String,String> reportParams = new HashMap<String,String>();
	        reportParams.put("report_title", getProperty(RESOURCE_FILE, "classification.report." + caller + ".title"));
	        reportParams.put("report_subtitle", subTitle);
	        reportParams.put("report_subtitle2", subTitle2);
	        reportParams.put("report_subtitle3", subTitle3);
	        reportParams.put("report_subtitle4", subTitle4);
	        reportParams.put("report_creator", appUser.getUserID());
	        reportParams.put("report_location", getProperty(RESOURCE_FILE, "classification.location"));
	        reportParams.put("report_logo", appPath + getProperty(RESOURCE_FILE,"logo"));
	        reportParams.put("body_loc_cd", theForm.getFacilityCd()+"");
	        reportParams.put("fclty_typ_cd", theForm.getLocTypCd()+"");
			
		        
			appUser.log(OtrackConstants.INFO, this, method, "Ready to Write Report.");
		    Map[] reportRows = getStatusReportList(theForm.getEntryList());
		    JRMapArrayDataSource dataSource = new JRMapArrayDataSource(reportRows);
         
 			if (ReportUtil.writeJasperReport(response, reportPath, reportParams, dataSource)) {
 		        appUser.log(OtrackConstants.INFO, this, method, "Report Written:" + reportPath);
 		        reportReturn = true;
 		    } else {
 	 			throw new AppException(this, method, "error.general.processing");
 		    }
 		} catch (Exception e2) {
 			e2.printStackTrace();
 		}
 		
 		appUser.log(OtrackConstants.INFO, this, method, OtrackConstants.END);
 		return reportReturn;
	}
    
	private Map[] getStatusReportList(Collection dataList){
		ArrayList list = (ArrayList)dataList;
		HashMap[] reportRows = new HashMap[dataList.size()];
			int i=0;
			for(Iterator it = list.iterator();it.hasNext();){
				ClassificationEntryVO myVO = (ClassificationEntryVO)it.next();
				HashMap<String, Object> row = new HashMap<String, Object>();
					row.put("ofndr_num", myVO.getOffenderNum());
					row.put("loc", myVO.getOffenderLoc());
					row.put("ofndr_nam", myVO.getOffenderName());
					row.put("status", myVO.getStatus());
					if(myVO.getScrdCstdyDesc().equals("RIDER")){
						row.put("cust_lvl", "RIDER");
					} else {
						if(myVO.getStatus().equals("PREPARED")){
							row.put("cust_lvl", myVO.getRecmmdCstdyLvlDesc());
						} else if(myVO.getStatus().equals("DENIED")){
							row.put("cust_lvl", myVO.getRecmmdCstdyLvlDesc());
						} else if(myVO.getStatus().equals("REVIEWED")){
							row.put("cust_lvl", myVO.getFnlCstdyLvlDesc());
						} else if(myVO.getStatus().equals("READY FOR SERVICE")){
							row.put("cust_lvl", myVO.getFnlCstdyLvlDesc());
						} else if(myVO.getStatus().equals("SERVED")){
							row.put("cust_lvl", myVO.getFnlCstdyLvlDesc());
						}
					}
					row.put("prep_id", myVO.getPrepUser());
					row.put("prep_dt", myVO.getPrepDate());
					reportRows[i] = row;
					i++;
				}
		
		return reportRows;
	}   
    public boolean getTestReportData(HttpServletResponse response, HttpServletRequest request, ActionForm form)throws AppException{
        String method = "getTestReportData";
        boolean reportReturn = false;
        ClassificationReportsForm theForm = (ClassificationReportsForm) form;
        String caller = theForm.getCaller();
        appUser.log(OtrackConstants.INFO, this, method, caller + " " + OtrackConstants.START);
        try {
	        //Get the report template
			String appPath = this.getProperty("app.context-path") + this.getProperty("app.context");
			
			String reportPath;	        	
			reportPath = appPath + getProperty(RESOURCE_FILE, "classification.report." + caller);
			String subTitle = null;
			subTitle = request.getParameter("subTitle").replace("Filter", "\nFilter Type");
			String subTitle2 = null;
			subTitle2 = request.getParameter("subTitle2");
			String subTitle3 = null;
			subTitle3 = request.getParameter("subTitle3").replace("Status", "\nStatus");
			String subTitle4 = null;
			subTitle4 = request.getParameter("subTitle4");
			
			appUser.log(OtrackConstants.INFO, this, method, reportPath);
			
	        Map<String,String> reportParams = new HashMap<String,String>();
	        reportParams.put("report_title", getProperty(RESOURCE_FILE, "classification.report." + caller + ".title"));
	        reportParams.put("report_subtitle", subTitle);
	        reportParams.put("report_subtitle2", subTitle2);
	        reportParams.put("report_subtitle3", subTitle3);
	        reportParams.put("report_subtitle4", subTitle4);
	        reportParams.put("report_creator", appUser.getUserID());
	        reportParams.put("report_location", getProperty(RESOURCE_FILE, "classification.location"));
	        reportParams.put("report_logo", appPath + getProperty(RESOURCE_FILE,"logo"));
	        reportParams.put("body_loc_cd", theForm.getFacilityCd()+"");
	        reportParams.put("fclty_typ_cd", theForm.getLocTypCd()+"");
			
		        
			appUser.log(OtrackConstants.INFO, this, method, "Ready to Write Report.");
		    Map[] reportRows = getStatusReportList(theForm.getEntryList());
		    JRMapArrayDataSource dataSource = new JRMapArrayDataSource(reportRows);
         
 			if (ReportUtil.writeJasperReport(response, reportPath, reportParams, dataSource)) {
 		        appUser.log(OtrackConstants.INFO, this, method, "Report Written:" + reportPath);
 		        reportReturn = true;
 		    } else {
 	 			throw new AppException(this, method, "error.general.processing");
 		    }
 		} catch (Exception e2) {
 			e2.printStackTrace();
 		}
 		
 		appUser.log(OtrackConstants.INFO, this, method, OtrackConstants.END);
 		return reportReturn;
	}
    
	private Map[] getTestReportList(Collection dataList){
		ArrayList list = (ArrayList)dataList;
		HashMap[] reportRows = new HashMap[dataList.size()];
			int i=0;
			for(Iterator it = list.iterator();it.hasNext();){
				ClassificationEntryVO myVO = (ClassificationEntryVO)it.next();
				HashMap<String, Object> row = new HashMap<String, Object>();
					row.put("ofndr_num", myVO.getOffenderNum());
					row.put("loc", myVO.getOffenderLoc());
					row.put("ofndr_nam", myVO.getOffenderName());
					row.put("status", myVO.getStatus());
					if(myVO.getScrdCstdyDesc().equals("RIDER")){
						row.put("cust_lvl", "RIDER");
					} else {
						if(myVO.getStatus().equals("PREPARED")){
							row.put("cust_lvl", myVO.getRecmmdCstdyLvlDesc());
						} else if(myVO.getStatus().equals("DENIED")){
							row.put("cust_lvl", myVO.getRecmmdCstdyLvlDesc());
						} else if(myVO.getStatus().equals("REVIEWED")){
							row.put("cust_lvl", myVO.getFnlCstdyLvlDesc());
						} else if(myVO.getStatus().equals("READY FOR SERVICE")){
							row.put("cust_lvl", myVO.getFnlCstdyLvlDesc());
						} else if(myVO.getStatus().equals("SERVED")){
							row.put("cust_lvl", myVO.getFnlCstdyLvlDesc());
						}
					}
					row.put("prep_id", myVO.getPrepUser());
					row.put("prep_dt", myVO.getPrepDate());
					reportRows[i] = row;
					i++;
				}
		
		return reportRows;
	}   
	private Connection buildConnection() throws SQLException {
		//log.debug("In the buildConnection method of DBConnection Builder.");
		String url = "";
		String userName = "";
		String passWord = "";
		try {
			Connection myConnection = null;
		
			Class.forName("com.informix.jdbc.IfxDriver");
			
			url = "jdbc:informix-sqli://ruby:1640/idoc:informixserver=online1";
			userName = "smcafee";
			passWord = "el5886_1";
			
			myConnection = DriverManager.getConnection(url, userName, passWord);
			return myConnection;
		} catch (ClassNotFoundException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

}
