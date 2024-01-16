package com.mercadolibre.be_java_hisp_w24_g02.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.mercadolibre.be_java_hisp_w24_g02.dao.UserRelationshipsDTO;

import java.io.IOException;
import java.util.List;

public class UserRelationshipsDTOSerializer extends StdSerializer<UserRelationshipsDTO> {

    public UserRelationshipsDTOSerializer() {
        this(null);
    }

    public UserRelationshipsDTOSerializer(Class<UserRelationshipsDTO> t) {
        super(t);
    }

    @Override
    public void serialize(UserRelationshipsDTO value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("userId", value.userId());
        gen.writeStringField("userName", value.userName());

        // Aquí decides el nombre de la clave
        String relationshipKey = value.isFollowers() ? "followers" : "followed";
        gen.writeObjectField(relationshipKey, value.relationshiDTOList());

        gen.writeEndObject();

    }
}