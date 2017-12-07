package otrack.dio.classification;

import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import otrack.core.GenericListForm;
import otrack.util.AppUtil;

/**
 *
 * ClassificationReportsForm.java
 *
 * @author smcafee
 * @Aug 25, 2009 artf 1371
 * @screen Name: classificationReportsMenu.jsp
 *
 * This class is the form bean for the Classification Reports
 */
public class ClassificationReportsForm extends GenericListForm {
	static final long serialVersionUID = 1l;
	ClassificationReportsVO currentObject;
    private String staff;
    private ArrayList locTypList;
    private ArrayList facilityList;
    private ArrayList unitList;
    private int locTypCd;
    private int facilityCd;
    private int unitCd;
	private String sortOrder;
	private ArrayList<Object> sortOrderList;
	private int sortOrderId;
    private int reClassDays;
	private String checkAllFlg;
	private String unClassFlg;
	private String closeRestrFlg;
	private String closeFlg;
	private String mediumFlg;
	private String minRestrFlg;
	private String minFlg;
	private String commFlg;
	private String riderFlg;
	private String scrdFlg;
	private String fnlFlg;
	private String filter;
	private ArrayList<Object> filterList;
	private int filterId;
	private String status;
	private ArrayList<Object> statusList;
	private int statusId;
	private Date strtDate;
	private Date endDate;
	private String strtDateAsString;
	private String endDateAsString;
	ClassificationEntryVO entryObject;	
	private ArrayList<ClassificationEntryVO> entryList;
	
	 private boolean userName;
	 private String userNameCaller;
	
	private boolean searchFlg;
	private String discrtnry1Text;
	private String discrtnry2Text;
	private String discrtnry3Text;
	private String discrtnry4Text;
	private String discrtnry5Text;
	private String mandtry1Text;
	private String mandtry2Text;
	private String mandtry3Text;
    // End Add
    public ClassificationReportsVO getCurrentObject() {
        if (currentObject != null) {
            //condition is true incase of save(edit/add) only
            return currentObject;
        } else {
            if (getLocationIndex() >= 0) {
                //condition met when view (edit mode) or delete
                //Note: Remember to dereference VO (by calling semiflush) in action class
                // to set current obj = null in delete mode
            	ClassificationReportsVO obj = (ClassificationReportsVO) getDatalist()
                                                                        .toArray()[getLocationIndex()];
                currentObject = new ClassificationReportsVO(obj);

                return currentObject;
            } else {
                //condition true for view (add/insert) mode
                currentObject = new ClassificationReportsVO();

                return currentObject;
            }
        }
    }

    public void setCurrentObject(ClassificationReportsVO myVO) {
        currentObject = myVO;
    }


    /**
     * @return
     */
    public String getStaff() {
        if (staff != null) {
            return staff.trim();
        } else {
            return staff;
        }
    }

    /**
     * @param string
     */
    public void setStaff(String string) {
        staff = string;
    }

    //Added for Security Threat Group Info Report
    public ArrayList getLocTypList(){
    	return locTypList;
    }
    public void setLocTypList(ArrayList list) {
    	locTypList = list;
    }
    public ArrayList getFacilityList(){
    	return facilityList;
    }
    public void setFacilityList(ArrayList list) {
    	facilityList = list;
    }

    public ArrayList getUnitList() {
    	return unitList;
    }
    public void setUnitList(ArrayList list) {
    	unitList = list;
    }

    public int getLocTypCd(){
    	return locTypCd;
    }
    public void setLocTypCd(int iCD) {
    	locTypCd = iCD;
    }
    public int getFacilityCd(){
    	return facilityCd;
    }
    public void setFacilityCd(int iCD) {
    	facilityCd = iCD;
    }

    public int getUnitCd() {
    	return unitCd;
    }
    public void setUnitCd(int iCD) {
    	unitCd = iCD;
    }
    
    public String getSortOrder() {
    	return sortOrder;
    }
    public void setSortOrder(String sStr) {
    	sortOrder = sStr;
    }
    public int getSortOrderId() {
    	return sortOrderId;
    }
    public void setSortOrderId(int iSOI) {
    	sortOrderId = iSOI;
    }
	public ArrayList getSortOrderList() {
		return sortOrderList; 
	}
	public void setSortOrderList(ArrayList<Object> al) {
		sortOrderList = al;
	}

