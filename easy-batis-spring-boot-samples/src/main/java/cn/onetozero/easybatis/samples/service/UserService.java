package cn.onetozero.easybatis.samples.service;

import cn.onetozero.easybatis.samples.entity.User;
import cn.onetozero.easybatis.samples.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/2/10 16:38
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @PostConstruct
    public void init() {
        User one = userMapper.findOne("123123");
        System.out.println(one);
    }
}
