import java.util.Arrays;
import java.util.Random;

public class Sorter {

    /**
     * Bubble sort {@param a} into ascending order.
     *
     * This sort algorithm works by comparing adjacent elements and move larger element backward,
     * so ex [7, 5] will become [5, 7]. We will do this comparison-then-swap many rounds, each round
     * will effectively move the-current-largest-element to the end.
     *
     * Ex use bubble sort to sort [9, 5, 1, 4, 3]
     * round 1:
     *      [(9, 5), 1, 4, 3] -> [5, 9, 1, 4, 3]
     *                      __________/
     *                    /
     *      [5, (9, 1), 4, 3] -> [5, 1, 9, 4, 3]
     *                      __________/
     *                    /
     *      [5, 1, (9, 4), 3] -> [5, 1, 4, 9, 3]
     *                      __________/
     *                    /
     *      [5, 1, 4, (9, 3)] -> [5, 1, 4, 3, 9]
     *
     *      After round 1, we effectively move the largest element to the end of the array
     *
     * round 2: (`X` means don't need to care anymore, we are going to move current largest element to the end)
     *      [(5, 1), 4, 3, X] -> [1, 5, 4, 3, X]
     *                      __________/
     *                    /
     *      [1, (5, 4), 3, X] -> [1, 4, 5, 3, X]
     *                      __________/
     *                    /
     *      [1, 4, (5, 3), X] -> [1, 4, 3, 5, X]
     *
     *      After round 2, we effectively move the *current* largest element to the *end*
     *
     * round 3:
     *      [(1, 4), 3, X, X] -> [1, 4, 3, X, X]
     *                      __________/
     *                    /
     *      [1, (4, 3), X, X] -> [1, 3, 4, X, X]
     *
     *      After round 3, we effectively move the *current* largest element to the *end*
     *
     * round 4:
     *      [1, 3, X, X, X]
     *         ...
     *
     *
     * @param a The array to be sorted
     */
    public static void bubbleSort(int[] a) {
        for (int round = 1; round < a.length; ++round) {
            for (int i = 0; i < a.length - round; ++i) {
                if (a[i] > a[i+1]) { // Swap
                    int tmp = a[i];
                    a[i] = a[i+1];
                    a[i+1] = tmp;
                }
            }
        }
    }

    /**
     * Sort {@param a} in ascending order using insertion sort.
     *
     * Insertion sort works by insert an un-handled element into correct position of an already sorted sub-array,
     * Ex, insert 3 into [1, 4], we will get [1, 3, 4]. After insert, the sub-array becomes a larger
     * sorted sub-array. We do this repeatedly, finally the sorted sub-array becomes as large as the original array.
     *
     * Ex use insertion sort to sort [9, 5, 1, 4, 3]
     * round 1: insert element 5 into sorted sub-array [9], we got [5, 9]
     * round 2: insert element 1 into sorted sub-array [5, 9], we got [1, 5, 9]
     * round 3: insert element 4 into sorted sub-array [1, 5, 9], we got [1, 4, 5, 9]
     * round 4: insert element 3 into sorted sub-array [1, 4, 5, 9], we got [1, 3, 4, 5, 9]
     *
     * @param a The array to be sorted
     */
    public static void insertionSort(int[] a) {
        // handleElement start from 1 because index 0 is already sorted (single element is treated as sorted)
        for (int handleElement = 1; handleElement < a.length; ++handleElement) {
            int value = a[handleElement];
            int ind = handleElement - 1; // We compare forward.
            while (ind >= 0 && a[ind] > value) {
                a[ind + 1] = a[ind]; // Move elements backward if it is bigger than `value`.
                --ind;
            }
            a[ind + 1] = value; // Put the value into the correct position.
        }
    }

