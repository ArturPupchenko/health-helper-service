package com.alevel.java.healthhepler.model.group;


import com.alevel.java.healthhepler.model.training.Training;
import com.alevel.java.healthhepler.model.user.HealthHelperUser;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "groups_users",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "user_id"))
    private Set<HealthHelperUser> users = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private HealthHelperUser admin;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<HealthHelperUser> getUsers() {
        return users;
    }

    public void setUsers(Set<HealthHelperUser> users) {
        this.users = users;
    }

    public HealthHelperUser getAdmin() {
        return admin;
    }

    public void setAdmin(HealthHelperUser admin) {
        this.admin = admin;
    }
}
