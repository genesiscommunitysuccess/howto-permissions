package permissions.utilities.api_utitilites.dataserver;

import permissions.utilities.api_utitilites.events.event_base.EventBase;
import permissions.utilities.api_utitilites.pojo.PojoDetails;

public class DataServer extends EventBase {
    private String criteriaMatch;

    public DataServer(String endpoint) {
        super.setEndPoint(endpoint);
    }

    public DataServer(String endpoint, String criteriaMatch) {
        super.setEndPoint(endpoint);
        this.criteriaMatch = criteriaMatch;
    }

    public String getCriteriaMatch() {
        return criteriaMatch;
    }

    public void setCriteriaMatch(String criteriaMatch) {
        this.criteriaMatch = criteriaMatch;
    }

    @Override
    public PojoDetails getBody() {
        this.getPojoBody().setCriteriaMatch(this.criteriaMatch);
        this.getPojoDetails().setPojoBody(this.getPojoBody());
        return this.getPojoDetails();
    }
}