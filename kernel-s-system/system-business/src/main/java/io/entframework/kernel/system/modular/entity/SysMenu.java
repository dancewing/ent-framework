package io.entframework.kernel.system.modular.entity;

import io.entframework.kernel.db.api.annotation.LogicDelete;
import io.entframework.kernel.db.api.pojo.entity.BaseEntity;
import io.entframework.kernel.rule.enums.StatusEnum;
import io.entframework.kernel.rule.enums.YesOrNotEnum;
import io.entframework.kernel.system.api.enums.LinkOpenTypeEnum;
import io.entframework.kernel.system.api.enums.MenuTypeEnum;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.JDBCType;
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
 * 系统菜单
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(value = "sys_menu", sqlSupport = SysMenuDynamicSqlSupport.class, tableProperty = "sysMenu")
public class SysMenu extends BaseEntity implements Serializable {

    /**
     * 主键
     */
    @Id
    @Column(name = "menu_id", jdbcType = JDBCType.BIGINT)
    private Long menuId;

    /**
     * 父id，顶级节点的父id是-1
     */
    @Column(name = "menu_parent_id", jdbcType = JDBCType.BIGINT)
    private Long menuParentId;

    /**
     * 父id集合，中括号包住，逗号分隔
     */
    @Column(name = "menu_pids", jdbcType = JDBCType.VARCHAR)
    private String menuPids;

    /**
     * 菜单的名称
     */
    @Column(name = "menu_name", jdbcType = JDBCType.VARCHAR)
    private String menuName;

    /**
     * 菜单类型
     */
    @Column(name = "menu_type", jdbcType = JDBCType.BIGINT)
    private MenuTypeEnum menuType;

    /**
     * 菜单的编码
     */
    @Column(name = "menu_code", jdbcType = JDBCType.VARCHAR)
    private String menuCode;

    /**
     * 所属应用
     */
    @Column(name = "app_id", jdbcType = JDBCType.BIGINT)
    private Long appId;

    /**
     * 排序
     */
    @Column(name = "menu_sort", jdbcType = JDBCType.DECIMAL)
    private BigDecimal menuSort;

    /**
     * 状态：1-启用，2-禁用
     */
    @Column(name = "status_flag", jdbcType = JDBCType.TINYINT)
    private StatusEnum statusFlag;

    /**
     * 备注
     */
    @Column(name = "remark", jdbcType = JDBCType.VARCHAR)
    private String remark;

    /**
     * 路由地址，浏览器显示的URL，例如/menu
     */
    @Column(name = "router", jdbcType = JDBCType.VARCHAR)
    private String router;

    /**
     * 图标
     */
    @Column(name = "icon", jdbcType = JDBCType.VARCHAR)
    private String icon;

    /**
     * 外部链接打开方式：1-内置打开外链，2-新页面外链
     */
    @Column(name = "link_open_type", jdbcType = JDBCType.TINYINT)
    private LinkOpenTypeEnum linkOpenType;

    /**
     * 外部链接地址
     */
    @Column(name = "link_url", jdbcType = JDBCType.VARCHAR)
    private String linkUrl;

    /**
     * 用于非菜单显示页面的重定向url设置
     */
    @Column(name = "active_url", jdbcType = JDBCType.VARCHAR)
    private String activeUrl;

    /**
     * 是否可见(分离版用)：Y-是，N-否
     */
    @Column(name = "visible", jdbcType = JDBCType.CHAR)
    private YesOrNotEnum visible;

    /**
     * 是否删除：Y-被删除，N-未删除
     */
    @Column(name = "del_flag", jdbcType = JDBCType.CHAR)
    @LogicDelete
    private YesOrNotEnum delFlag;

    /**
     * 所属应用
     */
    @ManyToOne
    @JoinColumn(target = SysApp.class, left = "appId", right = "appId")
    private SysApp app;

    private static final long serialVersionUID = 1L;

    public SysMenu menuId(Long menuId) {
        this.menuId = menuId;
        return this;
    }

    public SysMenu menuParentId(Long menuParentId) {
        this.menuParentId = menuParentId;
        return this;
    }

    public SysMenu menuPids(String menuPids) {
        this.menuPids = menuPids;
        return this;
    }

    public SysMenu menuName(String menuName) {
        this.menuName = menuName;
        return this;
    }

    public SysMenu menuType(MenuTypeEnum menuType) {
        this.menuType = menuType;
        return this;
    }

    public SysMenu menuCode(String menuCode) {
        this.menuCode = menuCode;
        return this;
    }

    public SysMenu appId(Long appId) {
        this.appId = appId;
        return this;
    }

    public SysMenu menuSort(BigDecimal menuSort) {
        this.menuSort = menuSort;
        return this;
    }

    public SysMenu statusFlag(StatusEnum statusFlag) {
        this.statusFlag = statusFlag;
        return this;
    }

    public SysMenu remark(String remark) {
        this.remark = remark;
        return this;
    }

    public SysMenu router(String router) {
        this.router = router;
        return this;
    }

    public SysMenu icon(String icon) {
        this.icon = icon;
        return this;
    }

    public SysMenu linkOpenType(LinkOpenTypeEnum linkOpenType) {
        this.linkOpenType = linkOpenType;
        return this;
    }

    public SysMenu linkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
        return this;
    }

    public SysMenu activeUrl(String activeUrl) {
        this.activeUrl = activeUrl;
        return this;
    }

    public SysMenu visible(YesOrNotEnum visible) {
        this.visible = visible;
        return this;
    }

    public SysMenu delFlag(YesOrNotEnum delFlag) {
        this.delFlag = delFlag;
        return this;
    }

}