    public int getReClassDays() {
        return reClassDays;
    }
    public void setReClassDays(int i) {
    	reClassDays = i;
    }
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
    	checkAllFlg = "N";
    	unClassFlg = "N";
    	closeRestrFlg = "N";
    	closeFlg = "N";
    	mediumFlg = "N";
    	minRestrFlg = "N";
    	minFlg = "N";
    	commFlg = "N";
    	riderFlg = "N";
    	scrdFlg = "N";
    	fnlFlg = "N";
    }

    public void flush() {
        super.flush();
        currentObject = null;
        staff = null;
    }

    public void semiFlush() {
        currentObject = null;
        staff = null;
    }

	public String getCheckAllFlg() {
		return checkAllFlg;
	}

	public void setCheckAllFlg(String checkAllFlg) {
		this.checkAllFlg = checkAllFlg;
	}

	public String getCloseFlg() {
		return closeFlg;
	}

	public void setCloseFlg(String closeFlg) {
		this.closeFlg = closeFlg;
	}

	public String getCloseRestrFlg() {
		return closeRestrFlg;
	}

	public void setCloseRestrFlg(String closeRestrFlg) {
		this.closeRestrFlg = closeRestrFlg;
	}

	public String getCommFlg() {
		return commFlg;
	}

	public void setCommFlg(String commFlg) {
		this.commFlg = commFlg;
	}

	public String getMediumFlg() {
		return mediumFlg;
	}

	public void setMediumFlg(String mediumFlg) {
		this.mediumFlg = mediumFlg;
	}

	public String getMinFlg() {
		return minFlg;
	}

	public void setMinFlg(String minFlg) {
		this.minFlg = minFlg;
	}

	public String getMinRestrFlg() {
		return minRestrFlg;
	}

	public void setMinRestrFlg(String minRestrFlg) {
		this.minRestrFlg = minRestrFlg;
	}

	public String getRiderFlg() {
		return riderFlg;
	}

	public void setRiderFlg(String riderFlg) {
		this.riderFlg = riderFlg;
	}

	public String getUnClassFlg() {
		return unClassFlg;
	}

	public void setUnClassFlg(String unClassFlg) {
		this.unClassFlg = unClassFlg;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public int getFilterId() {
		return filterId;
	}

	public void setFilterId(int filterId) {
		this.filterId = filterId;
	}

	public ArrayList<Object> getFilterList() {
		return filterList;
	}

	public void setFilterList(ArrayList<Object> filterList) {
		this.filterList = filterList;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
		try{
			this.setEndDateAsString(AppUtil.getSQLDateAsString(endDate));
		} catch(Exception e){
			this.setEndDateAsString("");
		}
	}

	public Date getStrtDate() {
		return strtDate;
	}

	public void setStrtDate(Date strtDate) {
		this.strtDate = strtDate;
		try{
			this.setStrtDateAsString(AppUtil.getSQLDateAsString(strtDate));
		} catch(Exception e){
			this.setStrtDateAsString("");
		}
	}

	public String getEndDateAsString() {
		return endDateAsString;
	}

	public void setEndDateAsString(String endDateAsString) {
		this.endDateAsString = endDateAsString;
	}

	public String getStrtDateAsString() {
		return strtDateAsString;
	}

	public void setStrtDateAsString(String strtDateAsString) {
		this.strtDateAsString = strtDateAsString;
	}

	public String getFnlFlg() {
		return fnlFlg;
	}

	public void setFnlFlg(String fnlFlg) {
		this.fnlFlg = fnlFlg;
	}

	public String getScrdFlg() {
		return scrdFlg;
	}

	public void setScrdFlg(String scrdFlg) {
		this.scrdFlg = scrdFlg;
	}

	public ClassificationEntryVO getEntryObject() {
		return entryObject;
	}

	public void setEntryObject(ClassificationEntryVO entryObject) {
		this.entryObject = entryObject;
	}

	public ArrayList<ClassificationEntryVO> getEntryList() {
		return entryList;
	}

	public void setEntryList(ArrayList<ClassificationEntryVO> entryList) {
		this.entryList = entryList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public ArrayList<Object> getStatusList() {
		return statusList;
	}

	public void setStatusList(ArrayList<Object> statusList) {
		this.statusList = statusList;
	}

	public boolean isSearchFlg() {
		return searchFlg;
	}

	public void setSearchFlg(boolean search) {
		this.searchFlg = search;
	}

	public boolean isUserName() {
		return userName;
	}

	public void setUserName(boolean userName) {
		this.userName = userName;
	}

	public String getUserNameCaller() {
		return userNameCaller;
	}

	public void setUserNameCaller(String userNameCaller) {
		this.userNameCaller = userNameCaller;
	}
//	public String getDiscrtnry5Text() {
////		return "Noncompliant with case plan";
//		AppObject ao = AppObject;
//		String returnText = AppObject.this.getProperty("DIO_ReportResources.properties", "classification.discretionary.1.text");
//		return returnText;
//	}

	public String getDiscrtnry1Text() {
		return discrtnry1Text;
	}

	public void setDiscrtnry1Text(String discrtnry1Text) {
		this.discrtnry1Text = discrtnry1Text;
	}

	public String getDiscrtnry2Text() {
		return discrtnry2Text;
	}

	public void setDiscrtnry2Text(String discrtnry2Text) {
		this.discrtnry2Text = discrtnry2Text;
	}

	public String getDiscrtnry3Text() {
		return discrtnry3Text;
	}

	public void setDiscrtnry3Text(String discrtnry3Text) {
		this.discrtnry3Text = discrtnry3Text;
	}

	public String getDiscrtnry4Text() {
		return discrtnry4Text;
	}

	public void setDiscrtnry4Text(String discrtnry4Text) {
		this.discrtnry4Text = discrtnry4Text;
	}

	public String getDiscrtnry5Text() {
		return discrtnry5Text;
	}

	public void setDiscrtnry5Text(String discrtnry5Text) {
		this.discrtnry5Text = discrtnry5Text;
	}

	public String getMandtry1Text() {
		return mandtry1Text;
	}

	public void setMandtry1Text(String mandtry1Text) {
		this.mandtry1Text = mandtry1Text;
	}

	public String getMandtry2Text() {
		return mandtry2Text;
	}

	public void setMandtry2Text(String mandtry2Text) {
		this.mandtry2Text = mandtry2Text;
	}

	public String getMandtry3Text() {
		return mandtry3Text;
	}

	public void setMandtry3Text(String mandtry3Text) {
		this.mandtry3Text = mandtry3Text;
	}

}
