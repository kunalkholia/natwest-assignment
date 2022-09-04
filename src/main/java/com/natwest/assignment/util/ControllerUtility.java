package com.natwest.assignment.util;

/**
 * Created by kunalkho on 9/3/2022.
 */
public class ControllerUtility {
    public static boolean optimisedIsPrime(int N){
        int i = 2;
        while (i * i <= N){
            if (N % i == 0)
                return false;
            i += 1;
        }
        return true;
    }
    public static boolean isPrime(int N){
        for(int i=2;i<N;i++){
            if (N % i == 0)
                return false;
        }
        return true;
    }
}
