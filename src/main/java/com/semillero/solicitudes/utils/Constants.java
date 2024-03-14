package com.semillero.solicitudes.utils;

public class Constants {

    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String EMPLOYEE_NOT_FOUND_MESSAGE = "Employee not found with id: ";
    public static final String USER_NOT_FOUND_MESSAGE = "User not found with id: ";
    public static final String LIST_NOT_FOUND_MESSAGE = "List is empty ";
    public static final String TYPE_CONTRACT_MESSAGE = "Vacation request can only be created for employees with fixed term or indefinite term contracts";
    public static final String NM_NUMBER_DAYS_NULL_MESSAGE = "You must enter the number of vacation days";
    public static final String NM_NUMBER_DAYS_BETWEEN_RANGE_MESSAGE = "Number of vacation days must be between 6 and 15";
    public static final String MONTHS_GREATER_THAN_TWO_MESSAGE = "Vacations can only be requested if you have more than 2 months";
    public static final String VACATIONS_REQUEST_DAYS_MESSAGE = "Vacation request must be made at least 15 days before the start date";
    public static final String EMAIL_NOT_VALID_MESSAGE = "The email is not valid, try again";
    public static final String SWAGGER_TITLE_MESSAGE = "Request Vacation API";
    public static final String SWAGGER_DESCRIPTION_MESSAGE = "This is a API for request a vacation";
    public static final String SWAGGER_VERSION_MESSAGE = "1.0";
    public static final String SWAGGER_CONTACT_NAME_MESSAGE = "CARLOS DUVAN LABRADOR HERNANDEZ";
    public static final String SWAGGER_CONTACT_EMAIL_MESSAGE = "carlosduvanlh@gmail.com";
    public static final String SWAGGER_CONTACT_URL_MESSAGE = "https://github.com/DuvanLabrador27/solicitudes";
    public static final String SWAGGER_LICENSE_NAME_MESSAGE = "Apache 2.0";
    public static final String SWAGGER_LICENSE_URL_MESSAGE = "http://www.apache.org/licenses/LICENSE-2.0";
    public static final String SWAGGER_TERMS_OF_SERVICE_MESSAGE = "Terms of service";

}
