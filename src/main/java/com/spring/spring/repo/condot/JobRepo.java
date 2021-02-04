package com.spring.spring.repo.condot;

import com.spring.spring.models.condot.tbl_job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository

public interface JobRepo extends JpaRepository<tbl_job, Long> {

    @Query(value = "SELECT TOP (100) JID, rT2_0ER02_Server.dbo.tbl_Job.JobName, rT2_0ER02_Server.dbo.tbl_Job.LastUpdatedDate, rT2_0ER02_Server.dbo.tbl_Job.DataTransferStage,rT2_0ER02_Server.dbo.tbl_Job.JobStatus ,\n" +
            "                   rT2_0ER02_Server.dbo.tbl_Product_Pkg_Master.PRODUCT_NAME, GTIN, rT2_0ER02_Server.dbo.tbl_Job.BatchNo, [rT2_0ER02_Server].[dbo].[tbl_Line_Master].[Line_Name], [rT2_0ER02_Server].[dbo].[tbl_Job].[Quantity]\n" +
            "   FROM rT2_0ER02_Server.dbo.tbl_Job\n" +
            "                                   LEFT JOIN [rT2_0ER02_Server].[dbo].tbl_Product_Pkg_Master\n" +
            "                                 ON [rT2_0ER02_Server].[dbo].[tbl_Job].Prod_Pkg_ID=[rT2_0ER02_Server].[dbo].[tbl_Product_Pkg_Master].Prod_Pkg_ID\n" +
            "                   LEFT JOIN [rT2_0ER02_Server].[dbo].tbl_CRPT_OrderBookingMaster\n" +
            "                  ON [rT2_0ER02_Server].[dbo].[tbl_Job].[JID]=[rT2_0ER02_Server].[dbo].[tbl_CRPT_OrderBookingMaster].[JobID]\n" +
            "                   LEFT JOIN [rT2_0ER02_Server].[dbo].[tbl_Line_Master]\n" +
            "                  ON [rT2_0ER02_Server].[dbo].[tbl_Job].[LineID]=[rT2_0ER02_Server].[dbo].[tbl_Line_Master].[Line_ID]\n" +
            "             where jid=?1", nativeQuery = true)
    List<tbl_job> findJobDetails(String jid);


    @Query(value = "  SELECT TOP (100) JID, rT2_0ER02_Server.dbo.tbl_Job.JobName, rT2_0ER02_Server.dbo.tbl_Job.LastUpdatedDate, rT2_0ER02_Server.dbo.tbl_Job.DataTransferStage,rT2_0ER02_Server.dbo.tbl_Job.JobStatus ,\n" +
            "        rT2_0ER02_Server.dbo.tbl_Product_Pkg_Master.PRODUCT_NAME, GTIN,[rT2_0ER02_Server].[dbo].[tbl_Job].[Quantity],  rT2_0ER02_Server.dbo.tbl_Job.BatchNo, [rT2_0ER02_Server].[dbo].[tbl_Line_Master].[Line_Name] FROM rT2_0ER02_Server.dbo.tbl_Job\n" +
            "                       LEFT JOIN [rT2_0ER02_Server].[dbo].tbl_Product_Pkg_Master\n" +
            "                      ON [rT2_0ER02_Server].[dbo].[tbl_Job].Prod_Pkg_ID=[rT2_0ER02_Server].[dbo].[tbl_Product_Pkg_Master].Prod_Pkg_ID\n" +
            "       LEFT JOIN [rT2_0ER02_Server].[dbo].tbl_CRPT_OrderBookingMaster\n" +
            "      ON [rT2_0ER02_Server].[dbo].[tbl_Job].[JID]=[rT2_0ER02_Server].[dbo].[tbl_CRPT_OrderBookingMaster].[JobID]\n" +
            "       LEFT JOIN [rT2_0ER02_Server].[dbo].[tbl_Line_Master]\n" +
            "      ON [rT2_0ER02_Server].[dbo].[tbl_Job].[LineID]=[rT2_0ER02_Server].[dbo].[tbl_Line_Master].[Line_ID]\n" +
            "                                      order by JID desc", nativeQuery = true)
    List<tbl_job> findALL4();


