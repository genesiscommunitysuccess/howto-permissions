import { User } from '@genesislcap/foundation-user';
import { customElement, GenesisElement } from '@genesislcap/web-core';
import { RowAndColumnLevelStyles as styles } from './row-and-column-level.styles';
import { RowAndColumnLevelTemplate as template } from './row-and-column-level.template';

@customElement({
  name: 'trades-rowand-column-level-manager',
  template,
  styles,
})
export class TradesRowandColumnLevelManager extends GenesisElement {
  @User user: User;
}
