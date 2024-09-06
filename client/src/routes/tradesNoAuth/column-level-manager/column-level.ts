import { User } from '@genesislcap/foundation-user';
import { customElement, GenesisElement } from '@genesislcap/web-core';
import { ColumnLevelStyles as styles } from './column-level.styles';
import { ColumnLevelTemplate as template } from './column-level.template';
import {TradesNoAuthStyles} from "../trades.styles";

@customElement({
  name: 'trades-noauth-column-level-manager',
  template,
  styles,
})
export class TradesNoAuthColumnLevelManager extends GenesisElement {
  @User user: User;
}
