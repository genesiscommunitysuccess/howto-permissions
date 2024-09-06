import { ColDef } from '@ag-grid-community/core';
import { getDateFormatter, getNumberFormatter } from '../utils';

export const columnDefs: ColDef[] = [
  {
    field: "TRADE_ID",
  },
  {
    field: "VERSION",
    valueFormatter: getNumberFormatter("0,0", null),
  },
  {
    field: "COUNTRY_NAME",
  },
  {
    field: "SIDE",
  },
  {
    field: "RATE",
    valueFormatter: getNumberFormatter("0,0.00000", null),
  },
  {
    field: "NOTIONAL",
    valueFormatter: getNumberFormatter("0,0.00", null),
  },
  {
    field: "SETTLEMENT_DATE",
    valueFormatter: getDateFormatter("en-GB", {"year":"numeric","month":"short","day":"2-digit","timeZone":"UTC"}),
  },
  {
    field: "SOURCE_CURRENCY",
  },
  {
    field: "TARGET_CURRENCY",
  },
  {
    field: "CUSTOMER_ID",
    valueFormatter: getNumberFormatter("0,0", null),
  }
]