    @Query(value = "  SELECT TOP (100) JID, rT2_0ER02_Server.dbo.tbl_Job.JobName, rT2_0ER02_Server.dbo.tbl_Job.LastUpdatedDate, rT2_0ER02_Server.dbo.tbl_Job.DataTransferStage,rT2_0ER02_Server.dbo.tbl_Job.JobStatus,\n" +
            "        rT2_0ER02_Server.dbo.tbl_Product_Pkg_Master.PRODUCT_NAME, [rT2_0ER02_Server].[dbo].[tbl_Job].[Quantity], GTIN, rT2_0ER02_Server.dbo.tbl_Job.BatchNo, [rT2_0ER02_Server].[dbo].[tbl_Line_Master].[Line_Name] FROM rT2_0ER02_Server.dbo.tbl_Job\n" +
            "                       LEFT JOIN [rT2_0ER02_Server].[dbo].tbl_Product_Pkg_Master\n" +
            "                      ON [rT2_0ER02_Server].[dbo].[tbl_Job].Prod_Pkg_ID=[rT2_0ER02_Server].[dbo].[tbl_Product_Pkg_Master].Prod_Pkg_ID\n" +
            "       LEFT JOIN [rT2_0ER02_Server].[dbo].tbl_CRPT_OrderBookingMaster\n" +
            "      ON [rT2_0ER02_Server].[dbo].[tbl_Job].[JID]=[rT2_0ER02_Server].[dbo].[tbl_CRPT_OrderBookingMaster].[JobID]\n" +
            "       LEFT JOIN [rT2_0ER02_Server].[dbo].[tbl_Line_Master]\n" +
            "      ON [rT2_0ER02_Server].[dbo].[tbl_Job].[LineID]=[rT2_0ER02_Server].[dbo].[tbl_Line_Master].[Line_ID]\n" +
            "      Where  rT2_0ER02_Server.dbo.tbl_Job.BatchNo= ?1 \n" +
            "                                      order by JID desc", nativeQuery = true)
    List<tbl_job> findByBatch(String batch);

    @Query(value = "  SELECT TOP (100) JID, rT2_0ER02_Server.dbo.tbl_Job.JobName, rT2_0ER02_Server.dbo.tbl_Job.LastUpdatedDate, rT2_0ER02_Server.dbo.tbl_Job.DataTransferStage,rT2_0ER02_Server.dbo.tbl_Job.JobStatus,\n" +
            "        rT2_0ER02_Server.dbo.tbl_Product_Pkg_Master.PRODUCT_NAME, GTIN,[rT2_0ER02_Server].[dbo].[tbl_Job].[Quantity],  rT2_0ER02_Server.dbo.tbl_Job.BatchNo, [rT2_0ER02_Server].[dbo].[tbl_Line_Master].[Line_Name] FROM rT2_0ER02_Server.dbo.tbl_Job\n" +
            "                       LEFT JOIN [rT2_0ER02_Server].[dbo].tbl_Product_Pkg_Master\n" +
            "                      ON [rT2_0ER02_Server].[dbo].[tbl_Job].Prod_Pkg_ID=[rT2_0ER02_Server].[dbo].[tbl_Product_Pkg_Master].Prod_Pkg_ID\n" +
            "       LEFT JOIN [rT2_0ER02_Server].[dbo].tbl_CRPT_OrderBookingMaster\n" +
            "      ON [rT2_0ER02_Server].[dbo].[tbl_Job].[JID]=[rT2_0ER02_Server].[dbo].[tbl_CRPT_OrderBookingMaster].[JobID]\n" +
            "       LEFT JOIN [rT2_0ER02_Server].[dbo].[tbl_Line_Master]\n" +
            "      ON [rT2_0ER02_Server].[dbo].[tbl_Job].[LineID]=[rT2_0ER02_Server].[dbo].[tbl_Line_Master].[Line_ID]\n" +
            "      Where  rT2_0ER02_Server.dbo.tbl_Job.JobName= ?1 \n" +
            "                                      order by JID desc", nativeQuery = true)
    List<tbl_job> findByJobname(String JobName);


