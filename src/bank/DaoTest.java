package bank;

import bank.dao.CustomerDAO;
import bank.model.Customer;

public class DaoTest {
    public static void main(String[] args) {
        CustomerDAO dao = new CustomerDAO();
        try {
            // CREATE
            int id = dao.insert(new Customer("Neo", "Kgosi", "Plot 12, Gaborone"));
            System.out.println("1. Created customer with id " + id);

            // READ
            Customer found = dao.findById(id);
            System.out.println("2. Found: " + found);

            // UPDATE
            dao.updateAddress(id, "Plot 99, Francistown");
            System.out.println("3. After update: " + dao.findById(id));

            // READ ALL
            System.out.println("4. All customers:");
            for (String line : dao.findAll()) {
                System.out.println("   " + line);
            }

            // DELETE
            dao.delete(id);
            System.out.println("5. After delete: " + dao.findById(id));
        } catch (Exception e) {
            System.out.println("Test failed: " + e.getMessage());
        }
    }
}
