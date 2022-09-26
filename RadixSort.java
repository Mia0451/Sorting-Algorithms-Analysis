import java.util.Random;

public class RadixSort {

    public static void main(String[] args) {
        Random rand = new Random();
        int[] numbers = new int[100];
        int largestDigitCount = 0;
        for (int i = 0; i < numbers.length; ++i) {
            int a = rand.nextInt(10000) + 1;
            numbers[i] = a;
            int digits = digitCount(a);
            largestDigitCount = digits > largestDigitCount ? digits : largestDigitCount;
        }

        System.out.println("Before Sorting: ");
        print(numbers);

        radixSort(numbers, largestDigitCount);

        System.out.println("After sorting: ");
        print(numbers);
    }

    /**
     * Radix sort integer array {@param numbers}
     *
     * Say {@param numbers} is [3, 45, 61, 90, 244, 56, 976, 32], after sorting, it will become
     * [3, 32, 45, 56, 61, 90, 244, 976]
     *
     * @param numbers The integer array to be sorted (in ascending order)
     * @param largestDigitCount The number of digits of the largest element in the array
     */
    public static void radixSort(int[] numbers, int largestDigitCount) {
        DLinkedList<Integer>[] tool = new DLinkedList[10];
        for (int i = 0; i < tool.length; ++i) {
            tool[i] = new DLinkedList<Integer>();
        }

        int mod = 1;

        // We start form the least significant digit. Say for 325, in round 1, we will handle digit `5`, in round 2
        // we will handle digit `2`, in round 3, we will handle digit `3`
        for (int round = 1; round <= largestDigitCount; ++round) {

            // Add numbers to the doubly linked lists.
            for (int i = 0; i < numbers.length; ++i) {
                int a = numbers[i];
                int interestedDigit = (a % (mod * 10)) / mod;
                tool[interestedDigit].Append(a);
            }

            // Read numbers back from doubly linked lists to the array.
            int position = 0;
            for (int i = 0; i < tool.length; ++i) {
                while (!tool[i].IsEmpty()) {
                    numbers[position++] = tool[i].Pop();
                }
            }

            mod *= 10;
        }
    }

    /**
     * Return the number of digits in {@param a}, {@param a} is non-negative.
     *
     * Say when {@param a} is 12, return 2; when {@param a} is 0, return 1, when {@param a} is 12356, return 5;
     * when {@param a} is 7463460, return 7 etc
     *
     * @param a
     * @return
     */
    private static int digitCount(int a) {
        if (a == 0) {
            return 1;
        }

        int count = 0;
        while (a > 0) {
            ++count;
            a /= 10;
        }

        return count;
    }

    private static void print(int[] arr) {
        for (int i = 0; i < arr.length; ++i) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
