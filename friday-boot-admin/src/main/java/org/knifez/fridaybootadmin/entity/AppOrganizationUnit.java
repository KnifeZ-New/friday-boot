package org.knifez.fridaybootadmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.knifez.fridaybootcore.entity.BaseAuditEntity;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 组织机构
 * </p>
 *
 * @author KnifeZ
 * @since 2022-09-01
 */
@Getter
@Setter
@TableName("app_organization_unit")
@Schema(defaultValue = "AppOrganizationUnit对象", description = "组织机构")
public class AppOrganizationUnit extends BaseAuditEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(defaultValue = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(defaultValue = "机构编码")
    private String unitCode;


    @Schema(defaultValue = "父级节点")
    private Long parentId;

    @Schema(defaultValue = "名称")
    private String name;

    @Schema(defaultValue = "简介")
    private String description;

    @Override
    public int hashCode() {
        int hashNo = 7;
        hashNo = 13 * hashNo + this.getId().hashCode();
        return hashNo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final AppOrganizationUnit organizationUnit = (AppOrganizationUnit) obj;
        if (this == organizationUnit) {
            return true;
        } else {
            return this.getId().equals(organizationUnit.getId());
        }
    }
}
