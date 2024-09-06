import { isDev } from '@genesislcap/foundation-utils';
import { html } from '@genesislcap/web-core';
import type { Static } from './static';
import { StaticCountrysManager } from './countrys-manager';
import { StaticUserCountrysManager } from './user-countrys-manager';

StaticCountrysManager;
StaticUserCountrysManager;

export const StaticTemplate = html<Static>`
  <rapid-layout auto-save-key="${() => (isDev() ? null : 'Static_1721917637692')}">
     <rapid-layout-region>
         <rapid-layout-item title="Countrys">
             <static-countrys-manager></static-countrys-manager>
         </rapid-layout-item>
         <rapid-layout-item title="User Countries">
             <static-user-countrys-manager></static-user-countrys-manager>
         </rapid-layout-item>
     </rapid-layout-region>
  </rapid-layout>
`;
