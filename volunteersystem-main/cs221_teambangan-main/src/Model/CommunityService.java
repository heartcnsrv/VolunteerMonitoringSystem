package Model;

public class CommunityService {
    private int serviceID;
    private int orgID;
    private String name;
    private String type;
    private String description;
    private String category;

    public CommunityService(int serviceID, String name, String type, String description) {
        this.serviceID = serviceID;
        this.orgID = orgID;
        this.name = name;
        this.type = type;
        this.description = description;
        this.category = category;
    }

    public CommunityService() {}

    public int getServiceID() {
        return serviceID;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

}
