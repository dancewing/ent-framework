package io.entframework.kernel.system.modular.entity;

import io.entframework.kernel.db.api.annotation.LogicDelete;
import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import io.entframework.kernel.rule.enums.GenderEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.system.api.enums.UserStatusEnum;
import java.io.Serializable;
import java.sql.JDBCType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.mybatis.dynamic.sql.annotation.Column;
import org.mybatis.dynamic.sql.annotation.Entity;
import org.mybatis.dynamic.sql.annotation.Id;
import org.mybatis.dynamic.sql.annotation.JoinColumn;
import org.mybatis.dynamic.sql.annotation.ManyToOne;
import org.mybatis.dynamic.sql.annotation.Table;

/**
 * 系统用户
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(value = "sys_user", sqlSupport = SysUserDynamicSqlSupport.class, tableProperty = "sysUser")
public class SysUser extends BaseEntity implements Serializable {

	/**
	 * 主键
	 */
	@Id
	@Column(name = "user_id", jdbcType = JDBCType.BIGINT)
	private Long userId;

	/**
	 * 姓名
	 */
	@Column(name = "real_name", jdbcType = JDBCType.VARCHAR)
	private String realName;

	/**
	 * 昵称
	 */
	@Column(name = "nick_name", jdbcType = JDBCType.VARCHAR)
	private String nickName;

	/**
	 * 账号
	 */
	@Column(name = "account", jdbcType = JDBCType.VARCHAR)
	private String account;

	/**
	 * 密码，加密方式为BCrypt
	 */
	@Column(name = "password", jdbcType = JDBCType.VARCHAR)
	private String password;

	/**
	 * 头像，存的为文件id
	 */
	@Column(name = "avatar", jdbcType = JDBCType.BIGINT)
	private Long avatar;

	/**
	 * 生日
	 */
	@Column(name = "birthday", jdbcType = JDBCType.DATE)
	private LocalDate birthday;

	/**
	 * 性别：M-男，F-女
	 */
	@Column(name = "sex", jdbcType = JDBCType.CHAR)
	private GenderEnum sex;

	/**
	 * 邮箱
	 */
	@Column(name = "email", jdbcType = JDBCType.VARCHAR)
	private String email;

	/**
	 * 手机
	 */
	@Column(name = "phone", jdbcType = JDBCType.VARCHAR)
	private String phone;

	/**
	 * 电话
	 */
	@Column(name = "tel", jdbcType = JDBCType.VARCHAR)
	private String tel;

	/**
	 * 是否是超级管理员：Y-是，N-否
	 */
	@Column(name = "super_admin_flag", jdbcType = JDBCType.CHAR)
	private YesOrNotEnum superAdminFlag;

	/**
	 * 所属机构id
	 */
	@Column(name = "org_id", jdbcType = JDBCType.BIGINT)
	private Long orgId;

	/**
	 * 职位id
	 */
	@Column(name = "position_id", jdbcType = JDBCType.BIGINT)
	private Long positionId;

	/**
	 * 状态：1-正常，2-冻结
	 */
	@Column(name = "status_flag", jdbcType = JDBCType.TINYINT)
	private UserStatusEnum statusFlag;

	/**
	 * 登录次数
	 */
	@Column(name = "login_count", jdbcType = JDBCType.INTEGER)
	private Integer loginCount;

	/**
	 * 最后登陆IP
	 */
	@Column(name = "last_login_ip", jdbcType = JDBCType.VARCHAR)
	private String lastLoginIp;

	/**
	 * 最后登陆时间
	 */
	@Column(name = "last_login_time", jdbcType = JDBCType.TIMESTAMP)
	private LocalDateTime lastLoginTime;

	/**
	 * 删除标记：Y-已删除，N-未删除
	 */
	@Column(name = "del_flag", jdbcType = JDBCType.CHAR)
	@LogicDelete
	private YesOrNotEnum delFlag;

	/**
	 * 职位id
	 */
	@ManyToOne
	@JoinColumn(target = HrPosition.class, left = "positionId", right = "positionId")
	private HrPosition position;

	/**
	 * 所属机构id
	 */
	@ManyToOne
	@JoinColumn(target = HrOrganization.class, left = "orgId", right = "orgId")
	private HrOrganization organization;

	private static final long serialVersionUID = 1L;

	public SysUser userId(Long userId) {
		this.userId = userId;
		return this;
	}

	public SysUser realName(String realName) {
		this.realName = realName;
		return this;
	}

	public SysUser nickName(String nickName) {
		this.nickName = nickName;
		return this;
	}

	public SysUser account(String account) {
		this.account = account;
		return this;
	}

	public SysUser password(String password) {
		this.password = password;
		return this;
	}

	public SysUser avatar(Long avatar) {
		this.avatar = avatar;
		return this;
	}

	public SysUser birthday(LocalDate birthday) {
		this.birthday = birthday;
		return this;
	}

	public SysUser sex(GenderEnum sex) {
		this.sex = sex;
		return this;
	}

	public SysUser email(String email) {
		this.email = email;
		return this;
	}

	public SysUser phone(String phone) {
		this.phone = phone;
		return this;
	}

	public SysUser tel(String tel) {
		this.tel = tel;
		return this;
	}

	public SysUser superAdminFlag(YesOrNotEnum superAdminFlag) {
		this.superAdminFlag = superAdminFlag;
		return this;
	}

	public SysUser orgId(Long orgId) {
		this.orgId = orgId;
		return this;
	}

	public SysUser positionId(Long positionId) {
		this.positionId = positionId;
		return this;
	}

	public SysUser statusFlag(UserStatusEnum statusFlag) {
		this.statusFlag = statusFlag;
		return this;
	}

	public SysUser loginCount(Integer loginCount) {
		this.loginCount = loginCount;
		return this;
	}

	public SysUser lastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
		return this;
	}

	public SysUser lastLoginTime(LocalDateTime lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
		return this;
	}

	public SysUser delFlag(YesOrNotEnum delFlag) {
		this.delFlag = delFlag;
		return this;
	}

}