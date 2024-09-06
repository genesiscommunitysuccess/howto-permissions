package permissions.utilities.api_utitilites.reqrep;

import org.jetbrains.annotations.NotNull;
import permissions.utilities.api_utitilites.events.event_base.EventBase;
import permissions.utilities.api_utitilites.pojo.PojoDetails;

public class ReqRep extends EventBase {
    /*
     * This class is used to create a request with query parameters
     * @param endpoint: The endpoint of the request
     * @param queryParam: The query parameters of the request. can accept multiple query parameters separated by "&"
     */
    public ReqRep(String endpoint, String queryParam) {
        super();
        super.setEndPoint(endpoint + "?" + setQueryParam(queryParam));
    }

    private @NotNull String setQueryParam(@NotNull String queryParam) {
        StringBuilder modifiedParams = new StringBuilder();
        if (queryParam.contains("&")) {
            String[] params = queryParam.split("&");
            for (String param : params) {
                if (!modifiedParams.isEmpty()) {
                    modifiedParams.append("&");
                }
                modifiedParams.append("REQUEST.").append(param);
            }
        } else {
            modifiedParams.append("REQUEST.").append(queryParam);
        }
        return modifiedParams.toString();
    }

    @Override
    public PojoDetails getBody() {
        return null;
    }
}