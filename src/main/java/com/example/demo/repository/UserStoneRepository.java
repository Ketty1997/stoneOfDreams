package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Stone;
import com.example.demo.model.UserStone;

import jakarta.transaction.Transactional;

public interface UserStoneRepository extends JpaRepository <UserStone, Integer> {

    /*  -stone.id AS stone_id: assegni un alias stone_id alla colonna id della tabella stone.
        -user_stone.id AS user_stone_id: assegni un alias user_stone_id alla colonna id della tabella user_stone.

        In questo modo, ogni colonna id avrà un alias distinto e Spring non avrà più conflitti quando cercherà di mappare i risultati della query agli oggetti Java.*/
    @Query(value = "SELECT stone.id AS stone_id, user_stone.id AS user_stone_id, stone.* FROM stone " +
    "JOIN user_stone ON stone.id = user_stone.id_stone " +
    "WHERE user_stone.id_user = :idUser", nativeQuery = true)
    List<Stone> findStonesByUserId(@Param("idUser") Integer idUser);


    /*
    L'annotazione @Modifying in Spring Data JPA viene utilizzata per indicare che la query eseguirà un'operazione di modifica dei dati nel database, come INSERT, UPDATE o DELETE.

    Di default, i metodi annotati con @Query sono considerati solo in lettura, quindi senza @Modifying, un'operazione DELETE non verrebbe eseguita correttamente.

    Quando serve @Modifying?
    DELETE → Quando vuoi eliminare dati con una query personalizzata.
    UPDATE → Quando vuoi aggiornare record nel database con una query personalizzata.
    INSERT → In alcuni casi, se vuoi inserire dati direttamente con una query SQL nativa.

    * L'annotazione @Modifying richiede che il metodo venga eseguito all'interno di una transazione. Se il metodo non viene chiamato all'interno di una transazione, potresti ricevere errori o il DELETE potrebbe non essere eseguito correttamente.
    */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM user_stone WHERE user_stone.id_stone = :id", nativeQuery = true)
    void deleteByStoneId(@Param("id")int id);

    boolean existsByUserIdAndStoneId(int userId, int stoneId);
    
    //query che mi restiruisce una lista di id di pietre associate ad un utente
    //stiamo filtrando tutte le righe in cui l'ID dell'utente è uguale al userId che passi come parametro. Questo ti permette di ottenere solo le pietre associate a un determinato utente.
    @Query("SELECT us.stone.id FROM UserStone us WHERE us.user.id = :userId")
    List<Integer> findStoneIdsByUserId(@Param("userId") int userId);
    
    //in pratica la query cerca tutte le righe delle colonne in userStone dove idUtente corrisponde a quello che passo nel parametro userID e quindi estrae gli id delle pietre stone.id assiociate a quell'utente
}
