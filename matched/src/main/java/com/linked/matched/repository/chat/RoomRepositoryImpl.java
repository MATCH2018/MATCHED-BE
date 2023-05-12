package com.linked.matched.repository.chat;

import com.linked.matched.entity.Room;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Room> getRoom() {
        return null;
    }
}
