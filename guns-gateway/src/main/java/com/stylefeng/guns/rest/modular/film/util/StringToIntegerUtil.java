package com.stylefeng.guns.rest.modular.film.util;

/**
 * Created by cute coder
 * 2019/5/24 9:02
 */
public class StringToIntegerUtil {
  public static Integer[] getArray(String sb) {
    Integer[] integers = null;
    if (sb != null && sb.contains("[") && sb.contains("]") && sb.length()== 3) {
      String substring = sb.substring(1, 2);
      integers = new Integer[1];
      integers[0] = Integer.parseInt(substring);
      return integers;
    }
    if (sb != null && sb.contains("[") && sb.contains("]")) {
      String s = sb.substring(1, sb.length() - 1);
      String[] split = s.split(",");
      integers = new Integer[split.length];
      for (int i = 0; i < integers.length; i++) {
        integers[i] = Integer.parseInt(split[i]);
      }
    }
    if (sb != null && !sb.contains("[") && !sb.contains("]")) {
      String[] split = sb.split(",");
      integers = new Integer[split.length];
      for (int i = 0; i < integers.length; i++) {
        integers[i] = Integer.parseInt(split[i]);
      }
    }
    return integers;
  }
}
