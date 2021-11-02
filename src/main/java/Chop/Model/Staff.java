package Chop.Model;

public class Staff {
    private Integer id;
    private String name;
    private String lastname;
    private String tel;
    private String user;
    private String pass;
    private String role;

    public Staff() {}
    public Staff(Integer id,
                 String name,
                 String lastname,
                 String tel,
                 String user,
                 String pass,
                 String role) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.tel = tel;
        this.pass = pass;
        this.lastname = lastname;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getLastname() {
        return lastname;
    }
    public String getTel() {
        return tel;
    }
    public String getUser() {
        return user;
    }
    public String getPass() {
        return pass;
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setId(Integer id) {
        this.id = id;
    }
}
