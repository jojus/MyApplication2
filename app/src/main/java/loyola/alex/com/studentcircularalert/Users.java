package loyola.alex.com.studentcircularalert;

/**
 * Created by Justin Joy (jojus) on 20-10-2017.
 */

public class Users {

    String userId, fullName, userName, emailId, password, mobileNumber, department;

    public Users() {
    }

    Users(String userId, String fullName, String userName, String emailId, String password,
            String mobileNumber,
            String department) {
        this.userId = userId;
        this.fullName = fullName;
        this.userName = userName;
        this.emailId = emailId;
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.department = department;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}

