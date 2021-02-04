package com.spring.spring.repo.condot;

import com.spring.spring.models.condot.tbl_crpt_dataupload;
import com.spring.spring.models.condot.tbl_job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface dataUploadRepo  extends JpaRepository<tbl_crpt_dataupload, Long> {

    @Query(value = "SELECT [ID]\n" +
            "      ,[JobID]\n" +
            "      ,[reportId]\n" +
            "  FROM [rT2_0ER02_Server].[dbo].[tbl_CRPT_DataUpload]\n" +
            "  Where JobID=?1",nativeQuery = true)
    List<tbl_crpt_dataupload> findReportId(String jobid);
}
