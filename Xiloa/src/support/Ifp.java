package support;

public class Ifp {
	private int ifpId;
	private String ifpNombre;
	
	public int getIfpId() {
		return ifpId;
	}
	public void setIfpId(int ifpId) {
		this.ifpId = ifpId;
	}
	public String getIfpNombre() {
		return ifpNombre;
	}
	public void setIfpNombre(String ifpNombre) {
		this.ifpNombre = ifpNombre;
	}

	public Ifp(int ifpId, String ifpNombre) {
		super();
		this.ifpId = ifpId;
		this.ifpNombre = ifpNombre;
	}
	
	public Ifp() {
		super();
	}

}
