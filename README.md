#技术选型：
Spring WebFlux 、Rective、Spring Date JPA、MongoDB


#



#接口文档地址：



#打包发布
1、打包：mvn package  -Dmaven.test.skip=true -Dmaven.javadoc.skip=true
mvn package -Dmaven.test.skip=true
2、上传：rz -bey
scp violation-applet-0.0.1-SNAPSHOT.jar root@127.0.0.1:/home/xiaoju/services/violation-applet
3、启动：nohup java -jar violation-applet-0.0.1-SNAPSHOT.jar & 
xmls阿里云不挂断运行命令 nohup java -jar violation-applet-0.0.1-SNAPSHOT.jar --spring.profiles.active=test & 

查看端口号占用情况：netstat -apn|grep 80 (ESTABLISHED6426/lighttpd)
确定进程号: ps -aux|grep <进程号> 
杀掉该进程 : kill -9

# TODO 
6、根据车牌识别出车型、logo。（需调查是否可以做）
7、监控接口
8、增加违章缓存

二、违章小程序
1、小程序后端添加 违章缓存 逻辑；
2、接口调用日志埋点；（OK）
3、预留-可调整额度- 用户 可绑定车辆数；
4、预留-可调整额度- 单日可查询违章次数；
5、预留-统计接口-可统计有多少用户使用了违章小程序；
6、预留-统计接口-可统计用户总绑定车辆数；
7、预留-统计接口-可统计某用户绑定车辆数及绑定了哪些车；
8、新增违章详情接口-取缓存数据；
9、


#测试CASE
一、绑定车辆
1、新用户第一次绑车，车辆验证不过；
2、新用户第一次绑车，车辆验证通过；
3、非新用户绑车，车辆验证不过；
4、非新用户绑车，车辆验证通过；









