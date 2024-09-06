import { User } from '@genesislcap/foundation-user';
import { customElement, GenesisElement } from '@genesislcap/web-core';
import { RowLevelStyles as styles } from './row-level.styles';
import { RowLevelTemplate as template } from './row-level.template';
import {TradesNoAuthStyles} from "../trades.styles";

@customElement({
  name: 'trades-noauth-row-level-manager',
  template,
  styles,
})
export class TradesNoAuthRowLevelManager extends GenesisElement {
  @User user: User;
}
