package net.toshimichi.dungeons.lang.ipstack;

import com.google.gson.*;
import org.apache.commons.collections4.map.ReferenceMap;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.apache.commons.collections4.map.AbstractReferenceMap.ReferenceStrength;

/**
 * IpStackの機能を提供します.
 */
public class IpStackApi {


    private final Map<String, IpStackInfo> map =
            new ReferenceMap<>(ReferenceStrength.SOFT, ReferenceStrength.SOFT);
    private final String accessKey;

    public IpStackApi(String accessKey) {
        this.accessKey = accessKey;
    }

    private JsonObject check() throws IOException {
        URL url = new URL("http://api.ipstack.com/check?access_key=" + accessKey);
        URLConnection co = url.openConnection();
        StringWriter writer = new StringWriter();
        try (InputStream in = co.getInputStream()) {
            IOUtils.copy(in, writer, StandardCharsets.UTF_8);
        }
        return new Gson().fromJson(writer.toString(), JsonObject.class);
    }

    /**
     * グローバルIPを返します.
     *
     * @return 当該マシンのグローバルIP
     * @throws IOException グローバルIPを取得できなかった場合
     */
    public String getGlobalAddress() throws IOException {
        JsonObject obj = check();
        JsonElement e = obj.get("ip");
        if (e == null) return null;
        return e.getAsString();
    }

    /**
     * APIが現在使えるかどうかを返します.
     *
     * @return 使える場合は {@code true} そうでない場合は {@code false}
     */
    public boolean isAvailable() {
        try {
            JsonObject obj = check();
            JsonElement element = obj.get("success");
            if (element == null) return true;
            return element.getAsBoolean();
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * IPに関連づけられた情報を返します.
     *
     * @param ip      調べるIP
     * @param options IpStackで利用できるオプション
     * @return IPに関連づけられた情報
     * @throws IOException 情報が読み込めなかった場合
     */
    public IpStackInfo getInfo(String ip, IpStackOption... options) throws IOException {
        if (ip.equals("127.0.0.1")) {
            ip = getGlobalAddress();
        }
        IpStackInfo result = map.get(ip);
        if (result != null) return result;

        StringBuilder builder = new StringBuilder();
        for (IpStackOption option : options) {
            builder.append('&');
            builder.append(option.getKey());
            builder.append('=');
            builder.append(option.getValue());
        }
        URL url = new URL("http://api.ipstack.com/" + ip + "?access_key=" + accessKey + builder);
        URLConnection co = url.openConnection();

        StringWriter writer = new StringWriter();
        try (InputStream in = co.getInputStream()) {
            IOUtils.copy(in, writer, StandardCharsets.UTF_8);
        }
        String str = writer.toString();
        IpStackInfo info = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create().fromJson(str, IpStackInfo.class);

        map.put(ip, info);
        return info;
    }

    /**
     * IPに関連付けられた情報を返します.
     *
     * @param ip 調べるIP
     * @return IPに関連づけられた情報
     * @throws IOException 情報が読み込めなかった場合
     */
    public IpStackInfo getInfo(String ip) throws IOException {
        return getInfo(ip, new IpStackOption[0]);
    }
}
