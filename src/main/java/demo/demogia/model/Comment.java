package demo.demogia.model;

public class Comment {
    private int commentID;
    private String message;
    private int masterID;
    private int requestID;

    public Comment(String message, int masterID, int requestID) {
        this.message = message;
        this.masterID = masterID;
        this.requestID = requestID;
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMasterID() {
        return masterID;
    }

    public void setMasterID(int masterID) {
        this.masterID = masterID;
    }

    public int getRequestID() {
        return requestID;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentID=" + commentID +
                ", message='" + message + '\'' +
                ", masterID=" + masterID +
                ", requestID=" + requestID +
                '}';
    }
}
