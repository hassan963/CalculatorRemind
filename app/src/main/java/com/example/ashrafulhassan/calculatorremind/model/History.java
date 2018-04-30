package com.example.ashrafulhassan.calculatorremind.model;

/**
 * Created by Hassan M Ashraful on 4/22/2018.
 * Jr Software Developer
 * Innovizz Technology
 */

public class History {

    private String expression;
    private String result;
    private String remarks;
    private String time;

    public History() {
        setExpression(""); setRemarks(""); setResult(""); setTime("");
    }

    public History(String expression, String result, String remarks, String time) {
        this.expression = expression;
        this.result = result;
        this.remarks = remarks;
        this.time = time;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
