package com.hm.mapper;

import com.hm.pojo.Clazz;
import com.hm.pojo.Result;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

/**
 * @Description ClazzMapper
 * @Author Lisheng Li
 * @Date 2025-08-13
 */
@Mapper
public interface ClazzMapper {

    @Select("select * from clazz")
    List<Clazz> getAll();

    @Insert("insert into clazz(name, room, begin_date, end_date, master_id, subject, create_time, update_time) " +
            "values (#{name}, #{room}, #{beginDate}, #{endDate}, #{masterId}, #{subject}, #{createTime}, #{updateTime})")
    void addClazz(Clazz clazz);


    List<Clazz> pageQueryClazz(String name, LocalDate begin, LocalDate end);

    @Select("select * from clazz where id=#{id}")
    Clazz findById(Integer id);

    @Delete("delete from clazz where id=#{id}")
    void deleteById(Integer id);

    void updateById(Clazz clazz);
}
