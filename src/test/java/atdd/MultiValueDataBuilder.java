package atdd;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class MultiValueDataBuilder {
    private MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();

    private MultiValueDataBuilder() {
    }

    public static MultiValueDataBuilder builder() {
        return new MultiValueDataBuilder();
    }

    public MultiValueMap build() {
        return this.params;
    }

    public MultiValueDataBuilder addParameter(String key, Object value) {
        this.params.add(key, value);
        return this;
    }
}
