# AndroidHelper

##集成方式

最新版本参见`Release`

1. Gradle
```
    // root build.gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
	// module build.gradle
	dependencies {
    	 compile 'com.github.didikee:AndroidHelper:v0.0.1'
    }
```

2. maven
```
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
	
	<dependency>
	    <groupId>com.github.didikee</groupId>
	    <artifactId>AndroidHelper</artifactId>
	    <version>v0.0.1</version>
	</dependency>
```

## 兼容Android新的文件系统，配置 path_authorities

建议 path_authorities 设置为ApplicationID
```
<string name="path_authorities"> </string>
```

