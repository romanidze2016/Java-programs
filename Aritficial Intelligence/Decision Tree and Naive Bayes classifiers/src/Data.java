
public class Data {
	public float preg;
	public float concentration;
	public float bp;
	public float skin;
	public float insulin;
	public float bmi;
	public float pedigree;
	public float age;
	public boolean status;
	
	public Data(float preg,float concentration,float bp, float skin, float insulin,
			float bmi, float pedigree, float age, boolean status){
		this.preg=preg;
		this.concentration=concentration;
		this.bp=bp;
		this.skin=skin;
		this.insulin=insulin;
		this.bmi=bmi;
		this.pedigree=pedigree;
		this.age=age;
		this.status=status;
	}
	public Data(String preg,String concentration,String bp, String skin, String insulin,
			String bmi, String pedigree, String age, String status){
		this.preg=Float.parseFloat(preg);
		this.concentration=Float.parseFloat(concentration);
		this.bp=Float.parseFloat(bp);
		this.skin=Float.parseFloat(skin);
		this.insulin=Float.parseFloat(insulin);
		this.bmi=Float.parseFloat(bmi);
		this.pedigree=Float.parseFloat(pedigree);
		this.age=Float.parseFloat(age);
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
		else if (i == 5) {
			return bmi;
		}
		else if (i == 6) {
			return pedigree;
		}
		else if (i == 7) {
			return age;
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
