package cz.cvut.fit.gorgomat.client.model;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class EquipmentModel extends RepresentationModel<EquipmentModel> {
    private final Long id;
    private final Integer size;
    private final String type;
    private final Boolean available;

    public EquipmentModel(Long id, Integer size, String type, Boolean available) {
        this.id = id;
        this.size = size;
        this.type = type;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public Integer getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    public Boolean isAvailable() {
        return available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EquipmentModel that = (EquipmentModel) o;
        return size == that.size &&
                available == that.available &&
                id.equals(that.id) &&
                type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, size, type, available);
    }
}
