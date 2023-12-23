package com.vn.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
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
    @Column(name = "staft_id")
    private Integer staftId;
    @Basic
    @Column(name = "project_id")
    private Integer projectId;
    @Basic
    @Column(name = "role_in_project_id")
    private Integer roleInProjectId;
    @Basic
    @Column(name = "start_date")
    private LocalDate startDate;
    @Basic
    @Column(name = "end_date")
    private LocalDate endDate;
    @OneToMany(mappedBy = "workingByWorkingId")
    @ToString.Exclude
    private List<Claim> claimsById = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "staft_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Staff staffByStaffId;
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Project projectByProjectId;
    @ManyToOne
    @JoinColumn(name = "role_in_project_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private RoleInProject roleInProjectByRoleInProjectId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Working working = (Working) o;

        if (id != working.id) return false;
        if (staftId != working.staftId) return false;
        if (projectId != working.projectId) return false;
        if (roleInProjectId != working.roleInProjectId) return false;
        if (startDate != null ? !startDate.equals(working.startDate) : working.startDate != null) return false;
        if (endDate != null ? !endDate.equals(working.endDate) : working.endDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + staftId;
        result = 31 * result + projectId;
        result = 31 * result + roleInProjectId;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }
}
