package fr.xebia.xke.jsfdemo.controller;

import fr.xebia.xke.jsfdemo.dao.CommentDao;
import fr.xebia.xke.jsfdemo.entity.Comment;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * Example datamodel pour lazy load + paginated comments
 */
public class CommentDataModel extends LazyDataModel<Comment> {

    private final CommentDao commentDao;

    private final Integer slotId;

    private final Integer totalCommentsForSlot;

    public CommentDataModel(CommentDao dao, Integer slotId, Integer totalCommentsForSlot) {
        this.commentDao = dao;
        this.slotId = slotId;
        this.totalCommentsForSlot = totalCommentsForSlot;
    }

    @Override
    public List<Comment> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        setRowCount(totalCommentsForSlot);
        return commentDao.getCommentsForSlot(slotId, first, pageSize);
    }
}
