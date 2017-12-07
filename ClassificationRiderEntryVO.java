package otrack.dio.classification;

import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;

import otrack.util.AppUtil;

/**
 * ClassificationRiderEntryVO.java
 * artf 1371
 * @author smcafee
 * @Sept 1, 2009
 * 
 * Holds the data for the classification entry page
 */
public class ClassificationRiderEntryVO implements Serializable{
	static final long serialVersionUID =1l;
//	instance variables
	 private int offenderNum;
	 private int clssfctnRiderEntryId;
	 private String locationIndex;
	 private String classificationType;
	 
	 private boolean firstRider;
	 private String subsquntRiderDates;
	 private boolean firstIncarceration;
	 private String subsquntIncarcerationDates;
	 
	 private String priorCrmnlHist;
	 private String behaviorHist;
	 private String healthConcerns;
	 private String staffComment;
	 
	 private String recommendedHousingCd;
	 private String recommendedHousingDesc;
	 
	 private Date prepDate;
	 private Date authDate;
	 private Date updtDate;
	 private String prepUser;
	 private String authUser;
	 private String updtUserId;
	 

	public ClassificationRiderEntryVO(int offenderNum, int clssfctnRiderEntryId, String locationIndex, String classificationType, boolean firstRider, String subsquntRiderDates, boolean firstIncarceration, String subsquntIncarcerationDates, String priorCrmnlHist, String behaviorHist, String healthConcerns, String staffComment, String recommendedHousingCd, String recommendedHousingDesc, Date prepDate, Date authDate, Date updtDate, String prepUser, String authUser, String updtUserId) {
		super();
		this.offenderNum = offenderNum;
		this.clssfctnRiderEntryId = clssfctnRiderEntryId;
		this.locationIndex = locationIndex;
		this.classificationType = classificationType;
		this.firstRider = firstRider;
		this.subsquntRiderDates = subsquntRiderDates;
		this.firstIncarceration = firstIncarceration;
		this.subsquntIncarcerationDates = subsquntIncarcerationDates;
		this.priorCrmnlHist = priorCrmnlHist;
		this.behaviorHist = behaviorHist;
		this.healthConcerns = healthConcerns;
		this.staffComment = staffComment;
		this.recommendedHousingCd = recommendedHousingCd;
		this.recommendedHousingDesc = recommendedHousingDesc;
		this.prepDate = prepDate;
		this.authDate = authDate;
		this.updtDate = updtDate;
		this.prepUser = prepUser;
		this.authUser = authUser;
		this.updtUserId = updtUserId;
	}


	public ClassificationRiderEntryVO() { }


	/**
	  * Clears all the instance variables
	  */
	 public void flush() {
		 this.offenderNum = 0;
		 this.clssfctnRiderEntryId = 0;
		 this.locationIndex = null;
		 this.classificationType = null;
		 this.firstRider = false;
		 this.subsquntRiderDates = null;
		 this.firstIncarceration = false;
		 this.subsquntIncarcerationDates = null;
		 this.priorCrmnlHist = null;
		 this.behaviorHist = null;
		 this.healthConcerns = null;
		 this.staffComment = null;
		 this.recommendedHousingCd = null;
		 this.prepDate = null;
		 this.authDate = null;
		 this.updtDate = null;
		 this.prepUser = null;
		 this.authUser = null;
		 this.updtUserId = null;
	 }

	 /**
	  * Returns a comma delimited list of the name/value pairs.
	  */
	 @Override
	public String toString() {
		 StringBuffer sb = new StringBuffer();
		 sb.append("prepDate=" + prepDate + ",");
		 sb.append("offenderNum=" + offenderNum + ",");
		 sb.append("clssfctnRiderEntryId=" + clssfctnRiderEntryId + ",");
		 sb.append("locationIndex=" + locationIndex + ",");
		 sb.append("classificationType=" + classificationType + ",");
		 sb.append("firstRider=" + firstRider + ",");
		 sb.append("subsquntRiderDates=" + subsquntRiderDates + ",");
		 sb.append("firstIncarceration=" + firstIncarceration + ",");
		 sb.append("subsquntIncarcerationDates=" + subsquntIncarcerationDates + ",");
		 sb.append("priorCrmnlHist=" + priorCrmnlHist + ",");
		 sb.append("behaviorHist=" + behaviorHist + ",");
		 sb.append("healthConcerns=" + healthConcerns + ",");
		 sb.append("staffComment=" + staffComment + ",");
		 sb.append("recommendedHousingCd=" + recommendedHousingCd + ",");
		 sb.append("recommendedHousingDesc=" + recommendedHousingDesc + ",");
		 sb.append("prepDate=" + prepDate + ",");
		 sb.append("authDate=" + authDate + ",");
		 sb.append("updtDate=" + updtDate + ",");
		 sb.append("prepUser=" + prepUser + ",");
		 sb.append("authUser=" + authUser + ",");
		 sb.append("updtUserId=" + updtUserId);
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
		 return this.toString().replace(",","\n");
	 }


