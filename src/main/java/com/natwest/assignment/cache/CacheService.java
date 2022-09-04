package com.natwest.assignment.cache;

import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by kunalkho on 9/3/2022.
 */

@Service
public class CacheService {

    private Integer lastUpdated;
    private Set<Integer> primeNumbers;

    public CacheService() {
        primeNumbers = new TreeSet<>();
        primeNumbers.add(2);
        lastUpdated = 2;
    }

    public synchronized Integer getLastUpdated() {
        return lastUpdated;
    }

    public synchronized Set<Integer> getPrimeNumbers() {
        return primeNumbers;
    }

    public synchronized void addPrimeNumbers(int N) {
        if (N > this.lastUpdated) {
            primeNumbers.add(N);
            lastUpdated = N;
        }
    }
}
