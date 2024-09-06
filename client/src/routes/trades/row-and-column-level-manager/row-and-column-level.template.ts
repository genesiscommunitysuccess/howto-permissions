import { html, whenElse, repeat } from '@genesislcap/web-core';
import { getViewUpdateRightComponent } from '../../../utils';
import type { TradesRowandColumnLevelManager } from './row-and-column-level';
import { createFormSchema } from '../../create.form.schema';
import { updateFormSchema } from '../../update.form.schema';
import { columnDefs } from '../../column.defs';
import { columnFullDefs } from '../../columnfull.defs';

// AUTH-COMMENT
// The function getViewUpdateRightComponent is called repeatedly to determine which front end features are enabled
// A RIGHT CODE is passed into this function to determine authorisation and hide or show aspects
// The first entry decides whether grid as a whole is visible (or show "not permitted"
// The next three operate on visibility of buttons (insert, modify and delete respectively)
// The final one determines which const to use for the columns (using either full columns or restricted)

export const RowAndColumnLevelTemplate = html<TradesRowandColumnLevelManager>`
    ${whenElse(
        (x) => getViewUpdateRightComponent(x.user, 'TradeViewRestricted'),
        html`
            <entity-management
                design-system-prefix="rapid"
                header-case-type="capitalCase"
                enable-row-flashing
                enable-cell-flashing
                resourceName="ALL_TRADES_4"
                createEvent="${(x) => getViewUpdateRightComponent(x.user, 'TradeUpdate', 'EVENT_TRADE_INSERT')}"
                :createFormUiSchema=${() => createFormSchema }
                updateEvent="${(x) => getViewUpdateRightComponent(x.user, 'TradeUpdate', 'EVENT_TRADE_MODIFY')}"
                :updateFormUiSchema=${() => updateFormSchema}
                deleteEvent="${(x) => getViewUpdateRightComponent(x.user, 'TradeUpdate', 'EVENT_TRADE_DELETE')}"
                :columns=${(x) => getViewUpdateRightComponent(x.user, 'TradeViewFull')? columnFullDefs : columnDefs }
                modal-position="centre"
                size-columns-to-fit
            ></entity-management>
        `,
        html`
          <not-permitted-component></not-permitted-component>
        `,
    )}`;
