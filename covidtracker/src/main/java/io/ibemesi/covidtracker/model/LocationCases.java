package io.ibemesi.covidtracker.model;

public class LocationCases {

    private String province;
    private String country;
    private int totalCases;
    private int totalDifference;

    // returns the province of the case
    public String getProvince() {
        return province;
    }
    // sets the province of the case
    public void setProvince(String province) {
        this.province = province;
    }
    // returns the country of the case
    public String getCountry() {
        return country;
    }
    // sets the country of the case
    public void setCountry(String country) {
        this.country = country;
    }
    // returns the total reports of the case
    public int getTotalCases() {
        return totalCases;
    }
    // sets the total reports of the case
    public void setTotalCases(int totalCases) {
        this.totalCases = totalCases;
    }
    // sets the difference between the current and previous reports
    public void setTotalDifference(int totalDifference) {
        this.totalDifference = totalDifference;
    }
    // returns the difference between the current and previous reports
    public int getTotalDifference() {
        return totalDifference;
    }
}
