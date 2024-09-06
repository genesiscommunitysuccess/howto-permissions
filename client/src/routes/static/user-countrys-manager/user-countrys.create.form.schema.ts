import { UiSchema } from '@genesislcap/foundation-forms';

export const createFormSchema: UiSchema = {
  "type": "VerticalLayout",
  "elements": [
    {
      "type": "Control",
      "label": "Country Name",
      "scope": "#/properties/COUNTRY_NAME",
      "options": {
        "allOptionsResourceName": 'ALL_COUNTRYS_ADMIN',
        "valueField": "COUNTRY_NAME",
        "labelField": "COUNTRY_NAME"
      }
    },
    {
      "type": "Control",
      "label": "User Name",
      "scope": "#/properties/USER_NAME",
      "options": {
        "allOptionsResourceName": 'ALL_USERS',
        "valueField": "USER_NAME",
        "labelField": "USER_NAME"
      }
    }
  ]
}
