import { getUser, navigateTo } from '@genesislcap/foundation-auth';
import { Connect } from '@genesislcap/foundation-comms';
import { FoundationRouterConfiguration } from '@genesislcap/foundation-ui';
import { GENESIS_SOCKET_URL, PUBLIC_PATH } from '@genesislcap/foundation-utils';
import { defaultLayout, loginLayout } from '../layouts';
import { Trades } from './trades/trades';
import { TradesNoAuth } from './tradesNoAuth/trades';
import { Static } from './static/static';
import { NotFound } from './not-found/not-found';
import { defaultNotPermittedRoute, NotPermitted } from './not-permitted/not-permitted';
import { LoginSettings } from './types';

// eslint-disable-next-line
declare var ENABLE_SSO: string;

const ssoSettings =
  typeof ENABLE_SSO !== 'undefined' && ENABLE_SSO === 'true'
    ? {
        autoAuth: true,
        sso: {
          toggled: true,
          identityProvidersPath: 'gwf/sso/list',
        },
      }
    : {};

const publicPath = typeof PUBLIC_PATH !== 'undefined' ? PUBLIC_PATH : '';

export class MainRouterConfig extends FoundationRouterConfiguration<LoginSettings> {
  @Connect private connect: Connect;

  async configure() {
    this.configureAnalytics();
    this.configureRoutePermittedChecks();
    this.configureFallbackRouteDefinition();
    this.title = 'Permissions';
    this.defaultLayout = defaultLayout;

    const authPath = 'login';

    this.routes.map(
      { path: '', redirect: authPath },
      {
        path: authPath,
        name: 'login',
        title: 'Login',
        element: async () => {
          const { configure, defaultAuthConfig } = await import(
            '@genesislcap/foundation-auth/config'
          );
          return configure({
            omitRoutes: ['request-account', 'forgot-password'],
            hostPath: this.loginPath,
            // The router accepts the WebSocket only once the HTTP login has set its session cookie
            postLoginRedirect: async () => {
              await this.connect.connect(GENESIS_SOCKET_URL);
              const lastPath =
                getUser().lastPath() === `${publicPath}/` ? undefined : getUser().lastPath();
              navigateTo(lastPath ?? `${publicPath}/trades`);
            },
            postLogoutRedirect: () => {
              if (this.connect.isConnected) {
                this.connect.disconnect();
              }
              defaultAuthConfig.postLogoutRedirect();
            },
            ...ssoSettings,
          });
        },
        layout: loginLayout,
        settings: { public: true },
        childRouters: true,
      },
      { path: 'not-found', element: NotFound, title: 'Not Found', name: 'not-found' },
      {
        path: defaultNotPermittedRoute,
        element: NotPermitted,
        title: 'Not Permitted',
        name: defaultNotPermittedRoute,
      },
      {
        path: 'trades',
        element: Trades,
        title: 'Trades',
        name: 'trades',
        navItems: [
          {
            title: 'Trades - UI Auth Implemented',
            icon: {
              name: 'cog',
              variant: 'solid',
            },
            // AUTH-COMMENT
            // The below restricts visibility of this tab button to people with the below right code
            // Note it does not restrict access to the tab - this could be accessed by specifically
            // addressing  the tab in the address bar
            permission: 'TradeViewRestricted',
          },
        ],
      },
      {
        path: 'tradesNoAuth',
        element: TradesNoAuth,
        title: 'Trades No Auth',
        name: 'tradesNoAuth',
        navItems: [
          {
            title: 'Trades - Without UI Auth Implemented',
            icon: {
              name: 'cog',
              variant: 'solid',
            },
            // AUTH-COMMENT
            // Note that no permission is given for this tab - everyone sees it
            permission: '',
          },
        ],
      },
      {
        path: 'static',
        element: Static,
        title: 'Static',
        name: 'static',
        navItems: [
          {
            title: 'Static',
            icon: {
              name: 'cog',
              variant: 'solid',
            },
            // AUTH-COMMENT
            // The below restricts visibility of this tab button to people with the below right code
            // Note it does not restrict access to the tab - this could be accessed by specifically
            // addressing  the tab in the address bar
            permission: 'StaticUpdate',
          },
        ],
      },
    );

    this.contributors.push({
      navigate: async (phase) => {
        const settings = phase.route.settings;

        /**
         * If the route is public or the user is authenticated don't block
         */
        if (settings?.public || this.user.isAuthenticated) {
          return;
        }

        /**
         * Otherwise route them to login
         */
        this.navigationPhaseLoginRedirect(phase);
      },
    });
  }
}
