package com.primitive.SmartFactoryServer.DAO;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {
    @CreatedDate//최초 생성시간
    private LocalDateTime createdDate;
    @LastModifiedDate//마지막 수정시간
    private LocalDateTime modifiedDate;
}
