How to install the protoc compiler?
-------------------------------------------------------------------------------
https://github.com/protocolbuffers/protobuf/blob/master/src/README.md

 git clone https://github.com/protocolbuffers/protobuf.git
 cd protobuf
 git submodule update --init --recursive
 ./autogen.sh

 ./configure --prefix=/usr
 make
 make check
 sudo make install
 sudo ldconfig # refresh shared library cache.


How to invoke the protoc compiler?
-------------------------------------------------------------------------------

$ protoc -I=./ --java_out=./ hello.proto

