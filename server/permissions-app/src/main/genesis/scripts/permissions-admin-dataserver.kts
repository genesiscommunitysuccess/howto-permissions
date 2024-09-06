
dataServer {
    // AUTH-COMMENT
    // This is authorised at the data server level.  This means that all specified queries receive this
    // authorisation unless otherwise specified within the query
    permissioning {
        permissionCodes = listOf("StaticUpdate")
    }

    // AUTH-COMMENT
    // No authorisation is added to these two data server queries
    query("ALL_USER_COUNTRY_ACCESSS", USER_COUNTRY_ACCESS)
    query("ALL_COUNTRYS_ADMIN", COUNTRY)
}
