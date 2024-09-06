import { html, whenElse, repeat } from '@genesislcap/web-core';
import { getViewUpdateRightComponent } from '../../../utils';
import type { StaticUserCountrysManager } from './user-countrys';
import { createFormSchema } from './user-countrys.create.form.schema';
import { updateFormSchema } from './user-countrys.update.form.schema';
import { columnDefs } from './user-countrys.column.defs';


export const UserCountrysTemplate = html<StaticUserCountrysManager>`
    ${whenElse(
        (x) => getViewUpdateRightComponent(x.user, ''),
        html`
            <entity-management
                design-system-prefix="rapid"
                header-case-type="capitalCase"
                enable-row-flashing
                enable-cell-flashing
                resourceName="ALL_USER_COUNTRY_ACCESSS"
                createEvent="${(x) => getViewUpdateRightComponent(x.user, '', 'EVENT_USER_COUNTRY_ACCESS_INSERT')}"
                :createFormUiSchema=${() => createFormSchema }
                updateEvent="${(x) => getViewUpdateRightComponent(x.user, '', 'EVENT_USER_COUNTRY_ACCESS_MODIFY')}"
                :updateFormUiSchema=${() => updateFormSchema}
                deleteEvent="${(x) => getViewUpdateRightComponent(x.user, '', 'EVENT_USER_COUNTRY_ACCESS_DELETE')}"
                :columns=${() => columnDefs }
                modal-position="centre"
                size-columns-to-fit
            ></entity-management>
        `,
        html`
          <not-permitted-component></not-permitted-component>
        `,
    )}`;
