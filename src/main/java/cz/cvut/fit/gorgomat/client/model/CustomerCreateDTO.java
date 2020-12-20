package cz.cvut.fit.gorgomat.client.model;

public class CustomerCreateDTO {

    private final String name;
    private final String email;

    public CustomerCreateDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
