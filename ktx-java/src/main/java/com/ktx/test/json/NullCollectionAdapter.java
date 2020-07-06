package com.ktx.test.json;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 描述:
 * <p>
 * author zys
 * create by 2020/7/3
 */

abstract class NullCollectionAdapter<C extends Collection<T>, T> extends JsonAdapter<C> {
    public static final JsonAdapter.Factory FACTORY = new JsonAdapter.Factory() {
        @Override
        public @Nullable
        JsonAdapter<?> create(
                Type type, Set<? extends Annotation> annotations, Moshi moshi) {
            Class<?> rawType = Types.getRawType(type);
            if (!annotations.isEmpty()) return null;
            if (rawType == List.class || rawType == Collection.class) {
                return newArrayListAdapter(type, moshi);
            } else if (rawType == Set.class) {
                return newLinkedHashSetAdapter(type, moshi);
            }
            return null;
        }
    };

    private final JsonAdapter<T> elementAdapter;

    private NullCollectionAdapter(JsonAdapter<T> elementAdapter) {
        this.elementAdapter = elementAdapter;
    }

    static <T> JsonAdapter<Collection<T>> newArrayListAdapter(Type type, Moshi moshi) {
        Type elementType = Types.collectionElementType(type, Collection.class);
        JsonAdapter<T> elementAdapter = moshi.adapter(elementType);
        return new NullCollectionAdapter<Collection<T>, T>(elementAdapter) {
            @Override
            Collection<T> newCollection() {
                return new ArrayList<>();
            }
        };
    }

    static <T> JsonAdapter<Set<T>> newLinkedHashSetAdapter(Type type, Moshi moshi) {
        Type elementType = Types.collectionElementType(type, Collection.class);
        JsonAdapter<T> elementAdapter = moshi.adapter(elementType);
        return new NullCollectionAdapter<Set<T>, T>(elementAdapter) {
            @Override
            Set<T> newCollection() {
                return new LinkedHashSet<>();
            }
        };
    }

    abstract C newCollection();

    @Override
    public C fromJson(JsonReader reader) throws IOException {
        System.out.println("coll");
        if (reader.peek() == JsonReader.Token.NULL) {
            reader.nextNull();
            return newCollection();
        }
        C result = newCollection();
        reader.beginArray();
        while (reader.hasNext()) {
            result.add(elementAdapter.fromJson(reader));
        }
        reader.endArray();
        return result;
    }

    @Override
    public void toJson(JsonWriter writer, C value) throws IOException {
        writer.beginArray();
        for (T element : value) {
            elementAdapter.toJson(writer, element);
        }
        writer.endArray();
    }

    @Override
    public String toString() {
        return elementAdapter + ".collection()";
    }
}