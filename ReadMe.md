relaynovel
===
# Terminal指令集
- 前回コンパイル済みのclassファイルを削除(MAC OSX, WINDOWSは要確認)
  - しないとCodeを更新しても上書きされない場合あり、必ず実行してからコンパイル
```
rm -rf */*.class && rm *.class
```
- コンパイル
```
javac -cp ..:mysql-connector-java-8.0.15.jar *.java
```
- 実行
```
java -cp ..:mysql-connector-java-8.0.15.jar relaynovel.Main
```

# MySQL
- Create database
```sql
create database relay_novel;
```
- DBAdapterのconstructor中のDB用のuser名とパスワードを確認
```java
this.con = DriverManager.getConnection(
  "jdbc:mysql://localhost/relay_novel?useSSL=false", ユーザー名ここ, パスワードここ
  ); // 知らない場合は "root" と "" で試す
```
- Create tables
```sql
create table user(user_id int auto_increment not null primary key, name varchar(255) unique not null, password varchar(255) not null, birthday date not null, gender int not null);
```