package com.example.database.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author czx
 * @title: UserEntity
 * @projectName dynamic-data-source
 * @description: TODO
 * @date 2020/4/2416:17
 */
@Data
@TableName("tb_user")
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends Model<UserEntity> {

    private String user;
    private String password;
}
