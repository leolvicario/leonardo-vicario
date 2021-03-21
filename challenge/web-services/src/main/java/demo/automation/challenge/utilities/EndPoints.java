package demo.automation.challenge.utilities;

import static demo.automation.challenge.utilities.EndPoints.Base.API_V3;

public class EndPoints {

    public static class Base {
        public static final String BASE_URL = "http://localhost";
        public static final Integer PORT = 8080;
        public static final String API_V3 = "/api/v3";
    }

    public static class Pet {
        public static final String PET = API_V3 + "/pet";
        public static final String PET_FIND_BY_STATUS = PET + "/findByStatus";
        public static final String PET_FIND_BY_TAGS = PET + "/findByTags";
        public static final String PET_BY_ID = PET + "/{petId}";
        public static final String PET_UPLOAD_IMG = PET_BY_ID + "/uploadImage";
    }

    public static class Store {
        public static final String STORE = API_V3 + "/store";
        public static final String STORE_INVENTORY = STORE + "/inventory";
        public static final String STORE_ORDER = STORE + "/order";
        public static final String STORE_ORDER_BY_ID = STORE_ORDER + "/{orderId}";
    }

    public static class User {
        public static final String USER = API_V3 + "/user";
        public static final String USER_CREATE_WITH_LIST = USER + "/createWithList";
        public static final String USER_LOGIN = USER + "/login";
        public static final String USER_LOGOUT = USER + "/logout";
        public static final String USER_BY_USERNAME = USER + "/{username}";
    }

}
