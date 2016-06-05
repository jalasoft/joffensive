package cz.jalasoft.joffensive.core;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Honza Lastovicka (lastovicka@avast.com)
 * @since 2016-04-21.
 */
public final class Recoil {

    public static Recoil.Builder recoil() {
        return new Builder();
    }

    //-----------------------------------------------------------
    //INSTANCE SCOPE
    //-----------------------------------------------------------

    private final Map<String, Object> attributes;

    private Recoil(Builder builder) {
        this.attributes = builder.attributes;
    }

    public Object attribute(String key) {
        return attributes.get(key);
    }

    @Override
    public String toString() {
        return new StringBuilder("Recoil[")
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

        return this.attributes.equals(that.attributes);
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = result * 37 + attributes.hashCode();

        return result;
    }

    //--------------------------------------------------------
    //BUILDER
    //--------------------------------------------------------

    public static final class Builder {

        private final Map<String, Object> attributes = new HashMap<>();

        Builder() {}

        public Builder withAttribute(String key, Object value) {
            //TODO
            attributes.put(key, value);
            return this;
        }

        public Recoil get() {
            return new Recoil(this);
        }
    }
}