    @Query(value = "  SELECT TOP (100) JID, rT2_0ER02_Server.dbo.tbl_Job.JobName, rT2_0ER02_Server.dbo.tbl_Job.LastUpdatedDate, rT2_0ER02_Server.dbo.tbl_Job.DataTransferStage,rT2_0ER02_Server.dbo.tbl_Job.JobStatus,\n" +
            "        rT2_0ER02_Server.dbo.tbl_Product_Pkg_Master.PRODUCT_NAME, GTIN,[rT2_0ER02_Server].[dbo].[tbl_Job].[Quantity], rT2_0ER02_Server.dbo.tbl_Job.BatchNo, [rT2_0ER02_Server].[dbo].[tbl_Line_Master].[Line_Name] FROM rT2_0ER02_Server.dbo.tbl_Job\n" +
            "                       LEFT JOIN [rT2_0ER02_Server].[dbo].tbl_Product_Pkg_Master\n" +
            "                      ON [rT2_0ER02_Server].[dbo].[tbl_Job].Prod_Pkg_ID=[rT2_0ER02_Server].[dbo].[tbl_Product_Pkg_Master].Prod_Pkg_ID\n" +
            "       LEFT JOIN [rT2_0ER02_Server].[dbo].tbl_CRPT_OrderBookingMaster\n" +
            "      ON [rT2_0ER02_Server].[dbo].[tbl_Job].[JID]=[rT2_0ER02_Server].[dbo].[tbl_CRPT_OrderBookingMaster].[JobID]\n" +
            "       LEFT JOIN [rT2_0ER02_Server].[dbo].[tbl_Line_Master]\n" +
            "      ON [rT2_0ER02_Server].[dbo].[tbl_Job].[LineID]=[rT2_0ER02_Server].[dbo].[tbl_Line_Master].[Line_ID]\n" +
            "      Where  JID= ?1 \n" +
            "                                      order by JID desc", nativeQuery = true)
    List<tbl_job> findByJobID(String jobid);

    @Query(value = "  SELECT TOP (100) JID, rT2_0ER02_Server.dbo.tbl_Job.JobName, rT2_0ER02_Server.dbo.tbl_Job.LastUpdatedDate, rT2_0ER02_Server.dbo.tbl_Job.DataTransferStage,rT2_0ER02_Server.dbo.tbl_Job.JobStatus,\n" +
            "        rT2_0ER02_Server.dbo.tbl_Product_Pkg_Master.PRODUCT_NAME, GTIN, [rT2_0ER02_Server].[dbo].[tbl_Job].[Quantity], rT2_0ER02_Server.dbo.tbl_Job.BatchNo, [rT2_0ER02_Server].[dbo].[tbl_Line_Master].[Line_Name] FROM rT2_0ER02_Server.dbo.tbl_Job\n" +
            "                       LEFT JOIN [rT2_0ER02_Server].[dbo].tbl_Product_Pkg_Master\n" +
            "                      ON [rT2_0ER02_Server].[dbo].[tbl_Job].Prod_Pkg_ID=[rT2_0ER02_Server].[dbo].[tbl_Product_Pkg_Master].Prod_Pkg_ID\n" +
            "       LEFT JOIN [rT2_0ER02_Server].[dbo].tbl_CRPT_OrderBookingMaster\n" +
            "      ON [rT2_0ER02_Server].[dbo].[tbl_Job].[JID]=[rT2_0ER02_Server].[dbo].[tbl_CRPT_OrderBookingMaster].[JobID]\n" +
            "       LEFT JOIN [rT2_0ER02_Server].[dbo].[tbl_Line_Master]\n" +
            "      ON [rT2_0ER02_Server].[dbo].[tbl_Job].[LineID]=[rT2_0ER02_Server].[dbo].[tbl_Line_Master].[Line_ID]\n" +
            "      Where rT2_0ER02_Server.dbo.tbl_Job.JobStatus=13 \n" +
            "                                      order by JID desc", nativeQuery = true)
    List<tbl_job> findClosedJobs();

