/**
  * This file defines the entities (or tables) for the application.  
  * Entities aggregation a selection of the universe of fields defined in 
  * {app-name}-fields-dictionary.kts file into a business entity.  
  *
  * Note: indices defined here control the APIs available to the developer.
  * For example, if an entity requires lookup APIs by one or more of its attributes, 
  * be sure to define either a unique or non-unique index.

  * Full documentation on tables may be found here >> https://docs.genesis.global/docs/develop/server-capabilities/data-model/

 */

tables {
  table(name = "COUNTRY", id = 11_000, audit = details(id = 11_500, sequence = "EA")) {
    field("COUNTRY_NAME", STRING).notNull()

    primaryKey("COUNTRY_NAME")

  }
  table(name = "USER_COUNTRY_ACCESS", id = 11_001, audit = details(id = 11_501, sequence = "UE")) {
    field("COUNTRY_NAME", STRING).notNull()
    field("USER_NAME", STRING).notNull()

    primaryKey("COUNTRY_NAME","USER_NAME")

  }
  table(name = "TRADE", id = 11_002, audit = details(id = 11_502, sequence = "TA")) {
    field("TRADE_ID", STRING).sequence("TD")
    field("CUSTOMER_ID", INT).notNull()
    field("CUSTOMER_NAME", STRING).notNull()
    field("COUNTRY_NAME", STRING).notNull()
    field("NOTIONAL", DOUBLE).notNull()
    field("RATE", DOUBLE).notNull()
    field("SETTLEMENT_DATE", DATE).notNull()
    field("SIDE", ENUM("Sell","Buy")).default("Buy").notNull()
    field("SOURCE_CURRENCY", STRING).notNull()
    field("TARGET_CURRENCY", STRING).notNull()
    field("VERSION", INT).notNull()

    primaryKey("TRADE_ID")

  }

  // TODO 2. Add further tables you wish to add to the application here. See the comments at the top of this file for learning and guidance.
}
