package src.ClassFolder;
public class Member {

    private String memberId;
    private String name;
    private String phone;
    private String email;
    private String registerDate;

    Member(String memberId, String name, String phone, String email, String registerDate) {
        this.memberId = memberId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.registerDate = registerDate;
    }

    // getters
    String getMemberId() {
        return memberId;
    }

    String getName() {
        return name;
    }

    String getPhone() {
        return phone;
    }

    String getEmail() {
        return email;
    }

    String getRegisterDate() {
        return registerDate;
    }

    String displayInfo() {
        return "Member ID: " + memberId
                + "\nName: " + name
                + "\nPhone: " + phone
                + "\nEmail: " + email
                + "\nRegistered: " + registerDate;
    }
}
