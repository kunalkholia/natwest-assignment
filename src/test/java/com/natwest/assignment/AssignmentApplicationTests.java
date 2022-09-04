package com.natwest.assignment;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AssignmentApplicationTests {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void getPrimeNumbers() throws Exception {
        ResponseEntity<String> result = template.withBasicAuth("kunal", "kunal123")
                .getForEntity("/api/natwest/assignment/getprimenumbers?number=10", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        JsonObject responseObj = new JsonParser().parse(result.getBody()).getAsJsonObject();
        assertEquals("\"[2, 3, 5, 7]\"", responseObj.get("output").toString());
    }


}
