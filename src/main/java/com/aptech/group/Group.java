package com.aptech.group;

import com.aptech.common.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "group")
public class Group extends AbstractEntity<Long> {
}
