# gateway-spring-boot-starter
is a simple gateway/proxy configuration you can add to a Spring Boot app.  

It provides a transparent proxy to any web backend

## Quickstart  
- Add the dependency to your spring boot app
- Add proxy configuations to your application properties file

### Maven 
```
<dependency>
  <groupId>net.savantly</groupId>
  <artifactId>gateway-spring-boot-starter</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```

### Gradle
`compile 'net.savantly:gateway-spring-boot-starter:0.0.1-SNAPSHOT'`

### Snapshot releases  
```
<repositories>
    <repository>
        <id>oss-sonatype</id>
        <name>oss-sonatype</name>
        <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
</repositories>
```


## Configuration
Routes are stored in a hash map [path, destination]. To add new routes, create additional properties under `gateway.routes`  

Example Config [yml] -  

```
gateway:
  routes:
    mesh: https://demo.getmesh.io:443
    somewhere: http://myWebApi.com
```

The resulting Rest paths would be valid -  
- /gateway/mesh
- /gateway/somewhere  

## Dependencies  

The Greenwich branch is used to get the `org.springframework.cloud:spring-cloud-gateway-mvc` dependency.

```
dependencyManagement {
    imports {
        mavenBom "org.springframework.cloud:spring-cloud-dependencies:Greenwich.SR2"
    }
}
```

