import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.common.base.CaseFormat;

/**
 * Created by kykkyn2 on 2016-10-21.
 */
public class JacksonUtils {

	public static final ObjectMapper JACKSON;

	static {
		JACKSON = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public static String toJson(Object obj) {
		try {
			return JACKSON.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new IllegalStateException(e);
		}
	}

	public static <T> T fromJson(String json, Class<T> t) {
		try {
			return JACKSON.readValue(json, t);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T fromJson(String json, TypeReference<T> t) {
		try {
			return JACKSON.readValue(json, t);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public static String toQueryToJson(String url) {

		String jsonString = null;

		try {

			Map<String, String> params = new HashMap<>();
			String query = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, url);

			for (String param : query.split("&")) {
				String[] pair = param.split("=");
				//	맨 앞글자가 소문자로 변환 작업
				String key = URLDecoder.decode(StringUtils.uncapitalize(pair[0]), "UTF-8");
				String value = "";
				if (pair.length > 1) {
					value = URLDecoder.decode(pair[1], "UTF-8");
				}

				params.put(key, value);

			}

			//	Map -> Json String
			jsonString = JacksonUtils.toJson(params);
			//	Json String -> Model
			//	AppContext.JACKSON.readValue(jsonString,Model.class);

		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}

		return jsonString;
	}

}
