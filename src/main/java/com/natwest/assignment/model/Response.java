package com.natwest.assignment.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by kunalkho on 9/3/2022.
 */
@XmlRootElement
public class Response {

    private String input;
    private String output;

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
