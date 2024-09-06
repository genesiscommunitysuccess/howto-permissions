import { User } from '@genesislcap/foundation-user';
import { customElement, GenesisElement } from '@genesislcap/web-core';
import { ColumnLevelStyles as styles } from './column-level.styles';
import { ColumnLevelTemplate as template } from './column-level.template';

@customElement({
  name: 'trades-column-level-manager',
  template,
  styles,
})
export class TradesColumnLevelManager extends GenesisElement {
  @User user: User;
}
