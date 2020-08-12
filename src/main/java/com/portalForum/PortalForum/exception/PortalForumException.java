package com.portalForum.PortalForum.exception;

public class PortalForumException extends RuntimeException {

  public PortalForumException(String message, Exception exception) {
    super(message, exception);
  }

  public PortalForumException(String message) {
    super(message);
  }
}
