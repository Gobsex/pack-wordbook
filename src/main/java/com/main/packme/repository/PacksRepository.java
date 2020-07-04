package com.main.packme.repository;

import com.main.packme.dao.entity.Pack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PacksRepository extends JpaRepository<Pack,Long> {
    Pack findById(long id);
//    @Query(nativeQuery = true,value = "select name from packs where owner_id=:ownerId")
//    List<String> findNamesByOwnerId(@Param("ownerId") long ownerId);
//    Pack findByName(String name);
    List<Pack> findAllByType(String type);
//    @Transactional
//    @Modifying
//    @Query(value ="update packs set word_list = JSON_ARRAY_APPEND(word_list, '$.words', cast(:word as json)) where id =:id", nativeQuery = true)
//    void addWordById(@Param("word")String wordJson, @Param("id")long id);
//
//
//
//    @Transactional
//    @Modifying
//    @Query(value ="update packs set word_list = json_remove(word_list,:indexes) where id =:id", nativeQuery = true)
//    void removeWordById(@Param("indexes")String indexes, @Param("id")long id);
//    @Modifying
//    @Transactional
//    @Query(nativeQuery = true, value = "update packs set word_list = cast(:wordList as json) where id=:id")
//    void saveWordListById(@Param("wordList") String wordList, @Param("id") long id);
//    boolean existsByWordList(String wordList);
//    Pack findByNameAndOwnerId(String name, long ownerId);
//    @Modifying
//    @Transactional
//    @Query(nativeQuery = true, value = "update packs set name =:name where id=:id")
//    void savePackNameById(@Param("name") String name,@Param("id") long id);


    void deleteById(long id);
}
