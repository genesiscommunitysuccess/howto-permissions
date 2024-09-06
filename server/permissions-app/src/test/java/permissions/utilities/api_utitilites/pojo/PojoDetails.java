package permissions.utilities.api_utitilites.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PojoDetails {
    @JsonProperty("DETAILS")
    private PojoBody pojoBody;

    public PojoDetails() {
        this.pojoBody = new PojoBody();
    }

    public PojoBody getPojoBody() {
        return pojoBody;
    }

    public void setPojoBody(PojoBody pojoBody) {
        this.pojoBody = pojoBody;
    }
}