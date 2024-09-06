import { html, whenElse, repeat } from '@genesislcap/web-core';
import { getViewUpdateRightComponent } from '../../../utils';
import type { StaticCountrysManager } from './countrys';
import { createFormSchema } from './countrys.create.form.schema';
import { updateFormSchema } from './countrys.update.form.schema';
import { columnDefs } from './countrys.column.defs';


export const CountrysTemplate = html<StaticCountrysManager>`
    ${whenElse(
        (x) => getViewUpdateRightComponent(x.user, ''),
        html`
            <entity-management
                design-system-prefix="rapid"
                header-case-type="capitalCase"
                enable-row-flashing
                enable-cell-flashing
                resourceName="ALL_COUNTRYS_ADMIN"
                createEvent="${(x) => getViewUpdateRightComponent(x.user, '', 'EVENT_COUNTRY_INSERT')}"
                :createFormUiSchema=${() => createFormSchema }
                updateEvent="${(x) => getViewUpdateRightComponent(x.user, '', 'EVENT_COUNTRY_MODIFY')}"
                :updateFormUiSchema=${() => updateFormSchema}
                deleteEvent="${(x) => getViewUpdateRightComponent(x.user, '', 'EVENT_COUNTRY_DELETE')}"
                :columns=${() => columnDefs }
                modal-position="centre"
                size-columns-to-fit
            ></entity-management>
        `,
        html`
          <not-permitted-component></not-permitted-component>
        `,
    )}`;
