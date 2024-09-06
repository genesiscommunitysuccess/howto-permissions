import { User } from '@genesislcap/foundation-user';
import { customElement, GenesisElement } from '@genesislcap/web-core';
import { CountrysStyles as styles } from './countrys.styles';
import { CountrysTemplate as template } from './countrys.template';

@customElement({
  name: 'static-countrys-manager',
  template,
  styles,
})
export class StaticCountrysManager extends GenesisElement {
  @User user: User;
}
