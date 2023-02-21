package lk.ijse.dilanCarSale.tm;

public class SupplierTM {
    private String supId;
    private String supName;
    private String supAddress;
    private int supContact;
    private String supEmail;
    private String supNic;

    public SupplierTM() {
    }

    public SupplierTM(String supId, String supName, String supAddress, int supContact, String supEmail, String supNic) {
        this.supId = supId;
        this.supName = supName;
        this.supAddress = supAddress;
        this.supContact = supContact;
        this.supEmail = supEmail;
        this.supNic = supNic;
    }

    public String getSupId() {
        return supId;
    }

    public void setSupId(String supId) {
        this.supId = supId;
    }

    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName;
    }

    public String getSupAddress() {
        return supAddress;
    }

    public void setSupAddress(String supAddress) {
        this.supAddress = supAddress;
    }

    public int getSupContact() {
        return supContact;
    }

    public void setSupContact(int supContact) {
        this.supContact = supContact;
    }

    public String getSupEmail() {
        return supEmail;
    }

    public void setSupEmail(String supEmail) {
        this.supEmail = supEmail;
    }

    public String getSupNic() {
        return supNic;
    }

    public void setSupNic(String supNic) {
        this.supNic = supNic;
    }

    @Override
    public String toString() {
        return "SupplierTM{" +
                "supId='" + supId + '\'' +
                ", supName='" + supName + '\'' +
                ", supAddress='" + supAddress + '\'' +
                ", supContact=" + supContact +
                ", supEmail='" + supEmail + '\'' +
                ", supNic='" + supNic + '\'' +
                '}';
    }
}
