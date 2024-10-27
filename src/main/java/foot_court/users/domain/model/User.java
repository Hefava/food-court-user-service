package foot_court.users.domain.model;

import java.time.LocalDate;

public class User {
    private Long id;
    private String name;
    private String lastname;
    private String identificationnumber;
    private String phone;
    private LocalDate bornDate;
    private String email;
    private String password;
    private Long role;

    public User() {
    }

    public User(Long id, String name, String lastname, String identificationnumber, String phone, LocalDate bornDate, String email, String password, Long role) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.identificationnumber = identificationnumber;
        this.phone = phone;
        this.bornDate = bornDate;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getIdentificationnumber() {
        return identificationnumber;
    }

    public void setIdentificationnumber(String identificationnumber) {
        this.identificationnumber = identificationnumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBornDate() {
        return bornDate;
    }

    public void setBornDate(LocalDate bornDate) {
        this.bornDate = bornDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }
}