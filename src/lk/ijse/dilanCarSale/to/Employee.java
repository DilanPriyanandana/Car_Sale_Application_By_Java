package lk.ijse.dilanCarSale.to;

public class Employee {
    private String id;
    private String name;
    private String address;
    private int contact;
    private String email;
    private String nic;


    public Employee() {

    }

    public Employee(String id, String name, String address, int contact, String email, String nic) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.email = email;
        this.nic = nic;
    }



    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }



    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }



    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddress() {
        return address;
    }



    public void setContact(int contact) {
        this.contact = contact;
    }
    public int getContact() {
        return contact;
    }



    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }



    public void setNic(String nic) {
        this.nic = nic;
    }
    public String getNic() {
        return nic;
    }



    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact=" + contact +
                ", email='" + email + '\'' +
                ", nic='" + nic + '\'' +
                '}';
    }
}
