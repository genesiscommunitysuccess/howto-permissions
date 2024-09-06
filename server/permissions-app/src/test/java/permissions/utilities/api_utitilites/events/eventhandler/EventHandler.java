package permissions.utilities.api_utitilites.events.eventhandler;

import io.restassured.response.Response;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import permissions.utilities.api_utitilites.dataserver.DataServer;
import permissions.utilities.api_utitilites.events.trade.EventTradeDelete;
import permissions.utilities.api_utitilites.events.trade.EventTradeInsert;
import permissions.utilities.api_utitilites.events.trade.EventTradeModify;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static permissions.utilities.api_utitilites.constants.Trade.TRADE_ID;

public class EventHandler {

    @Contract(pure = true)
    public static Response handleEvent(@NotNull String event, Map<String, String> tradeDetails) {
        final Response response;
        return switch (event) {
            case "EVENT_TRADE_INSERT" -> {
                EventTradeInsert eventTradeInsert = new EventTradeInsert(tradeDetails);
                response = eventTradeInsert.post();
                TRADE_ID = response.jsonPath().getString("GENERATED[0].TRADE_ID");
                yield response;
            }
            case "EVENT_TRADE_MODIFY" -> {
                EventTradeModify eventTradeModify = new EventTradeModify(tradeDetails);
                yield eventTradeModify.post();
            }
            case "EVENT_TRADE_DELETE" -> {
                EventTradeDelete eventTradeDelete = new EventTradeDelete(tradeDetails);
                yield eventTradeDelete.post();
            }
            default -> throw new IllegalStateException("Unexpected value: " + event);
        };
    }

    public static void verifyEvent(@NotNull String event) {
        if (event.contains("TRADE")) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            final String criteriaMatch = "TRADE_ID=='" + TRADE_ID + "'";
            final DataServer dataServer = new DataServer("ALL_TRADES_1", criteriaMatch);
            final List<Map<String, Object>> actual = dataServer.post().jsonPath().getList("ROW");
            switch (event) {
                case "EVENT_TRADE_INSERT":
                case "EVENT_TRADE_MODIFY":
                    assertEquals(TRADE_ID, actual.get(0).get("TRADE_ID"));
                    break;
                case "EVENT_TRADE_DELETE":
                    assertNull(actual);
                    break;
            }
        } else {
            System.out.println("Not a trade event");
        }
    }
}
