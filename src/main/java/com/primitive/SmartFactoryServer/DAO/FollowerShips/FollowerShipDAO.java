package com.primitive.SmartFactoryServer.DAO.FollowerShips;

import com.primitive.SmartFactoryServer.DAO.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@NoArgsConstructor
@Getter
public class FollowerShipDAO extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long index;
    @Column(columnDefinition = "LONG",nullable = false)
    private Long followerUserIndex;
    @Column(columnDefinition = "LONG", nullable = false)
    private Long followUserIndex;
    @Column(columnDefinition = "BOOLEAN", nullable = false)
    private boolean enable=false;
    public void followAccept(){
        enable=true;
    }

    public void followDisable(){
        enable=false;
    }
    @Builder
    public FollowerShipDAO(Long followerUserIndex, Long followUserIndex) {
        this.followerUserIndex = followerUserIndex;//새 값 추가할 때는 userID로 index 구해서 매개변수로 삽입하기.
        this.followUserIndex = followUserIndex;

    }}