package Model;

public class Organization {
    String orgName, contactInfo, address, password, website, missionStatement;
    int orgID;

    public Organization(String orgName, String contactInfo, String address, String password,
                        String website, String missionStatement, int orgID) {
        this.orgName = orgName;
        this.contactInfo = contactInfo;
        this.address = address;
        this.password = password;
        this.website = website;
        this.missionStatement = missionStatement;
        this.orgID = orgID;
    }

    public Organization() {

    }

    public String getOrgName() {
        return orgName;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public String getWebsite() {
        return website;
    }

    public String getMissionStatement() {
        return missionStatement;
    }

    public int getOrgID() {
        return orgID;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setMissionStatement(String missionStatement) {
        this.missionStatement = missionStatement;
    }

    public void setOrgID(int orgID) {
        this.orgID = orgID;
    }
}