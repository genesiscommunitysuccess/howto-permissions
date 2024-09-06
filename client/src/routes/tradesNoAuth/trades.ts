import { User } from '@genesislcap/foundation-user';
import { customElement, GenesisElement } from '@genesislcap/web-core';
import {TradesNoAuthStyles, TradesNoAuthStyles as styles} from './trades.styles';
import { TradesNoAuthTemplate as template } from './trades.template';

@customElement({
  name: 'trades-noauth-route',
  template,
  styles,
})
export class TradesNoAuth extends GenesisElement {
  @User user: User;

  constructor() {
    super();
  }
}
