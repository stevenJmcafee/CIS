package otrack.dio.classification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import otrack.core.AppException;
import otrack.core.AppUser;
import otrack.core.OtrackDispatchAction;
import otrack.omp.offender.FindUserIfc;
import otrack.shared.constants.OtrackConstants;
import otrack.util.ListSorterUtil;


/**
 * ClassificationReportsAction.java
 * Created for artf 1371
 * @author smcafee
 * @August 25, 2009
 * @screen Name: classificationRprtMenu.jsp
 *
 * To handle the requests for different classification reports
 */
public class ClassificationReportsAction extends OtrackDispatchAction {

	private static final String RESOURCE_FILE = "DIO_ReportResources.properties";

	/**
     *
     * @param mapping, form, request, response
     * @return ActionForward
     * @throws Exception
     * @screen: classificationRprtMenu.jsp
     *
     * This method is called to display the menu of classification reports
     */
    public ActionForward viewPage(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response)
        throws Exception {
        String method = "viewPage";
        AppUser appUser = getAppUser(request);
        ClassificationReportsForm theForm = (ClassificationReportsForm) form;
        ActionForward forward = null;
        appUser.log(3, this, method, "Start...");
        String caller = null;
        try {
        	theForm.setScrdFlg("C");
           caller = request.getParameter("caller");
        	
        	//Get dropdown values
           ClassificationReportsProcessorIfc processor = 
               (ClassificationReportsProcessorIfc) 
               		getBusinessObject("ClassificationReportsProcessor", appUser);
            
    	   if(caller.equals("StatusReport")){
    	       appUser.log(3, this, method, "Get status report LocTypList");
        	   ArrayList<Object> alLocTypList = getGenericDropDownList("classification.status","loctype");
        	   theForm.setLocTypList(processor.getGenericDropDownList(alLocTypList));
        	   //Added smcafee, 3/1/2010, artf 1371, atch 1864, Item 2
        	   theForm.setStatusId(0);
        	   theForm.setFilterId(0);
        	   //End Add
    	   } else {
    	       appUser.log(3, this, method, "Get others LocTypList");
        	   ArrayList<Object> alLocTypList = getGenericDropDownList("classification","loctype");
        	   theForm.setLocTypList(processor.getGenericDropDownList(alLocTypList));
    	   }
    	   theForm.setLocTypCd(1);
    	   
	       appUser.log(3, this, method, "Got status report LocTypList");
    	   theForm.setFacilityList(processor.getFacility(1));
    	   theForm.setFacilityCd(0);
	       appUser.log(3, this, method, "Got facility list");
    	   
    	   theForm.setUnitList(processor.getUnitList(theForm.getFacilityCd(), theForm.getLocTypCd()));
    	   theForm.setUnitCd(0);
	       appUser.log(3, this, method, "Got unit list");
    	   
    	   //Get Filter List and sort order List
    	   if(caller.equals("StatusReport")){
    	       appUser.log(3, this, method, "Get status report Lists");
         	   ArrayList<Object> filterList = getGenericDropDownList("classification.status","filter");
        	   theForm.setFilterList(processor.getGenericDropDownList(filterList));
        	   
         	   ArrayList<Object> statusList = getGenericDropDownList("classification.status","type");
        	   theForm.setStatusList(processor.getGenericDropDownList(statusList));
        	   
         	   ArrayList<Object> sortList = getGenericDropDownList("classification.status","sortorder");
        	   theForm.setSortOrderList(processor.getGenericDropDownList(sortList));
    	   } else {
    	       appUser.log(3, this, method, "Get other report Lists");
         	   ArrayList<Object> filterList = getGenericDropDownList("classification","filter");
        	   theForm.setFilterList(processor.getGenericDropDownList(filterList));
        	   
         	   ArrayList<Object> sortList = getGenericDropDownList("classification","sortorder");
        	   theForm.setSortOrderList(processor.getGenericDropDownList(sortList));
    	   }
    	   theForm.setSortOrderId(1);

	       appUser.log(3, this, method, "Got Lists");
    	   
    	   //Set Custody Level CheckBoxes
    	   theForm.setCheckAllFlg("Y");
    	   this.checkAllBoxes(theForm);
    	   
    	   theForm.setEntryList(null);
    	   theForm.setUserName(true);
    	   theForm.setStaff(null);
    	   theForm.setStrtDate(null);
    	   theForm.setEndDate(null);
    	   theForm.setReClassDays(30);
    	   theForm = this.getOvrrdText(theForm);
    	   
    	   //Get page
           if(caller != null && caller != ""){
        	   forward = mapping.findForward(caller);
        	   theForm.setCaller(caller);
           }
        } catch (Exception e) {
        	forward = super.handleError(e, this, method, request, mapping);
        }
        appUser.log(3, this, method, "...End");
        return forward;
    }
    
    
    public ActionForward getSecurityThreatGrpRprt(ActionMapping mapping, ActionForm form,
       HttpServletRequest request, HttpServletResponse response)
       throws Exception {

       String sMethod = "getSecurityThreatGrpRprt";
       AppUser appUser = getAppUser(request);

       appUser.log(3, this, sMethod, "start");
       ClassificationReportsForm theForm = (ClassificationReportsForm) form;
       theForm.setCaller("securityThreatGrpRprt");
       ActionForward afReturn = null;
       
       ClassificationReportsProcessorIfc processor = 
       	(ClassificationReportsProcessorIfc) 
       		getBusinessObject("ClassificationReportsProcessor", appUser);

       try {
 
    	   
    	   if(theForm.getLocTypList() == null) {
        	   ArrayList<Object> alLocTypList = getGenericDropDownList(theForm.getCaller(),"loctype");
        	   theForm.setLocTypList(processor.getGenericDropDownList(alLocTypList));
    	   }
    	   theForm.setLocTypCd(1);
    	   
    	   theForm.setFacilityList(processor.getFacility(1));
    	   theForm.setFacilityCd(0);
    	   
    	   theForm.setUnitList(processor.getUnitList(theForm.getFacilityCd(), theForm.getLocTypCd()));
    	   theForm.setUnitCd(0);
           
     	   ArrayList<Object> alRawSortOrderList = getGenericDropDownList(theForm.getCaller(),"sortorder");
    	   theForm.setSortOrderList(processor.getGenericDropDownList(alRawSortOrderList));
    	   theForm.setSortOrderId(0);
           afReturn = mapping.findForward(theForm.getCaller());

       } //end of try
       catch (Exception e) {
    	   afReturn = super.handleError(e, this, sMethod, request, mapping);
       }
       return afReturn;
   }

