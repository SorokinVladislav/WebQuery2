package com.spring.spring.repo.condot;

import com.spring.spring.models.condot.tbl_crpt_orderbookingmaster;
import com.spring.spring.models.condot.tbl_job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderBookingRepo extends JpaRepository<tbl_crpt_orderbookingmaster, Long> {

    @Query(value = "SELECT top(20) id, tbl_Job.JID\n" +
            "      ,[tbl_CRPT_OrderBookingMaster].[JobName]\n" +
            "      ,[tbl_CRPT_OrderBookingMaster].[BatchNo]\n" +
            "      ,[GTIN]\n" +
            "      ,[tbl_CRPT_OrderBookingMaster].[Quantity]\n" +
            "      ,[OBReqDT],product_name, OrderId\n" +
            "  FROM [rT2_0ER02_Server].[dbo].[tbl_CRPT_OrderBookingMaster]\n" +
            "  left join [tbl_Job] on tbl_Job.JID=tbl_CRPT_OrderBookingMaster.JobID\n" +
            "  order by id desc",nativeQuery = true)
    List<tbl_crpt_orderbookingmaster> findSUZStatus();

}
