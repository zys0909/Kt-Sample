package com.ktx.test.json;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
//import com.squareup.moshi.LinkedHashTreeMap;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * 描述:
 * <p>
 * author zys
 * create by 2020/7/3
 */
/*final class NullMapAdapter<K, V> extends JsonAdapter<Map<K, V>> {
    public static final JsonAdapter.Factory FACTORY = new Factory() {
        @Override public @Nullable
        JsonAdapter<?> create(
                Type type, Set<? extends Annotation> annotations, Moshi moshi) {
            if (!annotations.isEmpty()) return null;
            Class<?> rawType = Types.getRawType(type);
            if (rawType != Map.class) return null;
            Type[] keyAndValue = Types.mapKeyAndValueTypes(type, rawType);
            return new NullMapAdapter<>(moshi, keyAndValue[0], keyAndValue[1]).nullSafe();
        }
    };

    private final JsonAdapter<K> keyAdapter;
    private final JsonAdapter<V> valueAdapter;

    NullMapAdapter(Moshi moshi, Type keyType, Type valueType) {
        this.keyAdapter = moshi.adapter(keyType);
        this.valueAdapter = moshi.adapter(valueType);
    }

    @Override public void toJson(JsonWriter writer, Map<K, V> map) throws IOException {
        writer.beginObject();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getKey() == null) {
                throw new JsonDataException("Map key is null at " + writer.getPath());
            }
            writer.promoteValueToName();
            keyAdapter.toJson(writer, entry.getKey());
            valueAdapter.toJson(writer, entry.getValue());
        }
        writer.endObject();
    }

    @Override public Map<K, V> fromJson(JsonReader reader) throws IOException {
        LinkedHashTreeMap<K, V> result = new LinkedHashTreeMap<>();
        reader.beginObject();
        while (reader.hasNext()) {
            reader.promoteNameToValue();
            K name = keyAdapter.fromJson(reader);
            V value = valueAdapter.fromJson(reader);
            V replaced = result.put(name, value);
            if (replaced != null) {
                throw new JsonDataException("Map key '" + name + "' has multiple values at path "
                        + reader.getPath() + ": " + replaced + " and " + value);
            }
        }
        reader.endObject();
        return result;
    }

    @Override public String toString() {
        return "JsonAdapter(" + keyAdapter + "=" + valueAdapter + ")";
    }
}*/

