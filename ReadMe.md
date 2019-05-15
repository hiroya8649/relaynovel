relaynovel
===
# 指令集
- コンパイル済みのファイルを削除
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