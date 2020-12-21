package cz.cvut.fit.gorgomat.client.commands;

import cz.cvut.fit.gorgomat.client.model.CustomerModel;
import cz.cvut.fit.gorgomat.client.model.MyOrderCreateDTO;
import cz.cvut.fit.gorgomat.client.model.MyOrderModel;
import cz.cvut.fit.gorgomat.client.resource.MyOrderResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.ArrayTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;
import org.springframework.web.client.HttpClientErrorException;

import java.sql.Date;
import java.util.*;

@ShellComponent
public class MyOrderCommands {
    @Autowired
    private MyOrderResource myOrderResource;

    public String[] HEADER_ROW = {"ID", "From", "To", "Customer ID", "Equipment"};


    @ShellMethod(value = "Create myOrder", key = "orC")
    public void orCreate(Long from, Long to, Long cID) {
        try {
            Scanner in = new Scanner(System.in);
            List<Long> eIDs = new ArrayList<Long>();
            System.out.println("Enter equipment IDs: \n");
            while (in.hasNextLong()) {
                eIDs.add(in.nextLong());
            }
            Date f = new Date(from);
            Date t = new Date(to);
            MyOrderCreateDTO or = new MyOrderCreateDTO(f, t, cID, eIDs);
            myOrderResource.create(or);
            System.out.println("Success");
        } catch (HttpClientErrorException e) {
            System.out.println("400");
        } catch (Exception e) {
            System.out.println("409 - Wrong equipment!");
        }
    }

    @ShellMethod(value = "Read one myOrder by ID", key = "orID")
    public void orReadOne(Long id) {
        try {
            MyOrderModel m = myOrderResource.readOne(id.toString());
            System.out.printf("ID: %s From: %s To: %s Customer: %s \nEquipment: ",
                    m.getId(),
                    m.getDateFrom().toString(),
                    m.getDateTo().toString(),
                    m.getCustomerId());
            for (Long e : m.getEquipmentIds()) {
                System.out.printf("%s ", e.toString());
            }
            System.out.println("\nSuccess");
        } catch (HttpClientErrorException e) {
            System.out.println("404");
        }
    }

    @ShellMethod(value = "Read myOrder by customer name", key = "orCn")
    public void orReadAvailable(String name, int page, int size) {
        PagedModel<MyOrderModel> model = myOrderResource.readByCustomerName(name, page, size);
        Collection<MyOrderModel> collection = model.getContent();
        for (MyOrderModel e : collection) {
            System.out.printf("ID: %s\n", e.getId());
        }
    }

    @ShellMethod(value = "Makes a table from all orders from given customer", key = "orT")
    public void orTable(String name) {
        PagedModel<MyOrderModel> model = myOrderResource.readByCustomerName(name, 0, 200000);
        Collection<MyOrderModel> collection = model.getContent();
        String[][] data = new String[collection.size() + 2][5];
        data[0] = HEADER_ROW;
        data[1] = new String[]{"=====", "====================", "====================", "====================", "========================="};
        int i = 2;
        for (MyOrderModel e : collection) {
            data[i][0] = e.getId().toString();
            data[i][1] = e.getDateFrom().toString();
            data[i][2] = e.getDateTo().toString();
            data[i][3] = e.getCustomerId().toString();
            data[i][4] = e.getEquipmentIds().toString();
            i++;
        }
        TableModel tableModel = new ArrayTableModel(data);
        TableBuilder tableBuilder = new TableBuilder(tableModel);
        tableBuilder.addOutlineBorder(BorderStyle.fancy_double);
        System.out.printf("%s", tableBuilder.build().render(2000));
    }

    @ShellMethod(value = "Read myOrder by customer ID", key = "orTS")
    public void orReadTypeAndSize(Long id, int page, int size) {
        PagedModel<MyOrderModel> model = myOrderResource.readByCustomerId(id.toString(), page, size);
        Collection<MyOrderModel> collection = model.getContent();
        for (MyOrderModel e : collection) {
            System.out.printf("ID: %s\n", e.getId());
        }
    }

    @ShellMethod(value = "Delete myOrder", key = "orU")
    public void orUpdate(Long id, Long from, Long to, Long cId) {
        try {
            Scanner in = new Scanner(System.in);
            List<Long> eIDs = new ArrayList<Long>();
            System.out.println("Enter equipment IDs: \n");
            while (in.hasNextLong()) {
                eIDs.add(in.nextLong());
            }
            Date f = new Date(from);
            Date t = new Date(to);
            MyOrderModel dto = new MyOrderModel(id, f, t, cId, eIDs);
            myOrderResource.update(dto);
            System.out.println("Success");
        } catch (HttpClientErrorException.BadRequest e) {
            System.out.println("400");
        }
    }

    @ShellMethod(value = "Delete myOrder", key = "orD")
    public void orDelete(Long id) {
        try {
            myOrderResource.delete(id.toString());
            System.out.println("Success");
        } catch (HttpClientErrorException.BadRequest e) {
            System.out.println("400");
        }
    }
}
