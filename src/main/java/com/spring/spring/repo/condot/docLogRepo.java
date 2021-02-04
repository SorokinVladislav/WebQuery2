package com.spring.spring.repo.condot;

import com.spring.spring.models.condot.tbl_crpt_documentlog;
import com.spring.spring.models.condot.tbl_job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface docLogRepo extends JpaRepository<tbl_crpt_documentlog, Long> {


    @Query(value = "SELECT JobName, JobStatus, id, jobid, xmltype, document_receipt, document_id, transactionstatus, updateddate,\n" +
            "rT2_0ER02_Server.dbo.tbl_Product_Pkg_Master.PRODUCT_NAME\n" +
            "FROM [rT2_0ER02_Server].[dbo].[tbl_CRPT_DocumentLog]\n" +
            "left join tbl_Job on JobID=JID\n" +
            "left join tbl_Product_Pkg_Master on tbl_Job.Prod_Pkg_ID=tbl_Product_Pkg_Master.Prod_Pkg_ID\n" +
            "where [JobID]= ?1",nativeQuery = true)
    List<tbl_crpt_documentlog> findByJIDD(String jid);


    @Query(value =  "SELECT JobName, JobStatus, id, jobid, xmltype, document_receipt, document_id, transactionstatus, updateddate,\n" +
            "rT2_0ER02_Server.dbo.tbl_Product_Pkg_Master.PRODUCT_NAME\n" +
            "FROM [rT2_0ER02_Server].[dbo].[tbl_CRPT_DocumentLog]\n" +
            "left join tbl_Job on JobID=JID\n" +
            "left join tbl_Product_Pkg_Master on tbl_Job.Prod_Pkg_ID=tbl_Product_Pkg_Master.Prod_Pkg_ID\n" +
            "  where XMLType=213 and TransactionStatus=7 and JobStatus!=16 and JobStatus!=13 and jobid=?1",nativeQuery = true)
    List<tbl_crpt_documentlog> is213Success(String jobid);



    @Query(value = "SELECT JobName, JobStatus, id, jobid, xmltype, document_receipt, document_id, transactionstatus, updateddate,\n" +
            "rT2_0ER02_Server.dbo.tbl_Product_Pkg_Master.PRODUCT_NAME\n" +
            "FROM [rT2_0ER02_Server].[dbo].[tbl_CRPT_DocumentLog]\n" +
            "left join tbl_Job on JobID=JID\n" +
            "left join tbl_Product_Pkg_Master on tbl_Job.Prod_Pkg_ID=tbl_Product_Pkg_Master.Prod_Pkg_ID\n" +
            "\t  WHERE  DATEDIFF ( HOUR , [rT2_0ER02_Server].[dbo].[tbl_Job].LastUpdatedDate, SYSDATETIME () ) >2 and  [rT2_0ER02_Server].[dbo].[tbl_Job].JobStatus=13 \n" +
            "\t  and [rT2_0ER02_Server].[dbo].[tbl_CRPT_DocumentLog].TransactionStatus!=7 order by UpdatedDate desc",nativeQuery = true)
    List<tbl_crpt_documentlog> findJobsWithMistakes();

    @Query(value ="SELECT JobName, JobStatus, id, jobid, xmltype, document_receipt, document_id, transactionstatus, updateddate,\n" +
            "rT2_0ER02_Server.dbo.tbl_Product_Pkg_Master.PRODUCT_NAME\n" +
            "FROM [rT2_0ER02_Server].[dbo].[tbl_CRPT_DocumentLog]\n" +
            "left join tbl_Job on JobID=JID\n" +
            "left join tbl_Product_Pkg_Master on tbl_Job.Prod_Pkg_ID=tbl_Product_Pkg_Master.Prod_Pkg_ID\n" +
            " where [JobID]= ?1 and xmltype=?2",nativeQuery = true)
    List<tbl_crpt_documentlog> findByJIDDandXMLType(String jid, String xmltype);


    @Transactional
    @Modifying
    @Query(value = "UPDATE [rT2_0ER02_Server].[dbo].[tbl_CRPT_DocumentLog] SET [TransactionStatus] = 7 where [JobID]= ?1 and xmltype=?2",nativeQuery = true)
    void setJobStatus7(String jid, String xmltype);

    @Transactional
    @Modifying
    @Query(value = "UPDATE [rT2_0ER02_Server].[dbo].[tbl_CRPT_DocumentLog] " +
            "SET \n" +
            "       [Document_GUID] = NULL\n" +
            "      ,[Code] = NULL\n" +
            "      ,[Token] = NULL\n" +
            "      ,[Document_ID] = NULL\n" +
            "      ,[Document_Link] = NULL\n" +
            "      ,[Document_Receipt_Link] = NULL\n" +
            "      ,[Document_Receipt] = NULL\n" +
            "      ,[TransactionStatus] = 0\n" +
            "      ,[CreatedDate] = NULL\n" +
            "      ,[UpdatedDate] = NULL where [JobID]= ?1 and xmltype=?2",nativeQuery = true)
    void resendReport(String jid, String xmltype);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM [rT2_0ER02_Server].[dbo].[tbl_CRPT_DataUpload]\n" +
            "      where [JobID]= ?1",nativeQuery = true)
    void resend9151_1(String jid);

    @Transactional
    @Modifying
    @Query(value = "UPDATE [rT2_0ER02_Server].[dbo].[tbl_Job] \n" +
            "  SET DataTransferStage=4 WHERE JID=?1",nativeQuery = true)
    void resend9151_2(String jid);

    @Transactional
    @Modifying
    @Query(value = "UPDATE [rT2_0ER02_Server].[dbo].[tbl_CRPT_DocumentLog]\n" +
            "                        SET \n" +
            "                         [Document_GUID] = NULL\n" +
            "                             ,[Code] = NULL\n" +
            ", Signed_Code_Path = NULL, Signed_Document_Path = NULL, Base64_Document_Path = NULL\n" +
            "                             ,[Token] = NULL\n" +
            "                              ,[Document_ID] = NULL\n" +
            "                              ,[Document_Link] = NULL\n" +
            "                              ,[Document_Receipt_Link] = NULL\n" +
            "                              ,[Document_Receipt] = NULL\n" +
            "                              ,[TransactionStatus] = 0\n" +
            "                             WHERE XMLType='9151' AND JobID=?1",nativeQuery = true)
    void resend9151_3(String jid);


}
