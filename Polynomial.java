import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Polynomial {
    // Array to store polynomial coefficients
    private double[] coeffs;
    private double[] exponents;

    // No-argument constructor
    public Polynomial() {
        this.coeffs = new double[1]; // Represents the zero polynomial
        this.exponents = new double[1]; // Represents the zero polynomial
    }

    public Polynomial(File file) throws IOException {
        String polynomialString = new String(Files.readAllBytes(file.toPath())).trim();
        parsePolynomial(polynomialString);
    }

    private void parsePolynomial(String polynomial) {
        List<Double> resultCoeffs = new ArrayList<>();
        List<Double> resultExponents = new ArrayList<>();

        // Regex to match terms like "5", "-3x2", "+7x8"
        Pattern pattern = Pattern.compile("([+-]?\\d*\\.?\\d*)(x(\\d+)?)?");
        Matcher matcher = pattern.matcher(polynomial);

        while (matcher.find()) {
            String coeffStr = matcher.group(1);
            String xPart = matcher.group(2);
            double coeff = coeffStr.equals("+") ? 1.0 : coeffStr.equals("-") ? -1.0 : coeffStr.isEmpty() ? 0.0 : Double.parseDouble(coeffStr);
            double exponent = (xPart != null && matcher.group(3) != null) ? Double.parseDouble(matcher.group(3)) : 0.0;
            if(coeff == 0){
                continue;
            }
            // Store the coefficient and exponent
            resultCoeffs.add(coeff);
            resultExponents.add(exponent);
        }

        // Convert lists to arrays
        this.coeffs = resultCoeffs.stream().mapToDouble(Double::doubleValue).toArray();
        this.exponents = resultExponents.stream().mapToDouble(Double::doubleValue).toArray();
    }

    // Constructor that accepts an array of doubles for coefficients
    public Polynomial(double[] coefficients, double[] exponents) {
        this.coeffs = new double[coefficients.length];
        this.exponents = new double[exponents.length];
        System.arraycopy(coefficients, 0, this.coeffs, 0, coefficients.length);
        System.arraycopy(exponents, 0, this.exponents, 0, exponents.length);
    }

    // Method to add two polynomials
    public Polynomial add(Polynomial other) {
        List<Double> resultCoeffs = new ArrayList<>();
        List<Double> resultExponents = new ArrayList<>();

        for (int i = 0; i < this.exponents.length; i++) {
            boolean found = false;
            for (int j = 0; j < other.exponents.length; j++) {
                if (this.exponents[i] == other.exponents[j]) {
                    resultCoeffs.add(this.coeffs[i] + other.coeffs[j]);
                    resultExponents.add(this.exponents[i]);
                    found = true;
                    break;
                }
            }
            if (!found) {
                resultCoeffs.add(this.coeffs[i]);
                resultExponents.add(this.exponents[i]);
            }
        }

        // Add remaining terms from 'other'
        for (int j = 0; j < other.exponents.length; j++) {
            boolean found = false;
            for (int i = 0; i < this.exponents.length; i++) {
                if (other.exponents[j] == this.exponents[i]) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                resultCoeffs.add(other.coeffs[j]);
                resultExponents.add(other.exponents[j]);
            }
        }

        return new Polynomial(resultCoeffs.stream().mapToDouble(Double::doubleValue).toArray(), 
                            resultExponents.stream().mapToDouble(Double::doubleValue).toArray());
    }

    public Polynomial multiply(Polynomial other) {
        Map<Double, Double> termMap = new HashMap<>();

        // Multiply each term of this polynomial with each term of the other polynomial
        for (int i = 0; i < this.coeffs.length; i++) {
            for (int j = 0; j < other.coeffs.length; j++) {
                double newCoeff = this.coeffs[i] * other.coeffs[j];
                double newExponent = this.exponents[i] + other.exponents[j];

                // Combine coefficients if the exponent already exists
                termMap.put(newExponent, termMap.getOrDefault(newExponent, 0.0) + newCoeff);
            }
        }

        // Prepare the result arrays
        List<Double> resultCoeffs = new ArrayList<>();
        List<Double> resultExponents = new ArrayList<>();

        for (Map.Entry<Double, Double> entry : termMap.entrySet()) {
            resultExponents.add(entry.getKey());
            resultCoeffs.add(entry.getValue());
        }

        return new Polynomial(resultCoeffs.stream().mapToDouble(Double::doubleValue).toArray(), 
                              resultExponents.stream().mapToDouble(Double::doubleValue).toArray());
    }

    // Method to evaluate the polynomial at a given value of x
    public double evaluate(double x) {
        double result = 0.0;
        for (int i = 0; i <= coeffs.length - 1; i++) {
            result = result + coeffs[i] * Math.pow(x, exponents[i]);
        }
        return result;
    }

    public String getCoefficient(int i) {
        double coefficient = coeffs[i];
        // Check if the coefficient is an integer value
        if (coefficient == (int) coefficient) {
            return String.valueOf((int) coefficient); // Return as integer
        } else {
            return String.valueOf(coefficient); // Return as double
        }
    }

    public String getExponent(int i) {
        double exponent = exponents[i];
        // Check if the coefficient is an integer value
        if (exponent == (int) exponent) {
            return String.valueOf((int) exponent); // Return as integer
        } else {
            return String.valueOf(exponent); // Return as double
        }
    }

    // Method to check if a given value is a root of the polynomial
    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    public void saveToFile(String filename) {
        StringBuilder polynomialString = new StringBuilder();
        for (int i = 0; i <= coeffs.length - 1; i++) {
            if(coeffs[i] > 0){
                if(i != 0){
                    polynomialString.append("+");                    
                }
                polynomialString.append(getCoefficient(i));
                if(exponents[i] != 0){
                    polynomialString.append("x");                    
                    polynomialString.append(getExponent(i));                    
                }
            }
            else{
                polynomialString.append(getCoefficient(i));
                if(exponents[i] != 0){
                    polynomialString.append("x");                    
                    polynomialString.append(getExponent(i));                    
                }
            }
        }        

        // Write the polynomial string to the specified file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(polynomialString.toString());
        } catch (IOException e) {
            System.err.println("Error saving polynomial to file: " + e.getMessage());
        }
    }
}
