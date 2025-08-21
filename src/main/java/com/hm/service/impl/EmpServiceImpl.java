package com.hm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hm.annotation.InsertLog;
import com.hm.mapper.EmpExprMapper;
import com.hm.mapper.EmpMapper;
import com.hm.pojo.Emp;
import com.hm.pojo.EmpExpr;
import com.hm.pojo.EmpLog;
import com.hm.pojo.PageResult;
import com.hm.pojo.vo.LoginVO;
import com.hm.service.EmpLogService;
import com.hm.service.EmpService;
import com.hm.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description EmpServiceImpl
 * @Author Lisheng Li
 * @Date 2025-08-14
 */
@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private EmpExprMapper empExprMapper;
    @Autowired
    private EmpLogService empLogService;


    @Override
    public PageResult<Emp> findByPage(Integer page, Integer pageSize) {
        //1.调用mapper获取总条数，获取总数total
        Long total = empMapper.count();

        //2.调用mapper获取当前页数据列表,这是个公式，算出来就是起始页
        Integer star = (page - 1) * pageSize;
        List<Emp> empList = empMapper.findByPage(star, pageSize);
        //把起始页和每页页数放入emplist

        //3.封装PageResult对象并返回
        return new PageResult<>(empList, total);
        //为什么封装，因为单单empList提供的信息还不足以支撑分页，
        // 现在有了起始页star和每页页数pageSize,还缺了个总数total.前端计算总页数（total/pageSize）
        // PageResult可以接收list<T> 和total
    }

    @Override
    public PageResult<Emp> findByPage2(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);//这必须和要分页的sql语句在一起，他只会截取最近的一条sql
        List<Emp> empl = empMapper.findByPage2();//

        Page<Emp> pagelist = (Page<Emp>) empl;//强转
        //为什么不直接返回Page对象的pagelist而是又用PageResult包装一次?
        // 如果直接返回会导致调用方强制依赖 com.github.pagehelper.Page类，出现耦合
        return new PageResult<>(pagelist.getResult(), pagelist.getTotal());
    }

    @Override
    public PageResult<Emp> findByPage3(Integer page, Integer pageSize, String name, Integer gender, LocalDate begin, LocalDate end) {
        PageHelper.startPage(page, pageSize);//这必须和要分页的sql语句在一起，他只回截取最近的一条sql
        List<Emp> empl = empMapper.findByPage3(name, gender, begin, end);//

        Page<Emp> pagelist = (Page<Emp>) empl;//强转
        return new PageResult<>(pagelist.getResult(), pagelist.getTotal());
    }


    /**
     * 保存员工
     *
     * @param emp rollbackFor = Exception.class 设置spring事务什么异常回滚，这里是Exception及其子类都会回滚
     *            如果不设置只对运行时RuntimeException回滚
     *            所以@Transactional(rollbackFor = Exception.class) 这个是推荐写法
     */
    @Transactional(rollbackFor = Exception.class)//spring事务管理注解，底层使用动态代理技术对目标方法进行增强功能（事务的功能）
    @Override
    public void save(Emp emp) {

        try {
            //1.将员工数据添加到数据库中
            //1.1 补全数据
            // password，默认123456
            emp.setPassword("123456");
            // createTime ,默认当前时间
            emp.setCreateTime(LocalDateTime.now());
            // updateTime ,默认当前时间
            emp.setUpdateTime(LocalDateTime.now());
            //1.2 插入到数据库
            empMapper.insert(emp);//插入前emp中id为null，插入后emp中id有值

            //2.将员工中的工作经历集合添加到数据库中
            //2.1 判断工作经历集合是否有效
            if (emp.getExprList() != null && emp.getExprList().size() > 0) {
                //2.1.1 有效，遍历员工工作经历集合给每个工作经历补全员工id
                emp.getExprList().forEach(expr -> expr.setEmpId(emp.getId()));
                //2.1.2 插入员工工作经历集合到数据库
                empExprMapper.insertBatch(emp.getExprList());
            }
            EmpLog empLog = new EmpLog(null, LocalDateTime.now(), "添加成功");
            empLogService.insert(empLog);
        } catch (Exception e) {
            EmpLog empLog = new EmpLog(null, LocalDateTime.now(), "添加失败,因为：" + e.getMessage());
            empLogService.insert(empLog);
            throw new RuntimeException(e);
        }
    }

    @Override
    @InsertLog
    public List<Emp> getAll() {
        List<Emp> list = empMapper.getAll();
        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    //推荐设置为 Exception及其子类都会回滚
    @Override
    public void deleteBatch(List<Integer> ids) {
        empMapper.deleteBatch(ids);
        //删完员工还要删对应的员工经历
        empExprMapper.deleteByIds(ids);
    }

    @Override
    public Emp findById(Integer id) {
        return empMapper.findById(id);
    }

    //上面是一条sql语句
    //下面是两条sql语句解决
    @Override
    @InsertLog
    public Emp findById2(Integer id) {
        Emp emp = empMapper.findById2(id);
        List<EmpExpr> exprlist = empExprMapper.findById3(id);

        emp.setExprList(exprlist);//emp里有exprlist，不过原本是空的，现在set进去就有内容了

        return emp;
    }

    /**
     * 员工更新
     *
     * @param emp
     */
    @Override
    @InsertLog
    public void update(Emp emp) {
        //补全数据
        emp.setUpdateTime(LocalDateTime.now());

        empMapper.update(emp);

        //修改工作经历
        //先删老的工作经历
        empExprMapper.deleteByEmpId(emp.getId());
        //判断前端传递过来的新工作经历集合是否有数据
        // if(emp.getExprList()!=null && emp.getExprList().size()>0) {
        //hutool工具判断适合是否为null: CollectionUtil.isEmpty(集合) 为null返回true,否则返回false
        if (!CollectionUtil.isEmpty(emp.getExprList())) {
            //2.2.1 有，才插入，先给工作经历列表中每个经历重新设置员工id
            emp.getExprList().forEach(expr -> expr.setEmpId(emp.getId()));
            //2.2.2 批量插入数据库工作经历数据
            empExprMapper.insertBatch(emp.getExprList());
        }

    }

    @Override
    public LoginVO login(Emp emp) {
        //查找之后返回emp1，里面有empid等信息，emp传回的只有name和password
        Emp emp1 = empMapper.findByUserNameAndPassword(emp.getUsername(), emp.getPassword());

        if (emp1 != null) {

            //定义载荷
            Map<String, Object> claims = new HashMap<>();
            claims.put("empId", emp1.getId());
/*            System.out.println("-----------------------");
            System.out.println("empId:"+emp1.getId());//1
            System.out.println("empId:"+emp.getId());//null
            System.out.println("-----------------------");*/
            String token = JwtUtils.generateJwt(claims);

            LoginVO loginVO =
                    new LoginVO(emp1.getId(), emp1.getUsername(), emp1.getName(), token);

            return loginVO;
        } else
            return null;
    }
}
