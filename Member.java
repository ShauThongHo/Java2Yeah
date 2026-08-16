import java.time.LocalDate;

/**
 * Member: a reader's personal profile in the library system.
 *
 * This is a purely USER-FACING feature. There are no admin roles and no
 * member management console - a reader only ever sees their own profile.
 *
 * The profile stores:
 *   - memberId:     auto-generated ID, e.g. M001
 *   - name:         the reader's name (used to link their borrow records)
 *   - phone:        optional contact number
 *   - email:        optional contact email
 *   - registerDate: the day the profile was created, e.g. 2026-08-15
 */
class Member {

    private String memberId;
    private String name;
    private String phone;
    private String email;
    private String registerDate;

    public Member(String memberId, String name, String phone, String email, String registerDate) {
        this.memberId = memberId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.registerDate = registerDate;
    }

    // getters
    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public String displayInfo() {
        return "Member ID: " + memberId
                + "\nName: " + name
                + "\nPhone: " + phone
                + "\nEmail: " + email
                + "\nRegistered: " + registerDate;
    }
}
