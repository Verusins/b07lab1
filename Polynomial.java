import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Polynomial{
    private double[] co;
    private int[] ex;

    public Polynomial() {
        this.co = new double[]{0};
        this.ex = new int[]{0};
    }

    public Polynomial(double[] _co, int[] _ex) {
        this.co = _co;
        this.ex = _ex;
    }

    // new method
    private void addTerm(double[] coeffs, int[] exps, double coeff, int exp, int currentIndex) {
        for (int i = 0; i < currentIndex; i++) {
            if (exps[i] == exp) {
                this.co[i] += coeff;
                return;
            }
        }
        coeffs[currentIndex] = coeff;
        exps[currentIndex] = exp;
    }

    // file reader
    public Polynomial(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        String polynomialStr = reader.readLine();
        int character;

        reader.close();
        fileReader.close();

        String[] terms = polynomialStr.split("(?=[+-])");
        double[] coeffs = new double[terms.length];
        int[] exps = new int[terms.length];
        int currentIndex = 0;

        for (String term : terms) {
            String[] parts = term.split("x");
            double coeff = Double.parseDouble(parts[0]);
            int exp = 0;
            if (parts.length > 1) {
                exp = Integer.parseInt(parts[1]);
            }
            addTerm(coeffs, exps, coeff, exp, currentIndex);
            currentIndex++;
        }

        this.co = new double[currentIndex];
        this.ex = new int[currentIndex];
        System.arraycopy(coeffs, 0, this.co, 0, currentIndex);
        System.arraycopy(exps, 0, this.ex, 0, currentIndex);
    }

    public Polynomial add(Polynomial other) {
        int bigLen = this.ex.length + other.ex.length;
        double[] result_co = new double[bigLen];
        int[] result_ex = new int[bigLen];
        for(int i=0; i<this.ex.length; i++){
            result_co[i] = this.co[i];
            result_ex[i] = this.ex[i];
        }
        for(int i=this.ex.length; i<bigLen; i++){
            result_co[i] = other.co[i-this.ex.length];
            result_ex[i] = other.ex[i-this.ex.length];
        }

        // merging co with the same ex
        for(int i=0; i<bigLen; i++) {
            for(int j=i; j<this.ex.length; j++) {
                for(int k=this.ex.length; k<bigLen; k++) {
                    if(result_ex[j] == result_ex[k]) {
                        result_co[j] += result_co[k];
                        result_co[k] = 0;
                    }
                }
            }
        }

        // moving 0s to the end of the array
        for(int i=bigLen; i<this.ex.length; i--) {
            for(int j=this.ex.length; j<i; j++) {
                if(result_co[j] == 0 && result_co[j+1] != 0) {
                    double tmp = result_co[j];
                    result_co[j] = result_co[j+1];
                    result_co[j+1] = tmp;
                    int tmp2 = result_ex[j];
                    result_ex[j] = result_ex[j+1];
                    result_ex[j+1] = tmp2;
                }
            }
        }

        int result_len = 1;
        for(;result_len<=bigLen; result_len++) {
            if(result_co[result_len-1] == 0) break;
        }

        // have an array of the proper length.
        double[] ans_co = new double[result_len];
        int[] ans_ex = new int[result_len];

        for(int i=0; i<result_len; i++) {
            ans_co[i] = result_co[i];
            ans_ex[i] = result_ex[i];
        }

        return new Polynomial(ans_co, ans_ex);
    }

    public double evaluate(double x) {
        double ans = 0.0;
        for(int i=0; i<this.co.length; ++i) {
            ans += Math.pow(x, this.ex[i]) * this.co[i];
        }

        return ans;
    }

    public Polynomial multiply(Polynomial other) {
        double[] result_co = new double[this.co.length * other.co.length];
        int[] result_ex = new int[this.co.length * other.co.length];
        int resultIndex = 0;

        for (int i = 0; i < this.co.length; i++) {
            for (int j = 0; j < other.co.length; j++) {
                double coeff = this.co[i] * other.co[j];
                int exp = this.ex[i] + other.ex[j];
                result_co[resultIndex] = coeff;
                result_ex[resultIndex] = exp;
                resultIndex++;
            }
        }

        return new Polynomial(result_co, result_ex);
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    public void saveToFile(String fileName) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.co.length; i++) {
            if (this.co[i] != 0) {
                if (i > 0 && this.co[i] > 0) {
                    sb.append("+");
                }
                sb.append(this.co[i]);
                if (this.ex[i] > 0) {
                    sb.append("x");
                    sb.append(this.co[i]);
                }
            }
        }

        FileWriter writer = new FileWriter(fileName);
        writer.write(sb.toString());
        writer.close();
    }

    public void print(){
        for(int i=0; i<this.co.length; ++i) {
            System.out.println("co:"+this.co[i]+" ex:"+this.ex[i]+"\n");
        }
    }
}