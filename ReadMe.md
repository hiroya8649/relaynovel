relaynovel
===
# Terminal指令集
## Mac
- 前回コンパイル済みのclassファイルを削除(MAC OSX用、 WINDOWSは要確認)
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

## Windows

まずはMain.javaとmysql-connector-java-8.0.15.jarをフォルダー取り出して、
フォルダーをrelaynovelにリネームして、
Main.javaの一行目のpackage relaynovel;を消してからコンパイルしてください。
- コンパイル
```
javac -encoding UTF-8 -cp .;mysql-connector-java-8.0.15.jar; *.java
```
- 実行
```
java -cp .;mysql-connector-java-8.0.15.jar; Main
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

create table review_text(review_text int auto_increment not null primary key, user_id int not null, text_id int not null, text text not null, score int not null, date datetime not null);
```
- ここまでこればコンパイルして実行可能です。
- CSVロード
  - 表とファイル名を自分のものに変更する必要あり
```sql
load data local infile "~/Downloads/text.csv" into table text fields terminated by ',' lines terminated by '\r\n' ignore 1 rows;
```
