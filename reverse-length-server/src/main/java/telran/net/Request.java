package telran.net;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Request {
    private RequestType type;
    private String value;

    public Request() {
    }

    public Request(RequestType type, String value) {
        this.type = type;
        this.value = value;
    }

    public RequestType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        String res = null;
        try {
            res = objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return res;
    }
}
