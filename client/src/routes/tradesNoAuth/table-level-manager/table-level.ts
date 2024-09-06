import { User } from '@genesislcap/foundation-user';
import { customElement, GenesisElement } from '@genesislcap/web-core';
import { TableLevelStyles as styles } from './table-level.styles';
import { TableLevelTemplate as template } from './table-level.template';
import {TradesNoAuthStyles} from "../trades.styles";

@customElement({
  name: 'trades-noauth-table-level-manager',
  template,
  styles,
})
export class TradesNoAuthTableLevelManager extends GenesisElement {
  @User user: User;
}
