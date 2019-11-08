package com.hbsi.shopping.elasticsearch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * 借助该javabean 完成 crud
 *
 * 测试elasticsearch用
 */
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor

/** 注意(坑点): ES 6以后不允许一个索引下有多个类型,只允许一个索引下有一个类型
 *
 *
 * String indexName(); //索引库的名称，个人建议以项目的名称命名
 *
 * String type() default ""; //类型，个人建议以实体的名称命名
 *
 * short shards() default 5; //默认分区数
 *
 * short replicas() default 1; //每个分区默认的备份数
 *
 * String refreshInterval() default "1s"; //刷新间隔
 *
 * String indexStoreType() default "fs"; //索引文件存储类型
 */
@Document(indexName = "shopping-mall-test", type = "test")
public class EsDemo implements Serializable {

    private static final long serialVersionUID = 1L;


    private Integer id;

    private String name;

    private Integer  age;

    private String sex;

}