   /**
    * @author smcafee
    * @param mapping, form, request, response
    * @return ActionForward
    * @throws Exception
    * @screen: prlHrngRprt.jsp
    *
    * This method is used to display the report page
    */
	public ActionForward submitReportJspPage(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

		String sMethod = "submitReportJspPage";
		AppUser appUser = getAppUser(request);
		ClassificationReportsForm theForm = (ClassificationReportsForm) form;
    	ActionForward afReturn = null;
		
        ClassificationReportsProcessorIfc processor = 
           	(ClassificationReportsProcessorIfc) 
           		getBusinessObject("ClassificationReportsProcessor", appUser);
		try 
		{
			appUser.log(3, this, sMethod, "start");
			//theForm.flush();

			if(theForm.getFacilityList() == null)
			{theForm.setFacilityList(processor.getFacility(1));}
			
			theForm.setUnitList(processor.getUnitList(theForm.getFacilityCd(), theForm.getLocTypCd()));
			
			if(theForm.getSortOrderList() == null)
			{
	        	   ArrayList<Object> alRawSortOrderList = getGenericDropDownList(theForm.getCaller(),"sortorder");
	        	   theForm.setSortOrderList(processor.getGenericDropDownList(alRawSortOrderList));
			}
					
			afReturn =  mapping.findForward(theForm.getCaller());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			afReturn = super.handleError(e, this, sMethod, request, mapping);
		}
		return afReturn;
	}

    /**
	* @author smcafee
	* @param mapping, form, request, response
	* @return ActionForward
	* @throws Exception
	*
	* This method is used to reset the unit list
	*/
    public ActionForward resetUnit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	AppUser appUser = getAppUser(request);
    	String sMethod = "resetUnit";
    	ClassificationReportsForm theForm = (ClassificationReportsForm) form;
    	ActionForward afReturn = null;
    	
