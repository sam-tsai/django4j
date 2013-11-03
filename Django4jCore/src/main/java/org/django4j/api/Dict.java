package org.django4j.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Dict extends AppendableDict<String> {
    public Dict() {
        super(true);
    }

    public String urlEncode() {
        if (isEmpty())
            return "";
        StringBuilder sb = new StringBuilder();
        sb.append('?');
        for (String key : keys()) {
            String enc_key = encode(key);
            IAppendable<String> values = get(key);
            if (values != null) {
                for (String value : values.list()) {
                    if (value != null) {
                        sb.append(enc_key).append('=').append(encode(value))
                                .append('&');
                    } else {
                        sb.append(enc_key).append('&');
                    }
                }
            } else {
                sb.append(enc_key).append('&');
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private String encode(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
        }
        return str;
    }

}
