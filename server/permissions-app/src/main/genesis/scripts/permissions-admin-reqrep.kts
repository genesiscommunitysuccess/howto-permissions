
requestReplies {
    // AUTH-COMMENT
    // This is authorised at the request reply level.  This means that all specified request replies receive this
    // authorisation unless otherwise specified within the query
    // Note these are a repeat of the dataserver ones, with the names prefixed with "RR_"
    // These are not called by the front end, but are here for completeness and test case coverage
    permissioning {
        permissionCodes = listOf("StaticUpdate")
    }

    // AUTH-COMMENT
    // No authorisation is added to these request reply queries
    requestReply("RR_ALL_USER_COUNTRY_ACCESSS", USER_COUNTRY_ACCESS)
    requestReply("RR_ALL_COUNTRYS_ADMIN", COUNTRY)
}
