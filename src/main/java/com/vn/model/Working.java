package com.vn.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Collection;

@Entity
public class Working {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "staft_id")
    private int staftId;
    @Basic
    @Column(name = "project_id")
    private int projectId;
    @Basic
    @Column(name = "role_in_project_id")
    private int roleInProjectId;
    @Basic
    @Column(name = "start_date")
    private Date startDate;
    @Basic
    @Column(name = "end_date")
    private Date endDate;
    @OneToMany(mappedBy = "workingByWorkingId")
    private Collection<Claim> claimsById;
    @ManyToOne
    @JoinColumn(name = "staft_id", referencedColumnName = "id", nullable = false)
    private Staft staftByStaftId;
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false)
    private Project projectByProjectId;
    @ManyToOne
    @JoinColumn(name = "role_in_project_id", referencedColumnName = "id", nullable = false)
    private RoleInProject roleInProjectByRoleInProjectId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStaftId() {
        return staftId;
    }

    public void setStaftId(int staftId) {
        this.staftId = staftId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getRoleInProjectId() {
        return roleInProjectId;
    }

    public void setRoleInProjectId(int roleInProjectId) {
        this.roleInProjectId = roleInProjectId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

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

    public Collection<Claim> getClaimsById() {
        return claimsById;
    }

    public void setClaimsById(Collection<Claim> claimsById) {
        this.claimsById = claimsById;
    }

    public Staft getStaftByStaftId() {
        return staftByStaftId;
    }

    public void setStaftByStaftId(Staft staftByStaftId) {
        this.staftByStaftId = staftByStaftId;
    }

    public Project getProjectByProjectId() {
        return projectByProjectId;
    }

    public void setProjectByProjectId(Project projectByProjectId) {
        this.projectByProjectId = projectByProjectId;
    }

    public RoleInProject getRoleInProjectByRoleInProjectId() {
        return roleInProjectByRoleInProjectId;
    }

    public void setRoleInProjectByRoleInProjectId(RoleInProject roleInProjectByRoleInProjectId) {
        this.roleInProjectByRoleInProjectId = roleInProjectByRoleInProjectId;
    }
}
