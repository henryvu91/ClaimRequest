package com.vn.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class Claim {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "working_id")
    private int workingId;
    @Basic
    @Column(name = "date")
    private Date date;
    @Basic
    @Column(name = "from_time")
    private Object fromTime;
    @Basic
    @Column(name = "to_time")
    private Object toTime;
    @Basic
    @Column(name = "total_hours")
    private Integer totalHours;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "remarks")
    private String remarks;
    @Basic
    @Column(name = "audit_trail")
    private String auditTrail;
    @ManyToOne
    @JoinColumn(name = "working_id", referencedColumnName = "id", nullable = false)
    private Working workingByWorkingId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWorkingId() {
        return workingId;
    }

    public void setWorkingId(int workingId) {
        this.workingId = workingId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Object getFromTime() {
        return fromTime;
    }

    public void setFromTime(Object fromTime) {
        this.fromTime = fromTime;
    }

    public Object getToTime() {
        return toTime;
    }

    public void setToTime(Object toTime) {
        this.toTime = toTime;
    }

    public Integer getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Integer totalHours) {
        this.totalHours = totalHours;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getAuditTrail() {
        return auditTrail;
    }

    public void setAuditTrail(String auditTrail) {
        this.auditTrail = auditTrail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Claim claim = (Claim) o;

        if (id != claim.id) return false;
        if (workingId != claim.workingId) return false;
        if (date != null ? !date.equals(claim.date) : claim.date != null) return false;
        if (fromTime != null ? !fromTime.equals(claim.fromTime) : claim.fromTime != null) return false;
        if (toTime != null ? !toTime.equals(claim.toTime) : claim.toTime != null) return false;
        if (totalHours != null ? !totalHours.equals(claim.totalHours) : claim.totalHours != null) return false;
        if (status != null ? !status.equals(claim.status) : claim.status != null) return false;
        if (remarks != null ? !remarks.equals(claim.remarks) : claim.remarks != null) return false;
        if (auditTrail != null ? !auditTrail.equals(claim.auditTrail) : claim.auditTrail != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + workingId;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (fromTime != null ? fromTime.hashCode() : 0);
        result = 31 * result + (toTime != null ? toTime.hashCode() : 0);
        result = 31 * result + (totalHours != null ? totalHours.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (remarks != null ? remarks.hashCode() : 0);
        result = 31 * result + (auditTrail != null ? auditTrail.hashCode() : 0);
        return result;
    }

    public Working getWorkingByWorkingId() {
        return workingByWorkingId;
    }

    public void setWorkingByWorkingId(Working workingByWorkingId) {
        this.workingByWorkingId = workingByWorkingId;
    }
}
