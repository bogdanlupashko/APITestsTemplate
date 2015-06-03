package org.bl.json.jersey.model.auth;

/**
 * @author Bogdan Lupashko
 */

public class AuthLogin  {

    private String token;
    private Integer id;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}