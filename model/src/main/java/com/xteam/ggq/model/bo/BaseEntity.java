package com.xteam.ggq.model.bo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 创建日期
     */
    @Column(nullable = false, updatable = false)
    private Timestamp createTime;

    /**
     * 修改日期
     */
    @Column(nullable = false)
    private Timestamp modifyTime;

    /** 乐观锁 */
    @Version
    private int version;

    @PrePersist
    private void onCreate() {
        modifyTime = createTime = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    private void onUpdate() {
        modifyTime = new Timestamp(System.currentTimeMillis());
    }

}
