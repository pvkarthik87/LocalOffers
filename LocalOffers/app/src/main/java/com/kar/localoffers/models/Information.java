
package com.kar.localoffers.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Information {

    @SerializedName("app_name")
    @Expose
    private String appName;
    @SerializedName("appid")
    @Expose
    private Integer appid;
    @SerializedName("virtual_currency")
    @Expose
    private String virtualCurrency;
    @SerializedName("virtual_currency_sale_enabled")
    @Expose
    private Boolean virtualCurrencySaleEnabled;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("support_url")
    @Expose
    private String supportUrl;

    /**
     * 
     * @return
     *     The appName
     */
    public String getAppName() {
        return appName;
    }

    /**
     * 
     * @param appName
     *     The app_name
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * 
     * @return
     *     The appid
     */
    public Integer getAppid() {
        return appid;
    }

    /**
     * 
     * @param appid
     *     The appid
     */
    public void setAppid(Integer appid) {
        this.appid = appid;
    }

    /**
     * 
     * @return
     *     The virtualCurrency
     */
    public String getVirtualCurrency() {
        return virtualCurrency;
    }

    /**
     * 
     * @param virtualCurrency
     *     The virtual_currency
     */
    public void setVirtualCurrency(String virtualCurrency) {
        this.virtualCurrency = virtualCurrency;
    }

    /**
     * 
     * @return
     *     The virtualCurrencySaleEnabled
     */
    public Boolean getVirtualCurrencySaleEnabled() {
        return virtualCurrencySaleEnabled;
    }

    /**
     * 
     * @param virtualCurrencySaleEnabled
     *     The virtual_currency_sale_enabled
     */
    public void setVirtualCurrencySaleEnabled(Boolean virtualCurrencySaleEnabled) {
        this.virtualCurrencySaleEnabled = virtualCurrencySaleEnabled;
    }

    /**
     * 
     * @return
     *     The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * 
     * @param country
     *     The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * 
     * @return
     *     The language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * 
     * @param language
     *     The language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * 
     * @return
     *     The supportUrl
     */
    public String getSupportUrl() {
        return supportUrl;
    }

    /**
     * 
     * @param supportUrl
     *     The support_url
     */
    public void setSupportUrl(String supportUrl) {
        this.supportUrl = supportUrl;
    }

}
