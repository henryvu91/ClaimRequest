package com.vn.model;

import com.vn.utils.validateGroup.ValidateCreateStaffGroup;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.thymeleaf.util.Validate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Staff {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "department_id")
    @NotBlank(groups = {ValidateCreateStaffGroup.class},message = "{MSG8}")
    private Integer departmentId;
    @Basic
    @Column(name = "role_id")
    @NotBlank(groups = {ValidateCreateStaffGroup.class},message = "{MSG8}")
    private Integer roleId;
    @Basic
    @Column(name = "email")
    @NotBlank(groups = {ValidateCreateStaffGroup.class},message = "{MSG8}")
    @Email(groups = {ValidateCreateStaffGroup.class},message = "{MSG19}")
    private String email;
    @Basic
    @Column(name = "password")
    @NotBlank(groups = {ValidateCreateStaffGroup.class},message = "{MSG8}")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$",groups = {ValidateCreateStaffGroup.class},message = "{MSG20}")
    private String password;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "salary")
    @Range(min = 1L,groups = {ValidateCreateStaffGroup.class},message = "{MSG8}")
    @NotBlank(groups = {ValidateCreateStaffGroup.class})
    private BigDecimal salary;
    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Department departmentByDepartmentId;
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Role roleByRoleId;
    @OneToMany(mappedBy = "staffByStaffId", fetch = FetchType.LAZY)
    private List<Working> workingsById = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Staff staff = (Staff) o;

        if (id != staff.id) return false;
        if (departmentId != staff.departmentId) return false;
        if (roleId != staff.roleId) return false;
        if (email != null ? !email.equals(staff.email) : staff.email != null) return false;
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
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (salary != null ? salary.hashCode() : 0);
        return result;
    }
}
