public class Polynomial {
    // Array to store polynomial coefficients
    private double[] coeffs;

    // No-argument constructor
    public Polynomial() {
        this.coeffs = new double[1]; // Represents the zero polynomial
    }

    // Constructor that accepts an array of doubles for coefficients
    public Polynomial(double[] coefficients) {
        this.coeffs = new double[coefficients.length];
        System.arraycopy(coefficients, 0, this.coeffs, 0, coefficients.length);
    }

    // Method to add two polynomials
    public Polynomial add(Polynomial other) {
        int maxLen = Math.max(this.coeffs.length, other.coeffs.length);
        double[] resultCoeffs = new double[maxLen];

        for (int i = 0; i < maxLen; i++) {
            double a = 0;
            double b = 0;
            if(i < this.coeffs.length)
                a = this.coeffs[i];
            else
                a = 0;
            if(i < other.coeffs.length)
                b = other.coeffs[i];
            else
                b = 0;
            resultCoeffs[i] = a + b;
        }

        return new Polynomial(resultCoeffs);
    }

    // Method to evaluate the polynomial at a given value of x
    public double evaluate(double x) {
        double result = 0.0;
        for (int i = 0; i <= coeffs.length - 1; i++) {
            result = result + coeffs[i] * Math.pow(x, i);
        }
        return result;
    }

    // Method to check if a given value is a root of the polynomial
    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }
}
