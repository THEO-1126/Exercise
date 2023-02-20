package cn.edu.guet;

public class Test {
    public static void main(String[] args) {
        int arr[]={0,53,38,47,24,69,5,17,39};
        bubble_sort(arr,8);
    }
    static void bubble_sort(int arr[], int n) {
        int i, j, temp;
        for (i = 1; i < n; i++) {
            System.out.println("µÚ" + i + "ÌË");
            int f1, f2, f = 0;
            for (j = 1; j <= n - i; j++) {
                f1 = arr[j];
                f2 = arr[j + 1];
                f = 0;
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    f = 1;
                }
                System.out.print("(" + j + ")");
                for (int k = 1; k <= n; k++) {
                    System.out.print(" "+arr[k]);
                }
                if (f == 1) System.out.print("("+f1 + "ºÍ" + f2 + "½»»»)");
                System.out.println();
            }
        }
    }
}
