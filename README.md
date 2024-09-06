# Permissions

This project has been created from the Genesis Blank Application Seed. Our seeds allow users to quickly bootstrap
their projects. Each seed adheres to strict Genesis best practices, and has passed numerous performance, compliance and
accessibility checks. 

Example app for authorisation

# Introduction

_Throughout this application please search for_ AUTH-COMMENT _to find appropriate points of authorisation code_

This application is designed to demonstrate the capabilities of the authorisation modules within Genesis.  It is set up to 
be as simple as possible, focussing on only the authorisation elements,
Following a build and remap the back end automatically loads 10 trades into a database entity and sets up three users:
1. AmyAccess - Amy is a member of the profile "Full_Access".  Amy has access to both Canada and UK trades
2. RogerRestricted - Roger is a member of the profile "Restricted_Access".  Roger is only able to view UK trades
3. LarryLondon - Larry is a member of "Booking_Access" and is able to view and enter trades for the UK only

There is also an admin user for the creation of further profiles.
The password for all users is "genesis"

The single table has four data servers operating against it, each with different authorisations demonstrated:
1. Entity level
2. Row level
3. Column level
4. Row and Column level

Please view the dataserver.kts to see this in code

The core of the front end consists of two tabs:
1. Trades - UI Auth Implemented
2. Trades - Without UI Auth Implemented

The former has authorisation applied throughout the front end, ensuring only operable components and views are present
(ie buttons are removed if associated permission does not exist).  Both the front end and the back are protected

The latter has no front end authorisation included.  This then relies upon the back end to provide the appropriate controls
This means many of the operations will return error messages where a user does not have the right level of permission

Both are included to show the experience to the end user depending on how the authorisation is implemented.  Of course,
it is vital to focus on the server level first to ensure an application is appropriate protected


The front end has a static reference page, where countries can be added, alongside assigning which countries a user can access
This is controlled, at both data server and event handler through grouped permissions
This is where the permission is applied at the overall data server and the overall event handler.  By doing this the
individual queries and events do not need explicit permissions.  However, permissions could be overridden if desired, by
specifying the right code in the specific query - this will take priority over the overall data server or event handler.
This can be seen in the permissions-admin-dataserver.kts and the permissions-admin-eventhandler.kts
The RIGHT_CODE used is StaticUpdate - this has been granted to the USER_ADMIN profile


Data entry is controlled in the event handler files.   The RIGHT_CODE TradeUpdate is required to allow inserts.  In this
specific application, given the customer name is not null, only users with full access and trade update can save trades
(otherwise a validation error is thrown).  It would be possible to make this nullable, to allow saving without that field
populated.  Entries are check for country level permissions (row level) in the event handlers as part of the authorisation


Note tha the Request Reply (reqrep) files have also been updated to show teh same level of authorisation, with the reqreps
prefixed with "RR_".  These are back end only to show teh code and support automated testing.


## Next Steps

To get a simple application running check the [Quick Start](https://docs.genesis.global/docs/develop/development-environments/) guide.

If you need an introduction to the Genesis platform and its modules it's worth heading [here](https://docs.genesis.global/docs/develop/platform-overview/).


## Project Structure

This project contains **server** and **client** directories which contain the server and client code respectively.

### Server

The server code for this project can be found [here](./server/README.md).
It is built using a DSL-like definition based on the Kotlin language: GPAL.

When first opening the project, if you receive a notification from IntelliJ IDE detecting Gradle project select the option to 'Load as gradle project'.

### Web Client

The Web client for this project can be found [here](./client/README.md). It is built using Genesis's next
generation web development framework, which is based on Web Components.

# License

This is free and unencumbered software released into the public domain. For full terms, see [LICENSE](./LICENSE)

**NOTE** This project uses licensed components listed in the next section, thus licenses for those components are required during development.

## Licensed components
Genesis low-code platform
# Test results
BDD test results can be found [here](https://genesiscommunitysuccess.github.io/howto-permissions/test-results)
