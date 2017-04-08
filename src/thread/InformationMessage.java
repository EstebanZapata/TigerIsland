package thread;

public class InformationMessage extends Message {
    private String information;

    public InformationMessage(String information) {
        this.information = information;
    }

    public String getInformation() {
        return this.information;
    }

}
