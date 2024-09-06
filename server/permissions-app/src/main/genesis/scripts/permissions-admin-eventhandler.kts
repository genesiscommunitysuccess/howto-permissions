
eventHandler {
    // AUTH-COMMENT
    // This is authorised at the event handler level.  This means that all specified events receive this
    // authorisation unless otherwise specified within the event
    permissioning {
        permissionCodes = listOf("StaticUpdate")
    }

    eventHandler<Country>("COUNTRY_INSERT", transactional = true) {
        // AUTH-COMMENT
        // No permissions added to this event
        onCommit { event ->
            val details = event.details
            entityDb.insert(details)
            ack()
        }
    }
    eventHandler<Country>("COUNTRY_MODIFY", transactional = true) {
        // AUTH-COMMENT
        // No permissions added to this event
        onCommit { event ->
            val details = event.details
            entityDb.modify(details)
            ack()
        }
    }
    eventHandler<Country.ByName>("COUNTRY_DELETE", transactional = true) {
        // AUTH-COMMENT
        // No permissions added to this event
        onCommit { event ->
            val details = event.details
            entityDb.delete(details)
            ack()
        }
    }
    eventHandler<UserCountryAccess>("USER_COUNTRY_ACCESS_INSERT", transactional = true) {
        // AUTH-COMMENT
        // No permissions added to this event
        onCommit { event ->
            val details = event.details
            entityDb.insert(details)
            ack()
        }
    }
    eventHandler<UserCountryAccess>("USER_COUNTRY_ACCESS_MODIFY", transactional = true) {
        // AUTH-COMMENT
        // No permissions added to this event
        onCommit { event ->
            val details = event.details
            entityDb.modify(details)
            ack()
        }
    }
    eventHandler<UserCountryAccess.ByCountryNameUserName>("USER_COUNTRY_ACCESS_DELETE", transactional = true) {
        // AUTH-COMMENT
        // No permissions added to this event
        onCommit { event ->
            val details = event.details
            entityDb.delete(details)
            ack()
        }
    }
}
