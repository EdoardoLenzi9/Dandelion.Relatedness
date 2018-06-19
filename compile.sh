javac \
-d \
./Dandelion.Relatedness/build/classes \
-classpath \
./Dandelion.Relatedness/build/classes:./Dandelion.Relatedness/lib:./Dandelion.Relatedness/lib/dsiutils-deps:./Dandelion.Relatedness/lib/SizeOf_0_2_2:./Dandelion.Relatedness/lib/dsiutils-deps/animal-sniffer-annotations-1.14.jar:./Dandelion.Relatedness/lib/dsiutils-deps/commons-collections-20040616.jar:./Dandelion.Relatedness/lib/dsiutils-deps/commons-configuration-1.10.jar:./Dandelion.Relatedness/lib/dsiutils-deps/commons-io-2.5.jar:./Dandelion.Relatedness/lib/dsiutils-deps/commons-lang-2.6.jar:./Dandelion.Relatedness/lib/dsiutils-deps/commons-logging-1.1.1.jar:./Dandelion.Relatedness/lib/dsiutils-deps/commons-math3-3.6.1.jar:./Dandelion.Relatedness/lib/dsiutils-deps/error_prone_annotations-2.0.18.jar:./Dandelion.Relatedness/lib/dsiutils-deps/fastutil-8.1.0.jar:./Dandelion.Relatedness/lib/dsiutils-deps/guava-23.2-jre.jar:./Dandelion.Relatedness/lib/dsiutils-deps/j2objc-annotations-1.1.jar:./Dandelion.Relatedness/lib/dsiutils-deps/jsap-2.1.jar:./Dandelion.Relatedness/lib/dsiutils-deps/jsr305-1.3.9.jar:./Dandelion.Relatedness/lib/dsiutils-deps/logback-classic-1.2.3.jar:./Dandelion.Relatedness/lib/dsiutils-deps/logback-core-1.2.3.jar:./Dandelion.Relatedness/lib/dsiutils-deps/slf4j-api-1.7.25.jar:./Dandelion.Relatedness/lib/SizeOf_0_2_2/SizeOf.jar:./Dandelion.Relatedness/lib/ObjectSizeFetcher.jar:./Dandelion.Relatedness/lib/dsiutils-2.3.6.jar \
-sourcepath \
./Dandelion.Relatedness/build/empty \
-target \
1.8 \
-encoding \
UTF-8 \
-g \
-processorpath \
./Dandelion.Relatedness/lib:./Dandelion.Relatedness/lib/dsiutils-deps:./Dandelion.Relatedness/lib/SizeOf_0_2_2:./Dandelion.Relatedness/lib/dsiutils-deps/animal-sniffer-annotations-1.14.jar:./Dandelion.Relatedness/lib/dsiutils-deps/commons-collections-20040616.jar:./Dandelion.Relatedness/lib/dsiutils-deps/commons-configuration-1.10.jar:./Dandelion.Relatedness/lib/dsiutils-deps/commons-io-2.5.jar:./Dandelion.Relatedness/lib/dsiutils-deps/commons-lang-2.6.jar:./Dandelion.Relatedness/lib/dsiutils-deps/commons-logging-1.1.1.jar:./Dandelion.Relatedness/lib/dsiutils-deps/commons-math3-3.6.1.jar:./Dandelion.Relatedness/lib/dsiutils-deps/error_prone_annotations-2.0.18.jar:./Dandelion.Relatedness/lib/dsiutils-deps/fastutil-8.1.0.jar:./Dandelion.Relatedness/lib/dsiutils-deps/guava-23.2-jre.jar:./Dandelion.Relatedness/lib/dsiutils-deps/j2objc-annotations-1.1.jar:./Dandelion.Relatedness/lib/dsiutils-deps/jsap-2.1.jar:./Dandelion.Relatedness/lib/dsiutils-deps/jsr305-1.3.9.jar:./Dandelion.Relatedness/lib/dsiutils-deps/logback-classic-1.2.3.jar:./Dandelion.Relatedness/lib/dsiutils-deps/logback-core-1.2.3.jar:./Dandelion.Relatedness/lib/dsiutils-deps/slf4j-api-1.7.25.jar:./Dandelion.Relatedness/lib/SizeOf_0_2_2/SizeOf.jar:./Dandelion.Relatedness/lib/ObjectSizeFetcher.jar:./Dandelion.Relatedness/lib/dsiutils-2.3.6.jar:./Dandelion.Relatedness/build/empty \
-s \
./Dandelion.Relatedness/build/generated-sources/ap-source-output \
-source \
1.8 \
./Dandelion.Relatedness/src/eu/dandelion/Domain/RelatednessHashMap.java \
./Dandelion.Relatedness/src/eu/dandelion/Domain/RelatednessMatrix.java \
./Dandelion.Relatedness/src/eu/dandelion/Dump/DumpGenerator.java \
./Dandelion.Relatedness/src/eu/dandelion/Implementations/HashMapImplementation.java \
./Dandelion.Relatedness/src/eu/dandelion/Implementations/NativeImplementation.java \
./Dandelion.Relatedness/src/eu/dandelion/Main.java \
./Dandelion.Relatedness/src/eu/dandelion/Memory/MemoryMoniton.java

jar cmvf ./Dandelion.Relatedness/manifest.mf ./Dandelion.Relatedness/dist/Dandelion.jar ./Dandelion.Relatedness/build/classes/* 