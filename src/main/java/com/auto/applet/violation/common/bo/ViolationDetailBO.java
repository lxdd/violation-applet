package com.auto.applet.violation.common.bo;

import java.io.Serializable;
import java.util.Date;

public class ViolationDetailBO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String violationLocation;

    private String violationReason;

    private Integer fine;

    private Integer degree;

    private Date violationOccurTime;

    public String getViolationLocation() {
        return violationLocation;
    }

    public void setViolationLocation(String violationLocation) {
        this.violationLocation = violationLocation;
    }

    public String getViolationReason() {
        return violationReason;
    }

    public void setViolationReason(String violationReason) {
        this.violationReason = violationReason;
    }

    public Integer getFine() {
        return fine;
    }

    public void setFine(Integer fine) {
        this.fine = fine;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public Date getViolationOccurTime() {
        return violationOccurTime;
    }

    public void setViolationOccurTime(Date violationOccurTime) {
        this.violationOccurTime = violationOccurTime;
    }

    @Override
    public String toString() {
        return "ViolationDetailBO{" +
                "violationLocation='" + violationLocation + '\'' +
                ", violationReason='" + violationReason + '\'' +
                ", fine=" + fine +
                ", degree=" + degree +
                ", violationOccurTime=" + violationOccurTime +
                '}';
    }
}
