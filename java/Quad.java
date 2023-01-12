import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

class Quad {

    public static void main(String[] args) {
        List<Integer> vars = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            vars.add(Integer.parseInt(args[i]));
        }
        System.out.println(solve(vars));
//        SortedSet<Double> nums = new TreeSet<>();
//        for (int i = 1; i < 4; i++) {
//            for (int j = 0; j < 4; j++) {
//                for (int  k = 0; k < 4; k++) {
//                    List<Integer> vars = new ArrayList<>();
//                    vars.add(i);
//                    if (j != 0) {
//                        vars.add(j);
//                        if (k != 0) {
//                            vars.add(k);
//                        }
//                    }
//                    nums.add(solve(vars));
//                    System.out.println(vars);
//                }
//            }
//        }
//        System.out.println(nums);
    }
    
    private int accA, accB, bakA, bakB;
    
    public Quad(List<Integer> vars) {
        this(vars.size(), vars);
    }
    
    private Quad(int i, List<Integer> vars) {
        if (i > 0) {
            Quad p = new Quad(i-1, vars);
            accA = vars.get(i-1) * p.accA + p.bakA;
            accB = vars.get(i-1) * p.accB + p.bakB;
            bakA = p.accA;
            bakB = p.accB;
        } else {
            accB = 1;
            bakA = 1;
        }
        System.out.println(this);
    }

    public static double solve(List<Integer> vars) {
        Quad r = new Quad(vars);
        int b = r.bakA - r.accB;
        int c = r.bakB;
        return (Math.sqrt(b * b + 4 * r.accA * c) - b) / (2 * c);
    }
    
    public String toString() {
        return accA + " + " + accB + "x / " + bakA + " + " + bakB + "x";
    }

}
