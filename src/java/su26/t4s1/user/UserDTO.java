
package su26.t4s1.user;


public class UserDTO {

    private String uID;
    private String password;
    private String roleID;
    private String name;
    private String email;
    private boolean status;

    public UserDTO(String uID, String password, String roleID, String name, boolean status) {
        this.uID = uID;
        this.password = password;
        this.roleID = roleID;
        this.name = name;
        this.status = status;
    }
    
       public UserDTO(String uID, String password, String roleID, String name, boolean status , String email) {
        this.uID = uID;
        this.password = password;
        this.roleID = roleID;
        this.name = name;
        this.status = status;
        this.email = email;
    }

    public UserDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
