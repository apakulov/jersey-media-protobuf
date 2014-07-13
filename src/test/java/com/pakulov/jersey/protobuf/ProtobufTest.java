/**
 * Copyright 2014 Alexander Pakulov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pakulov.jersey.protobuf;

import com.pakulov.jersey.protobuf.internal.MediaTypeExt;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import javax.ws.rs.*;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;

import static com.pakulov.jersey.protobuf.Example.*;
import static org.junit.Assert.*;

public class ProtobufTest extends JerseyTest {
    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(ProtobufResource.class);
    }

    @Path("/")
    public static class ProtobufResource {
        @GET
        @Produces(MediaTypeExt.APPLICATION_PROTOBUF)
        public Person getPerson() {
            return buildPerson();
        }

        @POST
        @Consumes(MediaTypeExt.APPLICATION_PROTOBUF)
        public String createPerson(Person person) {
            return person.toString();
        }
    }

    @Test
    public void serializePerson() {
        Person person = target("/").request(MediaTypeExt.APPLICATION_PROTOBUF).get(Person.class);
        assertEquals(1, person.getId());
        assertEquals("John Doe", person.getName());
        assertEquals("john.doe@google.com", person.getEmail());
    }

    @Test
    public void deserilizePerson() {
        String result = target("/").request().post(Entity.entity(buildPerson(), MediaTypeExt.APPLICATION_PROTOBUF_TYPE), String.class);
        assertEquals("name: \"John Doe\"\nid: 1\nemail: \"john.doe@google.com\"\n", result);
    }

    @Test(expected = BadRequestException.class)
    public void emptyBody() {
        target("/").request().post(Entity.entity(null, MediaTypeExt.APPLICATION_PROTOBUF_TYPE), String.class);
    }

    public static Person buildPerson() {
        return Person.newBuilder()
                .setId(1)
                .setName("John Doe")
                .setEmail("john.doe@google.com").build();
    }
}
