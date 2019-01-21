package com.auto.applet.violation.common.vo;

public class ViolationVO {

    public ViolationVO(String cSideUserId, String carId) {
        super();
        this.cSideUserId = cSideUserId;
        this.carId = carId;
    }

    private String cSideUserId;
    private String carId;

    public String getcSideUserId() {
        return cSideUserId;
    }

    public void setcSideUserId(String cSideUserId) {
        this.cSideUserId = cSideUserId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    @Override
    public String toString() {
        return "ViolationVO{" +
                "cSideUserId='" + cSideUserId + '\'' +
                ", carId='" + carId + '\'' +
                '}';
    }
}
