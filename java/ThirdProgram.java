public class ThirdProgram {
    
    public static void main(String[] args) {
        int a = Integer.parseInt(args[0]);
        //int b = Integer.parseInt(args[1]);
        System.out.println(dragon(a));
    }
    
    /**
     * Calculates the greatest common factor of two integers
     * @param   a   Should be the smaller number
     * @param   b   Should be the larger number
     * @return      The greatest common factor, or calls gcf() again
     */
    public static int gcf(int a, int b) {
        if (a == 0) return b;
        return gcf(b % a, a);
    }
    
    public static String dragon(int order) {
        String dragon = "F";
        String nogard = "F";
        for (int i = 0; i < order; i++) {
            String d = dragon;
            dragon = d + "L" + nogard;
            nogard = d + "R" + nogard;
        }
        return dragon;
    }
}
