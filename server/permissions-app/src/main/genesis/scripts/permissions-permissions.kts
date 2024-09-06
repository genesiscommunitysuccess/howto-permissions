package scripts

// AUTH-COMMENT
// This file creates a map of country permissions by user.  The map created is called
// COUNTRY_VISIBILITY and can be used from elsewhere in the application for checking authorisation
// The underlying data table (USER_COUNTRY_ACCESS) is managed like any other data table in the system, and
// can have its own authorisation applied to it to ensure this is managed correctly

dynamicPermissions {
    entity(USER_COUNTRY_ACCESS) {
        name = "COUNTRY_VISIBILITY"
        maxEntries = 10000
        batchingPeriod = 15
        idField = listOf(USER_COUNTRY_ACCESS.COUNTRY_NAME, USER_COUNTRY_ACCESS.USER_NAME) // Use primary key fields
        backwardsJoin = true
        expression {
            user.userName == entity.userName
        }
    }
}
