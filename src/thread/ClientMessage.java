package thread;

public class ClientMessage extends Message {
    private String information;

    public ClientMessage(String information) {
        this.information = information;
    }

    public String getInformation() {
        return this.information;
    }

}
