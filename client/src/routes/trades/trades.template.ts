import { isDev } from '@genesislcap/foundation-utils';
import { html } from '@genesislcap/web-core';
import type { Trades } from './trades';
import { TradesTableLevelManager } from './table-level-manager';
import { TradesRowLevelManager } from './row-level-manager';
import { TradesColumnLevelManager } from './column-level-manager';
import { TradesRowandColumnLevelManager } from './row-and-column-level-manager';

TradesTableLevelManager;
TradesRowLevelManager;
TradesColumnLevelManager;
TradesRowandColumnLevelManager;

export const TradesTemplate = html<Trades>`
  <rapid-layout auto-save-key="${() => (isDev() ? null : 'Trades_1721917637691')}">
     <rapid-layout-region type="horizontal">
         <rapid-layout-region type="vertical">
             <rapid-layout-item title="Table Level">
                 <trades-table-level-manager></trades-table-level-manager>
             </rapid-layout-item>
             <rapid-layout-item title="Row Level">
                 <trades-row-level-manager></trades-row-level-manager>
             </rapid-layout-item>
         </rapid-layout-region>
         <rapid-layout-region type="vertical">
             <rapid-layout-item title="Column Level">
                 <trades-column-level-manager></trades-column-level-manager>
             </rapid-layout-item>
             <rapid-layout-item title="Row and Column Level">
                 <trades-rowand-column-level-manager></trades-rowand-column-level-manager>
             </rapid-layout-item>
         </rapid-layout-region>
     </rapid-layout-region>
  </rapid-layout>
`;
