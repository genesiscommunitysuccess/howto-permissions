Feature: Permissions
#  ALL_TRADES_1 will return all the items in the Table Level with TradeViewFull permission
#  ALL_TRADES_2 will return all the items in the Row Level with TradeViewRestricted permission and COUNTRY_VISIBILITY
#  ALL_TRADES_3 will return all the items in the Column Level with TradeViewRestricted permission
#  ALL_TRADES_4 will return all the items in the Row and Column Level with TradeViewRestricted permission

  @Permissions
    @AmyAccess
    @API
  Scenario Outline: Login with AmyAccess and retrieve trades
    Given User connect with username "AmyAccess" and password "genesis"
    And a Dataserver script "permissions-dataserver.kts" with a query named "<endpoints>"
    When User sends request to dataserver gets response body "<endpoints>"
    Then User compares it to expected "<result>", "<PRIMARY_KEY>" and, "<KEYS_TO_IGNORE>"
    Examples:
      | endpoints    | result                                                                  | PRIMARY_KEY | KEYS_TO_IGNORE   |
      | ALL_TRADES_1 | src/test/resources/result/AmyAccess/expected/ALL_TRADES_1_expected.json | CUSTOMER_ID | DETAILS,TRADE_ID |
      | ALL_TRADES_2 | src/test/resources/result/AmyAccess/expected/ALL_TRADES_2_expected.json | CUSTOMER_ID | DETAILS,TRADE_ID |
      | ALL_TRADES_3 | src/test/resources/result/AmyAccess/expected/ALL_TRADES_3_expected.json | CUSTOMER_ID | DETAILS,TRADE_ID |
      | ALL_TRADES_4 | src/test/resources/result/AmyAccess/expected/ALL_TRADES_4_expected.json | CUSTOMER_ID | DETAILS,TRADE_ID |

  @Permissions
    @RogerRestricted
    @API
  Scenario Outline: Login with RogerRestricted and retrieve trades
    Given User connect with username "RogerRestricted" and password "genesis"
    And a Dataserver script "permissions-dataserver.kts" with a query named "<endpoints>"
    When User sends request to dataserver gets response body "<endpoints>"
    Then User compares it to expected "<result>", "<PRIMARY_KEY>" and, "<KEYS_TO_IGNORE>"
    Examples:
      | endpoints    | result                                                                        | PRIMARY_KEY | KEYS_TO_IGNORE   |
      | ALL_TRADES_1 | src/test/resources/result/RogerRestricted/expected/ALL_TRADES_1_expected.json | CUSTOMER_ID | DETAILS,TRADE_ID |
      | ALL_TRADES_2 | src/test/resources/result/RogerRestricted/expected/ALL_TRADES_2_expected.json | CUSTOMER_ID | DETAILS,TRADE_ID |
      | ALL_TRADES_3 | src/test/resources/result/RogerRestricted/expected/ALL_TRADES_3_expected.json | CUSTOMER_ID | DETAILS,TRADE_ID |
      | ALL_TRADES_4 | src/test/resources/result/RogerRestricted/expected/ALL_TRADES_4_expected.json | CUSTOMER_ID | DETAILS,TRADE_ID |

  @Permissions
    @AmyAccess
    @API
  Scenario Outline: Login with AmyAccess and Execute Event Trade
    Given User connect with username "AmyAccess" and password "genesis"
    And an EventHandler script "permissions-eventhandler.kts" with an event of type "Trade" named "<events>"
    And User send the event "<events>"
      | VERSION | COUNTRY_NAME | SIDE | RATE | NOTIONAL | SOURCE_CURRENCY | TARGET_CURRENCY | CUSTOMER_ID | CUSTOMER_NAME  | EVENT              |
      | 1       | Canada       | Buy  | 1.23 | 100.000  | GBP             | EUR             | 11          | AutomationUser | EVENT_TRADE_INSERT |
      | 1       | Canada       | Sell | 1.24 | 125.000  | USD             | TRY             | 12          | AutomationUser | EVENT_TRADE_MODIFY |
      | 1       | Canada       | Sell | 1.24 | 125.000  | USD             | TRY             | 12          | AutomationUser | EVENT_TRADE_DELETE |
    Then User verifies the event successfully "<events>" processed
    Examples:
      | events             |
      | EVENT_TRADE_INSERT |
      | EVENT_TRADE_MODIFY |
      | EVENT_TRADE_DELETE |

  @Permissions
    @AmyAccess
    @UI
  Scenario Outline: Login UI with AmyAccess and Insert/Modify Trades
    Given User enters username "AmyAccess" and password "genesis"
    When User click on the "<button>"
    And User fills the form with the trade details
      | VERSION | COUNTRY_NAME | SIDE | RATE | NOTIONAL  | SOURCE_CURRENCY | TARGET_CURRENCY | CUSTOMER_ID | CUSTOMER_NAME  |
      | 1       | Canada       | Buy  | 1.23 | 2,310,000 | USD             | EUR             | 1           | AutomationUser |
    When User click on the "Submit"
    Then User should see the trades
      | src/test/resources/result/AmyAccess/expected/trades-grid_expected.json |
    Examples:
      | button |
      | Add    |
      | Edit   |

  @Permissions
  @AmyAccess
  @UI
  Scenario: Login UI with AmyAccess and Delete Trades
    Given User enters username "AmyAccess" and password "genesis"
    When User click on the "Delete"
    When User click on the "Confirm"
    Then User should see the trades
      | src/test/resources/result/AmyAccess/expected/trades-grid_expected.json |

  @Permissions
    @RogerRestricted
    @UI
  Scenario Outline: Login UI with RogerRestricted and retrieve trades
    Given User enters username "RogerRestricted" and password "genesis"
    When User navigates to the "trades" page
    Then User shouldn't see the "<buttons>"
    Examples:
      | buttons |
      | Add     |
      | Edit    |
      | Delete  |

  @Permissions
    @ReqRep
    @API
  Scenario Outline: "<user>" executes <endpoints> ReqRep and <description>
    Given User connect with username "<user>" and password "genesis"
    And a RequestReply script "permissions-reqrep.kts" with a resource named "<endpoints>"
    When User sends request to "<endpoints>" with "<queryParam>"
    Then User compares it to expected "<result>", "<PRIMARY_KEY>" and, "<KEYS_TO_IGNORE>"
    Examples:
      | user            | endpoints           | queryParam | result                                                                               | description                             | PRIMARY_KEY | KEYS_TO_IGNORE   |
      | AmyAccess       | REQ_RR_ALL_TRADES_1 | VERSION=1  | src/test/resources/result/AmyAccess/expected/REQ_RR_ALL_TRADES_1_expected.json       | then receive all the trades             | CUSTOMER_ID | DETAILS,TRADE_ID |
      | AmyAccess       | REQ_RR_ALL_TRADES_2 | VERSION=1  | src/test/resources/result/AmyAccess/expected/REQ_RR_ALL_TRADES_2_expected.json       | then receive all the trades only Canada | CUSTOMER_ID | DETAILS,TRADE_ID |
      | AmyAccess       | REQ_RR_ALL_TRADES_3 | VERSION=1  | src/test/resources/result/AmyAccess/expected/REQ_RR_ALL_TRADES_3_expected.json       | then receive all the trades             | CUSTOMER_ID | DETAILS,TRADE_ID |
      | AmyAccess       | REQ_RR_ALL_TRADES_4 | VERSION=1  | src/test/resources/result/AmyAccess/expected/REQ_RR_ALL_TRADES_4_expected.json       | then receive all the trades only Canada | CUSTOMER_ID | DETAILS,TRADE_ID |
      | RogerRestricted | REQ_RR_ALL_TRADES_1 | VERSION=1  | src/test/resources/result/RogerRestricted/expected/REQ_RR_ALL_TRADES_1_expected.json | then won't receive any trades data      | CUSTOMER_ID | DETAILS,TRADE_ID |
      | RogerRestricted | REQ_RR_ALL_TRADES_2 | VERSION=1  | src/test/resources/result/RogerRestricted/expected/REQ_RR_ALL_TRADES_2_expected.json | then receive all the trades only UK     | CUSTOMER_ID | DETAILS,TRADE_ID |
      | RogerRestricted | REQ_RR_ALL_TRADES_3 | VERSION=1  | src/test/resources/result/RogerRestricted/expected/REQ_RR_ALL_TRADES_3_expected.json | then receive all the trades             | CUSTOMER_ID | DETAILS,TRADE_ID |
      | RogerRestricted | REQ_RR_ALL_TRADES_4 | VERSION=1  | src/test/resources/result/RogerRestricted/expected/REQ_RR_ALL_TRADES_4_expected.json | then receive all the trades only UK     | CUSTOMER_ID | DETAILS,TRADE_ID |