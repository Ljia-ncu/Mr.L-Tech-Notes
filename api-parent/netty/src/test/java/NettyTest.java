import java.util.*;

/**
 * @ClassName: NettyTest
 * @Description
 * @Author Mr.L
 * @Date 2021/1/3 13:35
 * @Version 1.0
 */
public class NettyTest {

    public static void main(String[] args) {
        List<List<String>> equations = new ArrayList<>();
        equations.add(Arrays.asList("a", "b"));
        equations.add(Arrays.asList("e", "f"));
        equations.add(Arrays.asList("b", "e"));

        //b a 3.4
        //f e 1.4
        //e b 2.3

        double[] values = {3.4, 1.4, 2.3};

        List<List<String>> queries = new ArrayList<>();
        queries.add(Arrays.asList("a", "a"));

        new NettyTest().calcEquation(equations, values, queries);
    }

    private Map<String, Map<String, Double>> map = new HashMap<>();
    private Set<String> set = new HashSet<>();

    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        for (int i = 0; i < equations.size(); i++) {
            String A = equations.get(i).get(0);
            String B = equations.get(i).get(1);
            set.add(A);
            set.add(B);
            map.computeIfAbsent(B, k -> new HashMap<>()).put(A, values[i]);
        }

        double[] ans = new double[queries.size()];
        for (int i = 0; i < queries.size(); i++) {
            String A = queries.get(i).get(0);
            String B = queries.get(i).get(1);
            double r1 = recur(A, B);
            if (r1 == 0) {
                r1 = recur(B, A);
                if (r1 == 0) {
                    r1 = -1;
                } else {
                    r1 = 1 / r1;
                }
            }
            ans[i] = r1;
        }
        return ans;
    }

    private double recur(String A, String B) {
        if (A.equals(B)) {
            return set.contains(B) ? 1 : 0;
        }
        if (map.containsKey(B)) {
            if (map.get(B).containsKey(A)) {
                return map.get(B).get(A);
            } else {
                for (String key : map.get(B).keySet()) {
                    double result = map.get(B).get(key) * recur(A, key);
                    if (result != 0) {
                        return result;
                    }
                }
            }
        }
        return 0;
    }

    public boolean equationsPossible(String[] equations) {
        int[] map = new int[26];
        for(int i=0;i<26;i++){
            map[i] = i;
        }

        for(int i=0;i<equations.length;i++){
            if(equations[i].charAt(1) == '='){
                union(map,equations[i].charAt(0) - 'a',equations[i].charAt(3) -'a');
            }
        }

        for(int i=0;i<equations.length;i++){
            if(equations[i].charAt(1) == '!'){
                if(findOri(map,equations[i].charAt(0) - 'a') == findOri(map,equations[i].charAt(3) - 'a')){
                    return false;
                }
            }
        }
        return true;

    }

    private void union(int[] map,int index1,int index2){
        map[findOri(map,index1)] = findOri(map,index2);
    }

    private int findOri(int[] map,int index){
        while(map[index] != index){
            map[index] = map[map[index]];
            index = map[index];
        }
        return index;
    }
}
