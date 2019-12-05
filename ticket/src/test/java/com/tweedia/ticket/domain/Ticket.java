package com.tweedia.ticket.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
 * @since 2019-09-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Ticket extends Model<Ticket> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("userId")
    private Long userId;

    private String phone;

    private String email;

    private String title;

    private String reply;

    private String server;

    @TableField("questionType")
    private String questionType;

    @TableField("createDate")
    private LocalDateTime createDate;

    @TableField("operatorId")
    private Long operatorId;

    @TableField("isDelete")
    private Integer isDelete;

    private String status;

    private String description;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
