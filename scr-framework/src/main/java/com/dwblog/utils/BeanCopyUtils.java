package com.dwblog.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {

    //private the constructor, only need to provide the static methods.

    private BeanCopyUtils(){
    }

    /**
     * Copy properties from source to target
     * 第一个v的意思是这是泛型方法
     * 第二个v是返回值是V类型的
     * @return target object
     */
    public static <V> V copyBean(Object source, Class<V> clazz) {
        V result = null;
        try {
            //Create target object class
            result = clazz.newInstance();
            //Copy properties
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Return
        return result;
    }

    public static <O,V> List<V> copyBeanList(List<O>list, Class<V> clazz){
        return list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }
}
