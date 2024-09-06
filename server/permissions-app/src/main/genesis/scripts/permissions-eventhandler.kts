import global.genesis.db.DbRecord
import global.genesis.db.rx.entity.multi.SyncEntityDb

/**
  * This file defines the event handler APIs. These APIs (modeled after CQRS
  * commands) allow callers to manipulate the data in the database. By default,
  * insert, update and delete event handlers (or commands) have been created.
  * These result in the data being written to the database and messages to be
  * published for the rest of the platform to by notified.
  * 
  * Custom event handlers may be added to extend the functionality of the
  * application as well as custom logic added to existing event handlers.
  *
  * The following objects are visible in each eventhandler
  * `event.details` which holds the entity that this event handler is acting upon
  * `entityDb` which is database object used to perform INSERT, MODIFY and UPDATE the records
  * Full documentation on event handler may be found here >> https://docs.genesis.global/docs/develop/server-capabilities/core-business-logic-event-handler/

 */

eventHandler {
  // AUTH-COMMENT
  // Trade Modify and Trade Delete events authorisation checks require access to the existing trade record in database.
  // We can use this synchronous DB object to perform database lookups within relevant configuration blocks.
  val syncDb = inject<SyncEntityDb>()

  eventHandler<Trade>("TRADE_INSERT", transactional = true) {
    // AUTH-COMMENT
    // A user must have the RIGHT CODE "TradeUpdate" to call this event
    // Without this call will be rejected
    permissioning {
      permissionCodes = listOf("TradeUpdate")
      // AUTH-COMMENT
      // A user must have authorisation for the country they are inserting a transaction against
      // This is applying row level insert authorisation
      auth(mapName = "COUNTRY_VISIBILITY") {
        authKeyWithUserName {
          key(data.countryName, userName)
        }
      }
    }
    onCommit { event ->
      val details = event.details
      // AUTH-COMMENT
      // If a user cannot see the Customer Name column they should not be able to insert this value so set to blank
      // Cannot think of a viable business use case for this, but showing the code for reference
      if (!userHasRight(event.userName, "TradeViewFull")) {
        details.customerName = ""
      }
      val insertedRow = entityDb.insert(details)
      // return an ack response which contains a list of record IDs
      ack(listOf(mapOf(
        "TRADE_ID" to insertedRow.record.tradeId,
      )))
    }
  }
  eventHandler<Trade>("TRADE_MODIFY", transactional = true) {
    // AUTH-COMMENT
    // A user must have the RIGHT CODE "TradeUpdate" to call this event
    // Without this call will be rejected
    permissioning {
      permissionCodes = listOf("TradeUpdate")
      // AUTH-COMMENT
      // A user must have authorisation for the country they are inserting a transaction against
      // This is applying row level insert authorisation.
      // Both the existing database record as well as the update to apply must have correct authorisation.
      auth(mapName = "COUNTRY_VISIBILITY") {
        authKeyWithUserName {
          val existingTrade= syncDb.get(Trade.byId(data.tradeId))
          key(existingTrade?.countryName, userName)
        }
      } and auth(mapName = "COUNTRY_VISIBILITY") {
        authKeyWithUserName {
          key(data.countryName, userName)
        }
      }
    }
    // AUTH-COMMENT
    //This verifies prior version of trade exists in the database - otherwise reject
    onValidate { event ->
      verify {
        entityDb hasEntry Trade.ById(event.details.tradeId)
      }
      ack()
    }
    onCommit { event ->
      val details = event.details
      // AUTH-COMMENT
      // If a user cannot see the Customer Name column they should not be able to edit this value
      // so ensure set to value in prior version - ie it is not changed
      if (!userHasRight(event.userName, "TradeViewFull")) {
        val trade = entityDb.get(Trade.ById(event.details.tradeId))!!  //The two !! are stating "we know there is a trade" as we checked in onValidate
        details.customerName = trade.customerName
      }
      entityDb.modify(details)
      ack()
    }
  }
  eventHandler<Trade>("TRADE_DELETE", transactional = true) {
    // AUTH-COMMENT
    // A user must have the RIGHT CODE "TradeUpdate" to call this event
    // Without this call will be rejected
    permissioning {
      permissionCodes = listOf("TradeUpdate")
      // AUTH-COMMENT
      // A user must have authorisation for the country they are deleting a transaction against
      // This is applying row level insert authorisation
      auth(mapName = "COUNTRY_VISIBILITY") {
        authKeyWithUserName {
          val existingTrade= syncDb.get(Trade.byId(data.tradeId))
          key(existingTrade?.countryName, userName)
        }
      }
    }
    onCommit { event ->
      val details = event.details
      entityDb.delete(details)
      ack()
    }
  }

  //TODO - add new or customize event handlers to add validation, access permission checks
  /**
    * If you want to provide some validation before the action, you need to have an onValidate block before the onCommit. The last value of the code block must always be the return message type.
    * eventHandler<THIS_ENTITY>(name = "THIS_ENTITY_INSERT") {
    *      onValidate { event ->
    *          val thisEntity = event.details
    *          require(thisEntity.name != null) { "ThisEntity should have a name" }
    *          ack()
    *      }
    *      onCommit { event ->
    *          ...
    *      }
    *  }
    * You can add permission to the query by using permission codes like below
    * permissioning {
    *     // 'permission Code' list, users must have the permission to access the enclosing resource
    *     permissionCodes = listOf("PERMISSION1", "PERMISSION2")
    * }
    * Full documentation on permissions may be found here https://docs.genesis.global/docs/develop/server-capabilities/access-control/authorization/
    */
}
