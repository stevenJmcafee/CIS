package otrack.dio.classification;

import java.sql.Date;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import otrack.core.GenericListForm;
import otrack.util.AppUtil;

/**
 * ClassificationEntryVO.java
 * artf 1371
 * @author smcafee
 * @Oct 5, 2009
 * 
 * Holds the data for the classification entry page
 */
public class ClassificationRiderDetailForm extends GenericListForm {
	static final long serialVersionUID =1l;
	 private int clssfctnRiderDetailId;
	 private Date endDate;
	 private String judicialDistrictId;
	 private String judicialDistrictDesc;
	private Collection judicialDistrictList;
	 private short judgeId;
	 private String judgeDesc;
	 private short days = 180;
	 private String crimeDesc;
	 private boolean completion;
	 private String comment;
	 private Date updtDate;
	 private String updtUser;
	 private ClassificationRiderDetailVO detailVO;
	 
	public void flush() {
		clssfctnRiderDetailId = 0;
		endDate = null;
		updtDate = null;
		judicialDistrictId = null;
		judicialDistrictDesc = null;
		judgeId = 0;
		judgeDesc = null;
		days = 180;
		crimeDesc = null;
		completion = false;
		comment = null;
	 }

	 /**
	  * Returns a comma delimited list of the name/value pairs.
	  */
	 @Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("clssfctnRiderDetailId=" + clssfctnRiderDetailId + ",");
		sb.append("endDate=" + endDate + ",");
		sb.append("judicialDistrictId=" + judicialDistrictId + ",");
		sb.append("judicialDistrictDesc=" + judicialDistrictDesc + ",");
		sb.append("judgeId=" + judgeId + ",");
		sb.append("judgeDesc=" + judgeDesc + ",");
		sb.append("days=" + days + ",");
		sb.append("crimeDesc=" + crimeDesc + ",");
		sb.append("completion=" + completion + ",");
		sb.append("comment=" + comment + ",");
		sb.append("updtDate=" + updtDate + ",");
		sb.append("updtUser=" + updtUser);
		return sb.toString();
	 }

	 /**
	  * Returns the name/value pairs in a readable format.
	  */
	 public String print() {
		return this.toString().replace(",", "\n");
	 }

	public int getClssfctnRiderDetailId() {
		return clssfctnRiderDetailId;
	}

	public void setClssfctnRiderDetailId(int clssfctnRiderDetailId) {
		this.clssfctnRiderDetailId = clssfctnRiderDetailId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isCompletion() {
		return completion;
	}

	public void setCompletion(boolean completion) {
		this.completion = completion;
	}

	public String getCrimeDesc() {
		return crimeDesc;
	}

	public void setCrimeDesc(String crimeDesc) {
		this.crimeDesc = crimeDesc;
	}

	public short getDays() {
		return days;
	}

	public void setDays(short days) {
		this.days = days;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getEndDateAsString() {
		String dateString = null;
		try{
			dateString = AppUtil.getSQLDateAsString(endDate);
		}catch(Exception e){
			e.printStackTrace();
		}
		return dateString;
	}

	public String getJudgeDesc() {
		return judgeDesc;
	}

	public void setJudgeDesc(String judgeDesc) {
		this.judgeDesc = judgeDesc;
	}

	public short getJudgeId() {
		return judgeId;
	}

	public void setJudgeId(short judgeId) {
		this.judgeId = judgeId;
	}

	public String getJudicialDistrictDesc() {
		return judicialDistrictDesc;
	}

	public void setJudicialDistrictDesc(String judicialDistrictDesc) {
		this.judicialDistrictDesc = judicialDistrictDesc;
	}

	public String getJudicialDistrictId() {
		return judicialDistrictId;
	}

	public void setJudicialDistrictId(String judicialDistrictId) {
		this.judicialDistrictId = judicialDistrictId;
	}

	public Date getUpdtDate() {
		return updtDate;
	}

	public void setUpdtDate(Date updtDate) {
		this.updtDate = updtDate;
	}

	public String getUpdtUser() {
		return updtUser;
	}

	public void setUpdtUser(String updtUser) {
		this.updtUser = updtUser;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
        completion = false;
        days = 180;
    }

	public ClassificationRiderDetailVO getDetailVO() {
		return detailVO;
	}

	public void setDetailVO(ClassificationRiderDetailVO detailVO) {
		this.detailVO = detailVO;
	}

	public Collection getJudicialDistrictList() {
		return judicialDistrictList;
	}

	public void setJudicialDistrictList(Collection judicialDistrictList) {
		this.judicialDistrictList = judicialDistrictList;
	}

 } // END CLASS


