import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;



public class Polynomial {
		double[] coeff;
		int[] exponents;
		
		public Polynomial(){
			coeff = null;
			exponents = null;
		}

		public Polynomial(double[] input_coeff, int[] input_exponents){
			int occurences = 0;
			if (input_coeff == null)
			{
				coeff = null;
				exponents = null;
			} else {	
				for(int i = 0; i < input_coeff.length; i++)
				{
					if(input_coeff[i] == 0)
					{
						occurences++;
					}
				}
				if(input_coeff.length - occurences == 0)
				{
					coeff = null;
					exponents = null;
				}else 
				{
					coeff = new double[input_coeff.length - occurences];
					exponents = new int[input_exponents.length - occurences];
					int increment = 0;
					for(int i =0; i < input_coeff.length; i++)
					{
						if(input_coeff[i] != 0)
						{
							coeff[increment] = input_coeff[i];
							exponents[increment] = input_exponents[i];
							increment++;
						}
					}
				}
			}
		}
		
		public Polynomial(File p) throws FileNotFoundException 
		{
			Scanner input = new Scanner(p);
			String poly = input.nextLine();
			input.close();
			if(poly.equals("0"))
			{
				coeff = null;
				exponents = null;
			}else {
				String plus_minus = poly.replaceAll("-", "+-");
				if(plus_minus.length() > 2)
				{
					if(plus_minus.substring(0, 2).equals("+-"))
					{
						plus_minus = plus_minus.substring(1);
					}
				}
				String[] poly_split = plus_minus.split("\\+");
				exponents = new int[poly_split.length];
				coeff = new double[poly_split.length];
				for(int i = 0; i < poly_split.length; i++)
				{
					if(poly_split[i].indexOf("x") != -1)
					{
						coeff[i] = Double.parseDouble(poly_split[i].substring(0, poly_split[i].indexOf("x")));
						exponents[i] = Integer.parseInt(poly_split[i].substring(poly_split[i].indexOf("x") + 1));
					}
					else {
						coeff[i] = Double.parseDouble(poly_split[i].substring(0));
						exponents[i] = 0;
					}
				}
			}	
		}
		public Polynomial add(Polynomial p2)
		{
			int max_degree = 0;
			int max_length = 0;
			if(p2.coeff == null)
				return this;
			if(this.coeff == null)
				return p2;
			
			for(int i = 0; i < p2.exponents.length; i++)
			{
				max_degree = Math.max(max_degree, p2.exponents[i]);
			}
			for(int i  = 0; i<exponents.length; i++)
			{
				max_degree = Math.max(max_degree, exponents[i]);
				
			}
			max_length = max_degree + 1;
			double[] added_coeff = new double[max_length];
			int[] added_degree = new int[max_length];
			int found = 0;
			int increment = 0;
 			
			
			for(int j = 0; j < p2.coeff.length; j++)
			{
				for(int z = 0; z < coeff.length; z++)
				{
					if((p2.exponents[j] == exponents[z]) && (p2.coeff[j] + coeff[z] != 0))
					{
						added_coeff[increment] = p2.coeff[j] + coeff[z];
						added_degree[increment] = exponents[z];
						found = 1;
						increment++;
					}
					else if((p2.exponents[j] == exponents[z]) && (p2.coeff[j] + coeff[z] == 0))
					{
						found = 1;
					}
				
				}
				if(found == 1)
				{
					found = 0;
				}
				else 
				{
					added_coeff[increment] = p2.coeff[j];
					added_degree[increment] = p2.exponents[j];
					increment++;
				}
			}
			int found2 = 0;
			for(int z = 0; z < exponents.length; z++)
			{
				for(int j = 0; j < p2.exponents.length; j++)
				{
					if(exponents[z] == p2.exponents[j])
					{
						found2 = 1;
					}
				}
				if(found2 == 1)
				{
					found2 = 0;
				}else
				{
					added_coeff[increment] = coeff[z];
					added_degree[increment] = exponents[z];
					increment++;
				}
			}
			double[] final_coeff = new double[increment];
			int[] final_degree = new int[increment];
			for(int i = 0; i < increment; i++)
			{
				final_coeff[i] = added_coeff[i];
				final_degree[i] = added_degree[i];
			}
			Polynomial add_poly = new Polynomial(final_coeff, final_degree);
			return add_poly;
			
		}
			
		public double evaluate(double x){
			if(coeff == null)
				return 0;
			double value = 0;
			for (int i =0; i < coeff.length; i++){
				value  += coeff[i] * Math.pow(x, exponents[i]);
				
			}
			return value;
		}
		
		public boolean hasRoot(double x){
			if(this.evaluate(x) == 0){
				return true;
			}
			return false;
		}
		public Polynomial multiply(Polynomial p2)
		{
			if(this.coeff == null)
				return this;
			if(p2.coeff == null)
				return p2;
			int max_1 = 0;
			int max_2 = 0;
			int max_multiply = 0;
			for(int i = 0; i < exponents.length; i++)
			{
				max_1 = Math.max(max_1, exponents[i]);
			}
			for(int i = 0; i < p2.exponents.length; i++)
			{
				max_2 = Math.max(max_2, p2.exponents[i]);
			}
			max_multiply = max_1 + max_2 + 1;
			double[] multi_coeff = new double[max_multiply];
			int[] multi_degree = new int[max_multiply];
			double[] magic_coeff = new double[max_multiply];
			for(int i = 0; i < max_multiply; i++)
			{
				multi_degree[i] = i;
	
			}
			double coeff_term = 0;
			int degree_term_pos = 0;
			for(int i = 0; i < exponents.length; i++)
			{
				for(int j= 0; j< p2.exponents.length; j++)
				{
						coeff_term = coeff[i] * p2.coeff[j];
						degree_term_pos = exponents[i] + p2.exponents[j];
						multi_coeff[degree_term_pos] += coeff_term;
						
				}
			}
			
			Polynomial multi = new Polynomial(multi_coeff, multi_degree);
			Polynomial magic = new Polynomial(magic_coeff, multi_degree);
			return multi.add(magic);
		}
		
		public void saveToFile(String filename) throws IOException
		{
			File save = new File(filename);
			save.createNewFile();
			FileWriter read = new FileWriter(save.getPath(), false);
			if(coeff == null)
			{
				read.write("0");
			}
			else
			{
				for(int i = 0; i < coeff.length; i++)
				{
					String coefficient = String.valueOf(coeff[i]);
					String exponent = String.valueOf(exponents[i]);
					if(coeff[i] > 0 && i != 0)
					{
						read.write("+");
					}
					read.write(coefficient);
					if(exponents[i] > 0)
					{
						read.write("x");
						read.write(exponent);
					}
					
				}	
			}
			read.close();
		}
}
