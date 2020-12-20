package cz.cvut.fit.gorgomat.client.resource;

import cz.cvut.fit.gorgomat.client.model.MyOrderCreateDTO;
import cz.cvut.fit.gorgomat.client.model.MyOrderModel;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class MyOrderResource {
    private final RestTemplate restTemplate;

    public MyOrderResource(RestTemplateBuilder builder) {
        restTemplate = builder.rootUri("http://localhost:8080/api/v1/myOrders").build();
    }

    private static final String PAGED_URI = "/?page={page}&size={size}";
    private static final String ONE_URI = "/{id}";

    public URI create(MyOrderCreateDTO data) {
        return restTemplate.postForLocation("/", data);
    }

    public MyOrderModel readOne(String id) {
        return restTemplate.getForObject(ONE_URI, MyOrderModel.class, id);
    }

    public PagedModel<MyOrderModel> readByCustomerName(String name, int page, int size) {
        ResponseEntity<PagedModel<MyOrderModel>> response = restTemplate.exchange(
                PAGED_URI + "&customerName=" + name, HttpMethod.GET, null,
                new ParameterizedTypeReference<PagedModel<MyOrderModel>>() {
                },
                page, size
        );
        return response.getBody();
    }

    public PagedModel<MyOrderModel> readByCustomerId(String id, int page, int size) {
        ResponseEntity<PagedModel<MyOrderModel>> response = restTemplate.exchange(
                PAGED_URI + "&customerId=" + id, HttpMethod.GET, null,
                new ParameterizedTypeReference<PagedModel<MyOrderModel>>() {
                },
                page, size
        );
        return response.getBody();
    }

    public PagedModel<MyOrderModel> readAll(int page, int size) {
        ResponseEntity<PagedModel<MyOrderModel>> response = restTemplate.exchange(
                PAGED_URI, HttpMethod.GET, null,
                new ParameterizedTypeReference<PagedModel<MyOrderModel>>() {
                },
                page, size
        );
        return response.getBody();
    }

    public void update(MyOrderModel data) {
        restTemplate.put(ONE_URI, data, data.getId());
    }

    public void delete(String id) {
        restTemplate.delete(ONE_URI, id);
    }
}
