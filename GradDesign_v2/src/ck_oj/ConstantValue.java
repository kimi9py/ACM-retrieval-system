package ck_oj;

import javax.swing.JLabel;

public class ConstantValue {
	
	public int hduStartNumber;
	public int hduEndNumber;
	public int pojStartNumber;
	public int pojEndNumber;
	public int uvaStartNumber;
	public int uvaEndNumber;
	public int zojStartNumber;
	public int zojEndNumber;
	public boolean hdu = false;
	public boolean poj = false;
	public boolean uva = false;
	public boolean zoj = false;
	public String url="";
	public JLabel lable;

	public String InfoDir = "/home/ck/CK_ACM/Info";
	public String IdDir = "/home/ck/CK_ACM/id";
	public String UrlDir = "/home/ck/CK_ACM/url";
	public String RepDir = "/home/ck/CK_ACM/report";
	
	public String ROOTDIR = "/home/ck/CK_ACM";

	public String HDULink = "http://acm.hdu.edu.cn/listproblem.php?vol=";// 1-36
	public String HDUUrl = "http://acm.hdu.edu.cn/showproblem.php?pid=";
	public String HDUOJ = "hdu";
	public int HDUVolumeNumber = 36;

	public String POJLink = "http://poj.org/problemlist?volume=";// 1-31
	public String POJUrl = "http://poj.org/problem?id=";
	public String POJOJ = "poj";
	public int POJVolumeNumber = 31;

	public String UVALink = "http://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=";// 1-43
	public String UVAUrl = "http://uva.onlinejudge.org/external/";
	public String UVAOJ = "uva";
	public int UVAVolumeNumber = 43;

	public String ZOJLink = "http://acm.zju.edu.cn/onlinejudge/showProblems.do?contestId=1&pageNumber=";// 1-28
	public String ZOJUrl = "http://acm.zju.edu.cn/onlinejudge/showProblem.do?problemCode=";
	public String ZOJOJ = "zoj";
	public int ZOJVolumeNumber = 28;
	
	//public static final int ATTRIBUTENUMBER = 14;
	
	/**
	 * @return the lable
	 */
	public JLabel getLable() {
		return lable;
	}
	/**
	 * @param lable the lable to set
	 */
	public void setLable(JLabel lable) {
		this.lable = lable;
	}
	/**
	 * @return the ojDir
	 */
	public String getInfoDir() {
		return InfoDir;
	}
	/**
	 * @param ojDir the ojDir to set
	 */
	public void setInfoDir(String InfoDir) {
		this.InfoDir = InfoDir;
	}
	/**
	 * @return the idDir
	 */
	public String getIdDir() {
		return IdDir;
	}
	/**
	 * @param idDir the idDir to set
	 */
	public void setIdDir(String IdDir) {
		this.IdDir = IdDir;
	}
	/**
	 * @return the urlDir
	 */
	public String getUrlDir() {
		return UrlDir;
	}
	/**
	 * @param urlDir the urlDir to set
	 */
	public void setUrlDir(String UrlDir) {
		this.UrlDir = UrlDir;
	}
	/**
	 * @return the repDir
	 */
	public String getRepDir() {
		return RepDir;
	}
	/**
	 * @param repDir the repDir to set
	 */
	public void setRepDir(String RepDir) {
		this.RepDir = RepDir;
	}
	
	/**
	 * @return the hduStartNumber
	 */
	public int getHduStartNumber() {
		return hduStartNumber;
	}
	/**
	 * @param hduStartNumber the hduStartNumber to set
	 */
	public void setHduStartNumber(int hduStartNumber) {
		this.hduStartNumber = hduStartNumber;
	}
	/**
	 * @return the hduEndNumber
	 */
	public int getHduEndNumber() {
		return hduEndNumber;
	}
	/**
	 * @param hduEndNumber the hduEndNumber to set
	 */
	public void setHduEndNumber(int hduEndNumber) {
		this.hduEndNumber = hduEndNumber;
	}
	/**
	 * @return the pojStartNumber
	 */
	public int getPojStartNumber() {
		return pojStartNumber;
	}
	/**
	 * @param pojStartNumber the pojStartNumber to set
	 */
	public void setPojStartNumber(int pojStartNumber) {
		this.pojStartNumber = pojStartNumber;
	}
	/**
	 * @return the pojEndNumber
	 */
	public int getPojEndNumber() {
		return pojEndNumber;
	}
	/**
	 * @param pojEndNumber the pojEndNumber to set
	 */
	public void setPojEndNumber(int pojEndNumber) {
		this.pojEndNumber = pojEndNumber;
	}
	/**
	 * @return the uvaStartNumber
	 */
	public int getUvaStartNumber() {
		return uvaStartNumber;
	}
	/**
	 * @param uvaStartNumber the uvaStartNumber to set
	 */
	public void setUvaStartNumber(int uvaStartNumber) {
		this.uvaStartNumber = uvaStartNumber;
	}
	/**
	 * @return the uvaEndNumber
	 */
	public int getUvaEndNumber() {
		return uvaEndNumber;
	}
	/**
	 * @param uvaEndNumber the uvaEndNumber to set
	 */
	public void setUvaEndNumber(int uvaEndNumber) {
		this.uvaEndNumber = uvaEndNumber;
	}
	/**
	 * @return the zojStartNumber
	 */
	public int getZojStartNumber() {
		return zojStartNumber;
	}
	/**
	 * @param zojStartNumber the zojStartNumber to set
	 */
	public void setZojStartNumber(int zojStartNumber) {
		this.zojStartNumber = zojStartNumber;
	}
	/**
	 * @return the zojEndNumber
	 */
	public int getZojEndNumber() {
		return zojEndNumber;
	}
	/**
	 * @param zojEndNumber the zojEndNumber to set
	 */
	public void setZojEndNumber(int zojEndNumber) {
		this.zojEndNumber = zojEndNumber;
	}
	/**
	 * @param hduStartNumber the hduStartNumber to set
	 */

	
}
