package com.ktx.test.json;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;


/**
 * 描述:基础类型
 * <p>
 * author zys
 * create by 2020/7/3 13:02
 */
final class NullStandardAdapter {

    private static final boolean BOOLEAN_DEFAULT = false;
    private static final Double DOUBLE_DEFAULT = 0.0;
    private static final Float FLOAT_DEFAULT = 0f;
    private static final Integer INTEGER_DEFAULT = 0;
    private static final Long LONG_DEFAULT = 0L;
    private static final String STRING_DEFAULT = "";

    private NullStandardAdapter() {
    }

    public static final JsonAdapter.Factory FACTORY = new JsonAdapter.Factory() {
        @Override
        public JsonAdapter<?> create(
                Type type, Set<? extends Annotation> annotations, Moshi moshi) {
            if (type == Boolean.class) return BOOLEAN_JSON_ADAPTER;
            if (type == Double.class) return DOUBLE_JSON_ADAPTER;
            if (type == Float.class) return FLOAT_JSON_ADAPTER;
            if (type == Integer.class) return INTEGER_JSON_ADAPTER;
            if (type == Long.class) return LONG_JSON_ADAPTER;
            if (type == String.class) return STRING_JSON_ADAPTER;
            return null;
        }
    };
    static final JsonAdapter<Boolean> BOOLEAN_JSON_ADAPTER = new JsonAdapter<Boolean>() {
        @Override
        public Boolean fromJson(JsonReader reader) throws IOException {
            if (reader.peek() == JsonReader.Token.NULL) {
                reader.nextNull();
                return BOOLEAN_DEFAULT;
            }
            return reader.nextBoolean();
        }

        @Override
        public void toJson(JsonWriter writer, Boolean value) throws IOException {
            writer.value(value.booleanValue());
        }

        @Override
        public String toString() {
            return "JsonAdapter(Boolean)";
        }
    };

    static final JsonAdapter<Double> DOUBLE_JSON_ADAPTER = new JsonAdapter<Double>() {
        @Override
        public Double fromJson(JsonReader reader) throws IOException {
            if (reader.peek() == JsonReader.Token.NULL) {
                reader.nextNull();
                return DOUBLE_DEFAULT;
            }
            return reader.nextDouble();
        }

        @Override
        public void toJson(JsonWriter writer, Double value) throws IOException {
            writer.value(value.doubleValue());
        }

        @Override
        public String toString() {
            return "JsonAdapter(Double)";
        }
    };

    static final JsonAdapter<Float> FLOAT_JSON_ADAPTER = new JsonAdapter<Float>() {
        @Override
        public Float fromJson(JsonReader reader) throws IOException {
            if (reader.peek() == JsonReader.Token.NULL) {
                reader.nextNull();
                return FLOAT_DEFAULT;
            }
            float value = (float) reader.nextDouble();
            // Double check for infinity after float conversion; many doubles > Float.MAX
            if (!reader.isLenient() && Float.isInfinite(value)) {
                throw new JsonDataException("JSON forbids NaN and infinities: " + value
                        + " at path " + reader.getPath());
            }
            return value;
        }

        @Override
        public void toJson(JsonWriter writer, Float value) throws IOException {
            // Manual null pointer check for the float.class adapter.
            if (value == null) {
                throw new NullPointerException();
            }
            // Use the Number overload so we write out float precision instead of double precision.
            writer.value(value);
        }

        @Override
        public String toString() {
            return "JsonAdapter(Float)";
        }
    };

    static final JsonAdapter<Integer> INTEGER_JSON_ADAPTER = new JsonAdapter<Integer>() {
        @Override
        public Integer fromJson(JsonReader reader) throws IOException {
            if (reader.peek() == JsonReader.Token.NULL) {
                reader.nextNull();
                return INTEGER_DEFAULT;
            }
            return reader.nextInt();
        }

        @Override
        public void toJson(JsonWriter writer, Integer value) throws IOException {
            writer.value(value.intValue());
        }

        @Override
        public String toString() {
            return "JsonAdapter(Integer)";
        }
    };

    static final JsonAdapter<Long> LONG_JSON_ADAPTER = new JsonAdapter<Long>() {
        @Override
        public Long fromJson(JsonReader reader) throws IOException {
            if (reader.peek() == JsonReader.Token.NULL) {
                reader.nextNull();
                return LONG_DEFAULT;
            }
            return reader.nextLong();
        }

        @Override
        public void toJson(JsonWriter writer, Long value) throws IOException {
            writer.value(value.longValue());
        }

        @Override
        public String toString() {
            return "JsonAdapter(Long)";
        }
    };


    static final JsonAdapter<String> STRING_JSON_ADAPTER = new JsonAdapter<String>() {

        @Override
        public String fromJson(JsonReader reader) throws IOException {
            if (reader.peek() == JsonReader.Token.NULL) {
                reader.nextNull();
                return STRING_DEFAULT;
            }
            return reader.nextString();
        }

        @Override
        public void toJson(JsonWriter writer, String value) throws IOException {
            writer.value(value);
        }

        @Override
        public String toString() {
            return "JsonAdapter(String)";
        }
    };
}


