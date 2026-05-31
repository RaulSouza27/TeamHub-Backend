package teamhub.teamhub_backend.dto;

public class DocumentSubmissionRequestDTO {
    private String rgBase64;
    private String cpfBase64;
    private String workCardBase64;

    public String getRgBase64() {
        return rgBase64;
    }

    public void setRgBase64(String rgBase64) {
        this.rgBase64 = rgBase64;
    }

    public String getCpfBase64() {
        return cpfBase64;
    }

    public void setCpfBase64(String cpfBase64) {
        this.cpfBase64 = cpfBase64;
    }

    public String getWorkCardBase64() {
        return workCardBase64;
    }

    public void setWorkCardBase64(String workCardBase64) {
        this.workCardBase64 = workCardBase64;
    }
}
