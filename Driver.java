import java.io.IOException;

public class Driver {
    public static void main(String[] args) {
        Polynomial p = new Polynomial();
        System.out.println("p(3) = " + p.evaluate(3));

        Polynomial p1 = new Polynomial(new double[]{6, -2, 0, 5}, new int[]{0, 1, 2, 3});
        Polynomial p2 = new Polynomial(new double[]{0, -2, 0, 0, -9}, new int[]{0, 1, 2, 3, 4});

        Polynomial product = p1.multiply(p2);
        System.out.println("Product of p1 and p2: " + product);

        Polynomial s = p1.add(p2);
        System.out.println("s(0.1) = " + s.evaluate(0.1));

        if (s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");

        // Save the polynomial 's' to a file
        try {
            s.saveToFile("polynomial.txt");
            System.out.println("Polynomial saved to 'polynomial.txt'");
        } catch (IOException e) {
            System.err.println("Error saving polynomial to file: " + e.getMessage());
            
        }


    }
}