package com.vn.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Staff {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "department_id")
    private Integer departmentId;
    @Basic
    @Column(name = "role_id")
    private Integer roleId;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "salary")
    private BigDecimal salary;
    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id", nullable = false)
    private Department departmentByDepartmentId;
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role roleByRoleId;
    @OneToMany(mappedBy = "staffByStaffId")
    private List<Working> workingsById = new ArrayList<>();

   
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Staff staff = (Staff) o;

        if (id != staff.id) return false;
        if (departmentId != staff.departmentId) return false;
        if (roleId != staff.roleId) return false;
        if (username != null ? !username.equals(staff.username) : staff.username != null) return false;
        if (password != null ? !password.equals(staff.password) : staff.password != null) return false;
        if (name != null ? !name.equals(staff.name) : staff.name != null) return false;
        if (salary != null ? !salary.equals(staff.salary) : staff.salary != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + departmentId;
        result = 31 * result + roleId;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (salary != null ? salary.hashCode() : 0);
        return result;
    }

}
