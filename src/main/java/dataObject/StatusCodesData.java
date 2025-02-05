package dataObject;

public enum StatusCodesData {
    SUCCESS_200(200),
    Order_Not_Found_404(404),
    Internal_Server_Error_500(500);

    private final int value;

    StatusCodesData(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}