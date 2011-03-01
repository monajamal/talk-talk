/**
 * Classe contenant une ip et un port
 */

package talkTalk;

import java.io.Serializable;

public class Adresse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7825853973143121036L;
	private String addr;
	private int port;
	
	public Adresse(String addr, int port) {
		this.addr = addr;
		this.port = port;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getAddr() {
		return addr;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getPort() {
		return port;
	}
	
	@Override
	public boolean equals(Object o){
		if (o!=null && o instanceof Adresse) {
			Adresse a = (Adresse)o;
			if (a.getAddr().equals(addr) && a.getPort() == port) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	public String toString(){
		return(addr+":"+port);
	}
}
