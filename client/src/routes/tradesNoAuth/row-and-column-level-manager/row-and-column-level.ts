import { User } from '@genesislcap/foundation-user';
import { customElement, GenesisElement } from '@genesislcap/web-core';
import { RowAndColumnLevelStyles as styles } from './row-and-column-level.styles';
import { RowAndColumnLevelTemplate as template } from './row-and-column-level.template';
import {TradesNoAuthStyles} from "../trades.styles";

@customElement({
  name: 'trades-noauth-rowand-column-level-manager',
  template,
  styles,
})
export class TradesNoAuthRowandColumnLevelManager extends GenesisElement {
  @User user: User;
}
