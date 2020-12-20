package cz.cvut.fit.gorgomat.client.commands;

import cz.cvut.fit.gorgomat.client.model.CustomerCreateDTO;
import cz.cvut.fit.gorgomat.client.model.CustomerModel;
import cz.cvut.fit.gorgomat.client.resource.CustomerResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collection;

@ShellComponent
public class CustomerCommands {
    @Autowired
    private CustomerResource customerResource;


    @ShellMethod(value = "Create customer", key = "csC")
    public void csCreate(String name, String email) {
        CustomerCreateDTO cs = new CustomerCreateDTO(name, email);
        customerResource.create(cs);
        System.out.println("Success");
    }

    @ShellMethod(value = "Read one customer by ID", key = "csID")
    public String csReadOne(String id) {
        try {
            CustomerModel model = customerResource.readOne(id);
            return "Id:" + model.getId() + " Name:" + model.getName() + " Email:" + model.getEmail();
        } catch (HttpClientErrorException e) {
            return "404";
        }
    }

    @ShellMethod(value = "Read all customer", key = "csAll")
    public void csReadAll(int page, int size) {
        PagedModel<CustomerModel> model = customerResource.readAll(page, size);
        Collection<CustomerModel> collection = model.getContent();
        for (CustomerModel e : collection) {
            System.out.printf("ID: %s Name: %s Email: %s\n", e.getId(), e.getName(), e.getEmail());
        }
    }

    @ShellMethod(value = "Read customer by availability", key = "csAv")
    public void csReadAvailable(String name, int page, int size) {
        PagedModel<CustomerModel> model = customerResource.readWithNameContaining(name, page, size);
        Collection<CustomerModel> collection = model.getContent();
        for (CustomerModel e : collection) {
            System.out.printf("ID: %s Name: %s Email: %s\n", e.getId(), e.getName(), e.getEmail());
        }
    }

    @ShellMethod(value = "Read customer by type and size", key = "csTS")
    public void csReadTypeAndSize(String email, int page, int size) {
        PagedModel<CustomerModel> model = customerResource.readWithEmailContaining(email, page, size);
        Collection<CustomerModel> collection = model.getContent();
        for (CustomerModel e : collection) {
            System.out.printf("ID: %s Name: %s Email: %s\n", e.getId(), e.getName(), e.getEmail());
        }
    }

    @ShellMethod(value = "Delete customer", key = "csU")
    public void csUpdate(Long id, String name, String email) {
        try {
            CustomerModel dto = new CustomerModel(id, name, email);
            customerResource.update(dto);
            System.out.println("Success");
        } catch (HttpClientErrorException.BadRequest e) {
            System.out.println("400");
        }
    }

    @ShellMethod(value = "Delete customer", key = "csD")
    public void csDelete(Long id) {
        try {
            customerResource.delete(id.toString());
            System.out.println("Success");
        } catch (HttpClientErrorException.Conflict e) {
            System.out.println("409");
        } catch (HttpClientErrorException.BadRequest e) {
            System.out.println("400");
        }
    }

}
