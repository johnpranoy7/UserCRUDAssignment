package com.jyalla.demo.modal;

public class JwtResponse {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public JwtResponse(String token) {
        super();
        this.token = token;
    }

    public JwtResponse() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
        return "JwtResponse [token=" + token + "]";
    }

}