    @Query(value = "  SELECT TOP (100) JID, rT2_0ER02_Server.dbo.tbl_Job.JobName, rT2_0ER02_Server.dbo.tbl_Job.LastUpdatedDate, rT2_0ER02_Server.dbo.tbl_Job.DataTransferStage,rT2_0ER02_Server.dbo.tbl_Job.JobStatus,\n" +
            "        rT2_0ER02_Server.dbo.tbl_Product_Pkg_Master.PRODUCT_NAME, GTIN,[rT2_0ER02_Server].[dbo].[tbl_Job].[Quantity],  rT2_0ER02_Server.dbo.tbl_Job.BatchNo, [rT2_0ER02_Server].[dbo].[tbl_Line_Master].[Line_Name] FROM rT2_0ER02_Server.dbo.tbl_Job\n" +
            "                       LEFT JOIN [rT2_0ER02_Server].[dbo].tbl_Product_Pkg_Master\n" +
            "                      ON [rT2_0ER02_Server].[dbo].[tbl_Job].Prod_Pkg_ID=[rT2_0ER02_Server].[dbo].[tbl_Product_Pkg_Master].Prod_Pkg_ID\n" +
            "       LEFT JOIN [rT2_0ER02_Server].[dbo].tbl_CRPT_OrderBookingMaster\n" +
            "      ON [rT2_0ER02_Server].[dbo].[tbl_Job].[JID]=[rT2_0ER02_Server].[dbo].[tbl_CRPT_OrderBookingMaster].[JobID]\n" +
            "       LEFT JOIN [rT2_0ER02_Server].[dbo].[tbl_Line_Master]\n" +
            "      ON [rT2_0ER02_Server].[dbo].[tbl_Job].[LineID]=[rT2_0ER02_Server].[dbo].[tbl_Line_Master].[Line_ID]\n" +
            "      Where rT2_0ER02_Server.dbo.tbl_Job.JobStatus=12 \n" +
            "                                      order by JID desc", nativeQuery = true)
    List<tbl_job> findSuspendJobs();


    @Transactional
    @Modifying
    @Query(value = "UPDATE [rT2_0ER02_Server].[dbo].[tbl_Job] SET [JobStatus] = 16 where  jid=?1", nativeQuery = true)
    void rejectJob(String jobid);

    @Transactional
    @Modifying
    @Query(value = "UPDATE [rT2_0ER02_Server].[dbo].[tbl_Job] SET [JobStatus] = 4 WHERE  JID=?1 ", nativeQuery = true)
    void uidgeneratedJob(String jobid);

    @Transactional
    @Modifying
    @Query(value = "UPDATE [rT2_0ER02_Server].[dbo].[tbl_Job] SET [JobStatus] = 12 WHERE  JID=?1 ", nativeQuery = true)
    void suspendJob(String jobid);

