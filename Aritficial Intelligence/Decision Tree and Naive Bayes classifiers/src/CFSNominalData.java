
public class CFSNominalData {
	public Degree preg;
	public Degree concentration;
	public Degree bp;
	public Degree skin;
	public Degree insulin;
	public boolean status;
	
	
	public CFSNominalData(String preg,String concentration,String bp, String skin, String insulin, String status){
		this.preg=parseDegree(preg);
		this.concentration=parseDegree(concentration);
		this.bp=parseDegree(bp);
		this.skin=parseDegree(skin);
		this.insulin=parseDegree(insulin);
		if(status.equals("yes")){
			this.status=true;
		}
		else if(status.equals("no")){
			this.status=false;
		}		

	}
	
	//parse the string into a degree level
	public Degree parseDegree(String input){
		if(input.equals("low")){
			return Degree.LOW;
		}
		else if(input.equals("medium")){
			return Degree.MEDIUM;

		}
		else if(input.equals("high")){
			return Degree.HIGH;

				}
		else if(input.equals("very high")){
			return Degree.VERY_HIGH;

		}
		System.out.println("RETURNING NULL WARNING");

		return null;
	}
	
	public Degree getAttributeAt(int i) {
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
			System.out.println("RETURNING NULL WARNING");
			return null;
		}
	}
	
	public boolean getStatus() {
		return status;
	}
}
