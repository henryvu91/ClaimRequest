package com.vn.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Working {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "staff_id")
    private Integer staffId;
    @Basic
    @Column(name = "project_id")
    private Integer projectId;
    @Basic
    @Column(name = "job_rank_id")
    private Integer jobRankId;
    @Basic
    @Column(name = "start_date")
    private LocalDate startDate;
    @Basic
    @Column(name = "end_date")
    private LocalDate endDate;
    @OneToMany(mappedBy = "workingByWorkingId", fetch = FetchType.LAZY)
    private List<Claim> claimsById = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "staff_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Staff staffByStaffId;
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Project projectByProjectId;
    @ManyToOne
    @JoinColumn(name = "job_rank_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private JobRank jobRankByJobRankId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Working working = (Working) o;

        if (id != working.id) return false;
        if (staffId != working.staffId) return false;
        if (projectId != working.projectId) return false;
        if (jobRankId != working.jobRankId) return false;
        if (startDate != null ? !startDate.equals(working.startDate) : working.startDate != null) return false;
        if (endDate != null ? !endDate.equals(working.endDate) : working.endDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + staffId;
        result = 31 * result + projectId;
        result = 31 * result + jobRankId;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }
}
