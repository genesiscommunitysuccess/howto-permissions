package permissions.utilities.api_utitilites.events.trade;

import permissions.utilities.api_utitilites.events.event_base.EventBase;
import permissions.utilities.api_utitilites.pojo.PojoDetails;

import java.util.Map;

import static permissions.utilities.functions.DateFunctions.todayDateInEpoch;

public class EventTradeInsert extends EventBase {
    protected Map<String, String> tradeDetails;

    public EventTradeInsert(Map<String, String> tradeDetails) {
        this.tradeDetails = tradeDetails;
        super.setEndPoint("EVENT_TRADE_INSERT");
    }

    @Override
    public PojoDetails getBody() {
        super.getPojoBody().setVersion(Integer.valueOf(tradeDetails.get("VERSION")));
        super.getPojoBody().setCountryName(tradeDetails.get("COUNTRY_NAME"));
        super.getPojoBody().setSide(tradeDetails.get("SIDE"));
        super.getPojoBody().setRate(Double.valueOf(tradeDetails.get("RATE")));
        super.getPojoBody().setNotional(Double.valueOf(tradeDetails.get("NOTIONAL")));
        super.getPojoBody().setSettlementDate(todayDateInEpoch());
        super.getPojoBody().setSourceCurrency(tradeDetails.get("SOURCE_CURRENCY"));
        super.getPojoBody().setTargetCurrency(tradeDetails.get("TARGET_CURRENCY"));
        super.getPojoBody().setCustomerId(Integer.valueOf(tradeDetails.get("CUSTOMER_ID")));
        super.getPojoBody().setCustomerName(tradeDetails.get("CUSTOMER_NAME"));
        super.getPojoDetails().setPojoBody(super.getPojoBody());
        return super.getPojoDetails();
    }
}
