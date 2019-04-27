package com.msgc.service;

import com.msgc.constant.enums.TableStatusEnum;
import com.msgc.entity.Table;
import com.msgc.entity.dto.TableDTO;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import java.util.List;

public interface ITableService {

    Table save(Table table);

    Table findById(Integer id);

    boolean deleteById(Integer operatorId, Integer tableId);

    List<Table> findAllByExample(Table tableExample);

    List<Table> findAllById(List<Integer> tableIdList);

    void increaseFilledNum(Integer tableId);

    boolean stopById(Integer operatorId, Integer tableId);

    List<Table> findAllActiveTable(Integer owner);

    String export();

    void processTableData(Integer tableId, boolean isOwner, Model model);

    Page<Table> getPageTable(int pageNumber, int pageSize);

    List<Table> searchByNameAndState(String tableName, TableStatusEnum state);

    //组装tableDTO
    List<TableDTO> constructTableDTO(List<Table> tableList);

    void addLikeTable(Integer tableId);

    void save(List<Table> endTableList);
}
