package me.kesx.tool.dao;

import me.kesx.tool.entity.Word;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface WordRepository extends CrudRepository<Word,Integer> {
//    @Query(value = "select w from Word w where Date(w.addDate)  = CURRENT_DATE() or" +
//            " w.dateToRound like "+"%"+"?1"+"%")
//    List<Word>  listToday(String today);

    @Query(value = "select word from Word word where word.finished = 0")
    List<Word>  listNotFinished();

    @Query(value = "select w from Word w where w.stillTough = 1")
    List<Word>  listMarked();

    @Query(value = "select w.dateToHasMarked from Word w where w.wordId= :wordId")
    String queryHasMarked(Integer wordId);

    @Modifying
    @Query(value = "update Word word set word.dateToHasMarked= :updatedHasMarked where word.wordId = :wordId")
    Integer updateMarked( Integer wordId,String updatedHasMarked);

    @Modifying
    @Query(value = "update Word word set word.finished= :isFinished where word.wordId = :wordId")
    Integer updateFinished( Integer wordId,Integer isFinished);

    @Modifying
    @Query(value = "update Word word set word.stillTough= :stillTough where word.wordId = :wordId")
    Integer updateTough( Integer wordId, Integer stillTough);

    @Modifying
    @Query(value = "update Word word set word.wordItem= :wordItem,word.notes= :notes,word.pos= :pos where word.wordId = :wordId")
    Integer updateDetail(String notes,String pos,String wordItem,Integer wordId);
}