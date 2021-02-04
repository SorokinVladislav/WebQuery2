package com.spring.spring.repo.condot;

import com.spring.spring.models.condot.tbl_production_packaging_data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface packDataRepo extends JpaRepository<tbl_production_packaging_data, Long> {

    @Query(value = "SELECT [PackDtlsID]," +
            "       [JobID]\n" +
            "      ,[Pkg_Level]\n" +
            "      ,[UIDCode]\n" +
            "      ,[NextLevel_UIDCode]\n" +
            "      ,[Prod_Pkg_ID]\n" +
            "      ,[IsPrinted]\n" +
            "      ,[IsInspected]\n" +
            "      ,[IsRejected]\n" +
            "      ,[IsSampled]\n" +
            "      ,[IsAggregated]" +
            "  FROM [rT2_0ER02_Server].[dbo].[tbl_Production_Packaging_Data]\n" +
            "       where JobID= ?1 order by NextLevel_UIDCode",nativeQuery = true)
    List<tbl_production_packaging_data> findByJIDD(String jid);

    @Query(value = "SELECT [PackDtlsID]," +
            "       [JobID]\n" +
            "      ,[Pkg_Level]\n" +
            "      ,[UIDCode]\n" +
            "      ,[NextLevel_UIDCode]\n" +
            "      ,[Prod_Pkg_ID]\n" +
            "      ,[IsPrinted]\n" +
            "      ,[IsInspected]\n" +
            "      ,[IsRejected]\n" +
            "      ,[IsSampled]\n" +
            "      ,[IsAggregated]" +
            "  FROM [rT2_0ER02_Server].[dbo].[tbl_Production_Packaging_Data]\n" +
            "       where UIDCode= ?1",nativeQuery = true)
    List<tbl_production_packaging_data> findByUID(String UIDCode);





}
