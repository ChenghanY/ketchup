package com.james.db.mapper;

import com.james.db.po.FileOutputRecordPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FileOutputRecordMapper {
    FileOutputRecordPO queryOneById(@Param("id") Long id);
    void insertOne(@Param("po") FileOutputRecordPO po);
    void updateOneById(@Param("po") FileOutputRecordPO po);
    List<FileOutputRecordPO> queryListByIds(@Param("ids") List<Long> ids);
    void batchInsert(@Param("list") List<FileOutputRecordPO> list);
    void batchUpdate(@Param("list") List<FileOutputRecordPO> list);
}
