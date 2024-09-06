import { html, whenElse, repeat } from '@genesislcap/web-core';
import { getViewUpdateRightComponent } from '../../../utils';
import type { TradesNoAuthRowandColumnLevelManager } from './row-and-column-level';
import { createFormSchema } from '../../create.form.schema';
import { updateFormSchema } from '../../update.form.schema';
import { columnFullDefs } from '../../columnfull.defs';
import {TradesNoAuthStyles} from "../trades.styles";

// AUTH-COMMENT
// The function getViewUpdateRightComponent is called repeatedly to determine which front end features are enabled
// However, in this example a blank string ('') is passed in as the RIGHT CODE, thus ensuring true in all cases
// This is then left reliant on the back end for security

export const RowAndColumnLevelTemplate = html<TradesNoAuthRowandColumnLevelManager>`
    ${whenElse(
        (x) => getViewUpdateRightComponent(x.user, ''),
        html`
            <entity-management
                design-system-prefix="rapid"
                header-case-type="capitalCase"
                enable-row-flashing
                enable-cell-flashing
                resourceName="ALL_TRADES_4"
                createEvent="${(x) => getViewUpdateRightComponent(x.user, '', 'EVENT_TRADE_INSERT')}"
                :createFormUiSchema=${() => createFormSchema }
                updateEvent="${(x) => getViewUpdateRightComponent(x.user, '', 'EVENT_TRADE_MODIFY')}"
                :updateFormUiSchema=${() => updateFormSchema}
                deleteEvent="${(x) => getViewUpdateRightComponent(x.user, '', 'EVENT_TRADE_DELETE')}"
                :columns=${() => columnFullDefs }
                modal-position="centre"
                size-columns-to-fit
            ></entity-management>
        `,
        html`
          <not-permitted-component></not-permitted-component>
        `,
    )}`;
