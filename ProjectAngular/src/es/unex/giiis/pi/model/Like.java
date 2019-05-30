package es.unex.giiis.pi.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Like {

	private int idu;
	private int idc;
	
	public int getIdu() {
		return idu;
	}
	public void setIdu(int idu) {
		this.idu = idu;
	}
	public int getIdc() {
		return idc;
	}
	public void setIdc(int idc) {
		this.idc = idc;
	}
	
}
