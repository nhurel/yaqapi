package me.hurel.usage;

import static org.fest.assertions.Assertions.*;
import me.hurel.dao.UserDao;
import me.hurel.entity.User;

import org.junit.Test;

public class DaoTest {

    UserDao dao = new UserDao();
    
    @Test
    public void test_query_on_dao(){
        User user = dao.getUserByFirstName("titi");
        assertThat(user).isNotNull();
    }
    
}
