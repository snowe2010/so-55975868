package com.promontech.loanapp.common.loan;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;

import org.jetbrains.annotations.NotNull;

@MappedSuperclass
public abstract class BaseId implements Comparable<BaseId>, Serializable {

    @Basic
    protected final UUID id;

    public BaseId(UUID id) {
        this.id = id;
    }

    public BaseId(String id) {
        this(UUID.fromString(id));
    }

    public abstract UUID getId();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseId oId = (BaseId) o;
        return id != null ? id.equals(oId.id) : oId.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    @Override
    public int compareTo(@NotNull BaseId compareId) {
        return this.id.compareTo(compareId.getId());
    }
}
