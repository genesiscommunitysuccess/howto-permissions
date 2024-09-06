/**
  * This file defines the data server queries for the application. Data server
  * will load the data defined here and expose APIs for the clients including
  * Genesis UI Components to present this data as well as keep it up to date as
  * the data set changes underneath.
  *
  * Data server queries also allow for the definition of dynamic columns as well
  * as rich access controls definitions.

  * Full documentation on dataserver may be found here >> https://docs.genesis.global/docs/develop/server-capabilities/real-time-queries-data-server/

 */

dataServer {

  // AUTH-COMMENT
  // Below are four data servers, each looking at the same table, but with different authorisation models implemented

  // AUTH-COMMENT
  // #1 Table level authorisation
  query("ALL_TRADES_1", TRADE) {
    permissioning {
      // AUTH-COMMENT
      // The below will force people to have this RIGHT_CODE to access this data server query
      // N.B. this can be a list of RIGHT_CODE's
      permissionCodes = listOf("TradeViewFull")
    }
  }

  // AUTH-COMMENT
  // #2 Row level authorisation
  query("ALL_TRADES_2", TRADE) {
    permissioning {
      // AUTH-COMMENT
      // The below will force people to have this RIGHT_CODE to access this data server query
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
  query("ALL_TRADES_3", TRADE) {
    permissioning {
      // AUTH-COMMENT
      // The below will force people to have this RIGHT_CODE to access this data server query
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
  query("ALL_TRADES_4", TRADE) {
    permissioning {
      // AUTH-COMMENT
      // The below will force people to have this RIGHT_CODE to access this data server query
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


  query("ALL_COUNTRYS", COUNTRY) {
    permissioning {
      // AUTH-COMMENT
      // The below will force people to have this RIGHT_CODE to access this data server query
      // N.B. this can be a list of RIGHT_CODE's
      permissionCodes = listOf("TradeUpdate")
      // AUTH-COMMENT
      // The below will force a mapping to the COUNTRY_VISIBILITY that is defined for authorisation purposes in  <appname>-permissions.kts
      auth(mapName = "COUNTRY_VISIBILITY") {
        authKeyWithUserName {
          key(data.countryName, userName)
        }
      }
    }
  }

  //TODO - add new queries or update existing queries to add authorization
  /**
    * You can add derived field like below
    * derivedField("FIELD_NAME", BOOLEAN) {
    *                 THIS_ENTITY.ATTR1 == "USD"
    *             }
    * You can add permission to the query by using permission codes like below
    * permissioning {
    *     // 'permission Code' list, users must have the permission to access the enclosing resource
    *     permissionCodes = listOf("PERMISSION1", "PERMISSION2")
    * }
    * Full documentation on permissions may be found here https://docs.genesis.global/docs/develop/server-capabilities/access-control/authorization/  */

}
