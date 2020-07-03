package com.ktx.test.json;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;

/**
 * 描述:
 * <p>
 * author zys
 * create by 2020/7/3
 */
public final class NullObjectAdapter<T> extends JsonAdapter<T> {

    private final JsonAdapter<T> delegate;

    public NullObjectAdapter(JsonAdapter<T> delegate) {
        this.delegate = delegate;
    }

    public JsonAdapter<T> delegate() {
        return delegate;
    }

    @Override
    public @Nullable
    T fromJson(JsonReader reader) throws IOException {
        if (reader.peek() == JsonReader.Token.NULL) {
            reader.nextNull();
            return (T) new Object();
        } else {
            return delegate.fromJson(reader);
        }
    }

    @Override
    public void toJson(JsonWriter writer, @Nullable T value) throws IOException {
        if (value == null) {
            writer.nullValue();
        } else {
            delegate.toJson(writer, value);
        }
    }

    @Override
    public String toString() {
        return delegate + ".nullSafe()";
    }
}

