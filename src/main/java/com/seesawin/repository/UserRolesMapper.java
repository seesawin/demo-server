package com.seesawin.repository;

import com.seesawin.models.UserRoles;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

public interface UserRolesMapper {
    @Delete({
        "delete from user_roles",
        "where user_id = #{userId,jdbcType=INTEGER}",
          "and role_id = #{roleId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    @Insert({
        "insert into user_roles (user_id, role_id)",
        "values (#{userId,jdbcType=INTEGER}, #{roleId,jdbcType=INTEGER})"
    })
    int insert(UserRoles record);

    @Select({
        "select",
        "user_id, role_id",
        "from user_roles"
    })
    @Results({
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="role_id", property="roleId", jdbcType=JdbcType.INTEGER, id=true)
    })
    List<UserRoles> selectAll();
}