        ClassificationReportsProcessorIfc processor = 
           	(ClassificationReportsProcessorIfc) 
           		getBusinessObject("ClassificationReportsProcessor", appUser);
    	try
    	{
    		theForm.setUnitList(processor.getUnitList(theForm.getFacilityCd(), theForm.getLocTypCd()));
    		theForm.setUnitCd(0);
			afReturn = mapping.findForward(theForm.getCaller());
    	}
    	catch(Exception e)
    	{
			e.printStackTrace();
			afReturn = super.handleError(e, this, sMethod, request, mapping);
    	}
    	return afReturn;
    }

    /**
	* @author smcafee
	* @param mapping, form, request, response
	* @return ActionForward
	* @throws Exception
	*
	* This method is used to reset the facility list
	*/
    public ActionForward resetFac(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	AppUser appUser = getAppUser(request);
    	String sMethod = "resetFac";
    	ClassificationReportsForm theForm = (ClassificationReportsForm) form;
    	ActionForward afReturn = null;
    	
        ClassificationReportsProcessorIfc processor = 
           	(ClassificationReportsProcessorIfc) 
           		getBusinessObject("ClassificationReportsProcessor", appUser);
    	try
    	{
	    	theForm.setFacilityList(processor.getFacility(theForm.getLocTypCd()));
    		theForm.setFacilityCd(0);
    		theForm.setUnitCd(0);
    		theForm.setEntryList(null);
			afReturn = mapping.findForward(theForm.getCaller());
    	}
    	catch(Exception e)
    	{
			e.printStackTrace();
			afReturn = super.handleError(e, this, sMethod, request, mapping);
    	}
    	return afReturn;
    }
    
	/**
	* This method collects all the information needed for a report, 
	* 	gets the data and sends it on to the Jasper report function
	* @author smcafee
	* Date 30 June, 2008
	* @param ActionMapping
	* @param form
	* @param request
	* @param response
	* @return ActionForward
	* @throws AppException
	*/
	public ActionForward generateJasperReport(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception{

		//Establish and initiate variables and objects
		String sMethod = "generateJasperReport";
		AppUser appUser = getAppUser(request);
		ClassificationReportsForm theForm = (ClassificationReportsForm) form;
		String sCaller = theForm.getCaller();
		appUser.log(OtrackConstants.INFO, this, sMethod, sCaller + " " + OtrackConstants.START);
		ActionForward reportReturn = null;
		try{
	        ClassificationReportsProcessorIfc processor = 
	           	(ClassificationReportsProcessorIfc) 
	           		getBusinessObject("ClassificationReportsProcessor", appUser);
			        
			if(theForm.getCaller().equals("FacilityReport")) {
				if( processor.getFacilityReportData(response, request, theForm)) {
					reportReturn = mapping.findForward(theForm.getCaller());
				}
			} else if(theForm.getCaller().equals("FacilityHistory")) {
				if( processor.getFacilityReportData(response, request, theForm)) {
					reportReturn = mapping.findForward(theForm.getCaller());
				}
			} else if(theForm.getCaller().equals("FacilitySummary")) {
				if( processor.getReportData(response, request, theForm)) {
					reportReturn = mapping.findForward(theForm.getCaller());
				}
			} else if(theForm.getCaller().equals("RaceMatrix")) {
				if( processor.getReportData(response, request, theForm)) {
					reportReturn = mapping.findForward(theForm.getCaller());
				}
			} else if(theForm.getCaller().equals("DueReport")) {
				if( processor.getReportData(response, request, theForm)) {
					reportReturn = mapping.findForward(theForm.getCaller());
				}
			} else if(theForm.getCaller().equals("StatusReport")) {
				if( processor.getStatusReportData(response, request, theForm)) {
					reportReturn = mapping.findForward(theForm.getCaller());
				}
			}
		}catch (Exception e2){
			e2.printStackTrace();
			reportReturn = super.handleError(e2, this, sMethod, request, mapping);
		}
		appUser.log(OtrackConstants.INFO, this, sMethod,  sCaller + " " + OtrackConstants.END);
		return reportReturn;
	}
	
	private ArrayList<Object> getGenericDropDownList(String sCaller, String sPropertyType)
	{
		ArrayList<Object> alReturn = new ArrayList<Object>();
		try{
			boolean isMore = true;
			int i = 0;
			while(isMore){
				try{
					String sNewItem = getProperty(RESOURCE_FILE, sCaller + "." + sPropertyType + "." + i);
					alReturn.add(sNewItem.toUpperCase());
					i++;
				} catch(IOException ioe) {
					if(i != 0){
						isMore = false;
					} else {
						i++;
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			alReturn = null;
		}
		return alReturn;
	}

    public ActionForward checkAll(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	AppUser appUser = getAppUser(request);
    	String sMethod = "checkAll";
    	ClassificationReportsForm theForm = (ClassificationReportsForm) form;
    	ActionForward afReturn = null;
        appUser.log(3, this, sMethod, "Start...");
    	try
    	{
    		this.checkAllBoxes(theForm);
 			afReturn = mapping.findForward(theForm.getCaller());
    	}
    	catch(Exception e)
    	{
			e.printStackTrace();
			afReturn = super.handleError(e, this, sMethod, request, mapping);
    	}
    	return afReturn;
    }
    
    private void checkAllBoxes(ClassificationReportsForm theForm){
		theForm.setUnClassFlg(theForm.getCheckAllFlg());
		theForm.setCloseRestrFlg(theForm.getCheckAllFlg());
		theForm.setCloseFlg(theForm.getCheckAllFlg());
		theForm.setMediumFlg(theForm.getCheckAllFlg());
		theForm.setMinRestrFlg(theForm.getCheckAllFlg());
		theForm.setMinFlg(theForm.getCheckAllFlg());
		theForm.setCommFlg(theForm.getCheckAllFlg());
		theForm.setRiderFlg(theForm.getCheckAllFlg());   	
    }

    public ActionForward submitForm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	AppUser appUser = getAppUser(request);
    	String sMethod = "submitForm";
    	ClassificationReportsForm theForm = (ClassificationReportsForm) form;
    	ActionForward afReturn = null;
        appUser.log(3, this, sMethod, "Start...");
    	try
    	{	
    		if(theForm.getScrdFlg().equals("Y")){
     			theForm.setScrdFlg("N");
    		} else {
     			theForm.setScrdFlg("Y");
    		}
    		afReturn = mapping.findForward(theForm.getCaller());
    	}
    	catch(Exception e)
    	{
			afReturn = super.handleError(e, this, sMethod, request, mapping);
    	}
    	return afReturn;
    }
    
	public ActionForward getFacilitySearchData(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception{

		//Establish and initiate variables and objects
		String method = "getFacilitySearchData";
		AppUser appUser = getAppUser(request);
		
		ClassificationReportsForm theForm = (ClassificationReportsForm) form;
		String sCaller = theForm.getCaller();
		appUser.log(OtrackConstants.INFO, this, method, sCaller + " " + OtrackConstants.START);
		ActionForward forwardReturn = null;
		try{
	        ClassificationReportsProcessorIfc processor = 
	           	(ClassificationReportsProcessorIfc) 
	           		getBusinessObject("ClassificationReportsProcessor", appUser);

			theForm.setEntryList(processor.getSearchData(theForm));
			
	        ListSorterUtil ut = new ListSorterUtil();
	        String sortBy = theForm.getSortOrderId()+"";
	        
        	if(sortBy.equals("2")){
	            Collection co = ut.doSort("offenderLoc:A,offenderName:A,fnlCstdyLvlDesc:D,recmmdCstdyLvlDesc:D,scrdCstdyDesc:D", theForm.getEntryList());
				theForm.setEntryList((ArrayList<ClassificationEntryVO>) co);
        	} else if(sortBy.equals("3")){
	            Collection co = ut.doSort("fnlCstdyLvlDesc:D,recmmdCstdyLvlDesc:D,scrdCstdyDesc:D,offenderName:A", theForm.getEntryList());
				theForm.setEntryList((ArrayList<ClassificationEntryVO>) co);
        	} else if(sortBy.equals("4")){
	            Collection co = ut.doSort("approvalDate:D,offenderName:A", theForm.getEntryList());
				theForm.setEntryList((ArrayList<ClassificationEntryVO>) co);
        	} else {
	            Collection co = ut.doSort("offenderName:A", theForm.getEntryList());
				theForm.setEntryList((ArrayList<ClassificationEntryVO>) co);
        	}
	        

			
			forwardReturn = mapping.findForward(theForm.getCaller());
		}catch (Exception e2){
			e2.printStackTrace();
			forwardReturn = super.handleError(e2, this, method, request, mapping);
		}
		appUser.log(OtrackConstants.INFO, this, method,  sCaller + " " + OtrackConstants.END);
		return forwardReturn;
	}
	
	public ActionForward getFacilityHistSearchData(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception{

		//Establish and initiate variables and objects
		String method = "getFacilitySearchData";
		AppUser appUser = getAppUser(request);
		
		ClassificationReportsForm theForm = (ClassificationReportsForm) form;
		String sCaller = theForm.getCaller();
		appUser.log(OtrackConstants.INFO, this, method, sCaller + " " + OtrackConstants.START);
		ActionForward forwardReturn = null;
		try{
	        ClassificationReportsProcessorIfc processor = 
	           	(ClassificationReportsProcessorIfc) 
	           		getBusinessObject("ClassificationReportsProcessor", appUser);

			theForm.setEntryList(processor.getHistSearchData(theForm));
			
	        ListSorterUtil ut = new ListSorterUtil();
	        String sortBy = theForm.getSortOrderId()+"";
	        
        	if(sortBy.equals("2")){
	        	sortBy = "offenderLoc";
        	} else if(sortBy.equals("3")){
	        	sortBy = "fnlCstdyLvlDesc";
        	} else if(sortBy.equals("4")){
	        	sortBy = "approvalDate";
        	} else {
        		sortBy = "offenderName";
        	}
	        
	        if (sortBy != null) {
	            Collection co = ut.doSort(sortBy, "D", theForm.getEntryList());

	            //set the sorted data on form
				theForm.setEntryList((ArrayList<ClassificationEntryVO>) co);
	        }

			
			forwardReturn = mapping.findForward(theForm.getCaller());
		}catch (Exception e2){
			e2.printStackTrace();
			forwardReturn = super.handleError(e2, this, method, request, mapping);
		}
		appUser.log(OtrackConstants.INFO, this, method,  sCaller + " " + OtrackConstants.END);
		return forwardReturn;
	}
	public ActionForward getStatusSearchData(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception{

		//Establish and initiate variables and objects
		String method = "getFacilitySearchData";
		AppUser appUser = getAppUser(request);
		
		ClassificationReportsForm theForm = (ClassificationReportsForm) form;
		String sCaller = theForm.getCaller();
		appUser.log(OtrackConstants.INFO, this, method, sCaller + " " + OtrackConstants.START);
		ActionForward forwardReturn = null;
		try{
	        ClassificationReportsProcessorIfc processor = 
	           	(ClassificationReportsProcessorIfc) 
	           		getBusinessObject("ClassificationReportsProcessor", appUser);

			theForm.setEntryList(processor.getStatusSearchData(theForm));
			
	        ListSorterUtil ut = new ListSorterUtil();
	        String sortBy = theForm.getSortOrderId()+"";
	        
        	if(sortBy.equals("2")){
	        	sortBy = "offenderName";
        	} else if(sortBy.equals("3")){
	        	sortBy = "offenderLoc";
        	} else {
        		sortBy = "prepDate";
        	}
	        
	        if (sortBy != null) {
	            Collection co = ut.doSort(sortBy, "D", theForm.getEntryList());

	            //set the sorted data on form
				theForm.setEntryList((ArrayList<ClassificationEntryVO>) co);
	        }

			
			forwardReturn = mapping.findForward(theForm.getCaller());
		}catch (Exception e2){
			e2.printStackTrace();
			forwardReturn = super.handleError(e2, this, method, request, mapping);
		}
		appUser.log(OtrackConstants.INFO, this, method,  sCaller + " " + OtrackConstants.END);
		return forwardReturn;
	}

    public ActionForward setSearchValue(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
    	AppUser appUser = getAppUser(request);
    	String sMethod = "setSearchValue";
    	ClassificationReportsForm theForm = (ClassificationReportsForm) form;
    	ActionForward afReturn = mapping.findForward(theForm.getCaller());
        appUser.log(3, this, sMethod, "Start...");
    	return afReturn;
    }
	public ActionForward checkName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = "checkName";
		AppUser appUser = getAppUser(request);
		appUser.log(3, this, method, "Start...");
		ActionForward forward = null;
        FindUserIfc processor = null;
        try {
        	ClassificationReportsForm theForm = (ClassificationReportsForm) form;
	        if (processor == null) {
	            processor = (FindUserIfc) getBusinessObject("FindUserProcessor", appUser);
	        }

	        if(processor.checkName(request.getParameter("name"))){
	        	theForm.setUserName(true);
	        } else {
	        	theForm.setUserName(false);
	        }
             forward = mapping.findForward(theForm.getCaller());
		} catch (Exception e) {
			forward = super.handleError(e, this, method, request, mapping);
		}
		appUser.log(3, this, method, "...End");
		return forward;
	}

	private ClassificationReportsForm getOvrrdText(ClassificationReportsForm theForm){
		ClassificationReportsForm returnForm = theForm;
		try{
			returnForm.setDiscrtnry1Text(getProperty("DIO_ReportResources.properties", "classification.discretionary.1.text"));
			returnForm.setDiscrtnry2Text(getProperty("DIO_ReportResources.properties", "classification.discretionary.2.text"));
			returnForm.setDiscrtnry3Text(getProperty("DIO_ReportResources.properties", "classification.discretionary.3.text"));
			returnForm.setDiscrtnry4Text(getProperty("DIO_ReportResources.properties", "classification.discretionary.4.text"));
			returnForm.setDiscrtnry5Text(getProperty("DIO_ReportResources.properties", "classification.discretionary.5.text"));
			returnForm.setMandtry1Text(getProperty("DIO_ReportResources.properties", "classification.mandatory.1.text"));
			returnForm.setMandtry2Text(getProperty("DIO_ReportResources.properties", "classification.mandatory.2.text"));
			returnForm.setMandtry3Text(getProperty("DIO_ReportResources.properties", "classification.mandatory.3.text"));
		}catch(IOException ioe){
		}
		
		return returnForm;
	}
	
	public ActionForward getTestSearchData(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception{

		//Establish and initiate variables and objects
		String method = "getTestSearchData";
		AppUser appUser = getAppUser(request);
		
		ClassificationReportsForm theForm = (ClassificationReportsForm) form;
		String sCaller = theForm.getCaller();
		appUser.log(OtrackConstants.INFO, this, method, sCaller + " " + OtrackConstants.START);
		ActionForward forwardReturn = null;
		try{
	        ClassificationReportsProcessorIfc processor = 
	           	(ClassificationReportsProcessorIfc) 
	           		getBusinessObject("ClassificationReportsProcessor", appUser);

			theForm.setEntryList(processor.getStatusSearchData(theForm));
			
	        ListSorterUtil ut = new ListSorterUtil();
	        String sortBy = theForm.getSortOrderId()+"";
	        
        	if(sortBy.equals("2")){
	        	sortBy = "offenderName";
        	} else if(sortBy.equals("3")){
	        	sortBy = "offenderLoc";
        	} else {
        		sortBy = "prepDate";
        	}
	        
	        if (sortBy != null) {
	            Collection co = ut.doSort(sortBy, "D", theForm.getEntryList());

	            //set the sorted data on form
				theForm.setEntryList((ArrayList<ClassificationEntryVO>) co);
	        }

			
			forwardReturn = mapping.findForward(theForm.getCaller());
		}catch (Exception e2){
			e2.printStackTrace();
			forwardReturn = super.handleError(e2, this, method, request, mapping);
		}
		appUser.log(OtrackConstants.INFO, this, method,  sCaller + " " + OtrackConstants.END);
		return forwardReturn;
	}

}
