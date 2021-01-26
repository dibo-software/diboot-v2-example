/*
 * Copyright (c) 2015-2020, www.dibo.ltd (service@dibo.ltd).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.example.vo;

import com.diboot.core.binding.annotation.*;
import com.example.entity.Department;
import com.example.entity.Organization;
import com.example.entity.Role;
import com.example.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 无需手写，启用devtools，该文件将自动生成
 */
/**
 * @author www.dibo.ltd
 * @version v2.0
 * @date 2019/1/30
 */
@Getter
@Setter
@Accessors(chain = true)
public class UserVO extends User {
    private static final long serialVersionUID = 3526115343377985725L;

    // 字段关联
    @BindField(entity= Department.class, field="name", condition="this.department_id=id AND parent_id IS NOT NULL")
    private String deptName;

    // 支持级联字段关联，相同条件的entity+condition将合并为一条SQL查询
    @BindField(entity = Organization.class, field="name", condition="this.department_id=department.id AND department.org_id=id")
    private String orgName;
    @BindField(entity = Organization.class, field="telphone", condition="this.department_id=department.id AND department.org_id=id")
    private String orgTelphone;

    // 通过中间表关联Entity
    @BindEntity(entity = Organization.class, condition = "this.department_id=department.id AND department.org_id=id") // AND deleted=0
    private Organization organization;

    // 支持通过中间表的多-多Entity实体关联
    @BindEntityList(entity = Role.class, condition="this.id=user_role.user_id AND user_role.role_id=id")
    private List<Role> roleList;

    // 支持通过中间表的多-多关联Entity单字段集合
    @BindFieldList(entity = Role.class, field = "code", condition="this.id=user_role.user_id AND user_role.role_id=id")
    private List<String> roleCodes;

    // 绑定数据字典枚举
    @BindDict(type="GENDER", field = "gender")
    private String genderLabel;

}