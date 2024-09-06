package permissions.utilities.api_utitilites.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PojoBody {
    @JsonProperty("CRITERIA_MATCH")
    private String criteriaMatch;
    @JsonProperty("USER_NAME")
    private String userName;
    @JsonProperty("PASSWORD")
    private String password;
    @JsonProperty("TRADE_ID")
    private String tradeId;
    @JsonProperty("VERSION")
    private Integer version;
    @JsonProperty("COUNTRY_NAME")
    private String countryName;
    @JsonProperty("SIDE")
    private String side;
    @JsonProperty("RATE")
    private Double rate;
    @JsonProperty("NOTIONAL")
    private Double notional;
    @JsonProperty("SETTLEMENT_DATE")
    private Long settlementDate;
    @JsonProperty("SOURCE_CURRENCY")
    private String sourceCurrency;
    @JsonProperty("TARGET_CURRENCY")
    private String targetCurrency;
    @JsonProperty("CUSTOMER_ID")
    private Integer customerId;
    @JsonProperty("CUSTOMER_NAME")
    private String customerName;

    public String getCriteriaMatch() {
        return criteriaMatch;
    }

    public void setCriteriaMatch(String criteriaMatch) {
        this.criteriaMatch = criteriaMatch;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getNotional() {
        return notional;
    }

    public void setNotional(Double notional) {
        this.notional = notional;
    }

    public Long getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Long settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}