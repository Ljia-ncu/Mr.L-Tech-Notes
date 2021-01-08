import java.util.Deque;
import java.util.LinkedList;

/**
 * @ClassName: NettyTest
 * @Description
 * @Author Mr.L
 * @Date 2021/1/3 13:35
 * @Version 1.0
 */
public class NettyTest {

    public static void main(String[] args) {
        new NettyTest().rotate(new int[]{1, 2, 3, 4, 5}, 3);
    }

    public void rotate(int[] nums, int k) {
        Deque<Integer> queue = new LinkedList<>();
        for (int n : nums) {
            queue.offer(n);
        }
        for (int i = 0; i < k; i++) {
            queue.push(queue.pollLast());
        }
        for (int i = 0; i < nums.length; i++) {
            nums[i] = queue.poll();
            System.out.print(nums[i] + " ");
        }
    }
}
