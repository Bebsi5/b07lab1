import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Polynomial {
    // Arrays to store coefficients and corresponding exponents
    private double[] coefficients;
    private int[] exponents;

    public Polynomial() {
        coefficients = new double[]{0};
        exponents = new int[]{0};
    }

    public Polynomial(double[] coefficients, int[] exponents) {
        if (coefficients.length != exponents.length) {
            throw new IllegalArgumentException("Coefficient and exponent arrays must have the same length");
        }
        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    public Polynomial(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        reader.close();

        if (line == null) {
            throw new IllegalArgumentException("File is empty");
        }

        // Split the line into terms
        String[] terms = line.split("\\+");
        List<Double> parsedCoefficients = new ArrayList<>();
        List<Integer> parsedExponents = new ArrayList<>();

        for (String term : terms) {
            // Split each term into coefficient and exponent
            String[] parts = term.split("x");
            if (parts.length == 1) {
                // Term is a constant (e.g., "5")
                parsedCoefficients.add(Double.parseDouble(parts[0]));
                parsedExponents.add(0);
            } else {
                // Term has both coefficient and exponent (e.g., "-3x2")
                parsedCoefficients.add(Double.parseDouble(parts[0]));
                parsedExponents.add(Integer.parseInt(parts[1]));
            }
        }

        coefficients = new double[parsedCoefficients.size()];
        exponents = new int[parsedExponents.size()];

        for (int i = 0; i < parsedCoefficients.size(); i++) {
            coefficients[i] = parsedCoefficients.get(i);
            exponents[i] = parsedExponents.get(i);
        }
    }

    public void saveToFile(String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

        // Convert coefficients and exponents to a list of terms
        String[] terms = new String[coefficients.length];
        for (int i = 0; i < coefficients.length; i++) {
            String term;
            if (exponents[i] == 0) {
                term = String.valueOf(coefficients[i]);
            } else {
                term = coefficients[i] + "x" + exponents[i];
            }
            terms[i] = term;
        }

        // Join terms with '+' to form the polynomial string
        String polynomialString = String.join(" + ", terms);

        // Write the polynomial string to the file
        writer.write(polynomialString);
        writer.close();
    }

    public Polynomial add(Polynomial p) {
    double[] c1 = this.coefficients;
    int[] e1 = this.exponents;
    double[] c2 = p.coefficients;
    int[] e2 = p.exponents;

    int n1 = c1.length;
    int n2 = c2.length;
    int i = 0, j = 0;

    List<Double> resultCoeffs = new ArrayList<>();
    List<Integer> resultExponents = new ArrayList<>();

    while (i < n1 && j < n2) {
        if (e1[i] == e2[j]) {
            resultCoeffs.add(c1[i] + c2[j]);
            resultExponents.add(e1[i]);
            i++;
            j++;
        } else if (e1[i] < e2[j]) {
            resultCoeffs.add(c1[i]);
            resultExponents.add(e1[i]);
            i++;
        } else {
            resultCoeffs.add(c2[j]);
            resultExponents.add(e2[j]);
            j++;
        }
    }

    // Copy any remaining terms from both polynomials
    while (i < n1) {
        resultCoeffs.add(c1[i]);
        resultExponents.add(e1[i]);
        i++;
    }
    while (j < n2) {
        resultCoeffs.add(c2[j]);
        resultExponents.add(e2[j]);
        j++;
    }

    double[] coefficientsArray = new double[resultCoeffs.size()];
    int[] exponentsArray = new int[resultExponents.size()];

    for (int k = 0; k < resultCoeffs.size(); k++) {
        coefficientsArray[k] = resultCoeffs.get(k);
        exponentsArray[k] = resultExponents.get(k);
    }

    return new Polynomial(coefficientsArray, exponentsArray);
    }

    public Polynomial multiply(Polynomial p) {
        double[] c1 = this.coefficients;
        int[] e1 = this.exponents;
        double[] c2 = p.coefficients;
        int[] e2 = p.exponents;

        int n1 = c1.length;
        int n2 = c2.length;

        List<Double> resultCoeffs = new ArrayList<>();
        List<Integer> resultExponents = new ArrayList<>();

        for (int i = 0; i < n1; i++) {
            for (int j = 0; j < n2; j++) {
                double productCoefficient = c1[i] * c2[j];
                int productExponent = e1[i] + e2[j];

                // Combine like terms with the same exponent
                boolean combined = false;
                for (int k = 0; k < resultExponents.size(); k++) {
                    if (resultExponents.get(k) == productExponent) {
                        resultCoeffs.set(k, resultCoeffs.get(k) + productCoefficient);
                        combined = true;
                        break;
                    }
                }

                if (!combined) {
                    resultCoeffs.add(productCoefficient);
                    resultExponents.add(productExponent);
                }
            }
        }

        double[] coefficientsArray = new double[resultCoeffs.size()];
        int[] exponentsArray = new int[resultExponents.size()];

        for (int i = 0; i < resultCoeffs.size(); i++) {
            coefficientsArray[i] = resultCoeffs.get(i);
            exponentsArray[i] = resultExponents.get(i);
        }

        return new Polynomial(coefficientsArray, exponentsArray);
    }


    public double evaluate(double x) {
        double sum = 0;
        for (int i = 0; i < coefficients.length; i++) {
            sum += coefficients[i] * Math.pow(x, exponents[i]);
        }
        return sum;
    }


    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    @Override
    public String toString() {
        StringBuilder polynomialString = new StringBuilder();

        for (int i = 0; i < coefficients.length; i++) {
            if (coefficients[i] != 0) {
                if (i > 0) {
                    polynomialString.append(" + ");
                }
                if (coefficients[i] != 1 || i == 0) {
                    polynomialString.append(coefficients[i]);
                }
                if (i > 0) {
                    polynomialString.append("x");
                    if (i > 1) {
                        polynomialString.append(i);
                    }
                }
            }
        }

        return polynomialString.toString();
    }

}

