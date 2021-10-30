package Chop.Model;

public class Customer {
    private Integer id;
    private String name;
    private String user;
    private Integer point;
    private Integer right;
    private String tel;
    private Boolean status;
    private String pass;

    public Customer() {}
    public Customer(String user) {
        this.user = user;
    }
    public Customer(Integer id, String name, String user, String tel, String pass, Boolean status, Integer point, Integer right) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.tel = tel;
        this.pass = pass;
        this.status = status;
        this.point = point;
        this.right = right;
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getUser() {
        return user;
    }
    public Integer getPoint() {
        return point;
    }
    public Integer getRight() {
        return right;
    }
    public String getTel() {
        return tel;
    }
    public Boolean getStatus() {
        return status;
    }
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    public void setStatus(Boolean status) {
        this.status = status;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public void setRight(Integer right) {
        this.right = right;
    }
    public void setPoint(Integer point) {
        this.point = point;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setId(Integer id) {
        this.id = id;
    }
}
