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
  <version>0.0.2-SNAPSHOT</version>
</dependency>
```

### Gradle
`compile 'net.savantly:gateway-spring-boot-starter:0.0.2-SNAPSHOT'`

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

## Further Configuration
If you want to intercept the request or response, you can extend the [GatewayEventHandlerAdapter](./src/main/java/net/savantly/gateway/autoconfigure/GatewayEventHandlerAdapter.java) and supply it as a Bean.  
There are 'on' events that are called before the request is proxied to the destination, and 'after' events that are called before the proxied response is returned to the client.  

You can short-circuit the proxy request by returning a response in the 'on' event handler.  
If you return null, the request is proxied to the destination server.  

The child path `/gateway/{child}` is passed into the methods, along with the request from the client, or the response entity from the proxied server.  
You can use the 'after' events to modify the reponse before sending it back to the client.  

```
		@Bean
		public GatewayEventHandler gatewayEventHandler() {
			return new GatewayEventHandlerAdapter() {
				
				@Override
				public ResponseEntity onGet(String child, ProxyExchange<byte[]> proxy) throws Exception {
					log.info("intercepted in onGet()");
					latch.countDown();
					return super.onGet(child, proxy);
				}
				
				@Override
				public ResponseEntity afterGet(String child, ResponseEntity<byte[]> proxy) throws Exception {
					log.info("intercepted in afterGet()");
					latch.countDown();
					return super.afterGet(child, proxy);
				}
				
			};
		}
```

## Dependencies  

The Greenwich branch is used to get the `org.springframework.cloud:spring-cloud-gateway-mvc` dependency.

```
dependencyManagement {
    imports {
        mavenBom "org.springframework.cloud:spring-cloud-dependencies:Greenwich.SR2"
    }
}
```

