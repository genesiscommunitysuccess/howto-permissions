import { User } from '@genesislcap/foundation-user';
import { customElement, GenesisElement } from '@genesislcap/web-core';
import { TradesStyles as styles } from './trades.styles';
import { TradesTemplate as template } from './trades.template';

@customElement({
  name: 'trades-route',
  template,
  styles,
})
export class Trades extends GenesisElement {
  @User user: User;

  constructor() {
    super();
  }
}
