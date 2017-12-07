package otrack.dio.classification;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;

import otrack.core.AppException;
import otrack.core.BizObjectIfc;


/**
 *
 * ClassificationReportsProcessorIfc.java
 *
 * @author smcafee
 * @Aug 25, 2009, artf 1371
 *
 * The processor class that handles business processes
 */
public interface ClassificationReportsProcessorIfc extends BizObjectIfc {

	public ArrayList getFacility(int Itype) throws AppException;

    /**
     * get unit drop down
     * @return ArrayList
     * @throws AppException
     * @screen: <JSP Name>
     *
     * <Purpose of this method>
     */
    public ArrayList getUnitList(int facilityCd, int iLocTyp) throws AppException;
    
    /**
     * This get the report data
     * @author smcafee
     * @param HttpServletResponse response
     * @param HttpServletRequest request
     * @param ActionForm form
     * @return boolean
     * @throws AppException
     */
    public boolean getReportData(HttpServletResponse response, HttpServletRequest request, ActionForm form)throws AppException;
    public boolean getFacilityReportData(HttpServletResponse response, HttpServletRequest request, ActionForm form)throws AppException;

    /**
     * This get the report location list formatted for the SPL
     * @author smcafee
     * @param Integer iLocId
     * @param Integer iFaccId
     * @return String
     * @throws AppException
     */
    public String getBLCList(Integer iLocId,Integer iFacId)throws AppException;

    /**
     * get Sort Order selection drop down
     * @return ArrayList
     * @throws AppException
     */
    public ArrayList<Object> getGenericDropDownList(ArrayList alList) throws AppException;
 
    public ArrayList<ClassificationEntryVO> getSearchData(ClassificationReportsForm theForm) throws AppException;
    public ArrayList<ClassificationEntryVO> getHistSearchData(ClassificationReportsForm theForm) throws AppException;
    public ArrayList<ClassificationEntryVO> getStatusSearchData(ClassificationReportsForm theForm) throws AppException;
    public boolean getStatusReportData(HttpServletResponse response, HttpServletRequest request, ActionForm form)throws AppException;
    public boolean getTestReportData(HttpServletResponse response, HttpServletRequest request, ActionForm form)throws AppException;
}
