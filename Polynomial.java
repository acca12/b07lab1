public class Polynomial{
	double[] coeff;
	
	public Polynomial(){
		coeff = new double[1];
		coeff[0] = 0;
	}

	public Polynomial(double[] input){
		coeff = new double[input.length];
		for (int i = 0; i < input.length; i++){
			coeff[i] = input[i];
		}
	}
	public Polynomial add(Polynomial p2){
		int length = Math.max(p2.coeff.length, coeff.length);
		
		double[] added_poly = new double[length];
		int min_length = Math.min(p2.coeff.length, coeff.length);
		for(int i =0; i < min_length; i++){
			added_poly[i] = p2.coeff[i] + coeff[i];
		}
		if(length == p2.coeff.length){
			for(int i = min_length; i < length; i++){
				added_poly[i] = p2.coeff[i];
			}
		}
		else{
			for(int i = min_length; i < length; i++){
				added_poly[i] = coeff[i];
			}
		}
		Polynomial new_poly = new Polynomial(added_poly);
		return new_poly;
	}

	public double evaluate(double x){
		double value = 0;
		for (int i =0; i < coeff.length; i++){
			value += coeff[i] * Math.pow(x, i);
		}
		return value;
	}
	public boolean hasRoot(double x){
		if(this.evaluate(x) == 0){
			return true;
		}
		return false;
	}
}
