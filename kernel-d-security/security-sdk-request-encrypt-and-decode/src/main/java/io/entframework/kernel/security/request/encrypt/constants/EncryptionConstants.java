/*******************************************************************************
 * Copyright (c) 2022. Licensed under the Apache License, Version 2.0.
 ******************************************************************************/

package io.entframework.kernel.security.request.encrypt.constants;

/**
 * 请求解密，响应加密 常量
 *
 * @date 2021/3/23 12:54
 */
public interface EncryptionConstants {

	/**
	 * 公钥
	 */
	String PUBLIC_KEY = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAwiRWfPs7Qds9K+gbabarxKOn71vZY9S7C5cmiTTmDoxvL8gixtlldbqaBCG0fhP48qxImw4jmAnXbSb51hDohTRXmGIuBEQuhIWwh28rorSiGuOye6PTbYNuup5CWwxMkD/ARHrs5Cvg9+vJTHXdg3TrRbwiW6GniDvVGPcl0d9TshX5Dgo6m9VZkLJfHJkVKmjAOOvede8uPgaM1ymt6JexjTcn6uiIrWlDkKTzvAq+Hb9cj9tz/Q5FKo17TF7oa4XC8lfximCzAvMwsl/kmqfh0cSNqeoW9s8LcjY0o7YexYJB3+jjp88QbzqXnUNYMVGz0M2cYLmAaM2LVwWvVrfs1HCB1o+WqGqjaBql/4apVyhqf77Py6M+2WUr1yKgDfRXjZ1h9w9e3jh3oQdjSk36fboJmHXBnKwwoecxW5csVJmj/M7CPP7Xw8BPGV4/rMmRKjBRTv5XdcRnnMm8nd8EdK/2AaXZqu0O2iRjQlFFgLNUzP+eudvLCA0+Dxczkpgvcr7S4oQtD+TCFm1gWaC+Kho5liMQ7OV0L7tL8O+tzzbKmoVj/fg8uIG5ljqsU6rE5WFUCl1w0v8Gzx1Yz3IXaRKmXTgmxZPdaN3nVA+YBT+N3EKQETVlN6w+65/HK/8ZsB36exrlkkxUo+y0umk0DzFFY/9i1x2kU7wXPzECAwEAAQ==";

	/**
	 * 私钥
	 */
	String PRIVATE_KEY = "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";

	/**
	 * 模块名
	 */
	String ENCRYPTION_MODULE_NAME = "kernel-d-encryption";

	/**
	 * 异常枚举的步进值
	 */
	String ENCRYPTION_EXCEPTION_STEP_CODE = "29";

}
