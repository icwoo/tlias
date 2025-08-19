import com.hm.SpringBootApplicationa;
import com.hm.mapper.EmpMapper;
import com.hm.service.DeptService;
import com.hm.service.EmpService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.HashMap;

/**
 * @Description a
 * @Author Lisheng Li
 * @Date 2025-08-11
 */
@SpringBootTest(classes = SpringBootApplicationa.class )
public class a {
    @Autowired
    private DeptService deptService;
    @Autowired
    private EmpService empService;
    @Autowired
    private EmpMapper empMapper;
    @Test
    void a0(){
        empMapper.findById(1);
    }
    @Test
    void setJwt(){
        //创建载荷
        HashMap<String, Object> claims = new HashMap<>();
        //给载荷添加信息
        claims.put("username","尼格");
        claims.put("hobby","rap");

        //开始生成令牌
        String token =
                //设置加密算法，并给个名字
                Jwts.builder().signWith(SignatureAlgorithm.HS256, "sign")
                //把内容加入载荷
                .addClaims(claims)
                //设置过期时间
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 6))
                // “打包并签名”，是生成 JWT 的最后一步。作用类似于 toString()，但专用于 JWT 的标准化输出
                .compact();

        System.out.println("令牌是："+token);
    }

    //令牌解析：客户端再次发送请求过来会携带令牌给服务器，服务器需要解析令牌的合法性，合法允许访问服务器资源，不合法不允许访问
    //        没有被篡改和令牌没有过期就是合法，否则报错
    @Test
    public void testParseJWT(){

        //定义一个令牌（假设前端传递过来到的）
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6IuWwvOagvCIsImhvYmJ5IjoicmFwIiwiZXhwIjoxNzU1NTEwOTY0fQ.vrrW_CP8yvyurH59Wv6djc1pWLtihRNpzMC4jWuvXuk";

        //解析令牌目的，不要报错，解析出载荷（用户唯一身份，知道是哪一个用户）说明解析成功
        Claims claims = Jwts.parser().setSigningKey("sign")//设置秘钥（不能给别人）
                .parseClaimsJws(token)//解析token
                .getBody();//解析后得到载荷
        //解析里面的数据
        Object username = claims.get("username");
        Object hobby = claims.get("hobby");
        System.out.println("username = "+username);
        System.out.println("hobby = "+hobby);

    }


    @Test
    void a1(){
        deptService.findAll().forEach(System.out::println);
    }

    private static final Logger log = LoggerFactory.getLogger(a.class);
    @Test
    void a2(){
        log.info("方法的开始");
        try {
            for (int i = 0; i < 3; i++) {
                System.out.println("test");
                log.info("执行第{}次",i);
            }
        } catch (Exception e) {
            log.error("错误信息");
        }
    }
}
