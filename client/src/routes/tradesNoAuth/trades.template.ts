import { isDev } from '@genesislcap/foundation-utils';
import { html } from '@genesislcap/web-core';
import type { TradesNoAuth } from './trades';
import { TradesNoAuthTableLevelManager } from './table-level-manager';
import { TradesNoAuthRowLevelManager } from './row-level-manager';
import { TradesNoAuthColumnLevelManager } from './column-level-manager';
import { TradesNoAuthRowandColumnLevelManager } from './row-and-column-level-manager';
import {TradesNoAuthStyles} from "./trades.styles";

TradesNoAuthTableLevelManager;
TradesNoAuthRowLevelManager;
TradesNoAuthColumnLevelManager;
TradesNoAuthRowandColumnLevelManager;

export const TradesNoAuthTemplate = html<TradesNoAuth>`
  <rapid-layout auto-save-key="${() => (isDev() ? null : 'Trades_1721917637692')}">
     <rapid-layout-region type="horizontal">
         <rapid-layout-region type="vertical">
             <rapid-layout-item title="Table Level">
                 <trades-noauth-table-level-manager></trades-noauth-table-level-manager>
             </rapid-layout-item>
             <rapid-layout-item title="Row Level">
                 <trades-noauth-row-level-manager></trades-noauth-row-level-manager>
             </rapid-layout-item>
         </rapid-layout-region>
         <rapid-layout-region type="vertical">
             <rapid-layout-item title="Column Level">
                 <trades-noauth-column-level-manager></trades-noauth-column-level-manager>
             </rapid-layout-item>
             <rapid-layout-item title="Row and Column Level">
                 <trades-noauth-rowand-column-level-manager></trades-noauth-rowand-column-level-manager>
             </rapid-layout-item>
         </rapid-layout-region>
     </rapid-layout-region>
  </rapid-layout>
`;
