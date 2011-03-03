/**
 * Classe contenant une ip et un port
 */

package talkTalk;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Adresse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7825853973143121036L;
	private String addr;
	private int port;
	private String nomDns;
	
	public Adresse(String addr, int port) {
		this.setAddr(addr);
		this.port = port;
	}
	public void setAddr(String addr) {
		if (!addr.matches("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$")) {
			this.nomDns = addr;
			try {
				InetAddress in[] = InetAddress.getAllByName(addr);
				if (in.length == 0) {
					System.out.println("OUPS");
				}
				this.addr = in[0].getHostAddress();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			this.nomDns = null;
			this.addr = addr;
		}
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
	public String getNomDns() {
		if (nomDns != null) {
			return nomDns;
		} else {
			return addr;
		}
	}
}
