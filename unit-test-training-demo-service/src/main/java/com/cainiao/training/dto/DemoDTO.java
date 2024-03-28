package com.cainiao.training.dto;

public class DemoDTO {
    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public final String getFinalValue() {
//        throw new RuntimeException();
        return "final_string";
    }
}
