package net.mapoint.dao.entity;

import java.sql.Timestamp;

public interface CreatedUpdatedAt {

    void setCreatedAt(Timestamp createdAt);

    void setUpdatedAt(Timestamp updatedAt);

}
