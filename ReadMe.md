relaynovel
===
# Class Diagram

![](https://github.com/hiroya8649/relaynovel/blob/master/Class.png?raw=true)
# Terminal指令集
## Mac

- コンパイル
```
javac @sourcefile
```
- 実行
```
java -cp .:class:mysql-connector-java-8.0.15.jar Main
```

# MySQL
- Create database
```sql
create database relay_novel;
```
- 作成後 `use relay_novel;` を忘れないこと。
- DBAdapterのconstructor中のDB用のuser名とパスワードを確認
```java
this.con = DriverManager.getConnection(
  "jdbc:mysql://localhost/relay_novel?useSSL=false", ユーザー名ここ, パスワードここ
  ); // 知らない場合は "root" と "" で試す
```
- Create tables
```sql
create table user(user_id int auto_increment not null primary key, name varchar(255) unique not null, password varchar(255) not null, birthday date not null, gender int not null);

create table novel(novel_id int auto_increment not null primary key, user_id int not null, title varchar(255) not null, date datetime not null);

create table text(text_id int auto_increment not null primary key, novel_id int not null, user_id int not null, text text not null, date datetime not null);

create table review_novel(review_novel_id int auto_increment not null primary key, user_id int not null, novel_id int not null, text text not null, score int not null, date datetime not null);

create table review_text(review_text_id int auto_increment not null primary key, user_id int not null, text_id int not null, text text not null, score int not null, date datetime not null);
```
- ここまでこればコンパイルして実行可能です。
- CSVロード
  - 表とファイル名を自分のものに変更する必要あり
```sql
load data local infile "~/Downloads/text.csv" into table text fields terminated by ',' lines terminated by '\r\n' ignore 1 rows;
```
