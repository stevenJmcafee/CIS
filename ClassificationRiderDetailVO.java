package otrack.dio.classification;

import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;

import otrack.util.AppUtil;

/**
 * ClassificationEntryVO.java
 * artf 1371
 * @author smcafee
 * @Sept 1, 2009
 * 
 * Holds the data for the classification entry page
 */
public class ClassificationRiderDetailVO implements Serializable{
	static final long serialVersionUID =1l;
//	instance variables
	private int clssfctnRiderDetailId;
	private int ofndrNum;
	private Date endDate;
	private String judicialDistrictId;
	private String judicialDistrictDesc;
	private short judgeId;
	private String judgeDesc;
	private short days = 180;
	private String crimeDesc;
	private boolean completion;
	private String comment;
	private Date updtDate;
	private String updtUser;
	private short callerIndex;
	private int clssfctnRiderEntryId;
	
	public ClassificationRiderDetailVO(
		int clssfctnRiderDetailId, 
		int locationIndex, 
		Date endDate, 
		String judicialDistrictId, 
		String judicialDistrictDesc, 
		short judgeId, 
		String judgeDesc, 
		short days, 
		String crimeDesc, 
		boolean completion, 
		String comment,
		Date updtDate, 
		String updtUser){
			this.clssfctnRiderDetailId = clssfctnRiderDetailId;
			this.endDate = endDate;
			this.judicialDistrictId = judicialDistrictId;
			this.judicialDistrictDesc = judicialDistrictDesc;
			this.judgeId = judgeId;
			this.judgeDesc = judgeDesc;
			this.days = days;
			this.crimeDesc = crimeDesc;
			this.completion = completion;
			this.comment = comment;
			this.updtDate = updtDate;
			this.updtUser = updtUser;
	}

	public ClassificationRiderDetailVO() { }


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
	  * Does a toString comparison to see if the objects
	  * are equal. Note: if the object is a composite
	  * this code will likely need to be modified for a
	  * proper comparison.
	  */
	 public boolean equals(OffenderCustodyLevelClassificationVO target) {
		 if ( this.toString().equals(target.toString()) ) {
			return true;
		}
		 return false;
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

	 public void setEndDateAsString(String v) throws ParseException {
		 endDate = AppUtil.getSQLDate(v);
	 }

	 public String getEndDateAsString() throws ParseException {
		 if (endDate == null) {
			return null;
		}
		 return AppUtil.getFormattedDateAsString(endDate);
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
	 public void setUpdtDateAsString(String v) throws ParseException {
		 updtDate = AppUtil.getSQLDate(v);
	 }

	 public String getUpdtDateAsString() throws ParseException {
		 if (updtDate == null) {
			return null;
		}
		 return AppUtil.getFormattedDateAsString(updtDate);
	 }

	public String getUpdtUser() {
		return updtUser;
	}

	public void setUpdtUser(String updtUser) {
		this.updtUser = updtUser;
	}

	public int getOfndrNum() {
		return ofndrNum;
	}

	public void setOfndrNum(int ofndrNum) {
		this.ofndrNum = ofndrNum;
	}

	public short getCallerIndex() {
		return callerIndex;
	}

	public void setCallerIndex(short callerIndex) {
		this.callerIndex = callerIndex;
	}

	public int getClssfctnRiderEntryId() {
		return clssfctnRiderEntryId;
	}

	public void setClssfctnRiderEntryId(int clssfctnRiderEntryId) {
		this.clssfctnRiderEntryId = clssfctnRiderEntryId;
	}


 } // END CLASS


