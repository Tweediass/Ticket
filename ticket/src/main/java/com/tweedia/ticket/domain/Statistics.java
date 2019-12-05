package com.tweedia.ticket.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Tweedia
 * @since 2019-09-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Statistics extends Model<Statistics> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("operatorId")
    private Long operatorId;

    @TableField("countProcessed")
    private Integer countProcessed;

    @TableField("countAssigned")
    private Integer countAssigned;

    @TableField("operatorName")
    private String operatorName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
