package cz.cvut.fit.gorgomat.client.commands;

import cz.cvut.fit.gorgomat.client.model.EquipmentCreateDTO;
import cz.cvut.fit.gorgomat.client.model.EquipmentModel;
import cz.cvut.fit.gorgomat.client.resource.EquipmentResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collection;

@ShellComponent
public class EquipmentCommands {
    @Autowired
    private EquipmentResource equipmentResource;


    @ShellMethod(value = "Create equipment", key = "eqC")
    public void eqCreate(Integer size, String type, int av) {
        EquipmentCreateDTO eq = new EquipmentCreateDTO(size, type, av != 0);
        equipmentResource.create(eq);
        System.out.println("Success");
    }

    @ShellMethod(value = "Read one equipment by ID", key = "eqID")
    public String eqReadOne(String id) {
        try {
            EquipmentModel model = equipmentResource.readOne(id);
            return "Id:" + model.getId() + " Type:" + model.getType() + " Size:" + model.getSize();
        } catch (HttpClientErrorException e) {
            return "404";
        }
    }

    @ShellMethod(value = "Read all equipment", key = "eqAll")
    public void eqReadAll(int page, int size) {
        PagedModel<EquipmentModel> model = equipmentResource.readAll(page, size);
        Collection<EquipmentModel> collection = model.getContent();
        for (EquipmentModel e : collection) {
            System.out.printf("ID: %s Type: %s Size: %s Available: %s \n", e.getId(), e.getType(), e.getSize(), e.isAvailable());
        }
    }

    @ShellMethod(value = "Read equipment by availability", key = "eqAv")
    public void eqReadAvailable(String av, int page, int size) {
        PagedModel<EquipmentModel> model = equipmentResource.readByAvailability(av, page, size);
        Collection<EquipmentModel> collection = model.getContent();
        for (EquipmentModel e : collection) {
            System.out.printf("ID: %s Type: %s Size: %s Available: %s \n", e.getId(), e.getType(), e.getSize(), e.isAvailable());
        }
    }

    @ShellMethod(value = "Read equipment by type and size", key = "eqTS")
    public void eqReadTypeAndSize(String type, Integer eqSize, int page, int size) {
        PagedModel<EquipmentModel> model = equipmentResource.readBySizeAndType(eqSize.toString(), type, page, size);
        Collection<EquipmentModel> collection = model.getContent();
        for (EquipmentModel e : collection) {
            System.out.printf("ID: %s Type: %s Size: %s Available: %s \n", e.getId(), e.getType(), e.getSize(), e.isAvailable());
        }
    }

    @ShellMethod(value = "Delete equipment", key = "eqU")
    public void eqUpdate(Long id, Integer size, String type, int av) {
        try {
            EquipmentModel dto = new EquipmentModel(id, size, type, av != 0);
            equipmentResource.update(dto);
            System.out.println("Success");
        } catch (HttpClientErrorException.BadRequest e) {
            System.out.println("400");
        }
    }

    @ShellMethod(value = "Delete equipment", key = "eqD")
    public void eqDelete(Long id) {
        try {
            equipmentResource.delete(id.toString());
            System.out.println("Success");
        } catch (HttpClientErrorException.Conflict e) {
            System.out.println("409");
        } catch (HttpClientErrorException.BadRequest e) {
            System.out.println("400");
        }
    }

}