    /**
     * Sort {@param a} in ascending order using selection sort.
     *
     * Selection sort works by finding *current* largest element, and put it into the correct position.
     *
     * Ex use selection sort to sort [9, 5, 1, 4, 3]
     * round 1: the *current* largest element of [9, 5, 1, 4, 3] is 9, it should be put at the end of the unsorted array
     *          we got
     *                                           [3, 5, 1, 4, 9] (we need to swap the elements)
     * round 2: the *current* largest element of [3, 5, 1, 4, X] is 5, it should be put at the *end* of the unsorted
     *          array, we got
     *                                           [3, 4, 1, 5, X] (we need to swap the elements)
     * round 3: the *current* largest element of [3, 4, 1, X, X] is 4, it should be put at the *end* of the unsorted
     *          array, we got
     *                                           [3, 1, 4, X, X] (we need to swap the elements)
     * round 4: the *current* largest element of [3, 1, X, X, X] is 3, it should be put at the *end* of the unsorted
     *          array, we got
     *                                           [1, 3, X, X, X]
     * @param a
     */
    public static void selectionSort(int[] a) {
        for (int putToPosition = a.length - 1; putToPosition >= 1; --putToPosition) {

            int currentLargestIndex = 0;
            for (int i = 0; i <= putToPosition; ++i) {
                if (a[i] > a[currentLargestIndex]) {
                    currentLargestIndex = i;
                }
            }

            int tmp = a[putToPosition];
            a[putToPosition] = a[currentLargestIndex];
            a[currentLargestIndex] = tmp;
        }
    }

    /**
     * Shell sort {@param a} into ascending order.
     *
     * Shell sort works by dividing array into multiple *equal* length *sub-array*s (the last *sub-array* might have
     * different length due to not dividable, say 17 / 3, the length of the *sub-array*s will be 6, 6, 5 respectively),
     * then sort each *sub-array*.
     *
     * However, Shell sort do dividing multiple rounds, also the elements of a specific *sub-array* is in effect not
     * continuous, it is taking every hth element. For sorting each *sub-array*, it is use similar operation as
     * insertion sort.
     *
     * Normally for implementing Shell sort, we will begin with large values of h , this allows elements to move
     * long distances in the original array, reducing large amounts of disorder quickly, and leaving less work for
     * smaller h-sort.
     *
     * Note: If the array is then k-sorted for some smaller integer k, then the array remains h-sorted. Following this
     * idea for a decreasing sequence of h values ending in 1 is guaranteed to leave a sorted array in the end.
     *
     * @param a
     */
    public static void shellSort(int[] a) {
        // Shell sort do multiple rounds of dividing.
        // Say for round 1, the *sub-array*s will take every (h)th element.
        // For round 2, the *sub-array*s will take every (h/2)th element.
        // For round 3, the *sub-array*s will take every (h/4)th element.
        // ...
        int h = a.length;
        for (int interval = h / 2; interval > 0; interval /= 2) {
            for (int i = interval; i < h; i += 1) {
                // We will use same operation as in insertion sort.
                int temp = a[i];
                int j;
                for (j = i; j - interval >= 0 && a[j - interval] > temp; j -= interval) {
                    a[j] = a[j - interval];
                }
                a[j] = temp;
            }
        }
    }