	public Date getAuthDate() {
		return authDate;
	}


	public void setAuthDate(Date authDate) {
		this.authDate = authDate;
	}

	 public void setAuthDateAsString(String v) throws ParseException {
		 authDate = AppUtil.getSQLDate(v);
	 }

	 public String getAuthDateAsString() throws ParseException {
		 if (authDate == null) {
			return null;
		}
		 return AppUtil.getFormattedDateAsString(authDate);
	 }

	public String getAuthUser() {
		return authUser;
	}


	public void setAuthUser(String authUser) {
		this.authUser = authUser;
	}


	public String getBehaviorHist() {
		return behaviorHist;
	}


	public void setBehaviorHist(String behaviorHist) {
		this.behaviorHist = behaviorHist;
	}


	public String getClassificationType() {
		return classificationType;
	}


	public void setClassificationType(String classificationType) {
		this.classificationType = classificationType;
	}


	public int getClssfctnRiderEntryId() {
		return clssfctnRiderEntryId;
	}


	public void setClssfctnRiderEntryId(int clssfctnRiderEntryId) {
		this.clssfctnRiderEntryId = clssfctnRiderEntryId;
	}


	public boolean isFirstIncarceration() {
		return firstIncarceration;
	}


	public void setFirstIncarceration(boolean firstIncarceration) {
		this.firstIncarceration = firstIncarceration;
	}


	public boolean isFirstRider() {
		return firstRider;
	}


	public void setFirstRider(boolean firstRider) {
		this.firstRider = firstRider;
	}


	public String getHealthConcerns() {
		return healthConcerns;
	}


	public void setHealthConcerns(String healthConcerns) {
		this.healthConcerns = healthConcerns;
	}


	public String getLocationIndex() {
		return locationIndex;
	}


	public void setLocationIndex(String locationIndex) {
		this.locationIndex = locationIndex;
	}


	public int getOffenderNum() {
		return offenderNum;
	}


	public void setOffenderNum(int offenderNum) {
		this.offenderNum = offenderNum;
	}


	public Date getPrepDate() {
		return prepDate;
	}


	public void setPrepDate(Date prepDate) {
		this.prepDate = prepDate;
	}

	 public void setPrepDateAsString(String v) throws ParseException {
		 prepDate = AppUtil.getSQLDate(v);
	 }

	 public String getPrepDateAsString() throws ParseException {
		 if (prepDate == null) {
			return null;
		}
		 return AppUtil.getFormattedDateAsString(prepDate);
	 }

	public String getPrepUser() {
		return prepUser;
	}


	public void setPrepUser(String prepUser) {
		this.prepUser = prepUser;
	}


	public String getPriorCrmnlHist() {
		return priorCrmnlHist;
	}


	public void setPriorCrmnlHist(String priorCrmnlHist) {
		this.priorCrmnlHist = priorCrmnlHist;
	}


	public String getRecommendedHousingCd() {
		return recommendedHousingCd;
	}


	public void setRecommendedHousingCd(String recommendedHousingCd) {
		this.recommendedHousingCd = recommendedHousingCd;
	}


	public String getRecommendedHousingDesc() {
		return recommendedHousingDesc;
	}


	public void setRecommendedHousingDesc(String recommendedHousingDesc) {
		this.recommendedHousingDesc = recommendedHousingDesc;
	}


	public String getStaffComment() {
		return staffComment;
	}


	public void setStaffComment(String staffComment) {
		this.staffComment = staffComment;
	}


	public String getSubsquntIncarcerationDates() {
		return subsquntIncarcerationDates;
	}


	public void setSubsquntIncarcerationDates(String subsquntIncarcerationDates) {
		this.subsquntIncarcerationDates = subsquntIncarcerationDates;
	}


	public String getSubsquntRiderDates() {
		return subsquntRiderDates;
	}


	public void setSubsquntRiderDates(String subsquntRiderDates) {
		this.subsquntRiderDates = subsquntRiderDates;
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

	public String getUpdtUserId() {
		return updtUserId;
	}


	public void setUpdtUserId(String updtUserId) {
		this.updtUserId = updtUserId;
	}

 } // END CLASS


