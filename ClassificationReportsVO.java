package otrack.dio.classification;

import java.io.Serializable;
import java.sql.Date;


/**
 *
 * ClassificationReportsVO.java
 *
 * @author smcafee
 * @Aug 25, 2009, artf 1371
 *
 * The VO class that holds data for reports forms
 */
public class ClassificationReportsVO implements Serializable {
	static final long serialVersionUID = 1l;
	
	private int ofndrNum;
    private String ofndrName;
    private String location;
    private String scrdCustLvl;
    private String rccmdCustLvl;
    private String fnlCustLvl;
    private String ovrrdRsn;
    private Date classDate;

    
    /*
     * This constructor is implemented
     * as it used by getCurrentObject method in the form beans
     */
    public ClassificationReportsVO(ClassificationReportsVO vo) {
        this.ofndrNum = vo.ofndrNum;
        this.ofndrName = vo.ofndrName;
        this.location = vo.location;
        this.scrdCustLvl = vo.scrdCustLvl;
        this.rccmdCustLvl = vo.rccmdCustLvl;
        this.fnlCustLvl = vo.fnlCustLvl;
        this.ovrrdRsn = vo.ovrrdRsn;
        this.classDate = vo.classDate;
    }

    public ClassificationReportsVO() {
        // TODO Auto-generated constructor stub
    }


    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("ofndrNum :" + ofndrNum + ",");
        buffer.append("ofndrName :" + ofndrName + ",");
        buffer.append("location :" + location + ",");
        buffer.append("scrdCustLvl :" + scrdCustLvl + ",");
        buffer.append("rccmdCustLvl :" + rccmdCustLvl + ",");
        buffer.append("fnlCustLvl :" + fnlCustLvl + ",");
        buffer.append("classDate :" + classDate + ",");
        buffer.append("ovrrdRsn :" + ovrrdRsn);

        return buffer.toString();
    }

    public String print() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("ofndrNum :" + ofndrNum + "\n");
        buffer.append("ofndrName :" + ofndrName + "\n");
        buffer.append("location :" + location + "\n");
        buffer.append("scrdCustLvl :" + scrdCustLvl + "\n");
        buffer.append("rccmdCustLvl :" + rccmdCustLvl + "\n");
        buffer.append("fnlCustLvl :" + fnlCustLvl + "\n");
        buffer.append("classDate :" + classDate + "\n");
        buffer.append("ovrrdRsn :" + ovrrdRsn);

        return buffer.toString();
    }

    public void flush() {
        ofndrNum = 0;
        ofndrName = null;
        location = null;
        scrdCustLvl = null;
        rccmdCustLvl = null;
        fnlCustLvl = null;
        classDate = null;
        ovrrdRsn = null;
    }

	public Date getClassDate() {
		return classDate;
	}

	public void setClassDate(Date classDate) {
		this.classDate = classDate;
	}

	public String getFnlCustLvl() {
		return fnlCustLvl;
	}

	public void setFnlCustLvl(String fnlCustLvl) {
		this.fnlCustLvl = fnlCustLvl;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getOfndrName() {
		return ofndrName;
	}

	public void setOfndrName(String ofndrName) {
		this.ofndrName = ofndrName;
	}

	public int getOfndrNum() {
		return ofndrNum;
	}

	public void setOfndrNum(int ofndrNum) {
		this.ofndrNum = ofndrNum;
	}

	public String getOvrrdRsn() {
		return ovrrdRsn;
	}

	public void setOvrrdRsn(String ovrrdRsn) {
		this.ovrrdRsn = ovrrdRsn;
	}

	public String getRccmdCustLvl() {
		return rccmdCustLvl;
	}

	public void setRccmdCustLvl(String rccmdCustLvl) {
		this.rccmdCustLvl = rccmdCustLvl;
	}

	public String getScrdCustLvl() {
		return scrdCustLvl;
	}

	public void setScrdCustLvl(String scrdCustLvl) {
		this.scrdCustLvl = scrdCustLvl;
	}
}
