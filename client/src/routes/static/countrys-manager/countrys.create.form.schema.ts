import { UiSchema } from '@genesislcap/foundation-forms';

export const createFormSchema: UiSchema = {
  "type": "VerticalLayout",
  "elements": [
    {
      "type": "Control",
      "label": "Country Name",
      "scope": "#/properties/COUNTRY_NAME",
      "options": {}
    }
  ]
}
