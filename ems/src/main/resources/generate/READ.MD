## DAO层代码生成
ems目录下执行：
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate

生成后先删除原来的xml->mapper接口->model（如果有）
然后再迁移生成的model->mapper接口->xml