    /**
     * Even Big-O notations for above 4 sorting algorithms are all O(n^2), but actually, the real runtime
     * performance are differently.
     *
     * I will analyse worst case for my above Shell sort:
     * 1) When (interval == n/2): the total array is effectively divided into 2 parts: [part0, part1],
     *      each part has n/2 elements,
     *      each element in part1 will do at most 1 comparison, 1 swap, so total to n/2 (comparison or swap)
     * 2) When (interval == n/4): the total array is effectively divided into 4 parts: [part0, part1, part2, part3],
     *      each part has n/4 elements,
     *      each element in part1 will do at most 1 comparison, 1 swap;
     *      each element in part2 will do at most 2 comparison, 2 swap;
     *      each element in part3 will do at most 3 comparison, 3 swap;
     *      so total to 4/n + 2 * 4/n + 3 * 4/n = (1+3)3/2 * n/4 = 3n/2 (comparison or swap)
     * 3) When (interval == n/8): the total array is effectively divided into 8 parts:
     *      [part0, part1, part2, part3, part4, part5, part6, part7], each part has n/8 elements
     *      each element in part1 will do at most 1 comparison, 1 swap;
     *      each element in part2 will do at most 2 comparison, 2 swap;
     *      each element in part3 will do at most 3 comparison, 3 swap;
     *      each element in part4 will do at most 1 comparison, 4 swap;
     *      each element in part5 will do at most 2 comparison, 5 swap;
     *      each element in part6 will do at most 3 comparison, 6 swap;
     *      each element in part7 will do at most 1 comparison, 7 swap;
     *      so total to n/8 + 2 * n/8 + 3 * n/8 + 4 * n/8 + 5 * n/8 + 6 * n/8 + 7 * n/8 = (1+7)*7/2 * n/8 = 7n/2
     * ...
     * X) When (interval == 1): the total array is effectively divided into n parts:
     *      ...
     *
     * As we continue do above steps, we will found the equation for the total number of comparison (or swap) is
     * n/2 + 3n/2 + 7n/2 + 15n/2 + 31n/2 + ...... (There are *logn* algebraic terms in this equation)
     *
     * = (n-n/2) + (2n-n/2) + (4n-n/2) + (8n-n/2) + (16n-n/2) + ...
     * = n + 2n + 4n + 8n + 16n + ... - n/2 * logn
     * = n * n - n/2 * logn (this is easy to understand if you treat n as 32)
     *
     *
     * 1) In worst case, say sorting [9, 8, 7, 6, 5, 4, 3, 2, 1] into ascending order:
     * ________________________________________________________________________________________________________________
     * |                  | Bubble sort | Insertion sort | Selection sort | Shell sort                                |
     * ----------------------------------------------------------------------------------------------------------------
     * | # of comparisons | (1+n)n/2    | (1+n)n/2       |  (1+n)n/2      | n/2 + 3n/4 + 7n/8 + ... (i.e. nlogn-n)    |
     * ----------------------------------------------------------------------------------------------------------------
     * | # of swaps       | (1+n)n/2    | (1+n)n/2       |    n           | f(n) = n/2 + 2 * f(n/2), i.e. ~ nlogn/2   |
     * ----------------------------------------------------------------------------------------------------------------
     * NOTE: above worst case might not be the worst case for Shell sort.
     *
     * 2) In best case, say sorting [1, 2, 3, 4, 5, 6, 7, 8, 9] into ascending order:
     * ________________________________________________________________________________________________________________
     * |                  | Bubble sort | Insertion sort | Selection sort | Shell sort                                |
     * ---------------------------------------------------------------------------------------------------------------
     * | # of comparisons | (1+n)n/2    |   n            |  (1+n)n/2      |  n/2 + 3n/4 + 7n/8 + .... (i.e. nlogn - n)|
     * ---------------------------------------------------------------------------------------------------------------
     * | # of swaps       |   0         |   0            |    0           |   0                                       |
     * ----------------------------------------------------------------------------------------------------------------
     *
     * 3) In average case, say sorting [1, 7, 6, 4, 5, 9, 3, 8, 2] into ascending order:
     * ________________________________________________________________________________________________________________
     * |                  | Bubble sort | Insertion sort | Selection sort | Shell sort                                |
     * ----------------------------------------------------------------------------------------------------------------
     * | # of comparisons | (1+n)n/2    |  < (1+n)n/2    |  (1+n)n/2      |  ~ nlogn                                  |
     * ----------------------------------------------------------------------------------------------------------------
     * | # of swaps       | < (1+n)n/2  |  < (1+n)n/2    |   n           |   ~ nlogn                                  |
     * ----------------------------------------------------------------------------------------------------------------
     *
     * So from above analysis, in real running application, insertion sort, selection sort, shell sort might work better
     * than bubble sort.
     */
    public static void main(String[] args) {
        Random rand = new Random();
        int[] numbers = new int[20];
        for (int i = 0; i < numbers.length; ++i) {
            int a = rand.nextInt(100) + 1;
            numbers[i] = a;
        }

        System.out.println("Before Sorting: ");
        print(numbers);

        int[] bubble = numbers.clone();
        bubbleSort(bubble);
        System.out.println("After bubble sorting: ");
        print(bubble);

        int[] insert = numbers.clone();
        insertionSort(insert);
        System.out.println("After Insertion sorting: ");
        print(insert);

        int[] select = numbers.clone();
        selectionSort(select);
        System.out.println("After selection sorting: ");
        print(select);

        int[] shell = numbers.clone();
        shellSort(shell);
        System.out.println("After shell sorting: ");
        print(shell);
    }

    private static void print(int[] arr) {
        for (int i = 0; i < arr.length; ++i) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
