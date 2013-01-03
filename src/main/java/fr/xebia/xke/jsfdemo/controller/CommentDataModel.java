package fr.xebia.xke.jsfdemo.controller;

import fr.xebia.xke.jsfdemo.dao.SlotDao;
import fr.xebia.xke.jsfdemo.entity.Comment;
import fr.xebia.xke.jsfdemo.entity.Slot;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * Example datamodel pour lazy load + paginated comments
 */
public class CommentDataModel extends LazyDataModel<Comment> {

    private final SlotDao slotDao;

    private final Integer slotId;

    private final Integer totalCommentsForSlot;

    public CommentDataModel(SlotDao slotDao, Integer slotId, Integer totalCommentsForSlot) {
        this.slotDao = slotDao;
        this.slotId = slotId;
        this.totalCommentsForSlot = totalCommentsForSlot;
    }

    @Override
    public List<Comment> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        setRowCount(totalCommentsForSlot);
        return slotDao.getCommentsForSlot(slotId, first, pageSize);
    }
}
