package cz.cvut.fit.gorgomat.client.resource;

import cz.cvut.fit.gorgomat.client.model.EquipmentCreateDTO;
import cz.cvut.fit.gorgomat.client.model.EquipmentModel;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class EquipmentResource {
    private final RestTemplate restTemplate;

    public EquipmentResource(RestTemplateBuilder builder) {
        restTemplate = builder.rootUri("http://localhost:8080/api/v1/equipment").build();
    }

    private static final String PAGED_URI = "/?page={page}&size={size}";
    private static final String ONE_URI = "/{id}";

    public URI create(EquipmentCreateDTO data) {
        return restTemplate.postForLocation("/", data);
    }

    public EquipmentModel readOne(String id) {
        return restTemplate.getForObject(ONE_URI, EquipmentModel.class, id);
    }

    public PagedModel<EquipmentModel> readByAvailability(String bool, int page, int size) {
        ResponseEntity<PagedModel<EquipmentModel>> response = restTemplate.exchange(
                PAGED_URI + "&available=" + bool, HttpMethod.GET, null,
                new ParameterizedTypeReference<PagedModel<EquipmentModel>>() {
                },
                page, size
        );
        return response.getBody();
    }

    public PagedModel<EquipmentModel> readBySizeAndType(String eqSize, String type, int page, int size) {
        ResponseEntity<PagedModel<EquipmentModel>> response = restTemplate.exchange(
                PAGED_URI + "&size=" + eqSize + "&type=" + type, HttpMethod.GET, null,
                new ParameterizedTypeReference<PagedModel<EquipmentModel>>() {
                },
                page, size
        );
        return response.getBody();
    }

    public PagedModel<EquipmentModel> readAll(int page, int size) {
        ResponseEntity<PagedModel<EquipmentModel>> response = restTemplate.exchange(
                PAGED_URI, HttpMethod.GET, null,
                new ParameterizedTypeReference<PagedModel<EquipmentModel>>() {
                },
                page, size
        );
        return response.getBody();
    }

    public void update(EquipmentModel data) {
        restTemplate.put(ONE_URI, data, data.getId());
    }

    public void delete(String id) {
        restTemplate.delete(ONE_URI, id);
    }
}
