package helpers;

import java.util.Arrays;
import java.util.Objects;


public class EnumHelper {

    public enum ShippingGroupEnum {
        EMPTY("Empty"),
        PARCEL("Parcel"),
        FINAL_MILE("Final Mile");

        private final String value;

        ShippingGroupEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static ShippingGroupEnum getByValue(String value){
            return Arrays.stream(ShippingGroupEnum.values()).filter(enumCode -> enumCode.value.equals(value)).findFirst().orElse(ShippingGroupEnum.EMPTY);
        }
    }

    public enum ShippingMethodEnum{
        FEDEX("Fedex"),
        USPS("USPS"),
        WHITE_GLOVE("White Glove");

        private final String value;

        ShippingMethodEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static ShippingMethodEnum getByValue(String value){
            return Arrays.stream(ShippingMethodEnum.values()).filter(shippingMethodEnum -> shippingMethodEnum.value.equals(value)).findFirst().orElse(USPS);
        }
    }

    public enum ShippingServiceLevelEnum{
        DOORSTEP("Doorstep", ShippingMethodEnum.WHITE_GLOVE.getValue()),
        ROOM_OF_CHOICE("Room of Choice", ShippingMethodEnum.WHITE_GLOVE.getValue()),
        WHITE_GLOVE("White Glove", ShippingMethodEnum.WHITE_GLOVE.getValue()),
        DOORSTEP_FEDEX("Doorstep", ShippingMethodEnum.FEDEX.getValue()),
        ROOM_OF_CHOICE_FEDEX("Room of Choice", ShippingMethodEnum.FEDEX.getValue()),
        FEDEX_MEDIUM("Medium", ShippingMethodEnum.FEDEX.getValue()),
        USPS("USPS", ShippingMethodEnum.USPS.getValue());

        private final String value;
        private final String shippingMethod;

        ShippingServiceLevelEnum(String value, String shippingMethod) {
            this.value = value;
            this.shippingMethod = shippingMethod;
        }

        public String getValue() {
            return value;
        }

        public String getShippingMethod() {
            return shippingMethod;
        }

        public static ShippingServiceLevelEnum getByValue(String value){
            return Arrays.stream(ShippingServiceLevelEnum.values()).filter(shippingServiceLevelEnum -> shippingServiceLevelEnum.value.equals(value)).findFirst().orElse(USPS);
        }

        public static ShippingServiceLevelEnum getByValueAndShippingMethod(String value, String shippingMethod){
            return Arrays.stream(ShippingServiceLevelEnum.values()).filter(shippingServiceLevelEnum -> shippingServiceLevelEnum.value.equals(value) && shippingServiceLevelEnum.shippingMethod.equals(shippingMethod)).findFirst().orElse(USPS);
        }
    }

    public enum ShippingAddressEnum{
        LOWER48("Lower 48", "4847 Edsel Road", "California", "Van Nuys", "91405", "US", "CA"),
        CANADA("Canada", "4990 rue Levy", "Quebec", "Montreal", "H3C 5K4", "CA", "QC"),
        AK("Alaska", "2811 Veltri Drive", "Alaska", "Nikolai", "99691", "US", "AK"),
        HAWAII("Hawaii", "73-5598 Olowalu St", "Hawaii", "Kailua-Kona", "96740", "US", "HI");

        private final String value;
        private final String address;
        private final String state;
        private final String city;
        private final String zip;
        private final String countryCode;
        private final String stateCode;

        ShippingAddressEnum(String value, String address, String state, String city, String zip, String countryCode, String stateCode) {
            this.value = value;
            this.address = address;
            this.state = state;
            this.city = city;
            this.zip = zip;
            this.countryCode = countryCode;
            this.stateCode = stateCode;
        }

        public String getValue() {
            return value;
        }
        public String getAddress() {
            return address;
        }
        public String getState() {
            return state;
        }
        public String getCity() {
            return city;
        }
        public String getZip() {
            return zip;
        }
        public String getCountryCode() {
            return countryCode;
        }
        public String getStateCode() {
            return stateCode;
        }
    }

    public enum GrantType{
        IMPLICIT("implicit"),
        CLIENT_CREDENTIALS("client_credentials");

        private final String value;

