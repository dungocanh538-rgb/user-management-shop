
package su26.t4s1.user;


public class UserError {

    private String uID;
    private String name;
    private String roleID;
    private String password;
    private boolean status;
    private String confirm;
    private String error;

    public UserError() {
        this.uID = "";
        this.name = "";
        this.roleID = "";
        this.password = "";
        this.status = true;
        this.confirm = "";
        this.error = "";
    }

    public UserError(String uID, String name, String roleID, String password, boolean status, String confirm, String error) {
        this.uID = uID;
        this.name = name;
        this.roleID = roleID;
        this.password = password;
        this.status = status;
        this.confirm = confirm;
        this.error = error;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
