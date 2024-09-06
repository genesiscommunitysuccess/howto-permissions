import type { AppRoute } from '@genesislcap/foundation-shell/app';

/**
 * Main route
 * @public
 */
export const users: AppRoute = {
    title: 'users',
    path: 'users',
    name: 'users',
    element: async () => (await import('@genesislcap/pbc-auth-ui')).Users,
    settings: { autoAuth: true, maxRows: 500 },
    navItems: [
        {
          navId: 'header',
          title: 'User Management',
          // AUTH-COMMENT
          // The below restricts visibility of this tab button to people with the below right code
          // Note it does not restrict access to the tab - this could be accessed by specifically
          // addressing  the tab in the address bar
          permission: 'INSERT_USER',
        },
    ],
};

export const profiles: AppRoute = {
    title: 'profiles',
    path: 'profiles',
    name: 'profiles',
    element: async () => (await import('@genesislcap/pbc-auth-ui')).Profiles,
    settings: { autoAuth: true, maxRows: 500 },
    navItems: [
        {
          navId: 'header',
          title: 'Profiles',
          // AUTH-COMMENT
          // The below restricts visibility of this tab button to people with the below right code
          // Note it does not restrict access to the tab - this could be accessed by specifically
          // addressing  the tab in the address bar
          permission: 'INSERT_PROFILE'
        },
    ],
};
