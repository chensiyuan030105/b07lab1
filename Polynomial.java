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
            double a = i < this.coeffs.length ? this.coeffs[i] : 0;
            double b = i < other.coeffs.length ? other.coeffs[i] : 0;
            resultCoeffs[i] = a + b;
        }

        return new Polynomial(resultCoeffs);
    }

    // Method to evaluate the polynomial at a given value of x
    public double evaluate(double x) {
        double result = 0.0;
        for (int i = coeffs.length - 1; i >= 0; i--) {
            result = result * x + coeffs[i];
        }
        return result;
    }

    // Method to check if a given value is a root of the polynomial
    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    // Override toString to get a string representation of the polynomial
    @Override
    public String toString() {
        if (coeffs.length == 0) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = coeffs.length - 1; i >= 0; i--) {
            if (coeffs[i] != 0) {
                if (sb.length() > 0 && coeffs[i] > 0) {
                    sb.append(" + ");
                } else if (coeffs[i] < 0) {
                    sb.append(" - ");
                }
                double absCoeff = Math.abs(coeffs[i]);
                if (i == 0 || absCoeff != 1) {
                    sb.append(absCoeff);
                }
                if (i > 0) {
                    sb.append("x");
                }
                if (i > 1) {
                    sb.append("^").append(i);
                }
            }
        }
        return sb.toString();
    }

    // Main method for testing
    public static void main(String[] args) {
        double[] coeffs = {6, -2, 0, 5};
        Polynomial p = new Polynomial(coeffs);
        Polynomial q = new Polynomial(new double[] {1, 1});

        System.out.println("Polynomial p: " + p);
        System.out.println("Polynomial q: " + q);

        Polynomial sum = p.add(q);
        System.out.println("Sum of p and q: " + sum);

        double value = -1;
        double eval = p.evaluate(value);
        System.out.println("Value of p at x = " + value + ": " + eval);

        double root = 1;
        boolean isRoot = p.hasRoot(root);
        System.out.println("Is x = " + root + " a root of p? " + isRoot);
    }
}
