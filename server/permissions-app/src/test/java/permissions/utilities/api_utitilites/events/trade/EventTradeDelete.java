package permissions.utilities.api_utitilites.events.trade;

import permissions.utilities.api_utitilites.pojo.PojoDetails;

import java.util.Map;

import static permissions.utilities.api_utitilites.constants.Trade.TRADE_ID;

public class EventTradeDelete extends EventTradeInsert {

    public EventTradeDelete(Map<String, String> tradeDetails) {
        super(tradeDetails);
        super.setEndPoint("EVENT_TRADE_DELETE");
    }

    @Override
    public PojoDetails getBody() {
        this.getPojoBody().setTradeId(TRADE_ID);
        super.getBody();
        return super.getPojoDetails();
    }
}
