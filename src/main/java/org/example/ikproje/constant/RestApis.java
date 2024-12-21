package org.example.ikproje.constant;

public class RestApis {
    private static final String VERSION = "/v1";
    private static final String API = "/api";
    private static final String DEVELOPER = "/dev";
    private static final String TEST = "/test";
    private static final String PROD = "/prod";

    private static final String ROOT = VERSION+ DEVELOPER;
    
    public static final String USER=ROOT+"/user";
    public static final String AUTH=ROOT+"/auth";
    public static final String COMPANY_MANAGER=ROOT+"/companymanager";
    public static final String EMPLOYEE = ROOT+"/employee";

    public static final String ADMIN=ROOT+"/admin";
    public static final String ASSET=ROOT+"/asset";
    public static final String LEAVE= ROOT+"/leave";

    
    public static final String REGISTER="/register";
    public static final String LOGIN="/login";
    public static final String UPDATE="/update";
    public static final String VERIFY_ACCOUNT="/verify-account";
    public static final String GETPROFILE = "/get-profile";
    public static final String FORGOT_PASSWORD = "/forgot-password";
    public static final String RESET_PASSWORD = "/reset-password";
    public static final String ADD_PERSONEL = "/add-personel";
    public static final String UPDATE_PERSONEL_STATE = "/update-personel-state";
    public static final String GET_PERSONEL_LIST = "/get-personel-list";
    
    
    public static final String UPDATE_COMPANY_LOGO="/update-company-logo";
    public static final String UPDATE_USER_AVATAR="/update-user-avatar";

}