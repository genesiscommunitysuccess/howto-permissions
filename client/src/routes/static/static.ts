import { User } from '@genesislcap/foundation-user';
import { customElement, GenesisElement } from '@genesislcap/web-core';
import { StaticStyles as styles } from './static.styles';
import { StaticTemplate as template } from './static.template';

@customElement({
  name: 'static-route',
  template,
  styles,
})
export class Static extends GenesisElement {
  @User user: User;

  constructor() {
    super();
  }
}
