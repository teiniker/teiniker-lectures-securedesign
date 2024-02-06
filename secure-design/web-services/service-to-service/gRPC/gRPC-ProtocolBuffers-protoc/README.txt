How to invoke the protoc compiler?
-------------------------------------------------------------------------------

$ mkdir -p ./target/generated-sources/protobuf/java/
$ protoc -I=./ --java_out=./target/generated-sources/protobuf/java/ ./src/main/resources/person.proto

$ mvn test


References:
-------------------------------------------------------------------------------
Language Guide (proto3)
https://developers.google.com/protocol-buffers/docs/proto3

