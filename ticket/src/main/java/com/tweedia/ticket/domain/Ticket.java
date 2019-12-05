package com.tweedia.ticket.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author Tweedia
 * @since 2019-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Ticket extends Model<Ticket> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(exist = false)
    private int rowNum;

    @TableField("tkId")
    private String tkId;

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

//    @TableField("isDelete")
//    @TableLogic
//    private Integer isDelete;

    private String status;

    private String description;

    private String evaluation;

    private String opinion;

    @TableField(exist = false)
    private String customerName;

    @TableField(exist = false)
    private String operatorName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
