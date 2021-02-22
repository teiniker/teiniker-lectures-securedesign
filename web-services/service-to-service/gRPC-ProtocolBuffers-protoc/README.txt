How to install the protoc compiler?
-------------------------------------------------------------------------------
https://github.com/protocolbuffers/protobuf/blob/master/src/README.md

$ sudo apt-get install autoconf automake libtool curl make g++ unzip

$ git clone https://github.com/protocolbuffers/protobuf.git
$ cd protobuf
$ git submodule update --init --recursive
$ ./autogen.sh

$ ./configure --prefix=/opt
$ make
$ sudo make install
$ sudo ldconfig

$ vi .bashrc
export PATH=$JAVA_HOME/bin:$ANT_HOME/bin:$M2_HOME/bin:/opt/bin:$PATH

$ protoc --version


How to invoke the protoc compiler?
-------------------------------------------------------------------------------

$ mkdir -p ./target/generated-sources/java
$ protoc -I=./ --java_out=./target/generated-sources/java ./src/main/resources/person.proto

$ mvn test


References:
-------------------------------------------------------------------------------
Language Guide (proto3)
https://developers.google.com/protocol-buffers/docs/proto3

