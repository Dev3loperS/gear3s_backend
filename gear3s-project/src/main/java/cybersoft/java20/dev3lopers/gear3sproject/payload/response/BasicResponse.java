package cybersoft.java20.dev3lopers.gear3sproject.payload.response;

public class BasicResponse {
    private String message;
    private Object data;

    public BasicResponse() {
    }

    public BasicResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
