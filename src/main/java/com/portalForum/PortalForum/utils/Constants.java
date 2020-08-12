package com.portalForum.PortalForum.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {

  private static String rootUrl;
  public static String confirmationEmailBody;

  public static final String ACCOUNT_VERIFICATED = "Account activated successfully";
  public static final String REFRESH_TOKEN_DELETED = "Refresh Token deleted successfully";

  public static final String confirmationEmailSubject = "Please activate your account";

  public static final String fromEmail = "portalforum@email.com";
  public static final String REGISTRATION_SUCCESSFUL = "User Registration Successful";

  @Autowired
  public Constants(@Value("${root.url}") String rootUrl) {
    Constants.rootUrl = rootUrl;
    Constants.confirmationEmailBody =
        "Thank you for signing up to Portal Forum, please click on the below url to activate your account: "
            + Constants.rootUrl
            + "/api/auth/accountVerification/";
  }
}
