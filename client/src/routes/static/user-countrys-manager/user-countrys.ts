import { User } from '@genesislcap/foundation-user';
import { customElement, GenesisElement } from '@genesislcap/web-core';
import { UserCountrysStyles as styles } from './user-countrys.styles';
import { UserCountrysTemplate as template } from './user-countrys.template';

@customElement({
  name: 'static-user-countrys-manager',
  template,
  styles,
})
export class StaticUserCountrysManager extends GenesisElement {
  @User user: User;
}
