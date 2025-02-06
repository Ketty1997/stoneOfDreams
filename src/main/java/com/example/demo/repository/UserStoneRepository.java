package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Stone;
import com.example.demo.model.UserStone;

public interface UserStoneRepository extends JpaRepository <UserStone, Integer> {

    /*  -stone.id AS stone_id: assegni un alias stone_id alla colonna id della tabella stone.
        -user_stone.id AS user_stone_id: assegni un alias user_stone_id alla colonna id della tabella user_stone.

        In questo modo, ogni colonna id avrà un alias distinto e Spring non avrà più conflitti quando cercherà di mappare i risultati della query agli oggetti Java.*/
    @Query(value = "SELECT stone.id AS stone_id, user_stone.id AS user_stone_id, stone.* FROM stone " +
    "JOIN user_stone ON stone.id = user_stone.id_stone " +
    "WHERE user_stone.id_user = :idUser", nativeQuery = true)
    List<Stone> findStonesByUserId(@Param("idUser") Integer idUser);


}
