package com.hm.mapper;

import com.hm.pojo.Clazz;
import com.hm.pojo.Emp;
import com.hm.pojo.EmpExpr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @Description EmpMapper
 * @Author Lisheng Li
 * @Date 2025-08-14
 */
@Mapper
public interface EmpMapper {
    /**
     * 查询总条数
     * @return
     */
    @Select("select count(*) from emp e left join dept d on e.dept_id = d.id")
    Long count();

    /**
     * 分页查询
     * @param start
     * @param length
     * @return
     */
    @Select("select e.*,d.name deptName from emp e left join dept d on e.dept_id = d.id limit #{start},#{length}")
    List<Emp> findByPage(Integer start, Integer length);

    /**
     * 分页查询2
     * @return
     */
    List<Emp> findByPage2();
    /**
     * 分页查询2
     * @return
     */
    List<Emp> findByPage3(String name,
                          Integer gender,
                          LocalDate begin,
                          LocalDate end);

    /**
     * 添加员工
     * @param emp
     */
    void insert(Emp emp);

    List<Emp> getAll();

    void deleteBatch(List<Integer> ids);

    Emp findById(Integer id);

    //第二种findbyid的查询，用两条sql语句写，拆开查询,另一条在empexprmapper接口
    @Select("select * from emp where id=#{id}")
    Emp findById2(Integer id);

    void update(Emp emp);


    List<Map<String,Object>> findJobData();

    List<Map<String,Object>> countEmpGender();


    List<Map<String, Object>> countStudentDegree();

    @Select("select * from emp where username=#{username} and password=#{password}")
    Emp findByUserNameAndPassword(String username, String password);

}
