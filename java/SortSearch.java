import java.util.ArrayList;
import java.util.Collections;

public class SortSearch {

    public static void main(String[] args) {
        ArrayList<Integer> nums = new ArrayList<>();
        if (args.length == 1) {
            int numNums = Integer.parseInt(args[0]);
            for (int i = 0; i < numNums; i++) {
                nums.add(i + (int) (5 * Math.random()));
            }
            Collections.shuffle(nums);
        } else {
            for (String arg : args) {
                nums.add(Integer.parseInt(arg));
            }
        }
        System.out.println(bubbleSort(new ArrayList<Integer>(nums)));
        System.out.println(selectionSort(new ArrayList<Integer>(nums)));
        System.out.println(insertionSort(new ArrayList<Integer>(nums)));
        System.out.println(quickSort(new ArrayList<Integer>(nums)));
    }
    
    public static String bubbleSort(ArrayList<Integer> nums) {
        Count c = new Count("Compares");
        Count r = new Count("Relocations");
        for (int i = 0; i < nums.size()-1; i++) {
            for (int j = 1; j < nums.size()-i; j++) {
                if (c.incr() && nums.get(j) < nums.get(j-1)) {
                    int temp = nums.get(j-1);
                    nums.set(j-1, nums.get(j));
                    nums.set(j, temp);
                    r.incr();
                }
            }
        }
        return nums + " (" + c + ", " + r + ")";
    }
    
    public static String selectionSort(ArrayList<Integer> nums) {
        Count c = new Count("Compares");
        Count r = new Count("Relocations");
        for (int i = 0; i < nums.size()-1; i++) {
            int slct = i;
            for (int j = i+1; j < nums.size(); j++) {
                if (c.incr() && nums.get(j) < nums.get(slct)) {
                    slct = j;
                }
            }
            if (i != slct) {
                nums.add(i, nums.remove(slct));
                r.incr();
            }
        }
        return nums + " (" + c + ", " + r + ")";
    }
    
    public static String insertionSort(ArrayList<Integer> nums) {
        Count c = new Count("Compares");
        Count r = new Count("Relocations");
        for (int i = 1; i < nums.size(); i++) {
            int j = 0;
            while (j < i && c.incr() && nums.get(j) < nums.get(i)) {
                j++;
            }
            if (j != i) {
                nums.add(j, nums.remove(i));
                r.incr();
            }
        }
        return nums + " (" + c + ", " + r + ")";
    }
    
    public static String quickSort(ArrayList<Integer> nums) {
        Count c = new Count("Compares");
        Count r = new Count("Relocations");
        qS(0, nums.size(), nums, c, r);
        return nums + " (" + c + ", " + r + ")";
    }
    
    private static void qS(int start, int end, ArrayList<Integer> nums, Count c, Count r) {
        if (end - start > 1) {
            int mid = start;
            int ref = nums.get(mid);
            for (int i = start+1; i < end; i++) {
                if (c.incr() && nums.get(i) < ref) {
                    nums.add(start, nums.remove(i));
                    mid++;
                    r.incr();
                }
            }
            qS(start, mid, nums, c, r);
            qS(mid+1, end, nums, c, r);
        }
    }
    
}
