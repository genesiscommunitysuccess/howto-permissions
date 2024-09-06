import { UiSchema } from '@genesislcap/foundation-forms';

export const createFormSchema: UiSchema = {
  "type": "VerticalLayout",
  "elements": [
    {
      "type": "Control",
      "label": "Trade Id",
      "scope": "#/properties/TRADE_ID",
      "options": {
        "hidden": true
      }
    },
    {
      "type": "Control",
      "label": "Version",
      "scope": "#/properties/VERSION",
      "options": {}
    },
    {
      "type": "Control",
      "label": "Country Name",
      "scope": "#/properties/COUNTRY_NAME",
      "options": {
        "allOptionsResourceName": 'ALL_COUNTRYS',
        "valueField": "COUNTRY_NAME",
        "labelField": "COUNTRY_NAME"
      }
    },
    {
      "type": "Control",
      "label": "Side",
      "scope": "#/properties/SIDE",
      "options": {}
    },
    {
      "type": "Control",
      "label": "Rate",
      "scope": "#/properties/RATE",
      "options": {}
    },
    {
      "type": "Control",
      "label": "Notional",
      "scope": "#/properties/NOTIONAL",
      "options": {}
    },
    {
      "type": "Control",
      "label": "Settlement Date",
      "scope": "#/properties/SETTLEMENT_DATE",
      "options": {}
    },
    {
      "type": "Control",
      "label": "Source Currency",
      "scope": "#/properties/SOURCE_CURRENCY",
      "options": {}
    },
    {
      "type": "Control",
      "label": "Target Currency",
      "scope": "#/properties/TARGET_CURRENCY",
      "options": {}
    },
    {
      "type": "Control",
      "label": "Customer Id",
      "scope": "#/properties/CUSTOMER_ID",
      "options": {}
    },
    {
      "type": "Control",
      "label": "Customer Name",
      "scope": "#/properties/CUSTOMER_NAME",
      "options": {}
    }
  ]
}
