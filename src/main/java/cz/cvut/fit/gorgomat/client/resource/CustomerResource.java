package cz.cvut.fit.gorgomat.client.resource;

import cz.cvut.fit.gorgomat.client.model.CustomerCreateDTO;
import cz.cvut.fit.gorgomat.client.model.CustomerModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class CustomerResource {
    private final RestTemplate restTemplate;

    public CustomerResource(RestTemplateBuilder builder) {
        restTemplate = builder.rootUri("http://localhost:8080/api/v1/customers").build();
    }

    private static final String PAGED_URI = "/?page={page}&size={size}";
    private static final String ONE_URI = "/{id}";

    public URI create(CustomerCreateDTO data) {
        return restTemplate.postForLocation("/", data);
    }

    public CustomerModel readOne(String id) {
        return restTemplate.getForObject(ONE_URI, CustomerModel.class, id);
    }

    public PagedModel<CustomerModel> readWithNameContaining(String name, int page, int size) {
        ResponseEntity<PagedModel<CustomerModel>> response = restTemplate.exchange(
                PAGED_URI + "&name=" + name, HttpMethod.GET, null,
                new ParameterizedTypeReference<PagedModel<CustomerModel>>() {
                },
                page, size
        );
        return response.getBody();
    }

    public PagedModel<CustomerModel> readWithEmailContaining(String email, int page, int size) {
        ResponseEntity<PagedModel<CustomerModel>> response = restTemplate.exchange(
                PAGED_URI + "&email=" + email, HttpMethod.GET, null,
                new ParameterizedTypeReference<PagedModel<CustomerModel>>() {
                },
                page, size
        );
        return response.getBody();
    }

    public PagedModel<CustomerModel> readAll(int page, int size) {
        ResponseEntity<PagedModel<CustomerModel>> response = restTemplate.exchange(
                PAGED_URI, HttpMethod.GET, null,
                new ParameterizedTypeReference<PagedModel<CustomerModel>>() {
                },
                page, size
        );
        return response.getBody();
    }

    public void update(CustomerModel data) {
        restTemplate.put(ONE_URI, data, data.getId());
    }

    public void delete(String id) {
        restTemplate.delete(ONE_URI, id);
    }
}
