import { User } from '@genesislcap/foundation-user';
import { customElement, GenesisElement } from '@genesislcap/web-core';
import { TableLevelStyles as styles } from './table-level.styles';
import { TableLevelTemplate as template } from './table-level.template';

@customElement({
  name: 'trades-table-level-manager',
  template,
  styles,
})
export class TradesTableLevelManager extends GenesisElement {
  @User user: User;
}
