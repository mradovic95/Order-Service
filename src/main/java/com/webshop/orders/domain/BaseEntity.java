package com.webshop.orders.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Persistable;

import java.time.Instant;

@Data
public abstract class BaseEntity implements Persistable {

    @Id
    private String id;
    @Version
    private Long versions;
    @CreatedDate
    private Instant createdDate;
    @LastModifiedDate
    private Instant lastModifiedDate;

    @Override
    public boolean isNew() {
        return id == null;
    }

}
