package cz.jalasoft.joffensive.core;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-21.
 */
public final class Recoil {

    public enum Type {
        SUCCESS, FAILURE;
    }

    public static Recoil.Builder success() {
        return new Builder(Type.SUCCESS);
    }

    public static Recoil.Builder failure() {
        return new Builder(Type.FAILURE);
    }

    public static Recoil.Builder result(Type type) {
        return new Builder(type);
    }

    //-----------------------------------------------------------
    //INSTANCE SCOPE
    //-----------------------------------------------------------

    private final Type type;
    private final long durationMillis;
    private final Map<String, Object> attributes;

    private Recoil(Builder builder) {
        this.type = builder.type;
        this.durationMillis = builder.durationMillis;
        this.attributes = builder.attributes;
    }

    public boolean isSuccess() {
        return type == Type.SUCCESS;
    }

    public Object attribute(String key) {
        return attributes.get(key);
    }

    public long durationMillis() {
        return durationMillis;
    }

    @Override
    public String toString() {
        return new StringBuilder("Recoil[")
                .append(type)
                .append(": ")
                .append(durationMillis)
                .append("ms, attributes=")
                .append(attributes)
                .append("]")
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Recoil)) {
            return false;
        }

        Recoil that = (Recoil) obj;

        if (this.type != that.type) {
            return false;
        }

        if (this.durationMillis != that.durationMillis) {
            return false;
        }

        return this.attributes.equals(that.attributes);
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = result * 37 + type.hashCode();
        result = result * 37 + (int)(durationMillis^(durationMillis>>>32));
        result = result * 37 + attributes.hashCode();

        return result;
    }

    //--------------------------------------------------------
    //BUILDER
    //--------------------------------------------------------

    public static final class Builder {

        private final Type type;
        private long durationMillis;
        private final Map<String, Object> attributes = new HashMap<>();

        Builder(Type type) {
            this.type = type;
        }

        public Builder lastingMillis(long millis) {
            //TODO
            this.durationMillis = millis;
            return this;
        }

        public Builder havingAttribute(String key, Object value) {
            //TODO
            attributes.put(key, value);
            return this;
        }

        public Recoil get() {
            return new Recoil(this);
        }
    }
}