        GrantType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum EstimateStatus{
        EXPIRED("expired"),
        OPEN("open");

        private final String value;

        EstimateStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum ProductStatus{
        INSTOCK("inStock"),
        BACKORDER("backOrder"),
        ISDROPSHIP("isDropship");

        private final String value;

        ProductStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum Grouping{
        FEDEX_SMALL("FedEx Small Delivery"),
        FEDEX_MEDIUM("FedEx Medium Delivery"),
        FEDEX_LARGE("FedEx Large Delivery"),
        WHITE_GLOVE_WHITE_GLOVE("White Glove White Glove Delivery"),
        WHITE_GLOVE_DOORSTEP("White Glove Doorstep Delivery"),
        WHITE_GLOVE_ROOM_OF_CHOICE("White Glove Room of Choice Delivery"),
        WHITE_GLOVE_GENERAL("White Glove Delivery");

        private final String value;

        Grouping(String value){ this.value = value; }

        public String getValue() { return value; }
    }

    public enum SourceAppEnum {
        ANY("Any"),
        PRODUCTION("Production"),
        PREVIEW("Preview");

        private final String value;

        SourceAppEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum PriceBookEnum {
        CUSTOMER("34801611-eef4-4dd8-b124-4d96cf07db2a");

        private final String value;

        PriceBookEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum CatalogContext {
        DsoPreview("DSO - preview"),
        DooPreview("Designer - preview"),
        CustomerPreview("Customer - preview");

        private final String value;

        CatalogContext(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum ShippingAddressByStateEnum {
        NEWYORK("new york 123 Alfred Road East"),
        PENSILVANIA("Pensilvania 123 Street Road"),
        ALASKA("5031 East Mayflower Lane, Alaska"),
        DELAWARE("301 South Bradford Street Delaware"),
        MONTANA("1224 Montana Street Missoula"),
        NEWHAMPSHIRE("1122 New Hampshire 106"),
        OREGON("Southeast 82nd Avenue hillsboro"),
        CANADA("4753 Avenue de la Villa Saint Vincent");

        private final String value;

        ShippingAddressByStateEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum SortOptionEnum {
        FEATURED("Featured"),
        NEW_ARRIVALS("New Arrivals"),
        PRICE_LOW_TO_HIGH("Price Low to High"),
        PRICE_HIGH_TO_LOW("Price High to Low");

        private final String value;

        SortOptionEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    // Create a new enum for Filter with these values: "Color", "Size", "Price", "Collection", "Category", "Sub Category", "Availability"
    public enum FilterOptionEnum {
        COLOR("Color"),
        SIZE("Size"),
        PRICE("Price"),
        COLLECTION("Collection"),
        CATEGORY("Category"),
        SUB_CATEGORY("Sub Category"),
        AVAILABILITY("Availability");

        private final String value;

        FilterOptionEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    // create new enums for Division with value: Furniture
    public enum DivisionEnum {
        FURNITURE("Furniture"),
        UPHOLSTERY("Upholstery");

        private final String value;

        DivisionEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    // create new enums for Department with value: Outdoor Furniture
    public enum DepartmentEnum {
        OUTDOOR_FURNITURE("Outdoor Furniture"),
        UPHOLSTERED_BEDROOM("Upholstered Bedroom");

        private final String value;

        DepartmentEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    // create new enums for SubDepartment with value: Outdoor Seating
    public enum SubDepartmentEnum {
        OUTDOOR_SEATING("Outdoor Seating"),
        BEDS("Beds");

        private final String value;

        SubDepartmentEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    // create new enums for Category with value: Lounge Chairs
    public enum CategoryEnum {
        LOUNGE_CHAIRS("Lounge Chairs"),
        BEDS("Beds");

        private final String value;

        CategoryEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    // create new enums for Collection with value: Hyannis Surf Chair
    public enum CollectionEnum {
        HYANNIS_SURF_CHAIR("Hyannis Surf Chair"),
        BUNGALOW_BED("Bungalow Bed");



        private final String value;

        CollectionEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    // create new enums for MasterCollection with value: Priano
    public enum MasterCollectionEnum {
        PRIANO("Priano");

        private final String value;

        MasterCollectionEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}

