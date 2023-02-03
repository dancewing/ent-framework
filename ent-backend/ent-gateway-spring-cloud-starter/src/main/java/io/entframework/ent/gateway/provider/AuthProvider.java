package io.entframework.ent.gateway.provider;

import java.util.ArrayList;
import java.util.List;

/**
 * 鉴权配置
 * @author jeff_qian
 */
public class AuthProvider {

	public static final String TARGET = "/**";
	public static final String REPLACEMENT = "";
	public static final String AUTH_HEAD_KEY = "Authorization";
	public static final String AUTH_PARAM_KEY = "token";

	private static List<String> defaultSkipUrl = new ArrayList<>();

	static {
		defaultSkipUrl.add("/client/**");
		defaultSkipUrl.add("/oauth/token/**");
		defaultSkipUrl.add("/oauth/user-info/**");
		defaultSkipUrl.add("/token/**");
		defaultSkipUrl.add("/actuator/health/**");
		defaultSkipUrl.add("/v2/api-docs/**");
		defaultSkipUrl.add("/v2/api-docs-ext/**");
		defaultSkipUrl.add("/auth/**");
		defaultSkipUrl.add("/log/**");
		defaultSkipUrl.add("/menu/routes");
		defaultSkipUrl.add("/menu/auth-routes");
		defaultSkipUrl.add("/menu/top-menu");
		defaultSkipUrl.add("/process/resource-view");
		defaultSkipUrl.add("/process/diagram-view");
		defaultSkipUrl.add("/manager/check-upload");
		defaultSkipUrl.add("/error/**");
		defaultSkipUrl.add("/assets/**");
	}

	/**
	 * 默认无需鉴权的API
	 */
	public static List<String> getDefaultSkipUrl() {
		return defaultSkipUrl;
	}

}
