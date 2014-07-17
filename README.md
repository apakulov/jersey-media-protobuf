Protobuf Provider for Jersey 2.x
================================

Install
-------
Maven
```
<dependency>
  <groupId>com.pakulov.jersey.media</groupId>
  <artifactId>jersey-media-protobuf</artifactId>
  <version>0.1.0</version>
</dependency>
```

Gradle
```
compile 'com.pakulov.jersey.media:jersey-media-protobuf:0.1.0'
```

Usage
-----
Extension implements Auto-Discovery mechanism in Jersey. If you have it disable - you should register Feature class.
``` java
public class ExampleApplication extends ResourceConfig {
    public ExampleApplication() {
        super(ProtobufFeature.class);
    }
}
```

Examples
--------
Server
``` java
@Path("/")
public static class ProtobufResource {
    @GET
    @Produces(MediaTypeExt.APPLICATION_PROTOBUF)
    public Person getPerson() {
        return buildPerson();
    }
}
```

Client
``` java
getClient().target("/").request(MediaTypeExt.APPLICATION_PROTOBUF).get(Person.class);
```

License
-------
    Copyright 2014 Alexander Pakulov
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.