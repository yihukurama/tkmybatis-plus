# what is tkmybatis plus?
tkmyBatis-plus is an powerful enhanced toolkit of tk.myBatis for simplify development. This toolkit provides some efficient, useful, out-of-the-box features for MyBatis, use it can effectively save your development time.It contains spring,springMVC base on some rules to devolop project quickly.

# quick start
```
<dependency>
	<groupId>com.github.yihukurama</groupId>
	<artifactId>tkmybatisplus</artifactId>
	<version>0.0.2-RELEASE</version>
</dependency>
```
1. 设计数据库表结构，约定：所有表id为32位uuid
2. 为表创建Entity类，与表一一对应，如有user表则对应有UserEntity.class类
3. 为Entity创建领域类，有UserEntity类则用User类
4. 为user领域类创建服务，UserService extends CrudService
5. 则框架自动启动user表的 create ,update, remove 和 list 方法，路由分别为/user/create  /user/update  user/remove  user/list
6. 该种方式相当于暴露数据库操作权限，因此对于需要做业务限制的接口可重写 UserService 的方法实现，如禁止删除则重写remove方法直接返回即可
