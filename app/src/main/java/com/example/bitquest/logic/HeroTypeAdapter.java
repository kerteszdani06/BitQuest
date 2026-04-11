package com.example.bitquest.logic;

import com.example.bitquest.model.Archer;
import com.example.bitquest.model.Cleric;
import com.example.bitquest.model.Hero;
import com.example.bitquest.model.Knight;
import com.example.bitquest.model.Necromancer;
import com.example.bitquest.model.Wizard;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class HeroTypeAdapter implements JsonSerializer<Hero>, JsonDeserializer<Hero> {

    private static final String TYPE_FIELD = "type";
    private static final String DATA_FIELD = "data";

    @Override
    public JsonElement serialize(Hero src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty(TYPE_FIELD, src.getClass().getSimpleName());
        jsonObject.add(DATA_FIELD, context.serialize(src));

        return jsonObject;
    }

    @Override
    public Hero deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get(TYPE_FIELD).getAsString();
        JsonElement data = jsonObject.get(DATA_FIELD);

        switch (type) {
            case "Knight":
                return context.deserialize(data, Knight.class);
            case "Archer":
                return context.deserialize(data, Archer.class);
            case "Cleric":
                return context.deserialize(data, Cleric.class);
            case "Wizard":
                return context.deserialize(data, Wizard.class);
            case "Necromancer":
                return context.deserialize(data, Necromancer.class);
            default:
                throw new JsonParseException("Unknown hero type: " + type);
        }
    }
}
