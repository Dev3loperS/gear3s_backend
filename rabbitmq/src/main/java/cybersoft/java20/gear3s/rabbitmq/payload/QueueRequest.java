package cybersoft.java20.gear3s.rabbitmq.payload;

public class QueueRequest {
    private int productId;
    private int requestNums;

    public QueueRequest() {
    }

    public QueueRequest(int productId, int requestNums) {
        this.productId = productId;
        this.requestNums = requestNums;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getRequestNums() {
        return requestNums;
    }

    public void setRequestNums(int requestNums) {
        this.requestNums = requestNums;
    }
}
