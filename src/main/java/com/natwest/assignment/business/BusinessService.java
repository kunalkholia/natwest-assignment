package com.natwest.assignment.business;

import com.google.gson.Gson;
import com.natwest.assignment.cache.CacheService;
import com.natwest.assignment.model.Response;
import com.natwest.assignment.util.ControllerUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by kunalkho on 9/3/2022.
 */

@Service
public class BusinessService {

    @Autowired
    CacheService cacheService;

    public List<Integer> processNumber(int number, Optional<String> algorithm) {
        if (number <= cacheService.getLastUpdated()) {
            List<Integer> primeNumbersList = cacheService.getPrimeNumbers().stream()
                    .filter(n -> n <= number)
                    .collect(Collectors.toList());
            return primeNumbersList;
        }
        List<Integer> primeNumbersList = new ArrayList<>(cacheService.getPrimeNumbers());
        if (algorithm.isPresent()) {
            switch (algorithm.get()) {
                case "fast":
                    for (int i = cacheService.getLastUpdated() + 1; i <= number; i++) {
                        if (ControllerUtility.isPrime(i)) {
                            cacheService.addPrimeNumbers(i);
                            primeNumbersList.add(i);
                        }
                    }
                    break;
                case "faster":
                    for (int i = cacheService.getLastUpdated() + 1; i <= number; i++) {
                        if (ControllerUtility.optimisedIsPrime(i)) {
                            cacheService.addPrimeNumbers(i);
                            primeNumbersList.add(i);
                        }
                    }
                    break;
                case "fastest":
                default:
                    primeNumbersList = findAllPrime(number);
            }
        } else primeNumbersList = findAllPrime(number);
        return primeNumbersList;
    }

    public List<Integer> findAllPrime(int N) {
        int limit = (int) (Math.floor(Math.sqrt(N)) + 1);
        List<Integer> primeNumbersList;
        //now we've to find prime numbers up to this limit, first we'll check if we've them already cached
        if (limit <= cacheService.getLastUpdated()) {
            primeNumbersList = cacheService.getPrimeNumbers().stream()
                    .filter(n -> n <= limit)
                    .collect(Collectors.toList());
        } else if (limit - cacheService.getLastUpdated() <= (limit / 2)) {
            primeNumbersList = cacheService.getPrimeNumbers().stream()
                    .collect(Collectors.toList());
            for (int i = cacheService.getLastUpdated() + 1; i <= limit; i++) {
                if (ControllerUtility.optimisedIsPrime(i)) {
                    cacheService.addPrimeNumbers(i);
                    primeNumbersList.add(i);
                }
            }
        } else {
            primeNumbersList = sieve(limit);
        }

        int left = limit;
        int right = limit * 2;

        while (left < N) {

            if (right >= N)
                right = N;

            //an array to mark prime numbers in segment
            boolean markPrime[] = new boolean[limit + 1];
            Arrays.fill(markPrime, true);

            for (int i = 0; i < primeNumbersList.size(); i++) {
                int base = (int) (Math.floor(left / primeNumbersList.get(i)) * primeNumbersList.get(i));
                if (base < left)
                    base += primeNumbersList.get(i);

                for (int j = base; j < right; j += primeNumbersList.get(i))
                    markPrime[j - left] = false;

            }
            for (int i = left; i < right; i++) {
                if (markPrime[i - left] == true) {
                    cacheService.addPrimeNumbers(i);
                    primeNumbersList.add(i);
                }
            }

            left = left + limit;
            right = right + limit;
        }

        return primeNumbersList;
    }

    private List<Integer> sieve(int N) {
        List<Integer> primeNumbersList = new ArrayList<>();
        boolean prime[] = new boolean[N + 1];
        for (int i = 0; i <= N; i++)
            prime[i] = true;
        for (int p = 2; p * p <= N; p++) {
            if (prime[p] == true) {
                for (int i = p * p; i <= N; i += p)
                    prime[i] = false;
            }
        }

        for (int i = 2; i <= N; i++) {
            if (prime[i] == true) {
                cacheService.addPrimeNumbers(i);
                primeNumbersList.add(i);
            }
        }
        return primeNumbersList;
    }

    public String createErrorResponseJson(int N, String message) {
        Response response = new Response();
        response.setInput(String.valueOf(N));
        response.setOutput(message);
        return new Gson().toJson(response);
    }

    public String createErrorResponseXml(int N, String message) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(Response.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter sw = new StringWriter();
        Response response = new Response();
        response.setInput(String.valueOf(N));
        response.setOutput(message);
        jaxbMarshaller.marshal(response, sw);
        return sw.toString();
    }

    public String createSuccessResponseJson(int N, List<Integer> list) {
        Response response = new Response();
        response.setInput(String.valueOf(N));
        response.setOutput(list.toString());
        return new Gson().toJson(response);
    }

    public String createSuccessResponseXml(int N, List<Integer> list) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(Response.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter sw = new StringWriter();
        Response response = new Response();
        response.setInput(String.valueOf(N));
        response.setOutput(list.toString());
        jaxbMarshaller.marshal(response, sw);
        return sw.toString();
    }
}
