package com.msgc.service;

import com.msgc.constant.enums.TableStatusEnum;
import com.msgc.entity.Table;
import com.msgc.entity.dto.TableDTO;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import java.util.List;

public interface ITableService {

    Table save(Table table);

    void save(List<Table> endTableList);

    Table findById(Integer id);

    List<Table> findAllByExample(Table tableExample);

    List<Table> findAllById(List<Integer> tableIdList);

    List<Table> findAllActiveTable(Integer owner);

    List<Table> searchByNameAndState(String tableName, TableStatusEnum state);

    void increaseFilledNum(Integer tableId);

    boolean stopById(Integer operatorId, Integer tableId);

    boolean deleteById(Integer operatorId, Integer tableId);

    String export();

    void processTableData(Integer tableId, boolean isOwner, Model model);

    Page<Table> getPageTable(int pageNumber, int pageSize);

    //组装tableDTO
    List<TableDTO> constructTableDTO(List<Table> tableList);

    void addLikeTable(Integer tableId);

    List<Table> findRecentCreateByOwner(Integer owner, Integer limitNum);

}
