import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoinCombo {

    private int bits;
    private Map<Integer, List<Integer>> combos;

    private CoinCombo(int bits) {
        this.bits = bits;
        combos = new HashMap<>();
        int totalCombos = (int) Math.pow(2, bits);
        for (int i = 0; i < totalCombos; i++) {
            combos.put(i, new ArrayList<>());
            makeNeighbors(i);
        }
    }

    private void makeNeighbors(int value) {
        for (int i = 0; i < bits; i++) {
            int pot = (int) Math.pow(2, i);
            combos.get(value).add(value + ((value / pot) % 2 == 0 ? pot : -pot));
        }
    }

    private static void addOptions(Map<Integer, String> code, CoinCombo cc) {
        for (List<Integer> neighbors : cc.combos.values()) {
            StringBuilder uncoded = new StringBuilder();
            for (int i = 0; i < cc.bits; i++) {
                uncoded.append(i);
            }
            for (Integer num : neighbors) {
                if (code.containsKey(num) && code.get(num).length() == 1) {
                    int cut = uncoded.indexOf(code.get(num));
                    if (cut == -1) {
                        System.out.println("ouch");
                    }
                    uncoded = new StringBuilder(uncoded.substring(0, cut) + uncoded.substring(cut + 1));
                }
            }
            for (Integer num : neighbors) {
                if (!code.containsKey(num) || (code.get(num).length() > uncoded.length() && uncoded.length() > 0)) {
                    code.put(num, uncoded.toString());
                }
            }
        }
    }

    private static void removeOptions(Map<Integer, String> code, CoinCombo cc) {
        for (List<Integer> neighbors : cc.combos.values()) {
            int[] countOptions = new int[cc.bits];
            for (Integer num : neighbors) {
                String options = code.get(num);
                if (options.length() > 1) {
                    for (int i = 0; i < options.length(); i++) {
                        countOptions[Character.getNumericValue(options.charAt(i))]++;
                    }
                }
            }
            for (int i = 0; i < cc.bits; i++) {
                if (countOptions[i] == 1) {
                    for (Integer num : neighbors) {
                        String ofI = Integer.toString(i);
                        if (code.get(num).contains(ofI)) {
                            code.put(num, ofI);
                        }
                    }
                }
            }
        }
    }

    private static void fillPlaces(Map<Integer, String> code, CoinCombo cc) {
        int[] countPlaced = new int[cc.bits];
        List<List<Integer>> allOptions = new ArrayList<>();
        for (int i = 0; i < cc.bits; i++) {
            allOptions.add(new ArrayList<>());
        }
        for (Map.Entry<Integer, String> entry : code.entrySet()) {
            String options = entry.getValue();
            if (options.length() == 1) {
                countPlaced[Integer.parseInt(options)]++;
                continue;
            }
            for (int i = 0; i < options.length(); i++) {
                allOptions.get(Character.getNumericValue(options.charAt(i))).add(entry.getKey());
            }
        }
        for (int i = 0; i < cc.bits; i++) {
            if (allOptions.get(i).size() == (code.size() / cc.bits) - countPlaced[i]) {
                for (Integer num : allOptions.get(i)) {
                    code.put(num, Integer.toString(i));
                }
            }
        }
    }

    public static void main(String[] args) {
        Map<Integer, String> code = new HashMap<>();

        code.put(0, "0");
        code.put(1, "0");
        code.put(2, "1");
        code.put(3, "1");
        code.put(4, "2");
        code.put(5, "2");
        code.put(6, "3");
        code.put(7, "3");
        code.put(8, "3");
        code.put(9, "3");
        code.put(10, "2");
        code.put(11, "2");
        code.put(12, "1");
        code.put(13, "1");
        code.put(14, "0");
        code.put(15, "0");

        code.put(48, "3");
        code.put(49, "3");
        code.put(50, "2");
        code.put(51, "2");
        code.put(52, "1");
        code.put(53, "1");
        code.put(54, "0");
        code.put(55, "0");
        code.put(56, "0");
        code.put(57, "0");
        code.put(58, "1");
        code.put(59, "1");
        code.put(60, "2");
        code.put(61, "2");
        code.put(62, "3");
        code.put(63, "3");

        code.put(16, "4");
        code.put(17, "4");
        code.put(32, "5");
        code.put(33, "5");
        code.put(64, "6");
        code.put(65, "6");
        code.put(126, "7");
        code.put(127, "7");
        code.put(128, "7");
        code.put(129, "7");
        code.put(190, "6");
        code.put(191, "6");
        code.put(222, "5");
        code.put(223, "5");
        code.put(238, "4");
        code.put(239, "4");


        code.put(255, "0");
        code.put(254, "0");
        code.put(253, "1");
        code.put(252, "1");
        code.put(251, "2");
        code.put(250, "2");
        code.put(249, "3");
        code.put(248, "3");
        code.put(247, "3");
        code.put(246, "3");
        code.put(245, "2");
        code.put(244, "2");
        code.put(243, "1");
        code.put(242, "1");
        code.put(241, "0");
        code.put(240, "0");

        CoinCombo cc = new CoinCombo(8);
        addOptions(code, cc);
        removeOptions(code, cc);
        fillPlaces(code, cc);
        System.out.println(code);
        // System.out.println(cc.combos);
    }

}
