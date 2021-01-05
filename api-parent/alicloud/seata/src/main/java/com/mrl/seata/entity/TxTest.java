package com.mrl.seata.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @ClassName: TxTest
 * @Description
 * @Author Mr.L
 * @Date 2020/12/19 11:45
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "TxTest对象", description = "")
public class TxTest implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户ID")
    @TableField("userId")
    private Integer userid;

    @ApiModelProperty(value = "模拟的金币数量")
    private Integer count;
}