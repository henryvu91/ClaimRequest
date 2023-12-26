package com.vn.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Claim {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "working_id")
    private Integer workingId;
    @Basic
    @Column(name = "date")
    private LocalDate date;
    @Basic
    @Column(name = "from_time")
    private LocalTime fromTime;
    @Basic
    @Column(name = "to_time")
    private LocalTime toTime;
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
    @JoinColumn(name = "working_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Working workingByWorkingId;

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

}
