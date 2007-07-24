package ru.org.linux.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.StringTokenizer;

import gnu.regexp.RE;
import gnu.regexp.REException;

public final class StringUtil {
  private StringUtil() {
  }

  public static String getFileName(String pathname) {
    StringTokenizer parsed = new StringTokenizer(pathname, "/\\", false);
    String filename = "";
    while (parsed.hasMoreElements()) {
      filename = parsed.nextToken();
    }
    return filename;
  }

  private static final RE loginCheckRE;

  static {
    try {
      loginCheckRE = new RE("[a-z][a-z0-9_-]*");
    } catch (REException e) {
      throw new RuntimeException(e);
    }
  }


  public static boolean checkLoginName(String login) {
    login = login.toLowerCase();

    // no zerosize login
    if (login.length() == 0) {
      return false;
    }
    if (login.length() >= 80) {
      return false;
    }

    return loginCheckRE.isMatch(login);
  }

  public static String makeTitle(String title) {
    if (title != null && !"".equals(title.trim())) {
      return title;
    }
    return "��� ��������";
  }

  /**
   * print stack trace to string
   */
  public static String getStackTrace(Throwable e) throws IOException {
    StringWriter str = new StringWriter();
    PrintWriter prt = new PrintWriter(str);

    e.printStackTrace(prt);

    String r = str.toString();

    prt.close();
    str.close();

    return r;
  }

  public static String md5hash(String pass) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5"); //$NON-NLS-1$
      BigInteger bi = new BigInteger(1, md.digest(pass.getBytes()));
      String hash = bi.toString(16);
      if (hash.length() < 32) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < 32 - hash.length(); i++) {
          buf.append('0');
        }
        buf.append(hash);
//        logger.fine("Calculated hash="+buf.toString()); //$NON-NLS-1$
        return buf.toString();
      } else {
        return hash;
      }
    } catch (GeneralSecurityException gse) {
      throw new RuntimeException(gse);
    }
  }

}