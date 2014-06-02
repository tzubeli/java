package com.theladders.solid.lsp;

import java.util.HashMap;

public class Environment extends HashMap<Object, Object>
{
  public static final String KEY_EMAIL_DOMAIN = "emaildomain";

  public Environment()
  {
    super();
  }

  /**
   * Convenience method that returns the admin email address for this ladder.
   *
   * @return email address or "" if either the user or domain is not defined
   */

  public String getAdminEmail()
  {
    String user = getString("admin");
    String domain = getString(KEY_EMAIL_DOMAIN);

    if (user.length() > 0 && domain.length() > 0)
        return user + "@" + domain;

    return "";

  }

  public String getString(String key)
  {
    Object val = get(key);

    if (val != null)
        return val.toString().trim();

    return "";

  }
}
