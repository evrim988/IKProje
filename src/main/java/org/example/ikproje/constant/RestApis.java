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
    public static final String SHIFT= ROOT+"/shift";
    public static final String USERSHIFT= ROOT+"/usershift";
    public static final String BREAK= ROOT+"/break";
    public static final String EXPENSE= ROOT+"/expense";
    public static final String COMMENT = ROOT+"/comment";

    
    public static final String APPROVE_ACCOUNT="/approve-account";
    public static final String REJECT_ACCOUNT="/reject-account";
    public static final String GET_UNAPPROVED_COMPANIES="/get-unapproved-companies";
    
    public static final String GET_PERSONEL_ASSETS="/get-personel-assets";
    public static final String GET_ASSETS_OF_COMPANY="/get-assets-of-company";
    public static final String ASSIGN_NEW_ASSET="/assign-new-asset";
    public static final String APPROVE_ASSET="/approve-asset";
    public static final String REJECT_ASSET="/reject-asset";
    
    public static final String GET_COMPANY_MANAGER_PROFILE="/get-company-manager-profile";
    public static final String UPDATE_COMPANY_MANAGER_PROFILE="/update-company-manager-profile";
    
    public static final String GET_PERSONEL_PROFILE="/get-personel-profile";
    public static final String UPDATE_PERSONEL_PROFILE="/update-personel-profile";
    
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
    
    public static final String GET_LEAVE_REQUEST = "/get-leave-requests";
    public static final String APPROVE_LEAVE_REQUEST = "/approve-leave-request";
    public static final String REJECT_LEAVE_REQUEST = "/reject-leave-request";
    public static final String NEW_LEAVE_REQUEST = "/new-leave-request";
    public static final String UPDATE_LEAVE_REQUEST = "/update-leave-request";
    public static final String DELETE_LEAVE_REQUEST = "/delete-leave-request";
    public static final String GET_PERSONEL_REQUEST_LEAVE = "/get-personel-request-leave";
    
    public static final String NEW_SHIFT_REQUEST = "/new-shift-request";
    public static final String UPDATE_SHIFT = "/update-shift";
    public static final String DELETE_SHIFT = "/delete-shift";
    public static final String ALL_SHIFTS_BY_COMPANY = "/all-shifts-by-company";
    public static final String ASSIGN_SHIFT_TO_USER = "/assign-shift-to-user";
    public static final String GET_ACTIVE_SHIFT_DETAILS = "/get-active-shift-details";
    public static final String GET_PERSONEL_LIST_BY_COMPANYID = "/get-personel-list-by-companyId";
    public static final String GET_PERSONEL_SHIFT_LIST = "/get-personel-shift-list";
    
    public static final String ADD_NEW_BREAK = "/add-new-break";
    public static final String UPDATE_BREAK = "/update-break";
    public static final String DELETE_BREAK = "/delete-break";
    public static final String GET_ALL_BREAK = "/get-all-break";
    public static final String GET_BREAKS_BY_SHIFT_ID = "/get-breaks-by-shift-id";
    
    public static final String GET_PERSONEL_EXPENSES = "/get-personel-expenses";
    public static final String GET_PERSONEL_EXPENSE_REQUESTS = "/get-personel-expense-requests";
    public static final String CREATE_NEW_EXPENSE_REQUEST = "/create-new-expense-request";
    public static final String APPROVE_EXPENSE = "/approve-expense";
    public static final String REJECT_EXPENSE = "/reject-expense";
    public static final String UPLOAD_RECEIPT = "/upload-receipt";
    public static final String UPDATE_EXPENSE = "/update-expense";
    public static final String DELETE_EXPENSE = "/delete-expense";
    
    
    public static final String CREATE_COMMENT = "/create-comment";
    public static final String UPDATE_COMMENT = "/update-comment";
    public static final String UPDATE_MANAGER_PHOTO = "/update-manager-photo";
    public static final String UPDATE_COMPANY_PHOTO = "/update-company-logo";
    public static final String GET_COMMENTS = "/get-comments";
    public static final String GET_COMMENT = "/get-comment";
    
    
    public static final String UPDATE_COMPANY_LOGO="/update-company-logo";
    public static final String UPDATE_USER_AVATAR="/update-user-avatar";

}