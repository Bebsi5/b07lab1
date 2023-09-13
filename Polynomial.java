
public class Polynomial{
        public double [] coefficients;
    
        public Polynomial(){
            coefficients = new double[1];
            coefficients[0] = 0;
        }
    
        public Polynomial(double [] coefficients){
            this.coefficients = coefficients;
        }
    
        public Polynomial add(Polynomial p){
            double [] c1 = this.coefficients;
            double [] c2 = p.coefficients;
            int n1 = c1.length;
            int n2 = c2.length;
            int n = Math.max(n1,n2);
            double [] c = new double[n];
            for(int i = 0; i < n; i++){
                if(i < n1 && i < n2){
                    c[i] = c1[i] + c2[i];
                }
                else if(i < n1){
                    c[i] = c1[i];
                }
                else{
                    c[i] = c2[i];
                }
            }
            return new Polynomial(c);
        }
    
        public double evaluate(double x){
            double sum = 0;
            for(int i = 0; i < coefficients.length; i++){
                sum += coefficients[i] * Math.pow(x,i);
            }
            return sum;
        }
    
        public boolean hasRoot(double x){
            return evaluate(x) == 0;
        }
}


