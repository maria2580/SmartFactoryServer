package com.primitive.SmartFactoryServer.DAO.FollowerShips;

import com.primitive.SmartFactoryServer.DAO.BaseTimeEntity;
import com.primitive.SmartFactoryServer.DAO.users.UsersDAO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "FollowerShipDAO")
public class FollowerShipDAO extends BaseTimeEntity {
    @Id
    @Column(name="followerShipIndex")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long index;
    @ManyToOne
    @JoinColumn(name="followerUserIndex")
    private UsersDAO followerUser;
    @ManyToOne
    @JoinColumn(name="followUserIndex")
    private UsersDAO followUser;
    @Column(columnDefinition = "BOOLEAN", nullable = false)
    private boolean enable=false;
    public void followAccept(){
        enable=true;
    }

    public void followDisable(){
        enable=false;
    }
    @Builder
    public FollowerShipDAO(UsersDAO followerUser, UsersDAO followUser) {
        this.followerUser = followerUser;//새 값 추가할 때는 userID로 index 구해서 매개변수로 삽입하기.
        this.followUser = followUser;

    }}