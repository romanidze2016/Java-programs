
public class CFSData {
	public float preg;
	public float concentration;
	public float bp;
	public float skin;
	public float insulin;
	public boolean status;
	
	public CFSData(float preg,float concentration,float bp, float skin, float insulin,boolean status){
		this.preg=preg;
		this.concentration=concentration;
		this.bp=bp;
		this.skin=skin;
		this.insulin=insulin;
		this.status=status;
	}
	public CFSData(String preg,String concentration,String bp, String skin, String insulin, String status){
		this.preg=Float.parseFloat(preg);
		this.concentration=Float.parseFloat(concentration);
		this.bp=Float.parseFloat(bp);
		this.skin=Float.parseFloat(skin);
		this.insulin=Float.parseFloat(insulin);
		
		if(status.equals("yes")){
			this.status=true;
		}
		else if(status.equals("no")){
			this.status=false;
		}		

	}
	
	public float getAttributeAt(int i) {
		if (i == 0) {
			return preg;
		}
		else if (i == 1) {
			return concentration;
		}
		else if (i == 2) {
			return bp;
		}
		else if (i == 3) {
			return skin;
		}
		else if (i == 4) {
			return insulin;
		}
		else {
			return -1;
		}
	}
	
	public boolean getStatus() {
		return status;
	}
	
	public void setStatus(boolean newStatus) {
		status = newStatus;
	}
}