    //where job reports less than 2
    @Query(value = "SELECT TOP (100) JID, rT2_0ER02_Server.dbo.tbl_Job.JobName, rT2_0ER02_Server.dbo.tbl_Job.LastUpdatedDate, rT2_0ER02_Server.dbo.tbl_Job.DataTransferStage,rT2_0ER02_Server.dbo.tbl_Job.JobStatus,\n" +
            "  rT2_0ER02_Server.dbo.tbl_Product_Pkg_Master.PRODUCT_NAME, GTIN, [rT2_0ER02_Server].[dbo].[tbl_Job].[Quantity], rT2_0ER02_Server.dbo.tbl_Job.BatchNo, [rT2_0ER02_Server].[dbo].[tbl_Line_Master].[Line_Name] FROM rT2_0ER02_Server.dbo.tbl_Job\n" +
            "       LEFT JOIN [rT2_0ER02_Server].[dbo].tbl_Product_Pkg_Master\n" +
            "  ON [rT2_0ER02_Server].[dbo].[tbl_Job].Prod_Pkg_ID=[rT2_0ER02_Server].[dbo].[tbl_Product_Pkg_Master].Prod_Pkg_ID\n" +
            "       LEFT JOIN [rT2_0ER02_Server].[dbo].tbl_CRPT_OrderBookingMaster\n" +
            "  ON [rT2_0ER02_Server].[dbo].[tbl_Job].[JID]=[rT2_0ER02_Server].[dbo].[tbl_CRPT_OrderBookingMaster].[JobID]\n" +
            "        LEFT JOIN [rT2_0ER02_Server].[dbo].[tbl_Line_Master]\n" +
            "  ON [rT2_0ER02_Server].[dbo].[tbl_Job].[LineID]=[rT2_0ER02_Server].[dbo].[tbl_Line_Master].[Line_ID]\n" +
            "  right join [rT2_0ER02_Server].[dbo].[tbl_CRPT_DocumentLog]\n" +
            "  ON [rT2_0ER02_Server].[dbo].[tbl_CRPT_DocumentLog].JobID=JID\n" +
            "  where JobStatus=13 and DATEDIFF ( HOUR , [rT2_0ER02_Server].[dbo].[tbl_Job].LastUpdatedDate, SYSDATETIME () ) >2\n" +
            "  and JID IN\n" +
            "  (SELECT TOP (1000) \n" +
            "   [JobID] \n" +
            "   FROM [rT2_0ER02_Server].[dbo].[tbl_CRPT_DocumentLog]\n" +
            "  inner join tbl_Job\n" +
            "  on JobID=JID\n" +
            "  where JobStatus=13 \n" +
            "    group by JobID having count(XMLType)<2)\n" +
            "  order by JID desc", nativeQuery = true)
    List<tbl_job> withonereport();

    @Query(value = "SELECT TOP (1000) JID, rT2_0ER02_Server.dbo.tbl_Job.JobName, rT2_0ER02_Server.dbo.tbl_Job.LastUpdatedDate, rT2_0ER02_Server.dbo.tbl_Job.DataTransferStage,rT2_0ER02_Server.dbo.tbl_Job.JobStatus,\n" +
            "              rT2_0ER02_Server.dbo.tbl_Product_Pkg_Master.PRODUCT_NAME, GTIN, [rT2_0ER02_Server].[dbo].[tbl_Job].[Quantity], rT2_0ER02_Server.dbo.tbl_Job.BatchNo, [rT2_0ER02_Server].[dbo].[tbl_Line_Master].[Line_Name] FROM rT2_0ER02_Server.dbo.tbl_Job\n" +
            "                   LEFT JOIN [rT2_0ER02_Server].[dbo].tbl_Product_Pkg_Master\n" +
            "              ON [rT2_0ER02_Server].[dbo].[tbl_Job].Prod_Pkg_ID=[rT2_0ER02_Server].[dbo].[tbl_Product_Pkg_Master].Prod_Pkg_ID\n" +
            "                   LEFT JOIN [rT2_0ER02_Server].[dbo].tbl_CRPT_OrderBookingMaster\n" +
            "              ON [rT2_0ER02_Server].[dbo].[tbl_Job].[JID]=[rT2_0ER02_Server].[dbo].[tbl_CRPT_OrderBookingMaster].[JobID]\n" +
            "                    LEFT JOIN [rT2_0ER02_Server].[dbo].[tbl_Line_Master]\n" +
            "              ON [rT2_0ER02_Server].[dbo].[tbl_Job].[LineID]=[rT2_0ER02_Server].[dbo].[tbl_Line_Master].[Line_ID]\n" +
            "              left join [rT2_0ER02_Server].[dbo].[tbl_CRPT_DocumentLog]\n" +
            "              ON [rT2_0ER02_Server].[dbo].[tbl_CRPT_DocumentLog].JobID=JID\n" +
            "              where JobStatus=7 and gtin is NULL  or JID IN\n" +
            "              (SELECT [JobID]  FROM [rT2_0ER02_Server].[dbo].[tbl_CRPT_DocumentLog]\n" +
            "              inner join tbl_Job on JobID=JID where JobStatus=7\n" +
            "              and XMLType=213 and TransactionStatus!=7)\n" +
            "              order by LastUpdatedDate desc", nativeQuery = true)
    List<tbl_job> codeswaiting();


}



