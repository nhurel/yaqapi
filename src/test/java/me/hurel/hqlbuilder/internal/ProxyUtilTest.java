package me.hurel.hqlbuilder.internal;

import static org.fest.assertions.Assertions.*;

import java.lang.reflect.Method;

import me.hurel.entity.User;

import org.junit.Test;

public class ProxyUtilTest {

    @Test
    public void find_class() throws NoSuchMethodException, SecurityException {
	Method m = User.class.getMethod("getChildren");
	Class<?> foundClass = ProxyUtil.getParameter(m);
	assertThat(foundClass).isEqualTo(User.class);
    }

}
