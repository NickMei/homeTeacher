package com.kayluo.pokerface.util;

/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/portal/branches/sakai_2-5-4/portal-util/util/src/java/org/sakaiproject/portal/util/URLUtils.java $
 * $Id: URLUtils.java 28982 2007-04-16 21:41:44Z ian@caret.cam.ac.uk $
 ***********************************************************************************
 *
 * Copyright (c) 2006 The Sakai Foundation.
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 **********************************************************************************/


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author ieb
 * @since Sakai 2.4
 * @version $Rev: 28982 $
 */

public class URLUtils
{

    public static String addParameter(String URL, String name, String value)
    {
        int qpos = URL.indexOf('?');
        int hpos = URL.indexOf('#');
        char sep = qpos == -1 ? '?' : '&';
        String seg = sep + encodeUrl(name) + '=' + encodeUrl(value);
        return hpos == -1 ? URL + seg : URL.substring(0, hpos) + seg
                + URL.substring(hpos);
    }

    /**
     * The same behaviour as Web.escapeUrl, only without the "funky encoding" of
     * the characters ? and ; (uses JDK URLEncoder directly).
     *
     * @param toencode
     *        The string to encode.
     * @return <code>toencode</code> fully escaped using URL rules.
     */
    public static String encodeUrl(String url)
    {
        try
        {
            return URLEncoder.encode(url, "UTF-8");
        }
        catch (UnsupportedEncodingException uee)
        {
            throw new IllegalArgumentException(uee);
        }
    }

}
