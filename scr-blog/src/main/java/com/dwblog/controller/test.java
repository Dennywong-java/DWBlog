package com.dwblog.controller;

public class test {
    public static int findIndex(int[] array, int number) {
        // Check for null or empty array
        if (array == null || array.length == 0) {
            return -1;
        }

        // Start the search from the first element of the array
        int start = 0;
        // End the search at the last element of the array
        int end = array.length - 1;

        // Keep searching while there are elements in the array to search
        while (start <= end) {
            // Find the midpoint of the current search range
            int mid = start + (end - start) / 2;

            // Check if the midpoint is the number we are looking for
            if (array[mid] == number) {
                // If so, return the index of the midpoint
                return mid;
            }

            // Check if the left side of the midpoint is sorted
            if (array[start] <= array[mid]) {
                // If so, check if the number we are looking for is in the left side of the midpoint
                if (number >= array[start] && number < array[mid]) {
                    // If so, move the end of the search range to the left of the midpoint
                    end = mid - 1;
                } else {
                    // If not, move the start of the search range to the right of the midpoint
                    start = mid + 1;
                }
            } else {
                // If the left side of the midpoint is not sorted, then the right side must be sorted
                // Therefore, check if the number we are looking for is in the right side of the midpoint
                if (number > array[mid] && number <= array[end]) {
                    // If so, move the start of the search range to the right of the midpoint
                    start = mid + 1;
                } else {
                    // If not, move the end of the search range to the left of the midpoint
                    end = mid - 1;
                }
            }
        }

        // If the number is not found, return -1
        return -1;
    }

    public static void main(String[] args){
        int[] array = new int[]{7, 8, 9, 1, 2, 3, 4, 5, 6};
        int target = 5;
        int index = findIndex(array,target);
        System.out.println("The target index is: " + index);
    }


}
