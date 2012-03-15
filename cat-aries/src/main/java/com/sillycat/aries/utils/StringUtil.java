package com.sillycat.aries.utils;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
	 /**
     * split the string with specific separator string
     * 
     * <p>
     * the separator will not appear in result array
     * 分隔符不会出现在目标数组中，连续的分隔符就被看作一个。如果字符串为 <code>null</code> ，则返回
     * <code>null</code>。
     * 
     * <pre>
     * 
     *  StringUtil.split(null, *)                = null
     *  StringUtil.split(&quot;&quot;, *)                  = []
     *  StringUtil.split(&quot;abc def&quot;, null)        = [&quot;abc&quot;, &quot;def&quot;]
     *  StringUtil.split(&quot;abc def&quot;, &quot; &quot;)         = [&quot;abc&quot;, &quot;def&quot;]
     *  StringUtil.split(&quot;abc  def&quot;, &quot; &quot;)        = [&quot;abc&quot;, &quot;def&quot;]
     *  StringUtil.split(&quot; ab:  cd::ef  &quot;, &quot;:&quot;)  = [&quot;ab&quot;, &quot;cd&quot;, &quot;ef&quot;]
     *  StringUtil.split(&quot;abc.def&quot;, &quot;&quot;)          = [&quot;abc.def&quot;]
     *   
     * </pre>
     * 
     * </p>
     * 
     * @param str
     *            要分割的字符串
     * @param separatorChars
     *            分隔符
     * 
     * @return 分割后的字符串数组，如果原字符串为 <code>null</code> ，则返回 <code>null</code>
     */
    public static String[] split(String str, String separatorChars) {
        return split(str, separatorChars, -1);
    }
	
	/**
     * 将字符串按指定字符分割。
     * 
     * <p>
     * 分隔符不会出现在目标数组中，连续的分隔符就被看作一个。如果字符串为 <code>null</code> ，则返回
     * <code>null</code>。
     * 
     * <pre>
     * 
     *  StringUtil.split(null, *, *)                 = null
     *  StringUtil.split(&quot;&quot;, *, *)                   = []
     *  StringUtil.split(&quot;ab cd ef&quot;, null, 0)        = [&quot;ab&quot;, &quot;cd&quot;, &quot;ef&quot;]
     *  StringUtil.split(&quot;  ab   cd ef  &quot;, null, 0)  = [&quot;ab&quot;, &quot;cd&quot;, &quot;ef&quot;]
     *  StringUtil.split(&quot;ab:cd::ef&quot;, &quot;:&quot;, 0)        = [&quot;ab&quot;, &quot;cd&quot;, &quot;ef&quot;]
     *  StringUtil.split(&quot;ab:cd:ef&quot;, &quot;:&quot;, 2)         = [&quot;ab&quot;, &quot;cdef&quot;]
     *  StringUtil.split(&quot;abc.def&quot;, &quot;&quot;, 2)           = [&quot;abc.def&quot;]
     *  
     * </pre>
     * 
     * </p>
     * 
     * @param str
     *            要分割的字符串
     * @param separatorChars
     *            分隔符
     * @param max
     *            返回的数组的最大个数，如果小于等于0，则表示无限制
     * 
     * @return 分割后的字符串数组，如果原字符串为 <code>null</code> ，则返回 <code>null</code>
     */
	public static String[] split(String str, String separatorChars, int max) {
        if (str == null) {
            return null;
        }

        int length = str.length();

        if (length == 0) {
            return null;
        }

        List<String> list = new ArrayList<String>();
        int sizePlus1 = 1;
        int i = 0;
        int start = 0;
        boolean match = false;

        if (separatorChars == null) {
            // null表示使用空白作为分隔符
            while (i < length) {
                if (Character.isWhitespace(str.charAt(i))) {
                    if (match) {
                        if (sizePlus1++ == max) {
                            i = length;
                        }

                        list.add(str.substring(start, i));
                        match = false;
                    }

                    start = ++i;
                    continue;
                }

                match = true;
                i++;
            }
        } else if (separatorChars.length() == 1) {
            // 优化分隔符长度为1的情形
            char sep = separatorChars.charAt(0);

            while (i < length) {
                if (str.charAt(i) == sep) {
                    if (match) {
                        if (sizePlus1++ == max) {
                            i = length;
                        }

                        list.add(str.substring(start, i));
                        match = false;
                    }

                    start = ++i;
                    continue;
                }

                match = true;
                i++;
            }
        } else {
            // 一般情形
            while (i < length) {
                if (separatorChars.indexOf(str.charAt(i)) >= 0) {
                    if (match) {
                        if (sizePlus1++ == max) {
                            i = length;
                        }

                        list.add(str.substring(start, i));
                        match = false;
                    }

                    start = ++i;
                    continue;
                }

                match = true;
                i++;
            }
        }

        if (match) {
            list.add(str.substring(start, i));
        }

        return (String[]) list.toArray(new String[list.size()]);
    }
	
	/**
     * 将字符串的首字符转成大写（ <code>Character.toTitleCase</code> ），其它字符不变。
     * 
     * <p>
     * 如果字符串是 <code>null</code> 则返回 <code>null</code>。
     * 
     * <pre>
     * 
     *  StringUtil.capitalize(null)  = null
     *  StringUtil.capitalize(&quot;&quot;)    = &quot;&quot;
     *  StringUtil.capitalize(&quot;cat&quot;) = &quot;Cat&quot;
     *  StringUtil.capitalize(&quot;cAt&quot;) = &quot;CAt&quot;
     *  
     * </pre>
     * 
     * </p>
     * 
     * @param str
     *            要转换的字符串
     * 
     * @return 首字符为大写的字符串，如果原字符串为 <code>null</code> ，则返回 <code>null</code>
     */
    public static String capitalize(String str) {
        int strLen;

        if ((str == null) || ((strLen = str.length()) == 0)) {
            return str;
        }

        return new StringBuffer(strLen).append(
                Character.toTitleCase(str.charAt(0))).append(str.substring(1))
                .toString();
    }

	public static String getTrimString(Object obj) {
		String target = "";
		if (obj == null || !(obj instanceof String)) {
			return target;
		}
		target = (String) obj;
		return target.trim();
	}

	public static boolean isNotOne(String source) {
		return !isOne(source);
	}

	public static boolean isOne(String source) {
		boolean flag = false;
		if (isBlank(source)) {
			return flag;
		}
		if ("1".equals(source) || "One".equalsIgnoreCase(source)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 检查字符串是否是空白： <code>null</code> 、空字符串 <code>""</code> 或只有空白字符。
     * 
     * <pre>
     * 
     *  StringUtil.isBlank(null)      = true
     *  StringUtil.isBlank(&quot;&quot;)        = true
     *  StringUtil.isBlank(&quot; &quot;)       = true
     *  StringUtil.isBlank(&quot;bob&quot;)     = false
     *  StringUtil.isBlank(&quot;  bob  &quot;) = false
     *  
     * </pre>
     * 
     * @param source
     *            要检查的字符串
     * 
     * @return 如果为空白, 则返回 <code>true</code>
	 */
	public static boolean isBlank(String source) {
		int length;
		if ((source == null) || ((length = source.length()) == 0)) {
			return true;
		}
		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace(source.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNotBlank(String source) {
		return !isBlank(source);
	}

}
