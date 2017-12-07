package otrack.dio.classification;

import java.sql.Date;
import java.util.Collection;

import otrack.core.GenericListForm;

/**
 * ClassificationRiderEntryForm.java artf 1371
 * 
 * @author smcafee
 * @Oct 5, 2009
 * 
 * Holds the data for the classification rider entry page
 */
public class ClassificationRiderEntryForm extends GenericListForm {
	static final long serialVersionUID = 1l;

	private int offenderNum;
	private int clssfctnRiderEntryId;
	private String classificationType;
	private boolean firstRider;
	private boolean firstIncarceration;
	
	private String subsquntRiderDates;
	private String subsquntIncarcerationDates;
	private String priorCrmnlHist;
	private String behaviorHist;
	private String healthConcerns;
	
	private String staffComment;
	private String recommendedHousingCd;
	private String recommendedHousingDesc;
	private String prepUser;
	private Date prepDate;
	
	private String authUser;
	private Date authDate;
	private String updtUser;
	private Date updtDate;

	private ClassificationRiderEntryVO entryVO;

	private Collection<ClassificationRiderDetailVO> detailList;

	private ClassificationRiderDetailVO detailObject;

	private Collection judicialDistrictList;

	public void flush() {
		offenderNum = 0;
		clssfctnRiderEntryId = 0;
		classificationType = null;
		firstRider = false;
		firstIncarceration = false;
		subsquntRiderDates = null;
		subsquntIncarcerationDates = null;
		priorCrmnlHist = null;
		behaviorHist = null;
		healthConcerns = null;
		staffComment = null;
		recommendedHousingCd = null;
		recommendedHousingDesc = null;
		prepUser = null;
		prepDate = null;
		authUser = null;
		authDate = null;
		updtUser = null;
		updtDate = null;
		entryVO = null;
		detailList = null;
	}

	/**
	 * Returns a comma delimited list of the name/value pairs.
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("offenderNum" + offenderNum + ",");
		sb.append("clssfctnRiderEntryId" + clssfctnRiderEntryId + ",");
		sb.append("classificationType" + classificationType + ",");
		sb.append("firstRider" + firstRider + ",");
		sb.append("firstIncarceration" + firstIncarceration + ",");
		sb.append("subsquntRiderDates" + subsquntRiderDates + ",");
		sb.append("subsquntIncarcerationDates" + subsquntIncarcerationDates + ",");
		sb.append("priorCrmnlHist" + priorCrmnlHist + ",");
		sb.append("behaviorHist" + behaviorHist + ",");
		sb.append("healthConcerns" + healthConcerns + ",");
		sb.append("staffComment" + staffComment + ",");
		sb.append("recommendedHousingCd" + recommendedHousingCd + ",");
		sb.append("recommendedHousingDesc" + recommendedHousingDesc + ",");
		sb.append("prepUser" + prepUser + ",");
		sb.append("prepDate" + prepDate + ",");
		sb.append("authUser" + authUser + ",");
		sb.append("authDate" + authDate + ",");
		sb.append("updtUser" + updtUser + ",");
		sb.append("updtDate" + updtDate);
		return sb.toString();
	}

	/**
	 * Returns the name/value pairs in a readable format.
	 */
	public String print() {
		return this.toString().replace(",", "\n");
	}

	public Date getAuthDate() {
		return authDate;
	}

	public void setAuthDate(Date authDate) {
		this.authDate = authDate;
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

	public int getClssfctnRiderEntryId() {
		return clssfctnRiderEntryId;
	}

	public void setClssfctnRiderEntryId(int clssfctnRiderEntryId) {
		this.clssfctnRiderEntryId = clssfctnRiderEntryId;
	}

	public ClassificationRiderEntryVO getEntryVO() {
		return entryVO;
	}

	public void setEntryVO(ClassificationRiderEntryVO entryVO) {
		this.entryVO = entryVO;
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

	public Date getPrepDate() {
		return prepDate;
	}

	public void setPrepDate(Date prepDate) {
		this.prepDate = prepDate;
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

	public String getUpdtUser() {
		return updtUser;
	}

	public void setUpdtUser(String updtUser) {
		this.updtUser = updtUser;
	}

	public Collection<ClassificationRiderDetailVO> getDetailList() {
		return detailList;
	}

	public void setDetailList(Collection<ClassificationRiderDetailVO> detailList) {
		this.detailList = detailList;
	}

	public ClassificationRiderDetailVO getDetailObject() {
		return detailObject;
	}

	public void setDetailObject(ClassificationRiderDetailVO detailObject) {
		this.detailObject = detailObject;
	}

	public Collection getJudicialDistrictList() {
		return judicialDistrictList;
	}

	public void setJudicialDistrictList(Collection judicialDistrictList) {
		this.judicialDistrictList = judicialDistrictList;
	}

	public String getClassificationType() {
		return classificationType;
	}

	public void setClassificationType(String classificationType) {
		this.classificationType = classificationType;
	}

	public int getOffenderNum() {
		return offenderNum;
	}

	public void setOffenderNum(int offenderNum) {
		this.offenderNum = offenderNum;
	}

} // END CLASS

