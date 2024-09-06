import { User } from '@genesislcap/foundation-user';
import { customElement, GenesisElement } from '@genesislcap/web-core';
import { RowLevelStyles as styles } from './row-level.styles';
import { RowLevelTemplate as template } from './row-level.template';

@customElement({
  name: 'trades-row-level-manager',
  template,
  styles,
})
export class TradesRowLevelManager extends GenesisElement {
  @User user: User;
}
