UPDATE `sys_config` SET `config_value` = concat(config_value, ',/sys-theme/add,/sys-theme/update') WHERE `config_code` = 'SYS_XSS_URL_EXCLUSIONS';
