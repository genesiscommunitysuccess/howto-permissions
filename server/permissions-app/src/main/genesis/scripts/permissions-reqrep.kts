import global.genesis.gen.config.tables.TRADE.CUSTOMER_NAME

/**
  * This file defines request-replies of the application. 
  * Request-Replies provide snapshot data from a table or view in response to a request from the front end.
  * Once the response is received, the transaction is over (unlike a Data Server, which stays connected to the client and pushes updates)

  * Full documentation on request server may be found here >> https://docs.genesis.global/docs/develop/server-capabilities/snapshot-queries-request-server/

 */

requestReplies {

  // AUTH-COMMENT
  // Below are four request replies, each looking at the same table, but with different authorisation models implemented
  // Note these are a repeat of the dataserver ones, with the names prefixed with "RR_"
  // These are not called by the front end, but are here for completeness and test case coverage

  // AUTH-COMMENT
  // #1 Table level authorisation
  requestReply("RR_ALL_TRADES_1", TRADE) {
    permissioning {
      // AUTH-COMMENT
      // The below will force people to have this RIGHT_CODE to access this request reply query
      // N.B. this can be a list of RIGHT_CODE's
      permissionCodes = listOf("TradeViewFull")
    }
  }

  // AUTH-COMMENT
  // #2 Row level authorisation
  requestReply("RR_ALL_TRADES_2", TRADE) {
    permissioning {
      // AUTH-COMMENT
      // The below will force people to have this RIGHT_CODE to access this request reply query
      // N.B. this can be a list of RIGHT_CODE's
      permissionCodes = listOf("TradeViewRestricted")
      // AUTH-COMMENT
      // The below will force a mapping to the COUNTRY_VISIBILITY that is defined for authorisation purposes in  <appname>-permissions.kts
      auth(mapName = "COUNTRY_VISIBILITY") {
        authKeyWithUserName {
          key(data.countryName, userName)
        }
      }
    }
  }

  // AUTH-COMMENT
  // #3 Column level authorisation
  requestReply("RR_ALL_TRADES_3", TRADE) {
    permissioning {
      // AUTH-COMMENT
      // The below will force people to have this RIGHT_CODE to access this request reply query
      // N.B. this can be a list of RIGHT_CODE's
      permissionCodes = listOf("TradeViewRestricted")
      auth{
        // AUTH-COMMENT
        // The below will check RIGHT_CODE's to hide columns at run time, enabling column specific authorisation
        hideFields { userName, rowData ->
          if (!userHasRight(userName, "TradeViewFull")) listOf(CUSTOMER_NAME)
          else emptyList()
        }
      }
    }
  }

  // AUTH-COMMENT
  // #4 Row and column level authorisation
  requestReply("RR_ALL_TRADES_4", TRADE) {
    permissioning {
      // AUTH-COMMENT
      // The below will force people to have this RIGHT_CODE to access this request reply query
      // N.B. this can be a list of RIGHT_CODE's
      permissionCodes = listOf("TradeViewRestricted")
      // AUTH-COMMENT
      // The below will force a mapping to the COUNTRY_VISIBILITY that is defined for authorisation purposes in the
      // file scripts\<appname>-permissions.kts (same directory as this file)
      // in this app it is called permissions-permissions.kts
      auth(mapName = "COUNTRY_VISIBILITY") {
        authKeyWithUserName {
          key(data.countryName, userName)
        }
      }
      auth{
        // AUTH-COMMENT
        // The below will check RIGHT_CODE's to hide columns at run time, enabling column specific authorisation
        hideFields { userName, rowData ->
          if (!userHasRight(userName, "TradeViewFull")) listOf(CUSTOMER_NAME)
          else emptyList()
        }
      }
    }
  }
  //TODO 4.a Add authorisation, where clauses, indices and restrict fields on the generated request server queries as required by the application.
  //TODO 4.b Add further request server queries (including custom request servers) as needed by the application here. See the comments at the top of this file for learning and guidance.
}
