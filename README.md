<p align="center">
    <p align="center">
        Entframework基于Spring Boot 3的核心支撑层，亦可用于任何java项目支撑内核。
        <br>
        <a href="https://spring.io/projects/spring-boot" target="_blank">
            <img src="https://img.shields.io/badge/spring--boot-3.0.2-green.svg" alt="spring-boot">
        </a>
        <a href="https://spring.io/projects/spring-cloud" target="_blank">
            <img src="https://img.shields.io/badge/spring--cloud-2022.0.1-green.svg" alt="spring-boot">
        </a>
        <a href="https://spring.io/projects/spring-cloud-alibaba" target="_blank">
            <img src="https://img.shields.io/badge/spring--cloud--alibaba-2022-green.svg" alt="spring-boot">
        </a>
        <a href="https://mybatis.org/mybatis-dynamic-sql" target="_blank">
            <img src="https://img.shields.io/badge/mybatis--dynamic--sql-1.4.1-blue.svg" alt="mybatis-plus">
        </a>  
        <a href="https://www.hutool.cn/" target="_blank">
            <img src="https://img.shields.io/badge/hutool-5.8.10-blue.svg" alt="hutool">
        </a>
    </p>
    <p align="center">
      Build Tool <br> <a href="https://www.hutool.cn/" target="_blank">
            <img src="https://img.shields.io/badge/gradle-7.6-red.svg" alt="gradle">
        </a>
    </p>
</p>
项目基于<a href="https://gitee.com/stylefeng/roses" target="_blank">Stylefeng Roses</a>改造，从Mybatis Plus改为Mybatis Dynamic Sql，为什么使用Mybatis Dynamic Sql，请阅读Wiki
<a href="https://github.com/dancewing/ent-framework/wiki/Why-Mybatis-Sql" target="_blank">Mybatis Dynamic Sql</a>

-----------------------------------------------------------------------------------------------

<a href="https://github.com/dancewing/ent-framework/wiki/Introduction" target="_blank">功能介绍</a>

### Quick Start

```shell
docker-compose -f docker/docker-compose.yaml up -d
./gradlew :ent-backend:ent-gateway-bootstrap:bootRun &
./gradlew :ent-backend:ent-admin-bootstrap:bootRun &
./gradlew :ent-backend:ent-misc-bootstrap:bootRun &
```