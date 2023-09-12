public class Polynomial{
    private double[] co;

    public Polynomial() {
        this.co = new double[]{0};
    }

    public Polynomial(double[] arr) {
        this.co = arr;
    }

    public Polynomial add(Polynomial other) {
        int maxLength = Math.max(this.co.length, other.co.length);
        double[] result = new double[maxLength];

        for (int i = 0; i < this.co.length; i++) {
            result[i] += this.co[i];
        }

        for (int i = 0; i < other.co.length; i++) {
            result[i] += other.co[i];
        }

        return new Polynomial(result);
    }

    public double evaluate(double x) {
        double ans = 0.0;
        for(int i=0; i<this.co.length; ++i) {
            ans += Math.pow(x, i) * this.co[i];
        }

        return ans;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }
}