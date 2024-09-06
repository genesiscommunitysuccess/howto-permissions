package permissions.utilities.api_utitilites.events.authentication;

import permissions.utilities.api_utitilites.events.event_base.EventBase;
import permissions.utilities.api_utitilites.pojo.PojoDetails;

public class EventLoginAuth extends EventBase {
    private final String username;
    private final String password;

    public EventLoginAuth(String username, String password) {
        super.setEndPoint("EVENT_LOGIN_AUTH");
        this.username = username;
        this.password = password;
    }

    @Override
    public PojoDetails getBody() {
        super.getPojoBody().setUserName(username);
        super.getPojoBody().setPassword(password);
        super.getPojoDetails().setPojoBody(super.getPojoBody());
        return super.getPojoDetails();
    }
}